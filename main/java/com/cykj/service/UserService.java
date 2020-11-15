package com.cykj.service;

import com.cykj.pojo.TKindergarten;
import com.cykj.pojo.TStaff;
import com.cykj.pojo.User;

public interface UserService {

    User login(String name, String userPwd);


    TStaff staffLogin(String account, String pwd);

    TKindergarten kinderLogin(String account, String pwd);
}
