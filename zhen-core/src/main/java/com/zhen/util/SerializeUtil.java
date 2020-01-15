package com.zhen.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName：SerializeUtil
 * @Description：
 * @Author：wuhengzhen
 * @Date：2019-10-25 14:34
 */
public class SerializeUtil {
    /**
     * logger
     */
    private transient static final Logger logger = LoggerFactory.getLogger(SerializeUtil.class);

    /**
     * 序列化 对象
     *
     * @param o
     * @return
     * @author tianya
     */
    public static byte[] serialize(Object o) {

        byte[] byteArray = null;

        try (ByteArrayOutputStream bty = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bty);) {
            oos.writeObject(o);
            byteArray = bty.toByteArray();
        } catch (Exception e) {
            logger.error("序列化失败！", e);
        }

        return byteArray;
    }


    /**
     * 反序列化 对象
     *
     * @param bytes
     * @return
     * @author tianya
     */
    public static Object unserialize(byte[] bytes) {

        Object o = null;

        try (ByteArrayInputStream bai = new ByteArrayInputStream(bytes);
             ObjectInputStream ois = new ObjectInputStream(bai)) {
            o = ois.readObject();
        } catch (Exception e) {
            logger.error("反序列化失败！", e);
        }
        return o;
    }
}
