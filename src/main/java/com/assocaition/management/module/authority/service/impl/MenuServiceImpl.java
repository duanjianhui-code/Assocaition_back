package com.assocaition.management.module.authority.service.impl;

import com.assocaition.management.module.authority.entity.AuthSysMenu;
import com.assocaition.management.module.authority.entity.AuthSysMenuMeta;
import com.assocaition.management.module.authority.mapper.AuthSysMenuMapper;
import com.assocaition.management.module.authority.mapper.AuthSysMenuMetaMapper;
import com.assocaition.management.module.authority.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements IMenuService {
    @Autowired
    private AuthSysMenuMapper menuMapper;
    @Autowired
    private AuthSysMenuMetaMapper metaMapper;
    @Override
    public List<AuthSysMenu> findFirstMenus() {
        return this.menuMapper.selectAll();
    }

    @Override
    public AuthSysMenu findById(int mnId) {
        return this.menuMapper.selectByPrimaryKey(mnId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addMenu(AuthSysMenu menu) {
        // 添加一级menu
        this.menuMapper.insert(menu);
        AuthSysMenuMeta meta = menu.getMeta();
        if(meta != null){
            meta.setMnId(menu.getId());
            this.metaMapper.insert(meta);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void editMenu(AuthSysMenu menu) {
        this.menuMapper.updateByPrimaryKey(menu);
        if(menu.getMeta() != null){
            this.metaMapper.updateByPrimaryKey(menu.getMeta());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteMenu(int id) {
        // 获取子菜单
        List<AuthSysMenu> subMenus = this.menuMapper.selectAllByParentId(id);
        if(subMenus != null) {
            for(AuthSysMenu menu:subMenus) {
                // 删除子菜单的meta
                this.metaMapper.deleteByPrimaryKey(menu.getMeta().getId());
                // 删除子菜单
                this.menuMapper.deleteByPrimaryKey(menu.getId());
            }
        }
        // 删除自己的meta
        this.metaMapper.deleteByMnId(id);
        // 删除自己
        this.menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public AuthSysMenu findParentMenu(Integer id) {
        AuthSysMenu self = this.menuMapper.selectByPrimaryKey(id);
        if(self != null){
            return this.menuMapper.selectByPrimaryKey(self.getParentId());
        }
        return null;
    }

    @Override
    public List<AuthSysMenu> findByRoleId(int rlId) {
        return this.menuMapper.selectByRlId(rlId);
    }
}
