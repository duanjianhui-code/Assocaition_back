package com.assocaition.management.module.authority.service;



import com.assocaition.management.module.authority.entity.AuthSysRole;

import java.util.List;

public interface IRoleService {
    List<AuthSysRole> search(String roleName);

    AuthSysRole findById(int rlId);

    AuthSysRole add(AuthSysRole role);

    void deleteRole(int rlId);

    void update(int rlId, AuthSysRole role);

    boolean roleHasMenu(int rlId, int mnId);

    List<Integer> searchMenuRoleIds(String mnName);

    void updateRolePermissions(AuthSysRole role);

    int addRole(Integer urId);

    int updateUserType(Integer userId);
}
