package com.cykj.service;

import com.cykj.pojo.TBaby;
import com.cykj.pojo.THealthcare;
import com.cykj.pojo.TParam;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface HealthService {


    String findBabyHealth(RowBounds rb, Map<String, Object> condition);

    int updateBaby(THealthcare tHealthcare);

    int updateBabyName(THealthcare tHealthcare);

    List<TParam> situationSelect();

    List<TBaby> selectBaby(String name);

    int insertBaby(THealthcare tHealthcare);

    THealthcare selectBabyInThealcare(int id);


}
