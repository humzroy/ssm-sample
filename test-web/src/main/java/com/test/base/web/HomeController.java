package com.test.base.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.base.service.IUsrRoleService;

/**
 * Created by kevin on 2017/6/11.
 */


@Controller
@RequestMapping("/base/home")
public class HomeController {
    @Autowired
    IUsrRoleService usrRoleService;
    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request) {
        System.out.println(usrRoleService.getUsrRole());
        return "index";
    }


}
