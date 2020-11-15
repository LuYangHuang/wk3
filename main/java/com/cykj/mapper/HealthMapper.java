package com.cykj.mapper;


import com.cykj.pojo.TBaby;
import com.cykj.pojo.THealthcare;
import com.cykj.pojo.TParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface HealthMapper {

    int findBabyHealthCount(Map<String, Object> condition);

    List<THealthcare> findBabyHealth(RowBounds rb, Map<String, Object> condition);

    int updateBaby(THealthcare tHealthcare);

    int updateBabyName(THealthcare tHealthcare);

    List<TParam> situationSelect();

    List<TBaby> selectBaby(@Param("name") String name);

    int insertBaby(THealthcare tHealthcare);

    THealthcare selectBabyInThealcare(@Param("id") int id);
}
