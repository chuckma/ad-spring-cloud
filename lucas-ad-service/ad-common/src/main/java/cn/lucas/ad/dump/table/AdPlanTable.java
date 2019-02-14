package cn.lucas.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author mcg
 * @Date 2019/2/14 20:38
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanTable {


    private Long id;
    private Long userId;
    private Integer planStatus;
    private Date startDate;
    private Date endDate;
}
