package com.assocaition.management.module.foreground.service;

import com.assocaition.management.module.authority.entity.AuthSysUser;

/**
 * @author Duanjianhui
 * @date 2021-04-28 3:56 下午
 * @describe
 */
public interface ForegroundIUserService {
    AuthSysUser findByUserTelAndPwd(AuthSysUser user);

    int addUser(AuthSysUser user);
}
