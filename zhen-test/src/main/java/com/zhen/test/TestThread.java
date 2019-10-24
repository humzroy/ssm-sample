package com.zhen.test;

import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName：TestThread
 * @Description：
 * @Author：wuhengzhen
 * @Date：2019-10-24 13:34
 */
public class TestThread implements Callable {



    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Object call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "线程开始！" + System.currentTimeMillis());
        return true;
    }
}
