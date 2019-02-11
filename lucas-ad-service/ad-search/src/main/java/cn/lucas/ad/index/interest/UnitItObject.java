package cn.lucas.ad.index.interest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitItObject {

    // 推广单元关联id
    private Long unitId;

    // 兴趣标签
    private String itTag;
}
