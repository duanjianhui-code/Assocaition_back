package com.assocaition.management.module.authority.service;



import com.assocaition.management.module.authority.entity.AuthSysUser;

import java.util.List;

public interface IUserService {
    AuthSysUser findByUserName(String username);

    List<AuthSysUser> findAll();

    void assignUserRoles(AuthSysUser user);

    List<String> getUserPermssions(String username);

    int updateUser(AuthSysUser user);

    AuthSysUser selectUserById(Integer userId);

    void deleteUserByUserId(Integer urId);

    void updateUser1(AuthSysUser authSysUser);

    void updateUserByUserId(Integer urId);

    List<Integer> selectUserId(String type);
}
