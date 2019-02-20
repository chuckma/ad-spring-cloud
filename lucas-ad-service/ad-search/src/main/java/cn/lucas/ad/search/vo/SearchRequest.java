package cn.lucas.ad.search.vo;

import cn.lucas.ad.search.vo.feature.DistrictFeature;
import cn.lucas.ad.search.vo.feature.FeatureRelation;
import cn.lucas.ad.search.vo.feature.ItFeature;
import cn.lucas.ad.search.vo.feature.KeywordFeature;
import cn.lucas.ad.search.vo.media.AdSlot;
import cn.lucas.ad.search.vo.media.App;
import cn.lucas.ad.search.vo.media.Device;
import cn.lucas.ad.search.vo.media.Geo;
import com.sun.org.apache.regexp.internal.RE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    /**
     * 媒体方的请求标识
     */
    private String mediaId;

    /**
     * 请求基本信息
     */
    private RequestInfo requestInfo;

    /**
     * 匹配信息
     */
    private FeatureInfo featureInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestInfo {
        private String requestId;

        /**
         * 广告位信息
         */
        private List<AdSlot> adSlots;
        private App app;
        private Geo geo;
        private Device device;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeatureInfo{
        private KeywordFeature keywordFeature;
        private DistrictFeature districtFeature;
        private ItFeature itFeature;

        // 默认是都需要满足匹配条件
        private FeatureRelation relation = FeatureRelation.AND;
    }
}
