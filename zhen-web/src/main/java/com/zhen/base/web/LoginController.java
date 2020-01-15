package com.zhen.base.web;

import com.alibaba.fastjson.JSON;
import com.zhen.base.service.ILoginService;
import com.zhen.common.master.BaseRequest;
import com.zhen.common.master.BaseResult;
import com.zhen.common.master.Master;
import com.zhen.exception.BusinessException;
import com.zhen.util.CookieUtil;
import com.zhen.util.ExceptionUtil;
import com.zhen.util.shiro.ShiroUser;
import com.zhen.util.shiro.ShiroUtil;
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
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : wuhengzhen
 * @description : LoginController
 * @date : 2018/08/02 18:54
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ILoginService loginService;

    /**
     * @description :用户登录
     * @author : wuhengzhen
     * @date : 2018-8-2 18:57
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object accountLogin(HttpServletRequest request, HttpServletResponse response, Master master) {
        // 获得当前请求的sessionId
        String sessionId = request.getSession().getId();
        // 用户名
        String userName = request.getParameter("userName");
        // 密码
        String password = request.getParameter("password");
        logger.info("登陆用户名：" + userName);
        logger.info("登陆密码：" + password);
        logger.info("sessionId：" + sessionId);
        BaseRequest baseRequest = BaseRequest.createRequest(request, master);
        BaseResult baseResult = BaseResult.createBaseResult();

        // 判断当前用户是否已登录
        if (!ShiroUtil.isLogin()) {
            try {
                saveSignInfo(sessionId, userName, password, baseRequest);
                saveCookie(request, response);
                baseResult.setMessage("登陆验证成功！");
                baseResult.setMessageCode("0000");
                baseResult.putValueToData("data", ShiroUtil.getShiroUser());
            } catch (AuthenticationException e) {
                baseResult.setSuccess(false);
                baseResult.setMessage("登陆验证失败！");
                baseResult.setMessageCode("9999");
                logger.error("登陆验证失败！");
                logger.error(ExceptionUtil.getStackTrace(e));
                return baseResult;
            } catch (BusinessException ex) {
                baseResult.setSuccess(false);
                baseResult.setMessage("登陆失败！");
                baseResult.setMessageCode("9999");
                logger.error("缓存用户信息到Redis/Cookie失败！");
                logger.error(ExceptionUtil.getStackTrace(ex));
                return baseResult;
            }
        } else {
            baseResult.setMessage("登陆成功！");
            baseResult.setMessageCode("0000");
            baseResult.putValueToData("data", ShiroUtil.getShiroUser());
        }
        return baseResult;
    }

    /**
     * description :存入cookie
     * author : wuhengzhen
     * date : 2018-12-5 16:29
     *
     * @param request
     * @param response
     */
    private void saveCookie(HttpServletRequest request, HttpServletResponse response) {
        ShiroUser shiroUser = ShiroUtil.getShiroUser();
        //将登录信息数据放入cookie
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("sessionId", shiroUser.getSessionId());
        tokenMap.put("userCde", shiroUser.getUserCde());
        tokenMap.put("userName", shiroUser.getUserName());
        tokenMap.put("email", shiroUser.getEmail());
        String tokens = JSON.toJSONString(tokenMap);
        logger.info("封装tokens信息成功" + tokens);

        CookieUtil.setCookie(request, response, "token", tokens, 60 * 60, true);
        // CookieUtil.setCookie(request, response, "masterSign", shiroUser.getSessionId(), 60 * 60, true);
        logger.info("存入cookie成功");
    }

    /**
     * description :登出
     * author : wuhengzhen
     * date : 2018-12-5 15:58
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public Object accountLogout(HttpServletRequest request, HttpServletResponse response) {
        BaseResult baseResult = BaseResult.createBaseResult();
        logger.info("session:" + ShiroUtil.getSubject().getSession().getId());

        try {
            Subject subject = ShiroUtil.getSubject();
            ShiroUtil.logout(subject);
            CookieUtil.setCookie(request, response, "token", null, 0);
            // Cookie cookie = new Cookie("token", null);
            // // 注销
            // cookie.setMaxAge(0);
            // cookie.setPath("/");
            // response.addCookie(cookie);
            logger.info("[注销]" + "跳转login页面");
            baseResult.setMessageCode("0000");
            baseResult.setMessage("注销成功！");
        } catch (AuthenticationException e) {
            logger.error(ExceptionUtil.getStackTrace(e));
            baseResult.setSuccess(false);
            baseResult.setMessageCode("9999");
            baseResult.setMessage("注销失败！");
        }
        return baseResult;
    }

    /**
     * 保存登录信息
     *
     * @param sessionId   session
     * @param userName    登录用户名
     * @param password    密码
     * @param baseRequest 公共参数
     */
    private void saveSignInfo(String sessionId, String userName, String password, BaseRequest baseRequest) {
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        ShiroUtil.getSubject().login(token);
        ShiroUtil.getShiroUser().setSessionId(sessionId);
        logger.info(userName + " -> Shiro验证成功！");
        baseRequest.putValueToData("shiroUser", ShiroUtil.getShiroUser());
        // 将用户信息放入到redis
        loginService.saveUserInfoToRedis(baseRequest);
    }
}
