package com.zhen.test;

/**
 * Created with IntelliJ IDEA
 *
 * @ClassName：SeqCount
 * @Description：假设每个线程都需要一个计数值记录自己做某件事做了多少次，各线程运行时都需要改变自己的计数值而且相互不影响
 *               那么ThreadLocal就是很好的选择，这里ThreadLocal里保存的当前线程的局部变量的副本就是这个计数值。
 * @Author：wuhengzhen
 * @Date：2019-10-18 10:41
 */
public class SeqCount {
    private static ThreadLocal<Integer> seqCount = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };


    public int nextSeq() {
        seqCount.set(seqCount.get() + 1);
        return seqCount.get();
    }

    public static void main(String[] args) {
        SeqCount seqCount = new SeqCount();

        SeqThread seqThread1 = new SeqThread(seqCount);
        SeqThread seqThread2 = new SeqThread(seqCount);
        SeqThread seqThread3 = new SeqThread(seqCount);
        SeqThread seqThread4 = new SeqThread(seqCount);

        seqThread1.start();
        seqThread2.start();
        seqThread3.start();
        seqThread4.start();
    }

    public static class SeqThread extends Thread {

        private SeqCount seqCount;

        public SeqThread(SeqCount seqCount) {
            this.seqCount = seqCount;
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.println(Thread.currentThread().getName() + " seqCount:" + seqCount.nextSeq());
            }
        }
    }
}
