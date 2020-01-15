package com.zhen.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : wuhengzhen
 * @Description : 简单封装alibaba产出的json处理框架 (JSON parser + JSON generator)
 * @date : 2018/05/03 10:50
 */
public class JsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

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

    /**
     * json字符串转Map
     *
     * @param jsonStr json字符串
     * @return
     */
    public static Map jsonToMap(String jsonStr) {
        if (StringUtil.isEmpty(jsonStr)) {
            return null;
        }
        Map map = new HashMap();
        try {
            map = JSONObject.parseObject(jsonStr, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将json数组转化为String型
     *
     * @param str
     * @return
     */
    public static String[] getJsonToStringArray(String str) {
        JSONArray jsonArray = JSONArray.fromObject(str);
        String[] arr = new String[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            arr[i] = jsonArray.getString(i);
        }
        return arr;
    }

    public static void main(String[] args) {

        /*
         *json解析时自动判断是object还是array
         */
        String json = "{\"scm\":[{\"key1\":\"vlaue1\",\"key2\":\"vlaue2\"},{\"key11\":\"vlaue11\",\"key22\":\"vlaue22\"}]}";
        Map map = jsonToMap(json);
        System.out.println(map);
        // JSONObject jsonObject = JSONObject.parseObject(json);
        // Object object = new JSONTokener(jsonObject.getString("scm")).nextValue();
        // if (object instanceof net.sf.json.JSONArray) {
        //     net.sf.json.JSONArray jsonArray = (net.sf.json.JSONArray) object;
        //     for (int k = 0; k < jsonArray.size(); k++) {
        //         net.sf.json.JSONObject parameterObject = jsonArray.getJSONObject(k);
        //         System.out.println(parameterObject);
        //     }
        // } else if (object instanceof net.sf.json.JSONObject) {
        //     net.sf.json.JSONObject jsonObject3 = (net.sf.json.JSONObject) object;
        //     System.out.println(jsonObject3);
        // }
    }
}
