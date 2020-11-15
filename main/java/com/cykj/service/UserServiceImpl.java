package com.cykj.service;

import com.cykj.mapper.UserMapper;
import com.cykj.pojo.TKindergarten;
import com.cykj.pojo.TStaff;
import com.cykj.pojo.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User login(String userName, String userPwd) {
        return userMapper.findUser(userName, userPwd);
    }

    @Override
    public TStaff staffLogin(String account, String pwd) {
        return userMapper.staffLogin(account, pwd);
    }

    @Override
    public TKindergarten kinderLogin(String account, String pwd) {
        return userMapper.kinderLogin(account, pwd);
    }
}
