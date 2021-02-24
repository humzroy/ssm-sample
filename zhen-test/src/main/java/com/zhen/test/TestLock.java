package com.zhen.test;

/**
 * @author wuhengzhen
 * @date 2021/02/24 16:47
 **/

public class TestLock {


    /**
     * 非静态方法使用 synchronized 修饰的写法，修饰实例方法时，锁定的是当前对象：
     * 将 count 自减，从 5 到 0：
     */
    public synchronized void minus() {
        int count = 5;
        for (int i = 0; i < 5; i++) {
            count--;
            System.out.println(Thread.currentThread().getName() + " - " + count);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
    }

    public synchronized void minus2() {
        int count = 5;
        for (int i = 0; i < 5; i++) {
            count--;
            System.out.println(Thread.currentThread().getName() + " - " + count);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     *
     */
    public void minus3() {
        int count = 5;
        for (int i = 0; i < 5; i++) {
            count--;
            System.out.println(Thread.currentThread().getName() + " - " + count);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
    }


}