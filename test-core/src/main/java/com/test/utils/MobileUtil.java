package com.test.utils;

import java.util.Random;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：手机号码工具类
 * Auth：wuhengzhen
 * Date：2018-11-16
 * Time：9:27
 */
public class MobileUtil {

    /**
     * 判断是否电话格式
     *
     * @param mobile 手机号
     * @return true false
     */
    public static boolean isMobileNO(String mobile) {
        String reg = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        return RegexUtils.match(mobile, reg);
    }

    /**
     * 返回随机电话号码
     *
     * @return 手机号
     */
    public static String getMobile() {

        while (true) {
            String randomPhone = randomPhone();
            if (MobileUtil.isMobileNO(randomPhone)) {
                return randomPhone;
            }
        }

    }

    /**
     * 产生随机电话号码格式数字
     *
     * @return 随机电话号码格式数字
     */
    public static String randomPhone() {
        String phone = "1";

        Random random = new Random();
        int nextInt = random.nextInt(3);

        if (nextInt == 0) {
            phone = phone + "3" + MobileUtil.randomNumber();
        } else if (nextInt == 1) {
            phone = phone + "5" + MobileUtil.randomNumber();
        } else {
            phone = phone + "8" + MobileUtil.randomNumber();
        }
        return phone;
    }

    /**
     * 生成长度为9的随机数
     *
     * @return
     */
    public static String randomNumber() {

        Random random = new Random();
        int nextInt = random.nextInt(900000000) + 100000000;
        int abs = Math.abs(nextInt);
        String valueOf = String.valueOf(abs);
        return valueOf;
    }

    public static void main(String[] args) {
        int test = 5;
        while (test > 0) {
            System.out.println(MobileUtil.getMobile());
            test--;
        }
    }
}
