package com.zhen.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * String工具
 * 主要对 org.apache.commons.lang3.StringUtil 的一些方法进行重写,达到更方便的使用
 *
 * @author
 */
public class StringUtil extends org.apache.commons.lang3.StringUtils {
    private static final Logger logger = Logger.getLogger(StringUtil.class);

    private static Pattern pattern = Pattern.compile("<(span)?\\sstyle.*?style>|(span)?\\sstyle=.*?>", Pattern.DOTALL);
    private static Pattern pattern2 = Pattern.compile("(<[^>]+>)", Pattern.DOTALL);
    private static Pattern patterncomma = Pattern.compile("(&[^;]+;)", Pattern.DOTALL);
    private static Pattern linePattern = Pattern.compile("_(\\w)");
    private static Pattern humpPattern = Pattern.compile("[A-Z]");
    /**
     * 字母
     */
    private static final String CHAR = "char";
    /**
     * 数字
     */
    private static final String NUM = "num";

    /**
     * 一次性判断多个或单个对象为空。
     *
     * @param objects
     * @return 只要有一个元素为Blank，则返回true
     * @author zhou-baicheng
     */
    public static boolean isBlank(Object... objects) {
        Boolean result = false;
        for (Object object : objects) {
            if (null == object || "".equals(object.toString().trim())
                    || "null".equals(object.toString().trim())) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * 获得指定长度的随机字符串
     * 参照ASCII码表 65到90为大写字母，97到122为小写字母
     *
     * @param length 要生成的字符串长度
     * @return 返回生成的随机字符串
     */
    public static String getStringRandom(int length) {
        StringBuilder valSb = new StringBuilder();
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            //输出是数字还是字母
            String charOrNum = random.nextInt(2) % 2 == 0 ? CHAR : NUM;
            switch (charOrNum) {
                case CHAR:
                    // 输出是大写字母还是小写字母
                    int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                    valSb.append((char) (random.nextInt(26) + choice));
                    break;
                case NUM:
                    valSb.append(random.nextInt(10));
                    break;
                default:
                    break;
            }
        }
        return valSb.toString().toLowerCase();
    }

    /**
     * 一次性判断多个或单个对象不为空。
     *
     * @param objects
     * @return 只要有一个元素不为Blank，则返回true
     * @author wuhengzhen
     */
    public static boolean isNotBlank(Object... objects) {
        return !isBlank(objects);
    }

    public static boolean isBlank(String... objects) {
        Object[] object = objects;
        return isBlank(object);
    }

    public static boolean isNotBlank(String... objects) {
        Object[] object = objects;
        return !isBlank(object);
    }

    public static boolean isBlank(String str) {
        Object object = str;
        return isBlank(object);
    }

    public static boolean isNotBlank(String str) {
        Object object = str;
        return !isBlank(object);
    }

    /**
     * 判断一个字符串在数组中存在几个
     *
     * @param baseStr
     * @param strings
     * @return
     */
    public static int indexOf(String baseStr, String[] strings) {

        if (null == baseStr || baseStr.length() == 0 || null == strings) {
            return 0;
        }

        int i = 0;
        for (String string : strings) {
            boolean result = baseStr.equals(string);
            i = result ? ++i : i;
        }
        return i;
    }

    /**
     * 判断一个字符串是否为JSONObject,是返回JSONObject,不是返回null
     *
     * @param args
     * @return
     */
    public static net.sf.json.JSONObject isJSONObject(String args) {
        net.sf.json.JSONObject result = null;
        if (isBlank(args)) {
            return result;
        }
        try {
            return net.sf.json.JSONObject.fromObject(args.trim());
        } catch (Exception e) {
            return result;
        }
    }

    /**
     * 判断一个字符串是否为JSONArray,是返回JSONArray,不是返回null
     *
     * @param args
     * @return
     */
    public static net.sf.json.JSONArray isJSONArray(Object args) {
        JSONArray result = new JSONArray();
        if (isBlank(args)) {
            return null;
        }
        if (args instanceof net.sf.json.JSONArray) {

            net.sf.json.JSONArray arr = (net.sf.json.JSONArray) args;
            for (Object json : arr) {
                if (json != null && json instanceof net.sf.json.JSONObject) {
                    result.add(json);
                    continue;
                } else {
                    result.add(JSONObject.fromObject(json));
                }
            }
            return result;
        } else {
            return null;
        }

    }

    public static String trimToEmpty(Object str) {
        return (isBlank(str) ? "" : str.toString().trim());
    }

    /**
     * 把Map转换成get请求参数类型,如 {"name"=20,"age"=30} 转换后变成 name=20&age=30
     *
     * @param map
     * @return
     */
    public static String mapToGet(Map<? extends Object, ? extends Object> map) {
        String result = "";
        if (map == null || map.size() == 0) {
            return result;
        }
        Set<? extends Object> keys = map.keySet();
        for (Object key : keys) {
            result += ((String) key + "=" + (String) map.get(key) + "&");
        }

        return isBlank(result) ? result : result.substring(0, result.length() - 1);
    }

    /**
     * 把一串参数字符串,转换成Map 如"?a=3&b=4" 转换为Map{a=3,b=4}
     *
     * @param args
     * @return
     */
    public static Map<String, ? extends Object> getToMap(String args) {
        if (isBlank(args)) {
            return null;
        }
        args = args.trim();
        //如果是?开头,把?去掉
        if (args.startsWith("?")) {
            args = args.substring(1, args.length());
        }
        String[] argsArray = args.split("&");

        Map<String, Object> result = new HashMap<String, Object>();
        for (String ag : argsArray) {
            if (!isBlank(ag) && ag.indexOf("=") > 0) {

                String[] keyValue = ag.split("=");
                //如果value或者key值里包含 "="号,以第一个"="号为主 ,如  name=0=3  转换后,{"name":"0=3"}, 如果不满足需求,请勿修改,自行解决.

                String key = keyValue[0];
                String value = "";
                for (int i = 1; i < keyValue.length; i++) {
                    value += keyValue[i] + "=";
                }
                value = value.length() > 0 ? value.substring(0, value.length() - 1) : value;
                result.put(key, value);

            }
        }

        return result;
    }

    /**
     * 转换成Unicode
     *
     * @param str
     * @return
     */
    public static String toUnicode(String str) {
        String[] as = new String[str.length()];
        StringBuilder s1 = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            int v = str.charAt(i);
            if (v >= 19968 && v <= 171941) {
                as[i] = Integer.toHexString(str.charAt(i) & 0xffff);
                s1.append("\\u").append(as[i]);
            } else {
                s1.append(str.charAt(i));
            }
        }
        return s1.toString();
    }

