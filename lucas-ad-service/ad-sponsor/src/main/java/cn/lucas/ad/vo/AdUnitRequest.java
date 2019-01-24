package cn.lucas.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * @author mcg
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitRequest {

    // 推广计划id 每一个推广单元都需要一个推广计划
    private Long planId;
    private String unitName;
    private Integer positionType;
    private Long budget;

    /**
     *  简单的校验
     * @return
     */
    public boolean createValidate() {
        return null != planId
                && !StringUtils.isEmpty(unitName)
                && positionType != null
                && budget != null;
    }

}
