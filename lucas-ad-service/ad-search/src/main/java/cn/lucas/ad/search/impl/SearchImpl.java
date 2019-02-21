package cn.lucas.ad.search.impl;

import cn.lucas.ad.index.CommonStatus;
import cn.lucas.ad.index.DataTable;
import cn.lucas.ad.index.adunit.AdUnitIndex;
import cn.lucas.ad.index.adunit.AdUnitObject;
import cn.lucas.ad.index.creative.CreativeIndex;
import cn.lucas.ad.index.creative.CreativeObject;
import cn.lucas.ad.index.creativeunit.CreativeUnitIndex;
import cn.lucas.ad.index.district.UnitDistrictIndex;
import cn.lucas.ad.index.interest.UnitItIndex;
import cn.lucas.ad.index.keyword.UnitKeywordIndex;
import cn.lucas.ad.search.ISearch;
import cn.lucas.ad.search.vo.SearchRequest;
import cn.lucas.ad.search.vo.SearchResponse;
import cn.lucas.ad.search.vo.feature.DistrictFeature;
import cn.lucas.ad.search.vo.feature.FeatureRelation;
import cn.lucas.ad.search.vo.feature.ItFeature;
import cn.lucas.ad.search.vo.feature.KeywordFeature;
import cn.lucas.ad.search.vo.media.AdSlot;
import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.omg.CORBA.INTERNAL;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Administrator
 */
@Slf4j
@Component
public class SearchImpl implements ISearch {

