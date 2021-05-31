package com.assocaition.management.module.foreground.service.person;

import com.assocaition.management.module.authority.entity.AuthSysUser;

/**
 * @author Duanjianhui
 * @date 2021-05-01 12:22 上午
 * @describe
 */
public interface PersonService {
    int updateUser(AuthSysUser user);

    AuthSysUser selectUserById(Integer urId);
}
