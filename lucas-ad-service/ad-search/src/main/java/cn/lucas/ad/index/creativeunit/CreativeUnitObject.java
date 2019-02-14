package cn.lucas.ad.index.creativeunit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author mcg
 * @Date 2019/2/14 19:47
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUnitObject {

    private Long adId;
    private Long unitId;

    // key 为 adId-unitId 连字符组合
}
