package com.zhen.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @ClassName Configutil
 * @Description 配置文件
 * @Author wuhengzhen
 * @Date 2019-11-26 10:14
 * @Version 1.0
 */
public class Configutil {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(Configutil.class);

    /**
     * 环境配置参数
     */
    private static final String ACTIVE_PROFILE_PROPERTY = "spring.profiles.active";

    private static String fileName;

    /**
     * 初始化配置文件名称
     */
    public void initConfigFileName() {
        //获取当前系统的使用的环境
        String env = System.getProperty(ACTIVE_PROFILE_PROPERTY);
        logger.info("当前系统环境：{}", env);
        if (StringUtil.isEmpty(env)) {
            fileName = "config.properties";
            logger.info("active.profile is null!");
        } else {
            fileName = "config." + env + ".properties";
        }

    }


    /**
     * 根据key获取参数
     *
     * @param key
     * @return
     */
    public static String getProperty(String key) {
        FileInputStream inStream = null;
        InputStreamReader reader = null;
        String value = null;
        try {
            inStream = new FileInputStream(fileName);
            reader = new InputStreamReader(inStream, StandardCharsets.UTF_8);
            Properties prop = new Properties();
            prop.load(reader);
            value = prop.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
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
        return value;

    }


}
