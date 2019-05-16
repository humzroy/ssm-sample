package com.zhen.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：请求恒生接口参数工具类
 * Auth：wuhengzhen
 * Date：2019-04-23
 * Time：17:22
 */
public class HundsunReqParamUtil {
    private HundsunReqParamUtil() {
    }

    /**
     * description :创建请求恒生接口用的参数map，已包含公共参数merid
     * author : wuhengzhen
     * date : 2019/4/23 17:29
     */
    public static Map<String, Object> createReqMap() {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("merid", "001");
        return reqMap;
    }
}
