package com.zhen.base;

import com.zhen.exception.BusinessException;
import com.zhen.utils.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

/**
 * Base Tester.
 *
 * @author wuhengzhen
 * @version 1.0
 * @since <pre>09/07/2018</pre>
 */
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = {"/spring-redis.xml"})
// @ContextConfiguration(locations = {"/spring-zhen.xml", })
//extends AbstractJUnit4SpringContextTests
public class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
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
    // private RedisUtils redisUtils;

    @Test
    public void test() throws Exception {

        // System.out.println("211476b12b1d48cabf0b15abd473351b".length());
        // int i = 8;
        // int j = 8;
        // System.out.println(i==j);
        String str = "23423423";
        String str1 = "";
        String str2 = "dfafdsf";
        StringBuilder stringBuilder = new StringBuilder();
        if (StringUtils.isNotBlank(str)) {
            stringBuilder.append(str).append(",");
        }
        if (StringUtils.isNotBlank(str1)) {
            stringBuilder.append(str1).append(",");
        }
        if (StringUtils.isNotBlank(str2)) {
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
     *
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void testMD5() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // String password = "admin";
        // String md5salt = MD5Utils.getMd5Salt(password);
        // System.out.println(md5salt);

        System.out.println(MD5Utils.checkPassword("admin", "vALIddME", "7E5089D8E64E39A26D2F1D38EFBB5003"));

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
    public void testMd5() throws NoSuchAlgorithmException {
        // System.out.println(MD5Utils.addSalt("admin"));
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
        Jedis redis = JedisUtil.getInstance().getJedis();
        redis.set("name", "admin");
        System.out.println(redis.get("name"));

        // System.out.println(redisUtils.getObject("myKey"));


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
        System.out.println(Base64Util.encryptBASE64(s.getBytes("utf-8")));


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
        String idNo = "37028519940507323x";
        System.out.println(RegexUtils.isIDNumber(idNo));
    }


    @Test
    public void testReadProperties() {
        System.out.println(PropertiesFileUtil.getInstance("../dubbo-resolve").get("dubbo.server.address"));
    }

    @Test
    public void testDate() {
        String date = "1994/4/12";
        try {
            System.out.println(DateUtils.formatStrDate(date, "yyyy/MM/dd", "yyyyMMdd"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println(0.5f);
        System.out.println(Float.valueOf("30.00") / 100);
    }


    @Test
    public void testDate1() {
        System.out.println(DateUtils.format(DateUtils.autoParseDate("1989-04-13 00:00:00.0")));
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
        String host = "47.95.208.138";
        int port = 9022;
        String loginUser = "mysftp";
        String password = "mysftp";

        SftpUtil instance = SftpUtil.getInstance(host, port, loginUser, password, null, null);

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
    public void testToString() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        System.out.println(list.toString());
    }

    @Test
    public void test33() {
        Map<String, String> map = new HashMap<>();
        int i = 5;
        if (i < 10) {
            map.put("msg:", "i小于10");
            logger.info(map.toString());
            return;
        }

        if (i < 5) {
            map.put("msg:", "i小于5");
            logger.info(map.toString());
            return;
        }
    }


    @Test
    public void test34() {
        //
        // double b = 0.665999999999;
        // BigDecimal uabLimitStA = new BigDecimal("33");
        // BigDecimal decimalUabA = BigDecimal.valueOf(b).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        // System.out.println("B:" + decimalUabA);
        // System.out.println(decimalUabA.multiply(BigDecimal.valueOf(100)).compareTo(uabLimitStA));
        // System.out.println(uabLimitStA.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_EVEN).compareTo(decimalUabA));

        // System.out.println(decimalUabA.multiply(BigDecimal.valueOf(100)).subtract(uabLimitStA).intValue() + "%");


        BigDecimal bigA = new BigDecimal("0.0022");
        BigDecimal bigB = new BigDecimal("0.1");
        NumberFormat format = NumberFormat.getInstance();
        // 不使用千分位，即展示为11672283.234，而不是11,672,283.234
        format.setGroupingUsed(false);
        // 设置数的小数部分所允许的最大位数
        format.setMaximumFractionDigits(4);
        System.out.println(Double.valueOf(format.format(Double.valueOf(bigA.multiply(bigB).setScale(4, BigDecimal.ROUND_HALF_EVEN).toString()))));
        // System.out.println("1:::::" + bigA.multiply(bigB).setScale(4, BigDecimal.ROUND_HALF_EVEN).toString());
        // System.out.println("2:::::" + bigA.multiply(bigB).setScale(4, BigDecimal.ROUND_HALF_EVEN).toPlainString());
        // System.out.println("4:::::" + Double.valueOf(bigA.multiply(bigB).setScale(4, BigDecimal.ROUND_HALF_EVEN).stripTrailingZeros().toPlainString()));


    }

}
