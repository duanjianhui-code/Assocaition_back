package com.assocaition.management.module.authority.service.impl;


import com.assocaition.management.module.authority.entity.AuthSysPermission;
import com.assocaition.management.module.authority.entity.AuthSysResources;
import com.assocaition.management.module.authority.mapper.AuthSysPermissionMapper;
import com.assocaition.management.module.authority.mapper.AuthSysResourcesMapper;
import com.assocaition.management.module.authority.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResourceServiceImpl implements IResourceService {
    @Autowired
    private AuthSysResourcesMapper resourcesMapper;
    @Autowired
    private AuthSysPermissionMapper permissionMapper;
    @Override
    public List<AuthSysResources> searchAllResources() {
        return this.resourcesMapper.selectAllResources();
    }

    @Override
    public AuthSysResources searchResource(int id) {
        return this.resourcesMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addResource(AuthSysResources resources) {
        this.resourcesMapper.insertResource(resources);
        List<AuthSysPermission> permissionList = resources.getPermissionList();
        if(permissionList != null){
            for(AuthSysPermission permission:permissionList) {
                permission.setRscId(resources.getRscId());
                this.permissionMapper.insert(permission);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateResource(AuthSysResources resources) {
        this.resourcesMapper.updateByPrimaryKey(resources);
        this.permissionMapper.deleteByRscId(resources.getRscId());
        List<AuthSysPermission> permissionList = resources.getPermissionList();
        if(permissionList != null){
            for(AuthSysPermission permission:permissionList) {
                this.permissionMapper.insert(permission);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteResource(int id) {
        this.permissionMapper.deleteByRscId(id);
        this.resourcesMapper.deleteByPrimaryKey(id);
    }
}
