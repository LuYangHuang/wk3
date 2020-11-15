package com.cykj.mapper;


import com.cykj.pojo.TMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MenuMapper {


    List<TMenu> findMenusByPid(@Param("roleId") int roleId, @Param("pid") int pid);

    List<TMenu> findMenusSon(@Param("pid") int pid);

    List<TMenu> findKinderMenuByPid(@Param("pid") int pid);
}
