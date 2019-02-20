package cn.lucas.ad.search.impl;

import cn.lucas.ad.index.DataTable;
import cn.lucas.ad.index.adunit.AdUnitIndex;
import cn.lucas.ad.search.ISearch;
import cn.lucas.ad.search.vo.SearchRequest;
import cn.lucas.ad.search.vo.SearchResponse;
import cn.lucas.ad.search.vo.feature.DistrictFeature;
import cn.lucas.ad.search.vo.feature.FeatureRelation;
import cn.lucas.ad.search.vo.feature.ItFeature;
import cn.lucas.ad.search.vo.feature.KeywordFeature;
import cn.lucas.ad.search.vo.media.AdSlot;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Administrator
 */
@Slf4j
@Component
public class SearchImpl implements ISearch {
    @Override
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
        }
        return null;
    }
}
