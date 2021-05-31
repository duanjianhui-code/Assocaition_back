package com.assocaition.management.module.authority.service.impl;


import com.assocaition.management.module.authority.entity.AuthSysPermission;
import com.assocaition.management.module.authority.entity.AuthSysRole;
import com.assocaition.management.module.authority.entity.AuthSysUser;
import com.assocaition.management.module.authority.mapper.AuthSysUserMapper;
import com.assocaition.management.module.authority.service.IPermissionService;
import com.assocaition.management.module.authority.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    private AuthSysUserMapper userMapper;
    @Autowired
    private IPermissionService permissionService;

    @Override
    public AuthSysUser findByUserName(String username) {
        return this.userMapper.selectByUserName(username);
    }

    @Override
    public List<AuthSysUser> findAll() {
        return this.userMapper.selectAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void assignUserRoles(AuthSysUser user) {
        this.userMapper.unassignRoles(user.getUrId());
        List<AuthSysRole> roleList = user.getRoleList();
        if(roleList != null){
            for(AuthSysRole role:roleList) {
                this.userMapper.assignRole(user.getUrId(), role.getRlId());
            }
        }
    }

    @Override
    public List<String>
    getUserPermssions(String username) {
        List<String> permList = null;

        List<AuthSysPermission> permissionList = this.permissionService.searchUserPermissions(username);
        if(permissionList != null){
            permList = new ArrayList<>();
            for(AuthSysPermission perm:permissionList){
                permList.add(perm.getResources().getRscUrl()+":"+perm.getOperator().getOptUrl()+":*");
            }
        }
        return permList;
    }

    @Override
    public int updateUser(AuthSysUser user) {
        return userMapper.updateUser(user);
    }

    @Override
    public AuthSysUser selectUserById(Integer userId) {
        return userMapper.selectUserById(userId);
    }

    @Override
    public void deleteUserByUserId(Integer urId) {
        userMapper.deleteUserByUserId(urId);
    }

    @Override
    public void updateUser1(AuthSysUser authSysUser) {
        userMapper.updateUser1(authSysUser);
    }

    @Override
    public void updateUserByUserId(Integer urId) {
        userMapper.updateUserByUserId(urId);
    }

    @Override
    public List<Integer> selectUserId(String type) {
        return userMapper.selectUserId(type);
    }
}
