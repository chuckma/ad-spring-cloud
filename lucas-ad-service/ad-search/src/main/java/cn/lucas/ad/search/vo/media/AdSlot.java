package cn.lucas.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Administrator
 *
 * 广告位信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdSlot {

    /**
     * 广告位编码
     */
    private String adSlotCode;

    /**
     * 流量类型
     */
    private Integer positionType;

    private Integer width;

    private Integer height;

    /**
     * 广告物料类型 eg. 图片，视频... 支持多类型
     */
    private List<Integer> type;

    /**
     * 最低出价
     */
    private Integer minCpm;

}
