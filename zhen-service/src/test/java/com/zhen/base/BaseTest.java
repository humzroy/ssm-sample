package com.zhen.base;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.java.emoji.EmojiConverter;
import com.google.common.collect.Lists;
import com.jcraft.jsch.ChannelSftp;
import com.vdurmont.emoji.EmojiManager;
import com.vdurmont.emoji.EmojiParser;
import com.zhen.exception.BusinessException;
import com.zhen.util.*;
import io.github.biezhi.ome.OhMyEmail;
import io.github.biezhi.ome.SendMailException;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Base Tester.
 *
 * @author wuhengzhen
 * @version 1.0
 * @since <pre>09/07/2018</pre>
 */
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = {"/spring-redis.xml"})
// @ContextConfiguration(locations = {"/spring-test.xml", "/spring-threadpool.xml"})
//extends AbstractJUnit4SpringContextTests
public class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    private static final DecimalFormat df = new DecimalFormat("#0.00");
    public static final String CONF_HOME = "";
    private EmojiConverter emojiConverter = EmojiConverter.getInstance();

    @Before
    public void before() {
        OhMyEmail.config(OhMyEmail.SMTP_163(false), "wu_worktest@163.com", "123456a");

    }

    @After
    public void after() {
    }

    // public <T> T getBean(Class<T> type) {
    //     return applicationContext.getBean(type);
    // }
    //
    // public Object getBean(String beanName) {
    //     return applicationContext.getBean(beanName);
    // }
    //
    // protected ApplicationContext getContext() {
    //     return applicationContext;
    // }
    // @Autowired
    // IUsrRoleService usrRoleService;
    // @Autowired
    // private MybatisPlusUserMapper userMapper;
    // @Autowired
    // private RedisUtil redisUtils;

    // @Autowired
    // ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Test
    public void test() {

        // System.out.println("211476b12b1d48cabf0b15abd473351b".length());
        // int i = 8;
        // int j = 8;
        // System.out.println(i==j);
        String str = "23423423";
        String str1 = "";
        String str2 = "dfafdsf";
        StringBuilder stringBuilder = new StringBuilder();
        if (StringUtil.isNotBlank(str)) {
            stringBuilder.append(str).append(",");
        }
        if (StringUtil.isNotBlank(str1)) {
            stringBuilder.append(str1).append(",");
        }
        if (StringUtil.isNotBlank(str2)) {
            stringBuilder.append(str2).append(",");
        }


        System.out.println(stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString());

        System.out.println(stringBuilder.toString().replaceAll(",", "\r\n"));
        System.out.println("heeeeee");
        // pritNoBug();
        // UUIDUtil uuidUtil = new UUIDUtil();
        // logger.info(uuidUtil.getUnid());

        /*  批量插入test */
		/*List<User> userList = new ArrayList<>();
		User userTest1 = new User();
		userTest1.setLoginName("test1");
		userTest1.setPassword("123456");
		userTest1.setUserName("test2");
		userTest1.setCreateTime(new Date());

		User userTest2 = new User();
		userTest2.setLoginName("test2");
		userTest2.setPassword("123456");
		userTest2.setUserName("test2");
		userTest2.setCreateTime(new Date());

		userList.add(userTest1);
		userList.add(userTest2);
		logger.info(userList.toString());
		int row = userMapper.insertBatch(userList);
		logger.info(Integer.toString(row));*/


    }

    /**
     * MD5
     */
    @Test
    public void testMD5() {
        // String password = "admin";
        // String md5salt = MD5Util.getMd5Salt(password);
        // System.out.println(md5salt);

        System.out.println(MD5Util.checkPassword("admin", "vALIddME", "7E5089D8E64E39A26D2F1D38EFBB5003"));

    }

    @Test
    public void testCollectionSort() {
        String timestamp = "1455868453";
        String nonce = "1677866950";
        String token = "weixin";

        ArrayList<String> list = new ArrayList<String>();
        list.add(nonce);
        list.add(timestamp);
        list.add(token);

        Collections.sort(list);
        System.out.println(DigestUtils.sha1Hex(list.get(0) + list.get(1) + list.get(2)));
        System.out.println("0f829ac4e6689efbd338abda4c090f90bab60725".equals(DigestUtils.sha1Hex(list.get(0) + list.get(1) + list.get(2))));
    }


    @Test
    public void testMd5() {
        // System.out.println(MD5Util.addSalt("admin"));
        // 2ZKzfby13j8E468c
        // 726bZ8fK06z3ef6ebb3y8e1be3f5ja183fEf54826af8c8c2
        System.out.println(SaltUtil.getSalt());
        // XDMkXT0o
        // System.out.println(SaltUtil.verifyPwd("admin","726bZ8fK06z3ef6ebb3y8e1be3f5ja183fEf54826af8c8c2"));

    }

    /**
     * zhen redis
     */
    @Test
    public void testRedis() {
        // redisUtils.del("name");
        // Jedis redis = JedisUtil.getInstance().getJedis();
        String paramName = "";
        JedisUtil jedisUtil = JedisUtil.getInstance();
        JedisUtil.Strings strings = jedisUtil.new Strings();
        //

        // redis.set("name", "admin");
        // System.out.println(redis.get("name"));


    }


    @Test
    public void pritNoBug() throws Exception {

        String s = "\n" +
                "   █████▒█    ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗\n" +
                " ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝\n" +
                " ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗\n" +
                " ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║\n" +
                " ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝\n" +
                "  ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝\n" +
                "  ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░\n" +
                "  ░ ░    ░░░ ░ ░ ░        ░ ░░ ░\n" +
                "           ░     ░ ░      ░  ░\n";

        System.out.println(s);
        System.out.println(Base64Util.encryptBASE64(s.getBytes(StandardCharsets.UTF_8)));


        String base64fozu = "Li4uLi4uLi4uLi4uLi4uLi4uLuaIkeS9m+aFiOaCsi4uLi4uLi4uLi4uLi4uLi4uLi4KICAgICAg\n" +
                "ICAgICAgICAgICAgIF9vb09vb18KICAgICAgICAgICAgICAgICAgbzg4ODg4ODhvCiAgICAgICAg\n" +
                "ICAgICAgICAgIDg4IiAuICI4OAogICAgICAgICAgICAgICAgICAofCAtXy0gfCkKICAgICAgICAg\n" +
                "ICAgICAgICAgT1wgID0gIC9PCiAgICAgICAgICAgICAgIF9fX18vYC0tLSdcX19fXwogICAgICAg\n" +
                "ICAgICAgLicgIFxcfCAgICAgfC8vICBgLgogICAgICAgICAgICAvICBcXHx8fCAgOiAgfHx8Ly8g\n" +
                "IFwKICAgICAgICAgICAvICBffHx8fHwgLTotIHx8fHx8LSAgXAogICAgICAgICAgIHwgICB8IFxc\n" +
                "XCAgLSAgLy8vIHwgICB8CiAgICAgICAgICAgfCBcX3wgICcnXC0tLS8nJyAgfCAgIHwKICAgICAg\n" +
                "ICAgICBcICAuLVxfXyAgYC1gICBfX18vLS4gLwogICAgICAgICBfX19gLiAuJyAgLy0tLi0tXCAg\n" +
                "YC4gLiBfXwogICAgICAuIiIgJzwgIGAuX19fXF88fD5fL19fXy4nICA+JyIiLgogICAgIHwgfCA6\n" +
                "ICBgLSBcYC47YFwgXyAvYDsuYC8gLSBgIDogfCB8CiAgICAgXCAgXCBgLS4gICBcXyBfX1wgL19f\n" +
                "IF8vICAgLi1gIC8gIC8KPT09PT09YC0uX19fX2AtLl9fX1xfX19fXy9fX18uLWBfX19fLi0nPT09\n" +
                "PT09CiAgICAgICAgICAgICAgICAgICBgPS0tLT0nCl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5eXl5e\n" +
                "Xl5eXl5eXl5eXl5eXl5eXl5eXgogICAgICAgICAgICDkvZvnpZbkv53kvZEgICAgICAg5rC45peg\n" +
                "QlVH";
        System.out.println(new String(Base64Util.decryptBASE64(s)));
    }

    @Test
    public void testIdNo() {
        String idNo = "370285199405073131";
        System.out.println(RegexUtil.isIDNumber(idNo));
    }


    @Test
    public void testReadProperties() {
        System.out.println(PropertiesFileUtil.getInstance().get("from"));

        System.out.println(PropertiesFileUtil.readPropertiesFromfiles("/email.properties", "from", "CONF_HOME"));
    }

    @Test
    public void testDate() {
        String date = "1994/4/12";
        try {
            System.out.println(DateUtil.formatStrDate(date, "yyyy/MM/dd", "yyyyMMdd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(0.5f);
        System.out.println(Float.valueOf("30.00") / 100);
    }


    @Test
    public void testDate1() {
        System.out.println(DateUtil.format(DateUtil.autoParseDate("1989-04-13 00:00:00.0")));
    }

    @Test
    public void testCycle() {
        logger.info("测试for循环异常");
        logger.info("第一种：try catch 在 for循环体外..");
        try {
            for (int i = 0; i < 10; i++) {
                if (i == 5) {
                    throw new BusinessException("i=5啦！exception！");
                }
                logger.info("i = " + i);
            }
        } catch (BusinessException e) {
            logger.error("1.捕捉到异常！" + e.getMessage());
        }

        logger.info("第二种：try catch 在 for循环体内..");

        for (int j = 0; j < 10; j++) {

            try {
                if (j == 5) {
                    throw new BusinessException("j=5啦！exception！");
                }
                logger.info("j = " + j);
            } catch (BusinessException e) {
                logger.error("2.捕捉到异常！" + e.getMessage());
            }
        }

    }

    @Test
    public void testSFTP() {


        logger.info("测试登录sftp..");
        // String host = "47.95.208.138";
        String host = "10.192.9.12";
        // int port = 9022;
        int port = 22;
        // String loginUser = "mysftp";
        String loginUser = "root";
        // String password = "mysftp";
        String password = "putty#123";

        String downRemotePath = "/YC_FILE/YYYYMMDD/";
        String downLoadPath = "D://bi//hips//file//downLoad//";
        String dataStr = DateUtil.format(new Date(), "yyyyMMdd");
        String downLoadPathNew = downLoadPath + dataStr + File.separator;
        String downRemotePathNew = downRemotePath.replace("YYYYMMDD", dataStr);
        String filepPrefix = "OFI";
        String fileSuffix = dataStr;

        logger.info("downLoadPathNew：{}", downLoadPathNew);
        logger.info("downRemotePathNew：{}", downRemotePathNew);
        logger.info("filepPrefix：{}", filepPrefix);
        logger.info("fileSuffix：{}", fileSuffix);

        SftpUtil instance = SftpUtil.getInstance(host, port, loginUser, password, null, null);
        try {
            List<String> fileList = instance.listFiles("/report/templates/");
            Vector v = instance.listFiles1("/report/templates/");

            System.out.println(v.size());

            Iterator it = v.iterator();
            while (it.hasNext()) {
                ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) it.next();
                String filename = entry.getFilename();
                System.out.println(filename);
            }
            System.out.println(fileList.size());
            logger.info("===========================: " + ArrayUtil.toString(fileList));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // List<String> fileNames = instance.batchDownLoadFile(downRemotePathNew, downLoadPathNew, filepPrefix, fileSuffix, false);
        // logger.info("fileNames: {}", fileNames.toString());

        // try {
        //     instance.uploadFile("/upload/", "/test/", "D:\\", "CentOS-Base.repo");
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        // List<String> fileNames = instance.batchDownLoadFile("/YC_FILE/20190618/", "D:\\test\\", "OFD", ".TXT", false);
        // System.out.println(fileNames.toString());
    }


    @Test
    public void test10() {
        printDir("D:\\software\\cmder_mini");
        readFile("D:\\software\\cmder_mini");
    }

    // 文件所在的层数
    private int fileLevel;

    /**
     * 生成输出格式
     *
     * @param name  输出的文件名或目录名
     * @param level 输出的文件名或者目录名所在的层次
     * @return 输出的字符串
     */
    public String createPrintStr(String name, int level) {
        // 输出的前缀
        String printStr = "";
        // 按层次进行缩进
        for (int i = 0; i < level; i++) {
            printStr = printStr + "  ";
        }
        printStr = printStr + "- " + name;
        return printStr;
    }

    /**
     * 输出初始给定的目录
     *
     * @param dirPath 给定的目录
     */
    public void printDir(String dirPath) {
        // 将给定的目录进行分割
        String[] dirNameList = dirPath.split("\\\\");
        // 设定文件level的base
        fileLevel = dirNameList.length;
        // 按格式输出
        for (int i = 0; i < dirNameList.length; i++) {
            System.out.println(createPrintStr(dirNameList[i], i));
        }
    }

    /**
     * 输出给定目录下的文件，包括子目录中的文件
     *
     * @param dirPath 给定的目录
     */
    public void readFile(String dirPath) {
        // 建立当前目录中文件的File对象
        File file = new File(dirPath);
        // 取得代表目录中所有文件的File对象数组
        File[] list = file.listFiles();
        // 遍历file数组
        for (int i = 0; i < list.length; i++) {
            if (list[i].isDirectory()) {
                System.out.println(createPrintStr(list[i].getName(), fileLevel));
                fileLevel++;
                // 递归子目录
                readFile(list[i].getPath());
                fileLevel--;
            } else {
                System.out.println(createPrintStr(list[i].getName(), fileLevel));
            }
        }
    }


    @Test
    public void testArray() {
        String json = "{\"cc\":[\"qqmail@qq.com\",\"163mail@163.com\"]}";
        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray jsonArray = jsonObject.getJSONArray("cc");
        System.out.println("jsonArray: " + jsonArray);
        // 方式1
        String[] cc = JsonUtil.getJsonToStringArray(jsonArray.toJSONString());
        // 方式2
        String[] strings = jsonArray.toArray(new String[jsonArray.size()]);

        testArrayUtil(strings);
    }

    private void testArrayUtil(String... cc) {
        System.out.println(ArrayUtil.isNotEmpty(cc));
    }


    @Test
    public void testLx() {
        String date = "2018.05";

        System.out.println(date.substring(0, 4));


    }

    @Test
    public void testOhMyEmail() {

        long start = System.currentTimeMillis();
        try {
            OhMyEmail.subject("test OhMyEmail").from("wu_worktest@163.com").to("15169720@qq.com").text("这是一封来自ohmyemail的测试邮件").send();
        } catch (SendMailException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        logger.info("OhMyEmail 邮件发送完成，耗时：" + (end - start) + " ms");
    }

    @Test
    public void testIp() {
        try {
            // 获得本机IP
            String addr = InetAddress.getLocalHost().getHostAddress();
            System.out.println(addr);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testBase64() {
        String baseStr = "eyJib3JuX25hdGlvbiI6IjE1OCIsImNsaWVudF9pZCI6IjQ0OCIsImN1cnJlbnRfYWRkciI6IjE4\\r\\nOCBIT05HS09ORyBST0FELCBMQU9TSEFOIERJU1RJTkNUIiwiY3VycmVudF93b3JrX2FkZHIiOiLl\\r\\ntILlsbHljLrpppnmuK/kuK3ot68xODjlj7ciLCJjdXJyZW50X3dvcmtfY2l0eSI6IjM3MDIiLCJj\\r\\ndXJyZW50X3dvcmtfY2l0eV9ubyI6IjM3MDIiLCJjdXJyZW50X3dvcmtfbmF0aW9uIjoiMTU2Iiwi\\r\\nY3VycmVudF93b3JrX3Byb3ZpY2VfY29kZSI6IjM3IiwiY3VzdF9mbGFnIjoiMiIsImVuZ19ib3Ju\\r\\nX25hdGlvbiI6IjE1OCIsImVuZ19jdXJyZW50X2FkZHIiOiJRSU5HREFPIiwiZW5nX2N1cnJlbnRf\\r\\nd29ya19uYXRpb24iOiIxNTYiLCJlbmdfaG9tZV9wbGFjZSI6IuWPsOWMlyIsImVuZ19sYXN0X25h\\r\\nbWUiOiJGRUkiLCJlbmdfbWFpbF9hZGRyIjoiMTg4IEhPTkdLT05HIFJPQUQsIExBT1NIQU4gRElT\\r\\nVElOQ1QiLCJlbmdfbWFpbF9jaXR5IjoiUUlOR0RBTyIsImVuZ19tYWlsX25hdGlvbiI6IjE1NiIs\\r\\nImVuZ19uYW1lIjoiTUVORyIsImhvbWVfcGxhY2UiOiLlj7DljJciLCJqc29uX2lkIjoiIiwibWFp\\r\\nbF9hZGRyIjoi5bSC5bGx5Yy66aaZ5riv5Lit6LevMTg45Y+3IiwibWFpbF9jaXR5IjoiMzcwMiIs\\r\\nIm1haWxfbmF0aW9uIjoiMTU2IiwidGF4aW5mbyI6W3siYmVsb25nX3R5cGUiOiIwIiwiaXNfbWFp\\r\\nbl90YXgiOiIxIiwianNvbl9iZWxvbmdfaWQiOiIiLCJqc29uX2lkIjoiIiwibm9fdGF4X25vX3Jl\\r\\nYXNvbiI6IkIiLCJ0YXhfbmF0aW9uIjoiMTU4IiwidGF4X25vIjoiMTIxMjEyIn0seyJiZWxvbmdf\\r\\ndHlwZSI6IjAiLCJpc19tYWluX3RheCI6IjEiLCJqc29uX2JlbG9uZ19pZCI6IiIsImpzb25faWQi\\r\\nOiIiLCJub190YXhfbm9fcmVhc29uIjoiQiIsInRheF9uYXRpb24iOiIxNTYiLCJ0YXhfbm8iOiIx\\r\\nMjMxMjMifV19\\r\\n";
        // 去除\r\n
        baseStr = baseStr.replace("\\r\\n", "");

        try {

            String str = new String(Base64Util.decryptBASE64(baseStr), Charset.defaultCharset());
            String str2 = StringUtil.toSemiangle(str);
            logger.info("解密后：" + str);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTime() {

        String createTime = "1545791729";

        // Long leave1 = DateUtil.stringToLong("2020-06-02 11:30:00", "yyyy-MM-dd HH:mm:ss");
        // Long leave2 = DateUtil.stringToLong("2020-06-02 12:30:00", "yyyy-MM-dd HH:mm:ss");
        //
        // System.out.println(leave1 > leave2);
        // System.out.println(leave1 < leave2);
        //
        //
        // String date = "1525747565902";
        //
        // long ii = Long.parseLong(date);
        // int ll = Integer.parseInt(ii / 1000 + "");
        //
        // System.out.println(ii);
        // System.out.println(ll);

    }




    @Test
    public void testEmojiStr() {
        // String str = "An 😀awesome 😃string with a few 😉emojis!";
        String str = "An 😀awesome 😃string with a few 😉emojis!       " + "🙅 🙆 💑 😃 😀 😉 ewerwewwwwwww";
        System.out.println(EmojiManager.containsEmoji(str));
        System.out.println(EmojiParser.removeAllEmojis(str));

        System.out.println(emojiConverter.toAlias(str));
    }


    @Test
    public void testToAlias() {
        String str = "  An 😃😀awesome 😃😃string with a few 😃😉emojis!";
        String alias = this.emojiConverter.toAlias(str);
        System.out.println(str);
        System.out.println("EmojiConverterTest.testToAlias()=====>");
        System.out.println(alias);
        Assert.assertEquals(
                ":no_good: :ok_woman: :couple_with_heart:An :smiley::grinning:awesome :smiley::smiley:string with a few :smiley::wink:emojis!",
                alias);
    }

    @Test
    public void testToHtml() {
        String str = "  An 😀😃awesome 😃😃string with a few 😉😃emojis!";
        String result = this.emojiConverter.toHtml(str);
        System.out.println(str);
        System.out.println("EmojiConverterTest.testToHtml()=====>");
        System.out.println(result);
        Assert.assertEquals(
                "&#128581; &#128582; &#128145;An &#128512;&#128515;awesome &#128515;&#128515;string with a few &#128521;&#128515;emojis!",
                result);
    }

    @Test
    public void testToUnicode() {
        String str = "   :smiley: :grinning: :wink:";
        String result = this.emojiConverter.toUnicode(str);
        System.err.println(str);
        System.err.println("EmojiConverterTest.testToUnicode()=====>");
        System.err.println(result);
        Assert.assertEquals("🙅 🙆 💑 😃 😀 😉", result);
    }


    public static boolean containsEmoji(String source) {
        int len = source.length();
        boolean isEmoji = false;
        for (int i = 0; i < len; i++) {
            char hs = source.charAt(i);
            if (0xd800 <= hs && hs <= 0xdbff) {
                if (source.length() > 1) {
                    char ls = source.charAt(i + 1);
                    int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;
                    if (0x1d000 <= uc && uc <= 0x1f77f) {
                        return true;
                    }
                }
            } else {
                // non surrogate
                if (0x2100 <= hs && hs <= 0x27ff && hs != 0x263b) {
                    return true;
                } else if (0x2B05 <= hs && hs <= 0x2b07) {
                    return true;
                } else if (0x2934 <= hs && hs <= 0x2935) {
                    return true;
                } else if (0x3297 <= hs && hs <= 0x3299) {
                    return true;
                } else if (hs == 0xa9 || hs == 0xae || hs == 0x303d
                        || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c
                        || hs == 0x2b1b || hs == 0x2b50 || hs == 0x231a) {
                    return true;
                }
                if (!isEmoji && source.length() > 1 && i < source.length() - 1) {
                    char ls = source.charAt(i + 1);
                    if (ls == 0x20e3) {
                        return true;
                    }
                }
            }
        }
        return isEmoji;
    }

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return source;
        }
        StringBuilder buf = null;
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isEmojiCharacter(codePoint)) {
                if (buf == null) {
                    buf = new StringBuilder(source.length());
                }
                buf.append(codePoint);
            }
        }
        if (buf == null) {
            return source;
        } else {
            if (buf.length() == len) {
                buf = null;
                return source;
            } else {
                return buf.toString();
            }
        }
    }


    @Test
    public void testStringUtils() {

        String status = "return1";
        String[] strings = ArrayUtils.toArray("return12", "return2", "return3", "return4", "return5", "close1", "close2", "close3", "close4", "close5");
        System.out.println(StringUtils.containsAny(status, strings));

    }

    @Test
    public void testStringUtils2() {
        String a = null;
        String b = "";
        String c = "";


        System.out.println(StringUtils.isNoneBlank(a, b, c));
    }

    @Test
    public void testSort() {
        List<Map<String, Object>> leaveList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("startDate", "2020-02-13");
        map.put("endDate", "2020-02-15");
        leaveList.add(map);

        Map<String, Object> map1 = new HashMap<>();
        map1.put("startDate", "2020-02-09");
        map1.put("endDate", "2020-02-12");
        leaveList.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("startDate", "2020-02-16");
        map2.put("endDate", "2020-02-18");
        leaveList.add(map2);

        System.out.println("1:leaveList *********   " + leaveList.toString());

        if (CollectionUtils.isNotEmpty(leaveList)) {

            leaveList.sort((m1, m2) -> {
                String startDate1 = (String) m1.get("startDate");
                String startDate2 = (String) m2.get("startDate");
                System.out.println(startDate1 + "  和  " + startDate2 + "   比较");
                if (startDate1.equals(startDate2)) {
                    System.out.println("0");
                    return 0;
                }
                long startDate1TimeStamp = DateUtil.StringToDate(startDate1).getTime() / 1000;
                long startDate2TimeStamp = DateUtil.StringToDate(startDate2).getTime() / 1000;
                // System.out.println(startDate1TimeStamp + "    " + startDate2TimeStamp);
                if (startDate1TimeStamp > startDate2TimeStamp) {
                    System.out.println("1");
                    return 1;
                } else {
                    System.out.println("-1");
                    return -1;
                }
            });

        }
        System.out.println("2:leaveList *********   " + leaveList.toString());

        caseFor:
        while (true) {
            break caseFor;

        }
    }

    @Test
    public void testInt() {

        Integer a = 100;
        Integer b = 100;

        System.out.println(a == b);

        Integer a1 = 130;
        Integer b1 = 130;
        System.out.println(a1 == b1);
        System.out.println(a1.equals(b1));

    }

    @Test
    public void testList() {
        List<String> users = Lists.newArrayList("a", "b", "c", "d");
        List<String> remove = Lists.newArrayList("a", "b");
        remove.forEach(s -> users.removeIf(s1 -> s1.equals(s)));

        System.out.println(users);


        System.out.println(StringUtils.join(users, ","));
        String con = JSON.toJSONString(Lists.newArrayList("2asd-55a2sf"));

        System.out.println(con);


        List<String> tidList = JSON.parseArray(con, String.class);
        System.out.println(tidList);

        String json = "{\"msg\": \"Json Errorwwww\", \"stateNo\": 1001}";


        JSONObject jsonObject = JSON.parseObject(json);
        System.out.println(jsonObject.getString("stateNo"));

        String regex = "[,，]";
        String address = "地址1,  首地址，23";
        // List<String> addressList = Lists.newArrayList();
        List<String> addressList = Arrays.stream(address.split(regex)).map(String::trim).collect(Collectors.toList());

        System.out.println(addressList.toString());
    }

    @Test
    public void testCheckSymbol() {
        String ss = "地址";
        System.out.println(RegexUtil.checkSymbol(ss));


    }


    @Test
    public void testStr() {
        String str = "level_1";
        System.out.println();

        System.out.println(".... level" + (Integer.parseInt(str.substring(str.lastIndexOf('_') + 1))+1));


        String rate = "80.99";

        Demo demo = new Demo();
        demo.setRate(new BigDecimal(rate));


        System.out.println(JSON.toJSONString(demo));

        String str2 = " 31d487f9-0fa6-4353-ae14-d814e929f3a6";
        System.out.println(StringUtils.trim(str2));



        //
        List<Demo> demos = new ArrayList<>();
        Demo demo2 = new Demo();
        demo2.setRateStr(rate);
        demos.add(demo2);
        Demo demo3 = new Demo();
        demo3.setRateStr("10");
        demos.add(demo3);

        Demo demo4 = new Demo();
        demo4.setRateStr("");
        demos.add(demo4);

        Demo demo5 = new Demo();
        demo5.setRate(BigDecimal.TEN);
        demo5.setRateStr("");
        demos.add(demo5);

        BigDecimal result2 = demos.stream()
                // 将user对象的age取出来map为Bigdecimal
                .map(extVisitUserInfo -> StringUtils.isNotEmpty(extVisitUserInfo.getRateStr()) ? new BigDecimal(extVisitUserInfo.getRateStr()) : BigDecimal.ZERO)
                // 使用reduce()聚合函数,实现累加器
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println(result2);

        List<Demo> strings = demos.stream().filter(extVisitUserInfo -> StringUtils.isEmpty(extVisitUserInfo.getRateStr())).collect(Collectors.toList());
        System.out.println(strings.toString());
        int s = (int) demos.stream().filter(extVisitUserInfo -> StringUtils.isEmpty(extVisitUserInfo.getRateStr())).count();
        System.out.println(s);


        BigDecimal averagePercent = (new BigDecimal("100").subtract(result2)).divide(new BigDecimal(s)).setScale(2,BigDecimal.ROUND_HALF_DOWN);
        System.out.println(averagePercent);
        System.out.println(new BigDecimal(0.359999).setScale(2, RoundingMode.DOWN));
    }

    @Data
    class Demo {
        private BigDecimal rate;
        private String rateStr;
    }

    @Test
    public void test22() {
        String reqJson = "{\"operateType\":\"channel\",\"info\":{\"detectionId\":\"8ffa00db-609d-43a9-ae40-ed44800335e6\",\"applyType\":\"normal\",\"isCashPaid\":\"2\",\"actualPaymentTime\":\"2020-12-09\",\"actualAmountPaid\":\"1000\",\"journalVoucherNo\":\"123456789\",\"paymentVoucherPath\":\"支付凭证路径\",\"auditUserId\":\"a0000000-d000-m000-i000-n00000000000\",\"auditUserName\":\"admin\",\"auditUserNumber\":\"LX001\",\"detectionAuditStatus\":\"case_over\"}}";

        String json = HttpUtil.doPostJson("http://localhost:8080/lxcs-admin/cases/auslegen/financeInfoConfirm", reqJson);
        System.out.println(json);


    }

    @Test
    public void testDownloadImg() throws Exception {
        // String imgUrl = "http://avatar.csdn.net/1/3/B/1_li1325169021.jpg";
        String imgUrl = "http://172.16.110.76:8180/cwxt-admin/cwdata/uploadFiles/attachments//202012/17/1608193385957.jpg";

        FileUtil.downLoadFromUrl(imgUrl, "test.jpg", "D:\\test\\image");
    }

    @Test
    public void testFastDfs() {
        try {
            FastDFSClient fastDFSClient = new FastDFSClient("D:\\workspace\\personal\\ssm-sample\\zhen-conf\\fastdfs-client.properties");

            String s = fastDFSClient.uploadFile("D:\\test\\image\\test.jpg");
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
