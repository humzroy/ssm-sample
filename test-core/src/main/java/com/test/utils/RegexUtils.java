package com.test.utils;

import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description：正则（校验）工具类
 * Auth：wuhengzhen
 * Date：2018-09-20
 * Time：16:00
 */
public class RegexUtils {

    // region 判断是否为空（不为空）begin

    public static boolean isNull(Object[] objs) {
        return objs == null || objs.length == 0;
    }

    public static boolean isNull(Integer integer) {
        return integer == null || integer == 0;
    }

    public static boolean isNull(Collection collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isNull(Map map) {
        return map == null || map.size() == 0;
    }

    public static boolean isNull(String str) {
        return str == null || "".equals(str.trim()) || "null".equals(str.toLowerCase());
    }


    public static boolean isNull(Long longs) {
        return longs == null || longs == 0;
    }

    public static boolean isNotNull(Long longs) {
        return !isNull(longs);
    }

    public static boolean isNotNull(String str) {
        return !isNull(str);
    }

    public static boolean isNotNull(Collection collection) {
        return !isNull(collection);
    }

    public static boolean isNotNull(Map map) {
        return !isNull(map);
    }

    public static boolean isNotNull(Integer integer) {
        return !isNull(integer);
    }

    public static boolean isNotNull(Object[] objs) {
        return !isNull(objs);
    }

    // endregion 判断是否为空（不为空）begin

    /**
     * 匹配URL地址
     *
     * @param str
     * @return
     * @author wuhengzhen
     */
    public static boolean isUrl(String str) {
        return match(str, "^http://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$");
    }

    /**
     * 匹配密码，以字母开头，长度在6-12之间，只能包含字符、数字和下划线。
     *
     * @param str
     * @return
     * @author wuhengzhen
     */
    public static boolean isPwd(String str) {
        return match(str, "^[a-zA-Z]\\w{6,12}$");
    }

    /**
     * 验证字符，只能包含中文、英文、数字、下划线等字符。
     *
     * @param str
     * @return
     * @author wuhengzhen
     */
    public static boolean stringCheck(String str) {
        return match(str, "^[a-zA-Z0-9\u4e00-\u9fa5-_]+$");
    }

    /**
     * 匹配Email地址
     *
     * @param str
     * @return
     * @author wuhengzhen
     */
    public static boolean isEmail(String str) {
        return match(str, "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    }

    /**
     * 匹配非负整数（正整数+0）
     *
     * @param str
     * @return
     * @author wuhengzhen
     */
    public static boolean isInteger(String str) {
        return match(str, "^[+]?\\d+$");
    }

    /**
     * 判断数值类型，包括整数和浮点数
     *
     * @param str
     * @return
     * @author wuhengzhen
     */
    public static boolean isNumeric(String str) {
        return isFloat(str) || isInteger(str);
    }

    /**
     * 只能输入数字
     *
     * @param str
     * @return
     * @author wuhengzhen
     */
    public static boolean isDigits(String str) {
        return match(str, "^[0-9]*$");
    }

    /**
     * 匹配正浮点数
     *
     * @param str
     * @return
     * @author wuhengzhen
     */
    public static boolean isFloat(String str) {
        return match(str, "^[-\\+]?\\d+(\\.\\d+)?$");
    }

    /**
     * 联系电话(手机/电话皆可)验证
     *
     * @param text
     * @return
     * @author wuhengzhen
     */
    public static boolean isTel(String text) {
        return isMobile(text) || isPhone(text);
    }

    /**
     * 电话号码验证
     *
     * @param text
     * @return
     * @author wuhengzhen
     */
    public static boolean isPhone(String text) {
        return match(text, "^(\\d{3,4}-?)?\\d{7,9}$");
    }

    /**
     * 手机号码验证
     *
     * @param text
     * @return
     * @author wuhengzhen
     */
    public static boolean isMobile(String text) {
        if (text.length() != 11) {
            return false;
        }
        return match(text, "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$");
    }

    /**
     * 身份证号码验证
     *
     * @param text
     * @return
     * @author wuhengzhen
     */
    public static boolean isIdCardNo(String text) {
        return match(text, "^(\\d{6})()?(\\d{4})(\\d{2})(\\d{2})(\\d{3})(\\w)$");
    }

    /**
     * 邮政编码验证
     *
     * @param text
     * @return
     * @author wuhengzhen
     */
    public static boolean isZipCode(String text) {
        return match(text, "^[0-9]{6}$");
    }

    /**
     * 判断整数num是否等于0
     *
     * @param num
     * @return
     * @author wuhengzhen
     */
    public static boolean isIntEqZero(int num) {
        return num == 0;
    }

    /**
     * 判断整数num是否大于0
     *
     * @param num
     * @return
     * @author wuhengzhen
     */
    public static boolean isIntGtZero(int num) {
        return num > 0;
    }

    /**
     * 判断整数num是否大于或等于0
     *
     * @param num
     * @return
     * @author wuhengzhen
     */
    public static boolean isIntGteZero(int num) {
        return num >= 0;
    }

    /**
     * 判断浮点数num是否等于0
     *
     * @param num 浮点数
     * @return
     * @author wuhengzhen
     */
    public static boolean isFloatEqZero(float num) {
        return num == 0f;
    }

    /**
     * 判断浮点数num是否大于0
     *
     * @param num 浮点数
     * @return
     * @author wuhengzhen
     */
    public static boolean isFloatGtZero(float num) {
        return num > 0f;
    }

    /**
     * 判断浮点数num是否大于或等于0
     *
     * @param num 浮点数
     * @return
     * @author wuhengzhen
     */
    public static boolean isFloatGteZero(float num) {
        return num >= 0f;
    }

    /**
     * 判断是否为合法字符(a-zA-Z0-9-_)
     *
     * @param text
     * @return
     * @author wuhengzhen
     */
    public static boolean isRightfulString(String text) {
        return match(text, "^[A-Za-z0-9_-]+$");
    }

    /**
     * 判断英文字符(a-zA-Z)
     *
     * @param text
     * @return
     * @author wuhengzhen
     */
    public static boolean isEnglish(String text) {
        return match(text, "^[A-Za-z]+$");
    }

    /**
     * 判断中文字符(包括汉字和符号)
     *
     * @param text
     * @return
     * @author wuhengzhen
     */
    public static boolean isChineseChar(String text) {
        return match(text, "^[\u0391-\uFFE5]+$");
    }

    /**
     * 匹配汉字
     *
     * @param text
     * @return
     * @author wuhengzhen
     */
    public static boolean isChinese(String text) {
        return match(text, "^[\u4e00-\u9fa5]+$");
    }

    /**
     * 是否包含中英文特殊字符，除英文"-_"字符外
     *
     * @param text
     * @return
     */
    public static boolean isContainsSpecialChar(String text) {
        if (StringUtils.isBlank(text)) {
            return false;
        }
        String[] chars = {"[", "`", "~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "+", "=", "|", "{", "}", "'",
                ":", ";", "'", ",", "[", "]", ".", "<", ">", "/", "?", "~", "！", "@", "#", "￥", "%", "…", "&", "*", "（", "）",
                "—", "+", "|", "{", "}", "【", "】", "‘", "；", "：", "”", "“", "’", "。", "，", "、", "？", "]"};
        for (String ch : chars) {
            if (text.contains(ch)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 过滤中英文特殊字符，除英文"-_"字符外
     *
     * @param text
     * @return
     */
    public static String stringFilter(String text) {
        String regExpr = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regExpr);
        Matcher m = p.matcher(text);
        return m.replaceAll("").trim();
    }

    /**
     * 过滤html代码
     *
     * @param inputString 含html标签的字符串
     * @return
     */
    public static String htmlFilter(String inputString) {
        // 含html标签的字符串
        String htmlStr = inputString;
        String textStr = "";
        Pattern pScript;
        Matcher mScript;
        Pattern pStyle;
        Matcher mStyle;
        Pattern pHtml;
        Matcher mHtml;
        Pattern pBa;
        Matcher mBa;

        try {
            // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regExScript = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regExStyle = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            // 定义HTML标签的正则表达式
            String regExHtml = "<[^>]+>";
            String patternStr = "\\s+";

            pScript = Pattern.compile(regExScript, Pattern.CASE_INSENSITIVE);
            mScript = pScript.matcher(htmlStr);
            // 过滤script标签
            htmlStr = mScript.replaceAll("");

            pStyle = Pattern.compile(regExStyle, Pattern.CASE_INSENSITIVE);
            mStyle = pStyle.matcher(htmlStr);
            // 过滤style标签
            htmlStr = mStyle.replaceAll("");

            pHtml = Pattern.compile(regExHtml, Pattern.CASE_INSENSITIVE);
            mHtml = pHtml.matcher(htmlStr);
            // 过滤html标签
            htmlStr = mHtml.replaceAll("");

            pBa = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
            mBa = pBa.matcher(htmlStr);
            // 过滤空格
            htmlStr = mBa.replaceAll("");

            textStr = htmlStr;

        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }
        // 返回文本字符串
        return textStr;
    }

    /**
     * 正则表达式匹配
     *
     * @param text 待匹配的文本
     * @param reg  正则表达式
     * @return
     * @author wuhengzhen
     */
    private static boolean match(String text, String reg) {
        if (StringUtils.isBlank(text) || StringUtils.isBlank(reg)) {
            return false;
        }
        return Pattern.compile(reg).matcher(text).matches();
    }

}
