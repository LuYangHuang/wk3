package com.cykj.controller;


import com.cykj.pojo.User;
import com.cykj.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/hello")
public class HelloController {

    @Resource
    private UserService userServiceImpl;

//    @RequestMapping("/login")
////    public String toLogin(){
////        return "login";
////    }

    @RequestMapping("/hello1")
    public String hello1(HttpServletRequest req) {

        req.setAttribute("user1", "用户1");
        req.getSession().setAttribute("user2", "用户2");
        return "hello1";
    }


    @RequestMapping("/login.action")
    public ModelAndView login(String userName, String userPwd) {
        ModelAndView modelAndView = new ModelAndView();
        User loginUser = userServiceImpl.login(userName, userPwd);
        if (null != loginUser) {
            modelAndView.addObject("loginUser", loginUser);
            modelAndView.setViewName("success");
        } else {
            modelAndView.setViewName("login");
        }
        return modelAndView;
    }


}
