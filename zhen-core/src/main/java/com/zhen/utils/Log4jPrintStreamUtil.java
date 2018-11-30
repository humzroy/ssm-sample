package com.zhen.utils;

import org.apache.log4j.Logger;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author : wuhengzhen
 * @Description :将System.out内容输出到LOG文件
 * @date : 2018/06/08 16:06
 * @system name:
 * @copyright:
 */
public class Log4jPrintStreamUtil extends PrintStream {
    private final Logger log = Logger.getLogger("SystemOut");
    private static PrintStream instance = new Log4jPrintStreamUtil(System.out);

    private Log4jPrintStreamUtil(OutputStream out) {
        super(out);
    }

    public static void redirectSystemOut() {
        System.setOut(instance);
    }

    @Override
    public void print(boolean b) {
        println(b);
    }

    @Override
    public void print(char c) {
        println(c);
    }

    @Override
    public void print(char[] s) {
        println(s);
    }

    @Override
    public void print(double d) {
        println(d);
    }

    @Override
    public void print(float f) {
        println(f);
    }

    @Override
    public void print(int i) {
        println(i);
    }

    @Override
    public void print(long l) {
        println(l);
    }

    @Override
    public void print(Object obj) {
        println(obj);
    }

    @Override
    public void print(String s) {
        println(s);
    }

    @Override
    public void println(boolean x) {
        log.info(x);
    }

    @Override
    public void println(char x) {
        log.info(x);
    }

    @Override
    public void println(char[] x) {
        log.info(x == null ? null : new String(x));
    }

    @Override
    public void println(double x) {
        log.info(x);
    }

    @Override
    public void println(float x) {
        log.info(x);
    }

    @Override
    public void println(int x) {
        log.info(x);
    }

    @Override
    public void println(long x) {
        log.info(x);
    }

    @Override
    public void println(Object x) {
        log.info(x);
    }

    @Override
    public void println(String x) {
        log.info(x);
    }
}
