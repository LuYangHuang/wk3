package com.cykj.service;

import com.cykj.pojo.TMenu;

import java.util.List;
import java.util.Map;

public interface MenuService {

    Map<String, List<TMenu>> findMenu(int roleId);


    Map<String, List<TMenu>> findKinderMenu(int roleId);
}
