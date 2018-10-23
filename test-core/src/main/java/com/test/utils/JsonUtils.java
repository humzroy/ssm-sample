package com.test.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * @author : wuhengzhen
 * @Description : 简单封装alibaba产出的json处理框架 (JSON parser + JSON generator)
 * @date : 2018/05/03 10:50
 */
public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private static SerializeConfig mapping = new SerializeConfig();

    private static ObjectMapper objectMapper;

    static {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
    }

    /**
     * @param obj 对象模型
     * @return String 转换完毕的字符串
     * @title: obj2Json
     * @description : 将对象转换为JSON字符串
     */
    public static String obj2Json(Object obj) {
        String str = null;
        try {
            str = JSON.toJSONString(obj, mapping, SerializerFeature.WriteMapNullValue);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return str;
    }

    /**
     * @param json  需要转换的字符串
     * @param clazz 转换时使用的实体类型
     * @param <T>
     * @return Object
     * @title: json2Obj
     * @description : 将JSON字符串转换为对象
     */
    public static <T> T json2Obj(String json, Class<T> clazz) {
        T obj = null;
        try {
            obj = JSON.parseObject(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return obj;
    }

    /**
     * map 转 json
     *
     * @param map
     * @return
     */
    public static String mapToJson(Map<String, Object> map) {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        try {
            return objectMapper.writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
