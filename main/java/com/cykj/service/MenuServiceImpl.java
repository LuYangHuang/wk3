package com.cykj.service;


import com.cykj.mapper.MenuMapper;
import com.cykj.pojo.TMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public Map<String, List<TMenu>> findMenu(int roleId) {
        Map<String, List<TMenu>> menuMap = new LinkedHashMap();
        System.out.println("roleId:" + roleId);
        //查询一级菜单
        List<TMenu> TMenuList = menuMapper.findMenusByPid(roleId, 0);

        System.out.println(TMenuList);
        for (TMenu menu : TMenuList) {
            //查询二级菜单
            System.out.println("父级菜单ID：" + menu.getId());
            List<TMenu> sonTMenuList = menuMapper.findMenusSon(menu.getId());
            menuMap.put(menu.getName(), sonTMenuList);
        }
        return menuMap;
    }

    @Override
    public Map<String, List<TMenu>> findKinderMenu(int roleId) {
        Map<String, List<TMenu>> menuMap = new LinkedHashMap();
        //查询一级菜单
        List<TMenu> TMenuList = menuMapper.findMenusByPid(roleId, 0);
//        List<TMenu> TMenuList = menuMapper.findKinderMenuByPid(100);

        System.out.println(TMenuList);
        for (TMenu menu : TMenuList) {
            //查询二级菜单
            System.out.println("父级菜单ID：" + menu.getId());
            List<TMenu> sonTMenuList = menuMapper.findMenusSon(menu.getId());
            menuMap.put(menu.getName(), sonTMenuList);
        }
        return menuMap;
    }


}
