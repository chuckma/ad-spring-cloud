package cn.lucas.ad.mysql.dto;

import cn.lucas.ad.mysql.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySqlRowData {


    private String tableName;
    /**
     * 数据表层级关系
     */
    private String level;

    private OpType opType;

    private List<Map<String, String>> fieldValueMap = new ArrayList<>();

}
