package com.zhen.util;

import org.apache.http.util.TextUtils;

/**
 * Description：埃特巴什码：最后一个字母代表第一个字母，倒数第二个字母代表第二个字母
 * Author：wuhengzhen
 * Date：2018-09-20
 * Time：13:25
 */
public class AtbashUtil {
    /**
     * 小写字母
     */
    private static final String NORMAL_SEQ_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    /**
     * 小写字母
     */
    private static final String PWD_SEQ_LOWERCASE = "zyxwvutsrqponmlkjihgfedcba";
    /**
     * 大写字母
     */
    private static final String NORMAL_SEQ_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 大写字母
     */
    private static final String PWD_SEQ_UPPERCASE = "ZYXWVUTSRQPONMLKJIHGFEDCBA";
    /**
     * 数字
     */
    private static final String NORMAL_SEQ_NUMBER = "0123456789";
    /**
     * 数字
     */
    private static final String PWD_SEQ_NUMBER = "9876543210";

    /**
     * 加密
     * <p>
     * 加密规则：
     * 1，判断密文中是否含有{@link #NORMAL_SEQ_LOWERCASE}，{@link #NORMAL_SEQ_LOWERCASE}和{@link #NORMAL_SEQ_NUMBER}中的任意char，
     * 2，如果有，就逐步获得那些char，然后替换成{@link #PWD_SEQ_LOWERCASE}，{@link #PWD_SEQ_LOWERCASE}和{@link #PWD_SEQ_NUMBER}对应的的char.
     * 3，合成明文
     * </p>
     *
     * @param clearStr 明文
     * @return
     */
    public static String encodeAtbash(String clearStr) {
        StringBuffer cipherSb = new StringBuffer();
        if (TextUtils.isEmpty(clearStr)) {
            return "";
        }
        int clearStrLength = clearStr.length();
        for (int i = 0; i < clearStrLength; i++) {
            char clearChar = clearStr.charAt(i);
            //当前字符是否存在于NORMAL_SEQ_LOWERCASE，NORMAL_SEQ_NUMBER和NORMAL_SEQ_UPPERCASE
            boolean hasPos = false;

            //处理字符存在于NORMAL_SEQ_LOWERCASE情况
            int pos = NORMAL_SEQ_LOWERCASE.indexOf(clearChar);
            if (-1 != pos) {
                cipherSb.append(PWD_SEQ_LOWERCASE.charAt(pos));
                hasPos = true;
            }
            if (hasPos) {
                continue;
            }

            //处理字符存在于NORMAL_SEQ_UPPERCASE情况
            pos = NORMAL_SEQ_UPPERCASE.indexOf(clearChar);
            if (-1 != pos) {
                cipherSb.append(PWD_SEQ_UPPERCASE.charAt(pos));
                hasPos = true;
            }
            if (hasPos) {
                continue;
            }

            //处理字符存在于NORMAL_SEQ_NUMBER情况
            pos = NORMAL_SEQ_NUMBER.indexOf(clearChar);
            if (-1 != pos) {
                cipherSb.append(PWD_SEQ_NUMBER.charAt(pos));
                hasPos = true;
            }
            if (hasPos) {
                continue;
            }

            if (hasPos) {
                continue;
            } else {
                cipherSb.append(clearChar);
            }
        }
        return cipherSb.toString();
    }

    /**
     * 解密
     *
     * @param cipherStr 密文
     * @return
     */
    public static String decodeAtbash(String cipherStr) {
        StringBuffer clearSb = new StringBuffer();
        if (TextUtils.isEmpty(cipherStr)) {
            return "";
        }
        int clearStrLength = cipherStr.length();
        for (int i = 0; i < clearStrLength; i++) {
            char clearChar = cipherStr.charAt(i);
            //当前字符是否存在于NORMAL_SEQ_LOWERCASE，NORMAL_SEQ_NUMBER和NORMAL_SEQ_UPPERCASE
            boolean hasPos = false;

            //处理字符存在于PWD_SEQ_LOWERCASE情况
            int pos = PWD_SEQ_LOWERCASE.indexOf(clearChar);
            if (-1 != pos) {
                clearSb.append(NORMAL_SEQ_LOWERCASE.charAt(pos));
                hasPos = true;
            }
            if (hasPos) {
                continue;
            }

            //处理字符存在于PWD_SEQ_UPPERCASE情况
            pos = PWD_SEQ_UPPERCASE.indexOf(clearChar);
            if (-1 != pos) {
                clearSb.append(NORMAL_SEQ_UPPERCASE.charAt(pos));
                hasPos = true;
            }
            if (hasPos) {
                continue;
            }

            //处理字符存在于PWD_SEQ_NUMBER情况
            pos = PWD_SEQ_NUMBER.indexOf(clearChar);
            if (-1 != pos) {
                clearSb.append(NORMAL_SEQ_NUMBER.charAt(pos));
                hasPos = true;
            }
            if (hasPos) {
                continue;
            }

            if (hasPos) {
                continue;
            } else {
                clearSb.append(clearChar);
            }
        }
        return clearSb.toString();
    }

    public static void main(String[] args) {
        String cipherStr = encodeAtbash("6783$%#!~!#$%^&*&(WUHENGZHENwuhengzhen");
        System.out.println(cipherStr);
        String clearStr = decodeAtbash(cipherStr);
        System.out.println(clearStr);
    }
}
