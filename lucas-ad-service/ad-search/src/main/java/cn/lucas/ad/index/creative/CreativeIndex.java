package cn.lucas.ad.index.creative;

import cn.lucas.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author mcg
 * @Date 2019/2/14 19:38
 *
 * 正向索引
 **/

@Slf4j
@Component
public class CreativeIndex implements IndexAware<Long, CreativeObject> {

    private static Map<Long, CreativeObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    /**
     * 11-6 5:00
     * @param adIds
     * @return
     */
    public List<CreativeObject> fetch(Collection<Long> adIds) {
        if (CollectionUtils.isEmpty(adIds)) {
            return Collections.emptyList();
        }

        List<CreativeObject> result = new ArrayList<>();
        adIds.forEach(u->{
            CreativeObject object = get(u);
            // 获取到的索引对象不存在
            if (null == object) {
                log.error("CreativeObject not found :{}", u);
                return;
            }
            result.add(object);
        });
        return result;
    }

    @Override
    public CreativeObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, CreativeObject value) {
        log.info("CreativeIndex before add : {}", objectMap);
        objectMap.put(key, value);
        log.info("CreativeIndex after add : {}", objectMap);

    }

    @Override
    public void update(Long key, CreativeObject value) {
        log.info("CreativeIndex before update : {}", objectMap);

        CreativeObject oldObject = objectMap.get(key);
        if (null == oldObject) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }

        log.info("CreativeIndex after update : {}", objectMap);

    }

    @Override
    public void delete(Long key, CreativeObject value) {
        log.info("CreativeIndex after delete : {}", objectMap);
        objectMap.remove(key);
        log.info("CreativeIndex after delete : {}", objectMap);
    }
}
