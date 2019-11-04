package com.zhen.utils;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：银行卡Util
 * Author：wuhengzhen
 * Date：2018-11-13
 * Time：13:32
 */
public class BankNumberUtil {

    private static int i = 0;

    /**
     * 生成一个随机银行卡
     * 需要传入一个前缀：6、8、9中的一个。
     * 其中：6：类型1，8：类型2，9：类型3 【根据自己的业务定义】
     * 其他则会返回异常
     *
     * @param prefix
     * @return
     */
    public synchronized static String getBrankNumber(String prefix) {
        String st = "";
        if (StringUtils.isNotBlank(prefix)) {
            if ("689".indexOf(prefix) >= 0 && prefix.length() == 1) {
                st = "666" + prefix + getUnixTime();
                st += RegexUtil.getBankCardCheckCode(st);
            } else {
                System.out.println("银行卡号前缀有误");
            }
        } else {
            System.out.println("银行卡号去前缀不能是空");
        }
        return st;
    }

    /***
     * 获取当前系统时间戳 并截取
     * @return
     */
    private synchronized static String getUnixTime() {
        try {
            //线程同步执行，休眠10毫秒 防止卡号重复
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        i++;
        i = i > 100 ? i % 10 : i;
        return ((System.currentTimeMillis() / 100) + "").substring(1) + (i % 10);
    }
}

