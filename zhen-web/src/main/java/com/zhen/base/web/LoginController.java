package com.zhen.base.web;

import com.zhen.base.service.ILoginService;
import com.zhen.utils.ExceptionUtil;
import com.zhen.utils.ResponseUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : wuhengzhen
 * @Description : AccountController
 * @date : 2018/08/02 18:54
 * @copyright:长安新生（深圳）金融投资有限公司
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ILoginService accountService;

    /**
     * @description :用户登录
     * @author : wuhengzhen
     * @date : 2018-8-2 18:57
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object accountLogin(HttpServletRequest request) {
        // 用户名
        String userName = request.getParameter("userName");
        // 密码
        String password = request.getParameter("password");
        logger.info("登陆用户名：" + userName);
        logger.info("登陆密码：" + password);
        ResponseUtil responseUtil = ResponseUtil.createResponseUtil();
        responseUtil.setMessageCode("login");

        //创建Subject实例对象
        Subject currentUser = SecurityUtils.getSubject();
        //判断当前用户是否已登录
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            try {
                currentUser.login(token);
            } catch (AuthenticationException e) {
                responseUtil.setSuccess(false);
                responseUtil.setMessage("登陆失败！");
                responseUtil.setMessageCode("9999");
                logger.error("登陆失败！");
                logger.error(ExceptionUtil.getStackTrace(e));
                return "logon";
            }
        }
        return responseUtil;
    }

}
