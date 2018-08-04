package com.test.base.web;

import com.test.base.service.IAccountService;
import com.test.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : wuhengzhen
 * @Description :
 * @date : 2018/08/02 20:01
 * @system name:新一代消费金融系统
 * @copyright:长安新生（深圳）金融投资有限公司
 */
@Controller
@RequestMapping("/hm_users")
public class UserController {
    @Autowired
    private IAccountService accountService;

    /**
     * @description :新增用户
     * @author : wuhengzhen
     * @date : 2018-8-2 20:27
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    @ResponseBody
    public Object createUser(HttpServletRequest request) {

        boolean flag = false;

        String username = request.getParameter("username");
        String loginid = request.getParameter("loginid");
        String password = request.getParameter("password");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        String avatar = request.getParameter("avatar");
        String createTime = request.getParameter("createTime");
        String lastUpdateTime = request.getParameter("lastUpdateTime");
        String lastLoginTime = request.getParameter("lastLoginTime");
        String securityLevel = request.getParameter("securityLevel");
        String type = request.getParameter("type");
        ResponseUtil responseUtil = ResponseUtil.createResponseUtil();

        Map<String, String> param = new HashMap<>();
        param.put("username", username);
        param.put("loginid", loginid);
        param.put("password", password);
        param.put("mobile", mobile);
        param.put("email", email);
        param.put("avatar", avatar);
        param.put("createTime", createTime);
        param.put("lastUpdateTime", lastUpdateTime);
        param.put("lastLoginTime", lastLoginTime);
        param.put("securityLevel", securityLevel);
        param.put("type", type);

        int record = accountService.createUser(param);
        if (record == 1) {
            flag = true;
            responseUtil.setMessage("创建用户成功！");
            responseUtil.putValueToData("result", flag);
        } else {
            responseUtil.setSuccess(false);
            responseUtil.setMessage("创建用户失败！");
            responseUtil.putValueToData("result", flag);
        }
        return responseUtil;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public Object editUser(HttpServletRequest request) {
        boolean flag = false;
        ResponseUtil responseUtil = ResponseUtil.createResponseUtil();
        String hmUserId = request.getParameter("hm_user_id");
        String userName = request.getParameter("username");
        String loginid = request.getParameter("loginid");
        String password = request.getParameter("password");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        String avatar = request.getParameter("avatar");
        String lastLoginTime = request.getParameter("lastLoginTime");
        String securityLevel = request.getParameter("securityLevel");
        String type = request.getParameter("type");

        Map<String, String> param = new HashMap<>();
        param.put("hmUserId", hmUserId);
        param.put("userName", userName);
        param.put("loginid", loginid);
        param.put("password", password);
        param.put("mobile", mobile);
        param.put("email", email);
        param.put("avatar", avatar);
        param.put("lastLoginTime", lastLoginTime);
        param.put("securityLevel", securityLevel);
        param.put("type", type);

        int record = accountService.updateUser(param);
        if (record == 1) {
            flag = true;
            responseUtil.setMessage("修改用户成功！");
            responseUtil.putValueToData("result", flag);
        } else {
            responseUtil.setSuccess(false);
            responseUtil.setMessage("修改用户失败！");
            responseUtil.putValueToData("result", flag);
        }
        return responseUtil;
    }


}
