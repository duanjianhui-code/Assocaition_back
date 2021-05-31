package com.assocaition.management.module.authority.service.impl;

import com.assocaition.management.module.authority.entity.AuthSysMenu;
import com.assocaition.management.module.authority.entity.AuthSysPermission;
import com.assocaition.management.module.authority.entity.AuthSysRole;
import com.assocaition.management.module.authority.mapper.AuthSysPermissionMapper;
import com.assocaition.management.module.authority.mapper.AuthSysRoleMapper;
import com.assocaition.management.module.authority.service.IMenuService;
import com.assocaition.management.module.authority.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private AuthSysRoleMapper roleMapper;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private AuthSysPermissionMapper permissionMapper;

    @Override
    public List<AuthSysRole> search(String roleName) {
        if(roleName != null && ("null".equalsIgnoreCase(roleName)||"".equals(roleName))){
            roleName = null;
        }
        List<AuthSysRole> roleList = this.roleMapper.selectByCondition(roleName, null);
        return roleList;
    }

    @Override
    public AuthSysRole findById(int rlId) {
        return this.roleMapper.selectByPrimaryKey(rlId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public AuthSysRole add(AuthSysRole role) {
        this.roleMapper.insertSelective(role);
        // 判断是否有相关权限需要插入，如果有，就同步添加
        List<AuthSysMenu> menus = role.getRoutes();
        if(menus != null && menus.size()>0){
            for(AuthSysMenu menu:menus){
                AuthSysMenu mn = this.menuService.findById(menu.getId());
                if(mn.getParentId() != null && mn.getParentId() > 0) {
                    this.roleMapper.assignMenu(role.getRlId(), menu);
                }
            }
        }
        return role;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteRole(int rlId) {
        // 删除角色的其它menu
        this.roleMapper.unassignMenus(rlId);
        this.roleMapper.deleteByPrimaryKey(rlId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(int rlId, AuthSysRole role) {
        // 调用Mapper维护角色的基本信息
        this.roleMapper.updateByPrimaryKey(role);
        // 删除角色的旧menu（路由信息）
        this.roleMapper.unassignMenus(rlId);
        // 获取角色新拥有的路由s
        List<AuthSysMenu> menus = role.getRoutes();
        AuthSysMenu parentMenu = null;
        if(menus != null && menus.size()>0){
            // 循环的将路由信息分配给角色（给角色分配新的路由信息）
            for(AuthSysMenu menu:menus){
                if(!this.roleHasMenu(role.getRlId(), menu.getId())) {
                    this.roleMapper.assignMenu(role.getRlId(), menu);
                }
                parentMenu = this.menuService.findParentMenu(menu.getId());
                if(parentMenu != null && !this.roleHasMenu(role.getRlId(), parentMenu.getId())){
                    this.roleMapper.assignMenu(role.getRlId(), parentMenu);
                }
            }
        }
    }

    @Override
    public boolean roleHasMenu(int rlId, int mnId) {
        List<AuthSysMenu> menus = this.menuService.findByRoleId(rlId);
        boolean bool = false;
        if(menus != null){
            for(AuthSysMenu mn:menus){
                if(mn.getId() == mnId){
                    bool = true;
                    break;
                }
            }
        }
        return bool;
    }

    @Override
    public List<Integer> searchMenuRoleIds(String mnName) {
        return this.roleMapper.selectRolesByMnName(mnName);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateRolePermissions(AuthSysRole role) {
        this.roleMapper.unassignPermssions(role.getRlId());
        if(role.getPermissionList() != null){
            for(AuthSysPermission perm:role.getPermissionList())
            this.roleMapper.assignPermssion(role.getRlId(), perm.getPrmId());
        }
    }

    @Override
    public int addRole(Integer urId) {
        return roleMapper.addRole(urId);
    }

    @Override
    public int updateUserType(Integer userId) {
        return roleMapper.updateUserType(userId);
    }
}
