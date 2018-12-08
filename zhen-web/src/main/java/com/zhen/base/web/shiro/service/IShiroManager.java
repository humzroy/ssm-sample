package com.zhen.base.web.shiro.service;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：shiro
 * Author：wuhengzhen
 * Date：2018-12-03
 * Time：21:14
 */
public interface IShiroManager {
    /**
     * description :加载shiro过滤配置信息
     * author : wuhengzhen
     * date : 2018-12-3 21:16
     */
    public String loadFilterChainDefinitions();

    /**
     * description :加载shiro过滤配置信息
     * author : wuhengzhen
     * date : 2018-12-3 21:17
     */
    public String loadFilterChainDefinitions(String type);
}
