package cn.lucas.ad.index.adplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author mcg
 * <p>
 * adplan 索引对象
 * 包含 adplan 的基本信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanObject {

    /**
     * 只需要给检索时需要的字段添加索引
     * 我们会把数据表的实体类转换成一个索引对象
     */

    private Long planId;
    private Long userId;
    private Integer planStatus;
    private Date startDate;
    private Date endDate;


    /**
     * 更新索引
     */
     void update(AdPlanObject newObject) {
        if (null != newObject.getPlanId()) {
            this.planId = newObject.getPlanId();
        }

        if (null != newObject.getUserId()) {
            this.userId = newObject.getUserId();
        }

        if (null != newObject.planStatus) {
            this.planStatus = newObject.getPlanStatus();
        }

        if (null != newObject.getStartDate()) {
            this.startDate = newObject.getStartDate();
        }

        if (null != newObject.getEndDate()) {
            this.endDate = newObject.getEndDate();
        }
    }
}
