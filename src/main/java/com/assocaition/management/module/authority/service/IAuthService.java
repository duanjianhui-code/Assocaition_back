package com.assocaition.management.module.authority.service;



import com.assocaition.management.module.authority.entity.AuthSysMenu;
import com.assocaition.management.module.authority.entity.AuthSysUser;

import java.util.List;

public interface IAuthService {
    List<AuthSysMenu> getMenus();

    AuthSysUser verify(String username, String password);

    AuthSysUser findUser(String username);
}
