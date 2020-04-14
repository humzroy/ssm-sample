package com.zhen.base;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.ChannelSftp;
import com.zhen.exception.BusinessException;
import com.zhen.util.*;
import io.github.biezhi.ome.OhMyEmail;
import io.github.biezhi.ome.SendMailException;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
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
// @ContextConfiguration(locations = {"/spring-test.xml", "/spring-threadpool.xml"})
//extends AbstractJUnit4SpringContextTests
public class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    private static final DecimalFormat df = new DecimalFormat("#0.00");
    public static final String CONF_HOME = "";

    @Before
    public void before() throws Exception {
        OhMyEmail.config(OhMyEmail.SMTP_163(false), "wu_worktest@163.com", "123456a");

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
    // private RedisUtil redisUtils;

    // @Autowired
    // ThreadPoolTaskExecutor threadPoolTaskExecutor;

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
     *
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @Test
    public void testMD5() throws UnsupportedEncodingException, NoSuchAlgorithmException {
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
    public void testMd5() throws NoSuchAlgorithmException {
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
        System.out.println(strings.get("wmsTRADE_DATA_DT"));
        // jedisUtil.getJedis().del("wmswms-BastPath");
        // System.out.println(strings.get("wmswms-BastPath"));
        // JedisUtil.Hash hash = jedisUtil.new Hash();
        //
        // byte[] bytes = hash.hget(SafeEncoder.encode("wmsadm_wms_params"), SafeEncoder.encode("RANK_CODE"));
        // Map<String, List<AdmWmsParams>> listMap = (Map<String, List<AdmWmsParams>>) SerializeUtil.unserialize(bytes);
        // System.out.println(listMap.toString());
        // List<AdmWmsParams> admWmsParams = listMap.get("CN");
        // if (CollectionUtils.isNotEmpty(admWmsParams)) {
        //     long start = System.currentTimeMillis();
        //     for (AdmWmsParams adm : admWmsParams) {
        //         if ("R1".equalsIgnoreCase(adm.getParamCode())) {
        //             paramName = adm.getParamName();
        //             break;
        //         }
        //     }
        //     long end = System.currentTimeMillis();
        //     logger.info("完成，耗时：" + (end - start) + " ms");
        // }
        // System.out.println(paramName);

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
            logger.info("===========================: "+ArrayUtil.toString(fileList));
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


    /**
     * @description：精度处理
     * @author：wuhengzhen
     * @date：2019/10/30 10:58
     **/
    private String dealPrecision(BigDecimal weight) {
        String res;
        if (weight == null) {
            return "";
        }
        int compareResultA;
        int compareResultB;
        if (weight.compareTo(BigDecimal.ONE) > 0) {
            compareResultA = weight.compareTo(new BigDecimal("100"));
            compareResultB = weight.compareTo(new BigDecimal("99.99"));
            if (compareResultA < 0 && compareResultB > 0) {
                // 大于99.99 但 小于100，约等于99.99
                weight = new BigDecimal("99.99");
                res = "≈" + weight.toPlainString();
            } else {
                res = df.format(weight);
            }

        } else {
            compareResultA = weight.compareTo(BigDecimal.ZERO);
            compareResultB = weight.compareTo(new BigDecimal("0.01"));
            if (compareResultA > 0 && compareResultB < 0) {
                // 大于0 但 小于0.01，约等于0.01
                weight = new BigDecimal("0.01");
                res = "≈" + weight.toPlainString();
            } else {
                res = df.format(weight);
            }
        }
        return res;

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

}
