/**
 * 软件著作权：长安新生（深圳）金融投资有限公司
 * <p>
 * 系统名称：马达贷
 */
package com.test.base;


import com.test.utils.UUIDUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Base Tester.
 *
 * @author wuhengzhen
 * @since <pre>09/07/2018</pre>
 * @version 1.0
 */
// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = {"/spring-test.xml"})
public class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    // @Autowired
    // IUsrRoleService usrRoleService;

    @Test
    public void test() {

        UUIDUtil uuidUtil = new UUIDUtil();
        logger.info(uuidUtil.getUnid());

    }

}
