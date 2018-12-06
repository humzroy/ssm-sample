/**
 * 软件著作权：长安新生（深圳）金融投资有限公司
 * <p>
 * 系统名称：马达贷
 */
package com.zhen.base;


import com.zhen.utils.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Base Tester.
 *
 * @author wuhengzhen
 * @version 1.0
 * @since <pre>09/07/2018</pre>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-redis.xml"})
// @ContextConfiguration(locations = {"/spring-zhen.xml", })
public class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    // @Autowired
    // IUsrRoleService usrRoleService;
    // @Autowired
    // private UserMapper userMapper;
    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void test() throws Exception {
        pritNoBug();
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

    /**
     * 16进制
     */
    @Test
    public void testHex() {

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
        // redisUtils.set("name", "admin");
        // redisUtils.del("name");
        System.out.println(redisUtils.getObject("179F45AC44D31E4882DD42A2C3AEB30E").toString());

        // System.out.println(redisUtils.getObject("myKey"));


    }

    public void pritNoBug() throws Exception {
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
        System.out.println(new String(Base64Util.decryptBASE64(base64fozu)));
    }

    @Test
    public void testIdNo(){
        String idNo = "37028519940507323x";
        System.out.println(RegexUtils.isIDNumber(idNo));
    }
}
