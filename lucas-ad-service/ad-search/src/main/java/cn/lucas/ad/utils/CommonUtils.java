package cn.lucas.ad.utils;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @Author mcg
 * @Date 2019/1/29 21:45
 **/

public class CommonUtils {


    public static <K, V> V getOrCreate(K key, Map<K, V> map, Supplier<V> factory) {

        return map.computeIfAbsent(key, k -> factory.get());
    }
}
