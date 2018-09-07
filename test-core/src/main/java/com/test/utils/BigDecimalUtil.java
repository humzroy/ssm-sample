package com.test.utils;

import java.math.BigDecimal;

/**
 * @Description:BigDecimal 工具类
 * @author: wuhengzhen
 * @date: 2018/4/17 16:22
 * @system name:
 * @copyright:
 */
public class BigDecimalUtil {
    /**
     * 加法
     *
     * @param d1
     * @param d2
     * @param scale
     * @param roundMode
     * @return
     */
    public static double add(double d1, double d2, int scale, int roundMode) {
        double d = 0;
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        d = bd1.add(bd2).setScale(scale, roundMode).doubleValue();
        return d;
    }

    /**
     * 减法
     *
     * @param d1
     * @param d2
     * @param scale
     * @param roundMode
     * @return
     */
    public static double sub(double d1, double d2, int scale, int roundMode) {
        double d = 0;
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        d = bd1.subtract(bd2).setScale(scale, roundMode).doubleValue();
        return d;
    }

    /**
     * 乘法
     *
     * @param d1
     * @param d2
     * @param scale
     * @param roundMode
     * @return
     */
    public static double mul(double d1, double d2, int scale, int roundMode) {
        double d = 0;
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        d = bd1.multiply(bd2).setScale(scale, roundMode).doubleValue();
        return d;
    }

    /**
     * 除法
     *
     * @param d1
     * @param d2
     * @param scale
     * @param roundMode
     * @return
     * @throws Exception
     */
    public static double div(double d1, double d2, int scale, int roundMode) throws Exception {
        double d = 0;
        BigDecimal bd1 = new BigDecimal(Double.toString(d1));
        BigDecimal bd2 = new BigDecimal(Double.toString(d2));
        d = bd1.divide(bd2, scale, roundMode).doubleValue();
        return d;
    }

    /**
     * 如果Object为null则返回0，反之返回 BigDecimal o
     */
    public static BigDecimal checkNull(Object o) {
        BigDecimal result = new BigDecimal(0);
        if (o != null) {
            if (o instanceof BigDecimal) {
                return (new BigDecimal(o.toString()));
            }
        }
        return result;
    }
}