    /**
     * fetchAds 方法抛出异常时候，会执行这里
     * 在 应用启动注解中 定义了一个 @EnableCircuitBreaker 它会通过 AOP 拦截 @HystrixCommand 的方法
     * @param request
     * @param throwable
     * @return
     */
    public SearchResponse fallBack(SearchRequest request, Throwable throwable) {
        return null;
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallBack")
    public SearchResponse fetchAds(SearchRequest request) {
        // 请求的广告位信息
        List<AdSlot> adSlots = request.getRequestInfo().getAdSlots();

        // 3 个 Feature
        KeywordFeature keywordFeature = request.getFeatureInfo().getKeywordFeature();
        DistrictFeature districtFeature = request.getFeatureInfo().getDistrictFeature();
        ItFeature itFeature = request.getFeatureInfo().getItFeature();
        FeatureRelation relation = request.getFeatureInfo().getRelation();


        // 构造响应对象
        SearchResponse response = new SearchResponse();
        Map<String, List<SearchResponse.Creative>> adSlot2Ads =
                response.getAdSlot2Ads();

        for (AdSlot adSlot : adSlots) {
            Set<Long> targetUnitIdSet;
            // 根据流量类型获取初识的 AdUnit
            // TODO
            Set<Long> adUnitIdSet = DataTable.of(AdUnitIndex.class).match(adSlot.getPositionType());
            if (relation == FeatureRelation.AND) {
                filterKeywordFeature(adUnitIdSet, keywordFeature);
                filterDistrictFeature(adUnitIdSet, districtFeature);
                filterItTagFeature(adUnitIdSet, itFeature);

                targetUnitIdSet = adUnitIdSet;
            } else {
                targetUnitIdSet = getORRelationUnitIds(adUnitIdSet, keywordFeature, districtFeature, itFeature);
            }
            List<AdUnitObject> unitObjects = DataTable.of(AdUnitIndex.class).fetch(targetUnitIdSet);
            filterAdUnitAndPlanStatus(unitObjects, CommonStatus.VALID);
            List<Long> adIds = DataTable.of(CreativeUnitIndex.class).selectAds(unitObjects);
            List<CreativeObject> creatives = DataTable.of(CreativeIndex.class).fetch(adIds);

            // 通过 AdSlot 实现对 CreativeObject 的过滤
            filterCreativeByAdSlot(creatives,
                    adSlot.getWidth(),
                    adSlot.getHeight(),
                    adSlot.getType()
            );
            adSlot2Ads.put(adSlot.getAdSlotCode(), bulidCreativeResponse(creatives));
        }
        log.info("fetchAds :{} - {} ", JSON.toJSONString(request), JSON.toJSONString(response));
        return response;
    }

    private Set<Long> getORRelationUnitIds(Set<Long> adUnitIdSet,
                                           KeywordFeature keywordFeature,
                                           DistrictFeature districtFeature,
                                           ItFeature itFeature) {
        if (CollectionUtils.isEmpty(adUnitIdSet)) {
            return Collections.EMPTY_SET;
        }
        Set<Long> keywordUnitIdSet = new HashSet<>(adUnitIdSet);
        Set<Long> districtUnitIdSet = new HashSet<>(adUnitIdSet);
        Set<Long> itUnitIdSet = new HashSet<>(adUnitIdSet);


        filterKeywordFeature(keywordUnitIdSet, keywordFeature);
        filterDistrictFeature(districtUnitIdSet, districtFeature);
        filterItTagFeature(itUnitIdSet, itFeature);

        // TODO 11-5
        // 并集合处理
        return new HashSet<>(
                CollectionUtils.union(
                        CollectionUtils.union(keywordUnitIdSet, districtUnitIdSet), itUnitIdSet
                )
        );
    }

    private void filterKeywordFeature(Collection<Long> adUnitIds, KeywordFeature keywordFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }
        if (CollectionUtils.isNotEmpty(keywordFeature.getKeywords())) {
            // TODO 11-5
            // for 循环过滤
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId -> DataTable.of(UnitKeywordIndex.class).match(adUnitId, keywordFeature.getKeywords())
            );
        }
    }

    private void filterDistrictFeature(Collection<Long> adUnitIds, DistrictFeature districtFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }

        if (CollectionUtils.isNotEmpty(districtFeature.getDistricts())) {
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId -> DataTable.of(UnitDistrictIndex.class).match(adUnitId, districtFeature.getDistricts())
            );
        }
    }


    private void filterItTagFeature(Collection<Long> adUnitIds, ItFeature itFeature) {
        if (CollectionUtils.isEmpty(adUnitIds)) {
            return;
        }

        if (CollectionUtils.isNotEmpty(itFeature.getIts())) {
            CollectionUtils.filter(
                    adUnitIds,
                    adUnitId -> DataTable.of(UnitItIndex.class).match(adUnitId, itFeature.getIts())
            );
        }
    }

    /**
     * 根据推广单元和所关联的推广计划的状态  实现对 unitObjects 的过滤
     *
     * @param unitObjects
     * @param status
     */
    private void filterAdUnitAndPlanStatus(List<AdUnitObject> unitObjects,
                                           CommonStatus status) {
        if (CollectionUtils.isEmpty(unitObjects)) {
            return;
        }
        CollectionUtils.filter(
                unitObjects,
                object -> object.getUnitStatus().equals(status.getStatus())
                        && object.getAdPlanObject().getPlanStatus().equals(status.getStatus())
        );

    }


    private void filterCreativeByAdSlot(List<CreativeObject> creativeObjects,
                                        Integer widht,
                                        Integer height,
                                        List<Integer> type) {

        if (CollectionUtils.isEmpty(creativeObjects)) {
            return;
        }

        CollectionUtils.filter(
                creativeObjects,
                creative -> creative.getAuditStatus().equals(CommonStatus.VALID.getStatus())
                        && creative.getWidth().equals(widht)
                        && creative.getHeight().equals(height)
                        && type.contains(creative.getType())
        );
    }


    /**
     * // 随机获取一个 Creative 对象 ,根据需求可以自行设定 排序
     * @param creatives
     * @return
     */
    private List<SearchResponse.Creative> bulidCreativeResponse(List<CreativeObject> creatives) {
        if (CollectionUtils.isEmpty(creatives)) {
            return Collections.emptyList();
        }
        CreativeObject randomObject = creatives.get(
                Math.abs(new Random().nextInt()) % creatives.size()
        );

        return Collections.singletonList(SearchResponse.convert(randomObject));
    }
}
