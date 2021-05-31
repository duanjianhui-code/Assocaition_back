package com.assocaition.management.module.authority.service;


import com.assocaition.management.module.authority.entity.AuthSysMenu;

import java.util.List;

public interface IMenuService {
    List<AuthSysMenu> findFirstMenus();
    AuthSysMenu findById(int mnId);

    void addMenu(AuthSysMenu menu);

    void editMenu(AuthSysMenu menu);

    void deleteMenu(int id);

    AuthSysMenu findParentMenu(Integer id);

    List<AuthSysMenu> findByRoleId(int rlId);
}
