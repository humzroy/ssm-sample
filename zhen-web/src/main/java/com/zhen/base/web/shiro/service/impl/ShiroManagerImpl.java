package com.zhen.base.web.shiro.service.impl;

import com.zhen.base.web.shiro.service.IShiroManager;
import com.zhen.util.INI4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Set;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：shiro
 * Author：wuhengzhen
 * Date：2018-12-03
 * Time：21:14
 */
public class ShiroManagerImpl implements IShiroManager {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ShiroManagerImpl.class);
    /**
     * 默认权限配置
     */
    public static final String AUTHO_BY_DEFAULT = "default";
    /**
     * 总的权限配置
     */
    public static final String AUTHO_TYPES = AUTHO_BY_DEFAULT;
    /**
     * 注意/r/n前不能有空格
     */
    private static final String CRLF = "\r\n";

    /**
     * description :加载shiro过滤器
     * author : wuhengzhen
     * date : 2018-12-3 21:16
     */
    @Override
    public String loadFilterChainDefinitions() {
        return loadFilterChainDefinitions(AUTHO_BY_DEFAULT);
    }

    /**
     * description :加载shiro过滤配置信息
     * author : wuhengzhen
     * date : 2018-12-3 21:17
     *
     * @param type
     */
    @Override
    public String loadFilterChainDefinitions(String type) {
        //固定权限，采用读取配置文件
        return getFixedAuthRule(type);
    }

    /**
     * 读取配置文件
     *
     * @param type 类型
     * @return
     */
    private String getFixedAuthRule(String type) {
        if (!AUTHO_TYPES.contains(type)) {
            logger.info("the autho type is wrong :{}", type);
            return null;
        }
        String fileName = type + "_shiro_base_auth.ini";
        ClassPathResource cp = new ClassPathResource(fileName);
        INI4j ini = null;
        try {
            ini = new INI4j(cp.getFile());
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("加载文件出错。file:[%s]", fileName);
        }
        String section = "base_auth";
        Set<String> keys = ini.get(section).keySet();
        StringBuilder sb = new StringBuilder();
        for (String key : keys) {
            String value = ini.get(section, key);
            sb.append(key).append(" = ")
                    .append(value).append(CRLF);
        }
        logger.info("the auth chain: \n {}", sb.toString());
        return sb.toString();
    }
}