    /**
     * 合并数据
     *
     * @param v
     * @return
     */
    public static String merge(Object... v) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < v.length; i++) {
            sb.append(v[i]);
        }
        return sb.toString();
    }

    /**
     * 字符串转urlcode
     *
     * @param value
     * @return
     */
    public static String strToUrlcode(String value) {
        try {
            value = java.net.URLEncoder.encode(value, "utf-8");
            return value;
        } catch (UnsupportedEncodingException e) {
            logger.error("字符串转换为URLCode失败,value:" + value, e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * urlcode转字符串
     *
     * @param value
     * @return
     */
    public static String urlcodeToStr(String value) {
        try {
            value = java.net.URLDecoder.decode(value, "utf-8");
            return value;
        } catch (UnsupportedEncodingException e) {
            logger.error("URLCode转换为字符串失败;value:" + value, e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断字符串是否包含汉字
     *
     * @param txt
     * @return
     */
    public static Boolean containsCN(String txt) {
        if (isBlank(txt)) {
            return false;
        }
        for (int i = 0; i < txt.length(); i++) {

            String bb = txt.substring(i, i + 1);

            boolean cc = Pattern.matches("[\u4E00-\u9FA5]", bb);
            if (cc) {
                return cc;
            }
        }
        return false;
    }

    /**
     * 去掉HTML代码
     *
     * @param news
     * @return
     */
    public static String removeHtml(String news) {
        String s = news.replaceAll("amp;", "").replaceAll("<", "<").replaceAll(">", ">");

        Matcher matcher = pattern.matcher(s);
        String str = matcher.replaceAll("");

        Matcher matcher2 = pattern2.matcher(str);
        String strhttp = matcher2.replaceAll(" ");


        String regEx = "(((http|https|ftp)(\\s)*((\\:)|：))(\\s)*(//|//)(\\s)*)?"
                + "([\\sa-zA-Z0-9(\\.|．)(\\s)*\\-]+((\\:)|(:)[\\sa-zA-Z0-9(\\.|．)&%\\$\\-]+)*@(\\s)*)?"
                + "("
                + "(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9])"
                + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
                + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)"
                + "(\\.|．)(25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])"
                + "|([\\sa-zA-Z0-9\\-]+(\\.|．)(\\s)*)*[\\sa-zA-Z0-9\\-]+(\\.|．)(\\s)*[\\sa-zA-Z]*"
                + ")"
                + "((\\s)*(\\:)|(：)(\\s)*[0-9]+)?"
                + "(/(\\s)*[^/][\\sa-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*";
        Pattern p1 = Pattern.compile(regEx, Pattern.DOTALL);
        Matcher matchhttp = p1.matcher(strhttp);
        String strnew = matchhttp.replaceAll("").replaceAll("(if[\\s]*\\(|else|elseif[\\s]*\\().*?;", " ");

        Matcher matchercomma = patterncomma.matcher(strnew);
        String strout = matchercomma.replaceAll(" ");
        String answer = strout.replaceAll("[\\pP‘’“”]", " ")
                .replaceAll("\r", " ").replaceAll("\n", " ")
                .replaceAll("\\s", " ").replaceAll("　", "");


        return answer;
    }

    /**
     * 把数组的空数据去掉
     *
     * @param array
     * @return
     */
    public static List<String> array2Empty(String[] array) {
        List<String> list = new ArrayList<String>();
        for (String string : array) {
            if (StringUtil.isNotBlank(string)) {
                list.add(string);
            }
        }
        return list;
    }

    /**
     * 把数组转换成set
     *
     * @param array
     * @return
     */
    public static Set<?> array2Set(Object[] array) {
        Set<Object> set = new TreeSet<Object>();
        for (Object id : array) {
            if (null != id) {
                set.add(id);
            }
        }
        return set;
    }

    /**
     * serializable toString
     *
     * @param serializable
     * @return
     */
    public static String toString(Serializable serializable) {
        if (null == serializable) {
            return null;
        }
        try {
            return (String) serializable;
        } catch (Exception e) {
            return serializable.toString();
        }
    }

    /**
     * 如果Object为null则返回“”空字符串，反之返回（String）Object
     */
    public static String checkNull(Object o) {
        String result = "";
        if (o != null) {
            result = o.toString();
        }
        return result;
    }

    /**
     * 下划线转驼峰
     *
     * @param str
     * @return
     */
    public static String lineToHump(String str) {
        if (null == str || "".equals(str)) {
            return str;
        }
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);

        str = sb.toString();
        str = str.substring(0, 1).toUpperCase() + str.substring(1);

        return str;
    }

    /**
     * 驼峰转下划线,效率比上面高
     *
     * @param str
     * @return
     */
    public static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 驼峰转下划线(简单写法，效率低于{@link #humpToLine(String)})
     *
     * @param str
     * @return
     */
    public static String humpToLine2(String str) {
        return str.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    /**
     * 首字母转小写
     *
     * @param s
     * @return
     */
    public static String toLowerCaseFirstOne(String s) {
        if (org.apache.commons.lang.StringUtils.isBlank(s)) {
            return s;
        }
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * 首字母转大写
     *
     * @param s
     * @return
     */
    public static String toUpperCaseFirstOne(String s) {
        if (org.apache.commons.lang.StringUtils.isBlank(s)) {
            return s;
        }
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuffer()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    /**
     * object转Integer
     *
     * @param object
     * @return
     */
    public static int getInt(Object object) {
        return getInt(object, -1);
    }

    public static int getInt(Object object, Integer defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(object.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * object转Boolean
     *
     * @param object
     * @return
     */
    public static boolean getBoolean(Object object) {
        return getBoolean(object, false);
    }

    public static boolean getBoolean(Object object, Boolean defaultValue) {
        if (null == object) {
            return defaultValue;
        }
        try {
            return Boolean.parseBoolean(object.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 全角字符串转换半角字符串
     *
     * @param fullWidthStr 非空的全角字符串
     * @return 半角字符串
     */
    public static String fullWidth2halfWidth(String fullWidthStr) {
        if (null == fullWidthStr || fullWidthStr.length() <= 0) {
            return "";
        }
        char[] charArray = fullWidthStr.toCharArray();
        // 对全角字符转换的char数组遍历
        for (int i = 0; i < charArray.length; ++i) {
            int charIntValue = (int) charArray[i];
            // 如果符合转换关系,将对应下标之间减掉偏移量65248;如果是空格的话,直接做转换
            if (charIntValue >= 65281 && charIntValue <= 65374) {
                charArray[i] = (char) (charIntValue - 65248);
            } else if (charIntValue == 12288) {
                charArray[i] = (char) 32;
            }
        }
        return new String(charArray);
    }

    /**
     * 全角空格为12288，半角空格为32
     * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     * <p>
     * 将字符串中的全角字符转为半角
     *
     * @param src 要转换的包含全角的任意字符串
     * @return 转换之后的字符串
     */
    public static String toSemiangle(String src) {
        char[] c = src.toCharArray();
        for (int index = 0; index < c.length; index++) {
            // 全角空格
            if (c[index] == 12288) {
                c[index] = (char) 32;
            } else if (c[index] > 65280 && c[index] < 65375) {
                // 其他全角字符
                c[index] = (char) (c[index] - 65248);
            }
        }
        return String.valueOf(c);
    }
}
