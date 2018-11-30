package com.zhen.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author : wuhengzhen
 * @Description : 异常工具类
 * @date : 2018/09/11 14:05
 * @system name:
 * @copyright:
 */
public class ExceptionUtil {

    /**
     * 获取异常的堆栈信息
     *
     * @param t
     * @return
     */
    public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            t.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }

}
