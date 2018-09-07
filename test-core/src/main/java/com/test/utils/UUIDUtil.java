package com.test.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * @author : wuhengzhen
 * @Description : UUID工具类
 * @date : 2018/09/07 16:54
 * @system name:
 * @copyright:
 */
public class UUIDUtil {
    /**
     * @description :获得一个小写UUID 32位 例如：06a5f6f79bdc4ff58f1841080b93dc66
     * @author : wuhengzhen
     * @date : 2018/3/22 16:18
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 获得一个大写的UUID 32位
     * 例如：8BEB3F6132BC43BC85A6F7D20E9CCD3F
     *
     * @return
     */
    public static String getUppercaseUUID() {
        return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
    }

    /**
     * 生成32位随机数UNID（16位本机标识码+8位时间戳+8位随机数）
     * 例如：0000AAFD7A79BE86B1D6B47752909B8E
     *
     * @return String UNID标识符
     */
    public String getUnid() {
        StringBuilder buf = new StringBuilder();
        SecureRandom seeder = new SecureRandom();
        long time = System.currentTimeMillis();
        // 生成当前时间戳
        int timeLow = (int) time;
        // 生成随机数
        int node = seeder.nextInt();
        String midString = null;
        try {
            InetAddress inet = InetAddress.getLocalHost();
            byte[] bytes = inet.getAddress();
            String hexAddress = hexFormat(getInt(bytes), 8);
            String hash = hexFormat(System.identityHashCode(this), 8);
            // 本机标识码
            midString = hexAddress + hash;
        } catch (UnknownHostException ignored) {

        }

        if (midString == null) {
            midString = "0000000000000000";
        }

        buf.append(midString).append(hexFormat(timeLow, 8)).append(hexFormat(node, 8));
        return buf.toString();
    }

    /**
     * 获得16位的UNID
     * 例如：B1D6B47CD1893CD7
     *
     * @return
     */
    public static String getUNID() {
        SecureRandom seeder = new SecureRandom();
        StringBuilder buf = new StringBuilder();
        long time = System.currentTimeMillis();
        int timeLow = (int) time;
        int node = seeder.nextInt();
        buf.append(hexFormat(timeLow, 8)).append(hexFormat(node, 8));
        return buf.toString();
    }

    /**
     * @description :获得指定数目的UUID
     * @author : wuhengzhen
     * @date : 2018/3/22 16:18
     */
    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUUID();
        }
        return ss;
    }

    /**
     * 转成16进制格式
     *
     * @param number
     * @param digits
     * @return
     */
    private static String hexFormat(int number, int digits) {
        String hex = Integer.toHexString(number).toUpperCase();
        if (hex.length() >= digits) {
            return hex.substring(0, digits);
        } else {
            int padding = digits - hex.length();
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < padding; ++i) {
                buf.append("0");
            }
            buf.append(hex);
            return buf.toString();
        }
    }

    /**
     * @param bytes
     * @return
     */
    private int getInt(byte[] bytes) {
        int size = (bytes.length > 32) ? 32 : bytes.length;
        int result = 0;
        for (int i = size - 1; i >= 0; i--) {
            if (i == (size - 1)) {
                result += bytes[i];
            } else {
                result += (bytes[i] << 4 * (size - 1 - i));
            }
        }
        return result;
    }

}
