package com.assocaition.management.module.authority.service.impl;


import com.assocaition.management.module.authority.entity.AuthSysMenu;
import com.assocaition.management.module.authority.entity.AuthSysUser;
import com.assocaition.management.module.authority.service.IAuthService;
import com.assocaition.management.module.authority.service.IMenuService;
import com.assocaition.management.module.authority.service.IUserService;
import com.assocaition.management.utils.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private IMenuService menuService;

    @Autowired
    private IUserService userService;
    @Override
    public List<AuthSysMenu> getMenus() {
        return this.menuService.findFirstMenus();
    }

    @Override
    public AuthSysUser verify(String username, String password) {
        AuthSysUser user = this.userService.findByUserName(username);
        if(user == null){
            throw new RuntimeException(username + "不存在");
        }else{
            password = EncryptUtil.getMD5Str(password);
            if(password.equals(user.getUrPassword())){
                return user;
            }
        }
        return null;
    }

    @Override
    public AuthSysUser findUser(String username) {
        return this.userService.findByUserName(username);
    }


}
