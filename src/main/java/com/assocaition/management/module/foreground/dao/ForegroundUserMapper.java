package com.assocaition.management.module.foreground.dao;

import com.assocaition.management.module.authority.entity.AuthSysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Duanjianhui
 * @date 2021-04-28 4:03 下午
 * @describe
 */
@Mapper
public interface ForegroundUserMapper {

    AuthSysUser findByUserTelAndPwd(AuthSysUser user);

    int addUser(AuthSysUser user);
}
