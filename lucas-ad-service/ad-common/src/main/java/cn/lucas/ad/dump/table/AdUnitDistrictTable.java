package cn.lucas.ad.dump.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author mcg
 * @Date 2019/2/14 21:45
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitDistrictTable {


    private Long unitId;
    private String province;
    private String city;
}
