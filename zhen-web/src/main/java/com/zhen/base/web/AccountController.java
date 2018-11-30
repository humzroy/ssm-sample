package com.zhen.base.web;

import com.zhen.base.service.IAccountService;
import com.zhen.utils.ResponseUtil;
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
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    /**
     * @description :用户登录
     * @author : wuhengzhen
     * @date : 2018-8-2 18:57
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object accountLogin(HttpServletRequest request) {
        // 用户名
        String loginId = request.getParameter("loginId");
        // 密码
        String password = request.getParameter("password");
        ResponseUtil responseUtil = ResponseUtil.createResponseUtil();
        responseUtil.setMessageCode("login");

        int record = accountService.accountLogin(loginId, password);
        if (record == 1) {
            responseUtil.setMessage("登录成功！");
            responseUtil.putValueToData("status" , "200");

        } else {
            responseUtil.setMessage("登录失败！");
            responseUtil.setSuccess(false);
        }
        return responseUtil;
    }

}
