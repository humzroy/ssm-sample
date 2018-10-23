package com.test.utils;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：自定义HashMap(用于查询结果列名的转换)
 * Auth：wuhengzhen
 * Date：2018-10-23
 * Time：10:34
 */
public final class ExtHashMap<K, V> extends HashMap<K, V> {
    public ExtHashMap() {
        super();
    }

    public ExtHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    public ExtHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    @SuppressWarnings("unchecked")
    @Override
    public V put(K key, V value) {
        if (key instanceof String) {
            String pamar = (String) key;
            if (pamar.indexOf("_") > 0) {
                StringBuilder sb = new StringBuilder();
                String lowKey = pamar.toLowerCase();
                String[] lowKeys = lowKey.split("_");
                sb.append(lowKeys[0]);
                for (int i = 1; i < lowKeys.length; i++) {
                    sb.append(lowKeys[i].substring(0, 1).toUpperCase().concat(lowKeys[i].substring(1).toLowerCase()));
                }
                String result = sb.toString();
                return super.put((K) result, value);
            } else {
                if (ExtHashMap.isUppercase(pamar)) {
                    String lowKey = pamar.toLowerCase();
                    return super.put((K) lowKey, value);
                } else {
                    return super.put(key, value);
                }
            }

        }
        return super.put(key, value);
    }

    /**
     * description :判断第一个字符是否为大写
     * author : wuhengzhen
     * date : 2018-10-23 10:35
     */
    private static boolean isUppercase(String str) {
        char c = str.charAt(0);
        return Character.isUpperCase(c);
    }
}
