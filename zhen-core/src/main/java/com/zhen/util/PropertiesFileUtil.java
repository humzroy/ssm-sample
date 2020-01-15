package com.zhen.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 资源文件读取工具
 *
 * @author wuhengzhen
 * @date 2017年10月15日
 */
public class PropertiesFileUtil {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(PropertiesFileUtil.class);

    /**
     * 当打开多个资源文件时，缓存资源文件
     */
    private static HashMap<String, PropertiesFileUtil> configMap = new HashMap<String, PropertiesFileUtil>();
    /**
     * 打开文件时间，判断超时使用
     */
    private Date loadTime = null;
    /**
     * 资源文件
     */
    private ResourceBundle resourceBundle = null;
    /**
     * 默认资源文件名称
     */
    private static final String NAME = "config";
    // 缓存时间
    private static final Integer TIME_OUT = 60 * 1000;

    /**
     * 私有构造方法，创建单例
     *
     * @param name
     */
    private PropertiesFileUtil(String name) {
        this.loadTime = new Date();
        this.resourceBundle = ResourceBundle.getBundle(name);
    }

    public static synchronized PropertiesFileUtil getInstance() {
        return getInstance(NAME);
    }

    public static synchronized PropertiesFileUtil getInstance(String name) {
        PropertiesFileUtil conf = configMap.get(name);
        if (null == conf) {
            conf = new PropertiesFileUtil(name);
            configMap.put(name, conf);
        }
        // 判断是否打开的资源文件是否超时1分钟
        if ((System.currentTimeMillis() - conf.getLoadTime().getTime()) > TIME_OUT) {
            conf = new PropertiesFileUtil(name);
            configMap.put(name, conf);
        }
        return conf;
    }

    /**
     * 根据key读取value
     *
     * @param key key
     * @return value
     */
    public String get(String key) {
        try {
            String value = resourceBundle.getString(key);
            return value;
        } catch (MissingResourceException e) {
            return "";
        }
    }

    /**
     * 根据key读取value(整形)
     *
     * @param key key
     * @return value(Integer)
     */
    public Integer getInt(String key) {
        try {
            String value = resourceBundle.getString(key);
            return Integer.parseInt(value);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    /**
     * 根据key读取value(布尔)
     *
     * @param key key
     * @return value(boolean)
     */
    public boolean getBool(String key) {
        try {
            String value = resourceBundle.getString(key);
            if ("true".equals(value)) {
                return true;
            }
            return false;
        } catch (MissingResourceException e) {
            return false;
        }
    }

    public Date getLoadTime() {
        return loadTime;
    }

    /**
     * @param propertiesFilename
     * @param property
     * @param vm
     * @return
     */
    public static String readPropertiesFromfiles(String propertiesFilename, String property, String vm) {
        // 系统属性
        Properties props = System.getProperties();
        String confHome = props.getProperty(vm);
        logger.info(vm + ":" + confHome);
        Properties prop = new Properties();
        InputStream in = null;
        InputStreamReader reader = null;
        try {
            in = new FileInputStream(confHome + propertiesFilename);
            reader = new InputStreamReader(in, StandardCharsets.UTF_8);
            prop.load(reader);
            String value = prop.getProperty(property);
            logger.info(value);
            in.close();
            return value;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
