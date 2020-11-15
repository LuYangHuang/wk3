package com.cykj.service;


import com.cykj.mapper.HealthMapper;
import com.cykj.pojo.TBaby;
import com.cykj.pojo.THealthcare;
import com.cykj.pojo.TParam;
import com.cykj.pojo.TableInf;
import com.google.gson.Gson;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class HealthServiceImpl implements HealthService {
    @Resource
    private HealthMapper healthMapper;

    @Override
    public String findBabyHealth(RowBounds rb, Map<String, Object> condition) {
        //查询所有的数据
        List<THealthcare> tHealthcareList = healthMapper.findBabyHealth(rb, condition);
        //查询所有数据的总个数
        int count = healthMapper.findBabyHealthCount(condition);
        TableInf tableInf = new TableInf(0, "数据列表信息", count, tHealthcareList);
        //用json将数据封装成string类型返回
        String data = new Gson().toJson(tableInf);
        return data;
    }

    @Override
    public int updateBaby(THealthcare tHealthcare) {
        int n = healthMapper.updateBaby(tHealthcare);
        return n;
    }

    @Override
    public int updateBabyName(THealthcare tHealthcare) {
        int n = healthMapper.updateBabyName(tHealthcare);
        return n;
    }

    @Override
    public List<TParam> situationSelect() {
        return healthMapper.situationSelect();
    }

    @Override
    public List<TBaby> selectBaby(String name) {
        return healthMapper.selectBaby(name);
    }

    @Override
    public int insertBaby(THealthcare tHealthcare) {
        int n = healthMapper.insertBaby(tHealthcare);
        return n;
    }

    @Override
    public THealthcare selectBabyInThealcare(int id) {
        return healthMapper.selectBabyInThealcare(id);
    }
}
