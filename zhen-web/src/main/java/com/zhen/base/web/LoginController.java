package com.zhen.base.web;

import com.zhen.base.service.ILoginService;
import com.zhen.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : wuhengzhen
 * @description : LoginController
 * @date : 2018/08/02 18:54
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ILoginService loginService;

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
        ResponseUtil responseUtil = ResponseUtil.createResponseUtil();
        responseUtil.setMessageCode("login");


        return responseUtil;
    }

}
