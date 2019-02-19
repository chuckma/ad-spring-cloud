package cn.lucas.ad.mysql.listener;

import cn.lucas.ad.mysql.TemplateHolder;
import cn.lucas.ad.mysql.dto.BinlogRowData;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * <p>
 * 收集聚合 mysql binlog
 * 实现对 binlog 的监听 并获取对表有兴趣的监听器，
 */
@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    /**
     * binlog 一定包含 dbname
     */
    private String dbName;

    /**
     * binlog 里的 tableName
     */
    private String tableName;

    /**
     * 1. binlog 解析出来的数据对象 BinlogRowData 需要给其一些处理方法
     * 2. 同一个监听器可以多次实现注册不同的数据库，数据表
     * 3. 由于数据库的每张表可以有不同的处理方法 ，所以可以用 map
     * 4. key->String 对应一张表，value->Ilistener 对应处理方法
     */
    private Map<String, Ilistener> ilistenerMap = new HashMap<>();

    private final TemplateHolder templateHolder;

    @Autowired
    public AggregationListener(TemplateHolder templateHolder) {
        this.templateHolder = templateHolder;
    }

    /**
     * 定义一个可以产生 ilistenerMap 的key
     * @param dbName
     * @param tableName
     * @return key
     */
    private String getKey(String dbName,String tableName) {
        return dbName + ":" + tableName;
    }

    /**
     * 注册监听器
     * @param dbName
     * @param tableName
     * @param ilistener
     */
    public void register(String dbName, String tableName, Ilistener ilistener) {
        // 对 tableName 实现了注册
        log.info("register : {}-{}", dbName, tableName);
        this.ilistenerMap.put(getKey(dbName, tableName), ilistener);
    }

    @Override
    public void onEvent(Event event) {
        // 目的是将 event 解析成 BinlogRowData，然后把 BinlogRowData 传递给 listener 实现增量数据的更新

        EventType type = event.getHeader().getEventType();
        log.debug("event type : {}", type);
        if (type == EventType.TABLE_MAP) {
             // table_map 里包含了数据库 已经数据表的名字
            TableMapEventData data = event.getData();
            this.tableName = data.getTable();
            this.dbName = data.getDatabase();
            // 接下来的一条binlog 是真正的处理方法，所以这里返回
            return;
        }

        /**
         * 如果是不是这三种就直接返回，因为增量的操作一定是对数据的 更新 写入 删除
         * 注意 EventType 这里和 mysql 的版本有关系
         */
        if (type != EventType.EXT_UPDATE_ROWS
                && type != EventType.EXT_WRITE_ROWS
                && type != EventType.EXT_DELETE_ROWS) {
            return;
        }

        // 表名和库名是否已经完成了填充
        if (StringUtils.isEmpty(dbName) || StringUtils.isEmpty(tableName)) {
            log.error("no meta data event");
            return;
        }

        // 找出对应表有兴趣的监听器 [之前 register 完成了注册 对应数据表的 listener ]
        String key = getKey(this.dbName, this.tableName);
        Ilistener ilistener = this.ilistenerMap.get(key);
        if (null == ilistener) {
            // 如果监听器为空
            log.debug("skip {}", key);
            return;
        }
        log.info("trigger event : {}", type.name());
        try {
            // 最终目的是将 event 里的 data 转变为 BinlogRowData
            BinlogRowData rowData = buildRowData(event.getData());
            if (rowData == null) {
                return;
            }

            rowData.setType(type);
            // 将转换完成的 binlogRowData 交给 listener 处理
            // 对检索服务实现来说 就是增量数据的更新
            ilistener.onEvent(rowData);

        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }finally {
            // 当处理完了一条事件之后，需要将 dbName 和  tableName 清空
            this.dbName = "";
            this.tableName = "";
        }



    }

    private BinlogRowData buildRowData(EventData eventData) {
        return null;
    }
}
