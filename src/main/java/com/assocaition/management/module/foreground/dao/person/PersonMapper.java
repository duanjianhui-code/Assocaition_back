package com.assocaition.management.module.foreground.dao.person;

import com.assocaition.management.module.authority.entity.AuthSysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Duanjianhui
 * @date 2021-05-01 12:21 上午
 * @describe
 */
@Mapper
public interface PersonMapper {

    int updateUser(@Param("user") AuthSysUser user);

    AuthSysUser selectUserById(Integer urId);
}
