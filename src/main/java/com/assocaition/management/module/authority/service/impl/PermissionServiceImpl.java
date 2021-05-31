package com.assocaition.management.module.authority.service.impl;


import com.assocaition.management.module.authority.entity.AuthSysPermission;
import com.assocaition.management.module.authority.mapper.AuthSysOperatorMapper;
import com.assocaition.management.module.authority.mapper.AuthSysPermissionMapper;
import com.assocaition.management.module.authority.mapper.AuthSysResourcesMapper;
import com.assocaition.management.module.authority.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    private AuthSysPermissionMapper permissionMapper;
    @Autowired
    private AuthSysResourcesMapper resourcesMapper;
    @Autowired
    private AuthSysOperatorMapper operatorMapper;

    @Override
    public List<AuthSysPermission> searchPermissions() {
        List<AuthSysPermission> permissionList =  this.permissionMapper.selectAllSimplePermissions();
        if(permissionList != null){
            for(AuthSysPermission permission:permissionList){
                permission.setResources(this.resourcesMapper.selectByPrimaryKey(permission.getRscId()));
                permission.setOperator(this.operatorMapper.selectByPrimaryKey(permission.getOptId()));
            }
        }
        return permissionList;
    }

    @Override
    public List<AuthSysPermission> searchUserPermissions(String userName) {
        return this.permissionMapper.selectByUserName(userName);
    }
}
