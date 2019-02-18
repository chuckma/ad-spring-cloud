package cn.lucas.ad.mysql.dto;

import cn.lucas.ad.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author mcg
 * @Date 2019/2/18 20:52
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableTemplate {
    private String tableName;
    private String level;

    // 操作类型 insert update delete
    private Map<OpType, List<String>> opTypeFieldSetMap = new HashMap<>();

    // 表示 字段索引 -> 字段名 的映射
    private Map<Integer, String> posMap = new HashMap<>();
}
