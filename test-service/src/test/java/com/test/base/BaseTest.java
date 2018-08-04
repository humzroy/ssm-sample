/**
 * 软件著作权：长安新生（深圳）金融投资有限公司
 *
 * 系统名称：马达贷
 *
 */
package com.test.base;



import com.test.base.service.IUsrRoleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


/**
 *
 *
 * @author 郑翔
 */
@ContextConfiguration(locations = { "/spring-test.xml" })
public class BaseTest   extends AbstractJUnit4SpringContextTests {

    @Autowired
    IUsrRoleService usrRoleService;
    @Test
    public void test() {

    	System.out.println(usrRoleService.getUsrRole().getUsr_lev());

    }

}
