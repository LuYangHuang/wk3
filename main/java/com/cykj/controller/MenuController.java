package com.cykj.controller;


import com.cykj.pojo.TMenu;
import com.cykj.pojo.TStaff;
import com.cykj.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuServiceImpl;

    /**
     * 后台staff登录查找权限菜单
     **/
    @RequestMapping(value = "/findMenu", produces = {"application/json;charset=utf-8"})
    public String getFindMenu(HttpServletRequest req) {
        TStaff loginTStaff = (TStaff) req.getSession().getAttribute("loginTStaff");
        int roleId = loginTStaff.getRole();
        Map<String, List<TMenu>> menuMap = menuServiceImpl.findMenu(roleId);
        req.getSession().setAttribute("menuMap", menuMap);
        return "staff-main";
    }

    /**
     * 后台kinder登录查找权限菜单
     **/
    @RequestMapping(value = "/findKinderMenu", produces = {"application/json;charset=utf-8"})
    public String getFindKinderMenu(HttpServletRequest req) {
        int roleId = 1;
        Map<String, List<TMenu>> menuMap = menuServiceImpl.findKinderMenu(roleId);
        req.getSession().setAttribute("menuMap", menuMap);
        return "kindergarten-main";
    }

    /**
     * 页面跳转---test
     **/
    @RequestMapping(value = "/test")
    public String getUserManager() {
        return "test";
    }

    @RequestMapping(value = "/healthcare")
    public String getHealthcare() {
        return "healthcare";
    }
}
