package com.assocaition.management.module.authority.service;


import com.assocaition.management.module.authority.entity.AuthSysPermission;

import java.util.List;

public interface IPermissionService {
    List<AuthSysPermission> searchPermissions();
    List<AuthSysPermission> searchUserPermissions(String userName);
}
