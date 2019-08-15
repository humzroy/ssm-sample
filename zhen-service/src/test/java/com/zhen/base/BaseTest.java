/**
 * 软件著作权：长安新生（深圳）金融投资有限公司
 * <p>
 * 系统名称：马达贷
 */
package com.zhen.base;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import java.security.NoSuchAlgorithmException;
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
    public void testStr() {

        String allInfoStr = "X00004_FUND;%;X04;%;0;%;上海农商银行|4561;%;X00001_FUND;%;X01;%;0;%;10296574212677734410295457241186406X00004_FUND;%;9000;%;X00004;%;X00001;%;SRCB;^;";
        //得到信息的数组
        String[] allInfo = allInfoStr.split(";\\^;");
        String[] pro = allInfo[0].split(";%;");
        for (String s : pro) {
            System.out.println(s);
        }

        logger.info("********************");

        List<String> cpfCodes = new ArrayList<>();//定义持仓表主键集合
        Map<String, String> unitMap = new HashMap<>();//根据cpfCode,取unit
        Map<String, String> bankCodeMap = new HashMap<>();//根据cpfCode,取bankCode
        Map<String, String> inProCodeMap = new HashMap<>();//转入基金代码
        Map<String, String> inProNameMap = new HashMap<>();//转入基金名称
        // 原基金份额分类
        String proChargeType = "";//得到具体的每条信息
        cpfCodes.add(pro[7]);//将持仓表主键放入集合

        unitMap.put(pro[7], pro[8]);//将主键和赎回单位数放入map
        bankCodeMap.put(pro[7], pro[11]);//将主键和bankCode放入map
        inProCodeMap.put(pro[7], pro[4]);//将inProCode转入基金代码放入map
        inProNameMap.put(pro[7], pro[5]);//将inProName转入基金名称放入map
        proChargeType = pro[2];
        System.out.println(proChargeType);


    }

    @Test
    public void testHttp() {

        String crs = "eyJjbGllbnRfaWQiOiIxNDgiLCJqc29uX2lkIjoiIiwiY3VzdF9mbGFnIjoiMSIsImVuZ19uYW1l\n" +
                "Ijoiamlnb3VtaW5nY2hlbmciLCJjdXJyZW50X3dvcmtfbmF0aW9uIjoiMTU2IiwiY3VycmVudF93\n" +
                "b3JrX2NpdHkiOiLmna3lt54iLCJjdXJyZW50X3dvcmtfYWRkciI6Iuaxn+WNl+Wkp+mBkyIsImVu\n" +
                "Z19jdXJyZW50X3dvcmtfbmF0aW9uIjoiMTU2IiwiZW5nX2N1cnJlbnRfYWRkciI6Imhhbmd6aG91\n" +
                "IiwiY3VycmVudF9hZGRyIjoiamlhbmduYW5kYWRhbyIsIm9yaV9jdXN0X3R5cGUiOiIxIiwiaXNf\n" +
                "bmVlZF9jb250cm9sbGVyIjoiMSIsIm1haWxfbmF0aW9uIjoiMTU2IiwibWFpbF9jaXR5Ijoi5p2t\n" +
                "5beeIiwibWFpbF9hZGRyIjoi5ruo5rGf5Yy6IiwiZW5nX21haWxfbmF0aW9uIjoiMTU2IiwiZW5n\n" +
                "X21haWxfY2l0eSI6Imhhbmd6aG91IiwiZW5nX21haWxfYWRkciI6ImJpbmppYW5ncXUiLCJyZWdf\n" +
                "bmF0aW9uIjoiMTU2IiwicmVnX2NpdHkiOiLkuIrmtbcxMSIsInJlZ19hZGRyIjoi6buE5rWm5rGf\n" +
                "IiwiZW5nX3JlZ19uYXRpb24iOiIxNTYiLCJlbmdfcmVnX2NpdHkiOiJzaGFuZ2hhaSIsImVuZ19y\n" +
                "ZWdfYWRkciI6Imh1YW5ncHVqaWFuZyIsInRheGluZm8iOlt7Impzb25faWQiOiIiLCJ0YXhfbmF0\n" +
                "aW9uIjoiMTU2IiwidGF4X25vIjoiMTIzMTIxMTMiLCJ0YXhfZXhwbGFpbiI6IuacquWPluW+l+WO\n" +
                "n+WboCIsImpzb25fYmVsb25nX2lkIjoiIiwiaXNfbWFpbl90YXgiOiIxIiwibm9fdGF4X25vX3Jl\n" +
                "YXNvbiI6IkIiLCJiZWxvbmdfdHlwZSI6IjAifSx7Impzb25faWQiOiIiLCJ0YXhfbmF0aW9uIjoi\n" +
                "MTU2IiwidGF4X25vIjoiMTIzMTIxMTMiLCJ0YXhfZXhwbGFpbiI6IuacquWPluW+l+WOn+WboCIs\n" +
                "Impzb25fYmVsb25nX2lkIjoiIiwiaXNfbWFpbl90YXgiOiIwIiwibm9fdGF4X25vX3JlYXNvbiI6\n" +
                "IkIiLCJiZWxvbmdfdHlwZSI6IjAifV0sImNvbnRyb2xsZXJpbmZvIjpbeyJqc29uX2lkIjoiIiwi\n" +
                "bmFtZSI6IuW8oOS4iSIsImVuZ19uYW1lIjoiWmhhbmciLCJlbmdfbGFzdF9uYW1lIjoiU2FuIiwi\n" +
                "Y3JzX2NvbnRyb2xfdHlwZSI6IjEiLCJjb250cm9sX3R5cGUiOiIxIiwiYmlydGhkYXkiOiIxOTkx\n" +
                "MDEwMSIsImJvcm5fbmF0aW9uIjoiMTU2IiwiaG9tZV9wbGFjZSI6Iuemj+W3niIsImVuZ19ib3Ju\n" +
                "X25hdGlvbiI6IjE1NiIsImVuZ19ob21lX3BsYWNlIjoiZnV6aG91IiwiY3VycmVudF9uYXRpb24i\n" +
                "OiIxNTYiLCJjdXJyZW50X2NpdHkiOiLljqbpl6giLCJjdXJyZW50X2FkZHIiOiLpvJPmtarlsb8x\n" +
                "MSIsImVuZ19jdXJyZW50X25hdGlvbiI6IjE1NiIsImVuZ19jdXJyZW50X2FkZHIiOiJ4aWFtZW4i\n" +
                "LCJlbmdfYWRkciI6Imd1bGFuZ3l1IiwiaG9sZF9yYXRpbyI6MC41LCJ0YXhpbmZvIjpbeyJqc29u\n" +
                "X2lkIjoiIiwidGF4X25hdGlvbiI6IjE1NiIsInRheF9ubyI6IjEyMzEyMyIsInRheF9leHBsYWlu\n" +
                "IjoiIiwianNvbl9iZWxvbmdfaWQiOiIiLCJpc19tYWluX3RheCI6IjAiLCJub190YXhfbm9fcmVh\n" +
                "c29uIjoiQSIsImJlbG9uZ190eXBlIjoiMSJ9XSwianNvbl9iZWxvbmdfaWQiOiIifSx7Impzb25f\n" +
                "aWQiOiIiLCJuYW1lIjoi5byg5ZubIiwiZW5nX25hbWUiOiJaaGFuZyIsImVuZ19sYXN0X25hbWUi\n" +
                "OiJzaSIsImNyc19jb250cm9sX3R5cGUiOiIxIiwiY29udHJvbF90eXBlIjoiMSIsImJpcnRoZGF5\n" +
                "IjoiMTk5MjAxMDEiLCJib3JuX25hdGlvbiI6IjE1NiIsImhvbWVfcGxhY2UiOiLnpo/lt54iLCJl\n" +
                "bmdfYm9ybl9uYXRpb24iOiIxNTYiLCJlbmdfaG9tZV9wbGFjZSI6ImZ1emhvdSIsImN1cnJlbnRf\n" +
                "bmF0aW9uIjoiMTU2IiwiY3VycmVudF9jaXR5Ijoi5Y6m6ZeoIiwiY3VycmVudF9hZGRyIjoi6byT\n" +
                "5rWq5bG/MTEiLCJlbmdfY3VycmVudF9uYXRpb24iOiIxNTYiLCJlbmdfY3VycmVudF9hZGRyIjoi\n" +
                "eGlhbWVuIiwiZW5nX2FkZHIiOiJndWxhbmd5dSIsImhvbGRfcmF0aW8iOjAuNSwidGF4aW5mbyI6\n" +
                "W3sianNvbl9pZCI6IiIsInRheF9uYXRpb24iOiIxNTYiLCJ0YXhfbm8iOiIxMjMxMjMiLCJ0YXhf\n" +
                "ZXhwbGFpbiI6IiIsImpzb25fYmVsb25nX2lkIjoiIiwiaXNfbWFpbl90YXgiOiIwIiwibm9fdGF4\n" +
                "X25vX3JlYXNvbiI6IkEiLCJiZWxvbmdfdHlwZSI6IjEifV0sImpzb25fYmVsb25nX2lkIjoiIn1d\n" +
                "fQ==";

        String crsUpdate = "eyJjbGllbnRfaWQiOiIxNDgiLCJjb250cm9sbGVyaW5mbyI6W3siYmlydGhkYXkiOiIxOTg5MDQx\n" +
                "MyIsImJvcm5fbmF0aW9uIjoiMTU2IiwiY29udHJvbF90eXBlIjoiMDA5IiwiY3JzX2NvbnRyb2xf\n" +
                "dHlwZSI6IjAiLCJjdXJyZW50X2FkZHIiOiLlkIzlronot68iLCJjdXJyZW50X2NpdHkiOiLpnZLl\n" +
                "spvluIIgIiwiY3VycmVudF9uYXRpb24iOiIxNTYiLCJlbmdfYWRkciI6InRuZ2FubHUiLCJlbmdf\n" +
                "Ym9ybl9uYXRpb24iOiIxNTYiLCJlbmdfY3VycmVudF9hZGRyIjoicWluZ2RhbyIsImVuZ19jdXJy\n" +
                "ZW50X25hdGlvbiI6IjE1NiIsImVuZ19sYXN0X25hbWUiOiJaaGlSZW4yIiwiZW5nX25hbWUiOiJL\n" +
                "b25nIiwiaG9sZF9yYXRpbyI6MC4zLCJqc29uX2JlbG9uZ19pZCI6IjIyIiwianNvbl9pZCI6IjEw\n" +
                "MDAwMDAwMjEiLCJuYW1lIjoi5o6n5Yi25Lq6MiIsInRheGluZm8iOlt7ImJlbG9uZ190eXBlIjoi\n" +
                "MSIsImlzX21haW5fdGF4IjoiMCIsImpzb25fYmVsb25nX2lkIjoiMTAwMDAwMDAyMSIsImpzb25f\n" +
                "aWQiOiIyMSIsInRheF9uYXRpb24iOiIxNTYiLCJ0YXhfbm8iOiIzMjI1NDIzNTIzNDUyMzQ1In1d\n" +
                "fV0sImN1cnJlbnRfYWRkciI6InRvbmdhbmx1IiwiY3VycmVudF93b3JrX2FkZHIiOiLlkIzlrono\n" +
                "t684ODblj7ciLCJjdXJyZW50X3dvcmtfY2l0eSI6IumdkuWym+W4giAiLCJjdXJyZW50X3dvcmtf\n" +
                "bmF0aW9uIjoiMTU2IiwiY3VzdF9mbGFnIjoiMCIsImVuZ19jdXJyZW50X2FkZHIiOiJxaW5nZGFv\n" +
                "IiwiZW5nX2N1cnJlbnRfd29ya19uYXRpb24iOiIxNTYiLCJlbmdfbWFpbF9hZGRyIjoidG9uZ2Fu\n" +
                "bHUiLCJlbmdfbWFpbF9jaXR5IjoicWluZ2RhbyIsImVuZ19tYWlsX25hdGlvbiI6IjE1NiIsImVu\n" +
                "Z19uYW1lIjoicWl5ZWtlaHUiLCJlbmdfcmVnX2FkZHIiOiJ0b25nYW5sdSIsImVuZ19yZWdfY2l0\n" +
                "eSI6InFpbmdkYW8iLCJlbmdfcmVnX25hdGlvbiI6IjE1NiIsImlzX25lZWRfY29udHJvbGxlciI6\n" +
                "IjEiLCJqc29uX2lkIjoiMjIiLCJtYWlsX2FkZHIiOiLlkIzlronot684ODblj7ciLCJtYWlsX2Np\n" +
                "dHkiOiLpnZLlspvluIIgIiwibWFpbF9uYXRpb24iOiIxNTYiLCJvcmlfY3VzdF90eXBlIjoiMSIs\n" +
                "InJlZ19hZGRyIjoi5ZCM5a6J6LevODg25Y+3IiwicmVnX2NpdHkiOiLpnZLlspvluIIgIiwicmVn\n" +
                "X25hdGlvbiI6IjE1NiIsInRheGluZm8iOlt7ImJlbG9uZ190eXBlIjoiMCIsImlzX21haW5fdGF4\n" +
                "IjoiMSIsImpzb25fYmVsb25nX2lkIjoiMjIiLCJqc29uX2lkIjoiMjMiLCJub190YXhfbm9fcmVh\n" +
                "c29uIjoiQiIsInRheF9leHBsYWluIjoiMzMzMzMzMzMiLCJ0YXhfbmF0aW9uIjoiMTU2In1dfQ==";
        Map map = new HashMap();

        System.out.println(crs);
        map.put("merid", "QINGDAOYICAICF40");
        map.put("crsinfo_json", crsUpdate);
        //map.put("merid", "");
        // 新增
        // String resp = HttpClientUtil.doGet("http://10.1.91.217:8208/fsdpl-api/ets40Service.call/ds/org/account/orgcrsinfooperate", map);
        // 修改
        String resp = HttpClientUtil.doGet("http://10.1.91.217:8208/fsdpl-api/ets40Service.call/ds/org/account/orgcrsinfooperate", map);
        System.out.println(resp);
    }


    @Test
    public void testJson1() {
        String resJson = "{\"code\":\"ETS-5BP0000\",\"crslist\":[{\"first_send_date\":\"20190422\",\"eng_current_addr\":\"hangzhou\",\"client_id\":\"148\",\"last_date\":\"20190422\",\"is_need_controller\":\"1\",\"eng_mail_nation\":\"156\",\"controllerinfo\":[{\"birthday\":\"19910101\",\"hold_ratio\":0.5,\"born_nation\":\"156\",\"eng_current_addr\":\"xiamen\",\"eng_born_nation\":\"156\",\"eng_current_nation\":\"156\",\"current_addr\":\"鼓浪屿11\",\"json_id\":\"1000000021\",\"json_belong_id\":\"22\",\"crs_control_type\":\"1\",\"home_place\":\"福州\",\"current_city\":\"厦门\",\"name\":\"张三\",\"eng_name\":\"Zhang\",\"eng_last_name\":\"San\",\"current_nation\":\"156\",\"taxinfo\":[{\"no_tax_no_reason\":\"A\",\"json_id\":\"21\",\"belong_type\":\"1\",\"tax_no\":\"123123\",\"json_belong_id\":\"1000000021\",\"is_main_tax\":\"0\",\"tax_nation\":\"156\"}],\"eng_addr\":\"gulangyu\",\"eng_home_place\":\"fuzhou\",\"control_type\":\"1\"},{\"birthday\":\"19920101\",\"hold_ratio\":0.5,\"born_nation\":\"156\",\"eng_current_addr\":\"xiamen\",\"eng_born_nation\":\"156\",\"eng_current_nation\":\"156\",\"current_addr\":\"鼓浪屿11\",\"json_id\":\"1000000022\",\"json_belong_id\":\"22\",\"crs_control_type\":\"1\",\"home_place\":\"福州\",\"current_city\":\"厦门\",\"name\":\"张四\",\"eng_name\":\"Zhang\",\"eng_last_name\":\"si\",\"current_nation\":\"156\",\"taxinfo\":[{\"no_tax_no_reason\":\"A\",\"json_id\":\"22\",\"belong_type\":\"1\",\"tax_no\":\"123123\",\"json_belong_id\":\"1000000022\",\"is_main_tax\":\"0\",\"tax_nation\":\"156\"}],\"eng_addr\":\"gulangyu\",\"eng_home_place\":\"fuzhou\",\"control_type\":\"1\"}],\"eng_reg_city\":\"shanghai\",\"current_work_city\":\"杭州\",\"ori_cust_type\":\"1\",\"eng_current_work_nation\":\"156\",\"mail_city\":\"杭州\",\"current_work_nation\":\"156\",\"current_work_addr\":\"江南大道\",\"reg_nation\":\"156\",\"mail_addr\":\"滨江区\",\"eng_reg_addr\":\"huangpujiang\",\"cust_flag\":\"1\",\"eng_mail_city\":\"hangzhou\",\"reg_city\":\"上海11\",\"current_addr\":\"jiangnandadao\",\"json_id\":\"22\",\"eng_reg_nation\":\"156\",\"eng_name\":\"jigoumingcheng\",\"mail_nation\":\"156\",\"eng_mail_addr\":\"binjiangqu\",\"reg_addr\":\"黄浦江\",\"taxinfo\":[{\"no_tax_no_reason\":\"B\",\"json_id\":\"23\",\"tax_explain\":\"未取得原因\",\"belong_type\":\"0\",\"tax_no\":\"12312113\",\"json_belong_id\":\"22\",\"is_main_tax\":\"1\",\"tax_nation\":\"156\"},{\"no_tax_no_reason\":\"B\",\"json_id\":\"24\",\"tax_explain\":\"未取得原因\",\"belong_type\":\"0\",\"tax_no\":\"12312113\",\"json_belong_id\":\"22\",\"is_main_tax\":\"0\",\"tax_nation\":\"156\"}]}],\"message\":\"\"}";

        String code = JSON.parseObject(resJson).getString("code");

        if ("ETS-5BP0000".equals(code)) {
            String crsListJson = JSON.parseObject(resJson).getString("crslist");
            System.out.println(crsListJson);

            JSONObject crsInfo = (JSONObject) JSON.parseArray(crsListJson).get(0);
            System.out.println(crsInfo);

            // Kongzhi ren
            JSONArray conArray = crsInfo.getJSONArray("controllerinfo");
            JSONObject con_object = (JSONObject) conArray.get(0);
            JSONArray contaxarray = con_object.getJSONArray("taxinfo");
            System.out.println(contaxarray);
            // System.out.println("json_id  1 -----" + crsInfo.getString("json_id"));
            // JSONArray taxInfoArray = crsInfo.getJSONArray("taxinfo");
            // logger.info("企业涉税信息有" + taxInfoArray.size() + "个，是：" + taxInfoArray.toJSONString());
            // if (2 <= taxInfoArray.size() - 1) {
            //     JSONObject taxInfo = (JSONObject) taxInfoArray.get(2);
            //     System.out.println(taxInfo.getString("json_id"));
            // } else {
            //     System.out.println("no!!!!");
            // }
            //
            // for (int i = 0; i < taxInfoArray.size(); i++) {
            //     JSONObject taxinfo = (JSONObject) taxInfoArray.get(i);
            //     System.out.println("org taxinfo json id ----" + taxinfo.getString("json_id"));
            // }

        }
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
    public void test10(){
        printDir("D:\\software\\cmder_mini");
        readFile("D:\\software\\cmder_mini");
    }

    // 文件所在的层数
    private int fileLevel;

    /**
     * 生成输出格式
     * @param name 输出的文件名或目录名
     * @param level 输出的文件名或者目录名所在的层次
     * @return 输出的字符串
     */
    public String createPrintStr(String name, int level) {
        // 输出的前缀
        String printStr = "";
        // 按层次进行缩进
        for (int i = 0; i < level; i ++) {
            printStr  = printStr + "  ";
        }
        printStr = printStr + "- " + name;
        return printStr;
    }

    /**
     * 输出初始给定的目录
     * @param dirPath 给定的目录
     */
    public void printDir(String dirPath){
        // 将给定的目录进行分割
        String[] dirNameList = dirPath.split("\\\\");
        // 设定文件level的base
        fileLevel = dirNameList.length;
        // 按格式输出
        for (int i = 0; i < dirNameList.length; i ++) {
            System.out.println(createPrintStr(dirNameList[i], i));
        }
    }

    /**
     * 输出给定目录下的文件，包括子目录中的文件
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
                fileLevel ++;
                // 递归子目录
                readFile(list[i].getPath());
                fileLevel --;
            } else {
                System.out.println(createPrintStr(list[i].getName(), fileLevel));
            }
        }
    }

}
