package cn.lucas.ad.search.vo;

import cn.lucas.ad.index.creative.CreativeObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.kafka.common.protocol.types.Field;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {

    /**
     * key adSlotCode 广告位编码
     */
    public Map<String, List<Creative>> adSlot2Ads = new HashMap<>();

    /**
     * 广告创意
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor

    public static class Creative {
        private Long adId;
        private String url;
        private Integer width;
        private Integer height;
        private Integer type;
        private Integer materialType;

        // 监测 点击 url 正常来说是和 创意（Creative）本身绑定的

        /**
         * 展示监测 url
         */
        private List<String> showMonitorUrl = Arrays.asList("www.baidu.com", "www.ali.com");

        /**
         * 点击监测 url
         */
        private List<String> clickMonitorUrl = Arrays.asList("www.baidu.com", "www.ali.com");
    }


    /**
     * 将索引对象装换成 Creative 对象
     */
    public static Creative convert(CreativeObject object) {
        Creative creative = new Creative();
        creative.setAdId(object.getAdId());
        creative.setUrl(object.getAdUrl());
        creative.setWidth(object.getWidth());
        creative.setHeight(object.getHeight());
        creative.setType(object.getType());
        creative.setMaterialType(object.getMaterialType());
        return creative;
    }

}
