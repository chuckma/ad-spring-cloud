package cn.lucas.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdUnitResponse {

    // 主键
    private Long id;
    private String unitName;

}
