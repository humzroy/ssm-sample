package com.zhen.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：MAP操作工具类
 * Auth：wuhengzhen
 * Date：2018-10-23
 * Time：10:33
 */
public class MapUtil {
    /**
     * description :将Map里面的key转换为驼峰命名
     * author : wuhengzhen
     * date : 2018-10-23 10:33
     */
    public static Map<String, Object> handleMapKey(Map map) {
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        Map<String, Object> utilMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : entries) {

            String key = entry.getKey();
            Object value = entry.getValue();
            StringBuilder sb = new StringBuilder();
            //将key值全部转为小写
            String lowKey = key.toLowerCase();
            if (lowKey.indexOf("_") > 0) {
                String[] lowKeys = lowKey.split("_");
                sb.append(lowKeys[0]);
                for (int i = 1; i < lowKeys.length; i++) {
                    sb.append(lowKeys[i].substring(0, 1).toUpperCase().concat(lowKeys[i].substring(1).toLowerCase()));
                }
                utilMap.put(sb.toString(), value);
            } else {
                utilMap.put(lowKey, value);
            }

        }
        return utilMap;
    }
}
