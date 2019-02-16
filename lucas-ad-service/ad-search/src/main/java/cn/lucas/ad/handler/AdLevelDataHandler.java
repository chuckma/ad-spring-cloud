package cn.lucas.ad.handler;

import cn.lucas.ad.index.IndexAware;
import cn.lucas.ad.mysql.constant.OpType;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 * <p>
 * 1. 索引之间存在着层级的划分，也就是依赖关系的划分
 * 2. 加载全量索引是增量索引 "添加" 的一种特殊实现
 */
@Slf4j
public class AdLevelDataHandler {

    private static <K, V> void handlerBinlogEvent(
            IndexAware<K, V> index,
            K key,
            V value,
            OpType type) {
        switch (type) {
            case ADD:
                index.add(key, value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;
            default:
                break;

        }

    }
}
