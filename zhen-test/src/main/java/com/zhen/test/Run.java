package com.zhen.test;

/**
 * @author wuhengzhen
 * @date 2021/02/24 16:52
 **/

public class Run {


    /**
     * 某个线程得到了对象锁之后，该对象的其他同步方法是锁定的，其他线程是无法访问的
     *
     * @param args
     */
    // public static void main(String[] args) {
    //
    //     final TestLock test = new TestLock();
    //
    //     Thread thread1 = new Thread(new Runnable() {
    //
    //         @Override
    //         public void run() {
    //             test.minus();
    //         }
    //     });
    //
    //     Thread thread2 = new Thread(new Runnable() {
    //
    //         @Override
    //         public void run() {
    //             test.minus2();
    //         }
    //     });
    //
    //     thread1.start();
    //     thread2.start();
    //
    // }


    /**
     * 如果某个线程得到了对象锁，但是另一个线程还是可以访问没有进行同步的方法或者代码。
     * 进行了同步的方法（加锁方法）和没有进行同步的方法（普通方法）是互不影响的，一个线程进入了同步方法，得到了对象锁，其他线程还是可以访问那些没有同步的方法（普通方法）
     * @param args
     */
    public static void main(String[] args) {
        final TestLock test = new TestLock();

        Thread thread1 = new Thread(new Runnable() {

            @Override
            public void run() {
                test.minus();
            }
        });

        Thread thread2 = new Thread(new Runnable() {

            @Override
            public void run() {
                test.minus3();
            }
        });

        thread1.start();
        thread2.start();

    }


}