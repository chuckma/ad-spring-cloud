package cn.lucas.ad.mysql.listener;

import cn.lucas.ad.mysql.constant.Constant;
import cn.lucas.ad.mysql.constant.OpType;
import cn.lucas.ad.mysql.dto.BinlogRowData;
import cn.lucas.ad.mysql.dto.MySqlRowData;
import cn.lucas.ad.mysql.dto.TableTemplate;
import cn.lucas.ad.sender.ISender;
import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * <p>
 * 增量数据处理器
 */
@Slf4j
@Component
public class IncrementListener implements Ilistener {

    @Resource(name = "")
    private ISender iSender;
    private final AggregationListener aggregationListener;

    @Autowired
    public IncrementListener(AggregationListener aggregationListener) {
        this.aggregationListener = aggregationListener;
    }

    /**
     * 将表注册
     * 这里所有的表都注册同一个 listener  IncrementListener
     * <p>
     * 给各个表注册处理器 发生在 lisrtener 实例化的时候 放在 String 容器时候就应该执行 register 实现注册
     */
    @Override
    @PostConstruct
    public void register() {
        log.info("IncrementListener register db and table info");
        Constant.table2Db.forEach(k, v)->aggregationListener.register(v, k, this);

    }

    /**
     * // 将 BinlogRowData 转换成 MySqlRowData 然后投放出去
     *
     * @param eventData
     */
    @Override
    public void onEvent(BinlogRowData eventData) {
        TableTemplate table = eventData.getTable();
        EventType eventType = eventData.getType();

        // 包装成最后需要投递的数据 MySqlRowData
        MySqlRowData rowData = new MySqlRowData();
        rowData.setTableName(table.getTableName());
        rowData.setLevel(eventData.getTable().getLevel());
        OpType opType = OpType.to(eventType);
        rowData.setOpType(opType);

        // 取出模板中该操作对应的字段列表
        List<String> fieldList = table.getOpTypeFieldSetMap().get(opType);
        if (null == fieldList) {
            log.warn("{} not support for {}", opType, table.getTableName());
            return;
        }

        for (Map<String, String> map : eventData.getAfter()) {
            Map<String, String> afterMap = new HashMap<>();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String colName = entry.getKey();
                String colValue = entry.getValue();
                afterMap.put(colName, colValue);
            }
            rowData.getFieldValueMap().add(afterMap);
        }

        // 投递 增量数据
        iSender.sender(rowData);

    }
}
