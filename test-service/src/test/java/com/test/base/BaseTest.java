/**
 * 软件著作权：长安新生（深圳）金融投资有限公司
 * <p>
 * 系统名称：马达贷
 */
package com.test.base;


import com.test.base.dao.system.UserMapper;
import com.test.base.domain.system.User;
import com.test.utils.MD5Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import sun.security.provider.MD5;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Base Tester.
 *
 * @author wuhengzhen
 * @version 1.0
 * @since <pre>09/07/2018</pre>
 */
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = {"/spring-test.xml"})
public class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    // @Autowired
    // IUsrRoleService usrRoleService;
    // @Autowired
    // private UserMapper userMapper;


    @Test
    public void test() {

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
}
