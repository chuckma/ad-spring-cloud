package cn.lucas.ad.mysql.dto;

import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 *
 *
 *  BinlogRowData 是为了将 binlog的日志数据，也就是 event 这个开源工具传递进来的 evnet
 *  将它定义成 java 对象
 */

@Data
public class BinlogRowData {

    /**
     * binlog 一定是对表的操作才有的数据，所以将表里包含的所有信息都包含
     */
    private TableTemplate table;

    /**
     * 操作类型
     */
    private EventType type;


    /**
     * 定义所有的已经变更的值为after
     * insert 和 delete 是没有 before 的，一定有after，就是更新之后的值
     *
     * key ->操作列的名字，value ->操作列的值
     */
    private List<Map<String,String >> after;

    /**
     * update 操作是有 before 的
     */
    private List<Map<String,String >> before;


}
