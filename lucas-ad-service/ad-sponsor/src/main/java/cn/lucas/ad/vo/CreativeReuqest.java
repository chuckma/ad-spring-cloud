package cn.lucas.ad.vo;

import cn.lucas.ad.constant.CommonStatus;
import cn.lucas.ad.entity.Creative;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeReuqest {

    // 创意名称
    private String name;

    // 创意主类型
    private Integer type;

    // 创意子类型
    private Integer materialType;

    // 高度
    private Integer height;

    // 宽度
    private Integer weight;

    // 大小
    private Long size;

    // 持续时长
    private Integer duration;

    private Long userId;

    private String url;


    public Creative convertToEntity() {
        Creative creative = new Creative();
        creative.setName(name);
        creative.setType(type);
        creative.setMaterialType(materialType);
        creative.setHeight(height);
        creative.setWidth(weight);
        creative.setSize(size);
        creative.setAuditStatus(CommonStatus.VALID.getStatus());
        creative.setDuration(duration);
        creative.setUserId(userId);
        creative.setUrl(url);
        creative.setCreateTime(new Date());
        creative.setUpdateTime(creative.getCreateTime());
        return creative;
    }

}
