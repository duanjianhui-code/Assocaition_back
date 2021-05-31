package com.assocaition.management.module.foreground.service.serviceimpl;

import com.assocaition.management.module.authority.entity.AuthSysUser;
import com.assocaition.management.module.foreground.dao.ForegroundUserMapper;
import com.assocaition.management.module.foreground.service.ForegroundIUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Duanjianhui
 * @date 2021-04-28 3:56 下午
 * @describe
 */
@Service
@Transactional
public class ForegroundIUserServiceImpl implements ForegroundIUserService {
    @Autowired
    ForegroundUserMapper foregroundUserMapper;
    @Override
    public AuthSysUser findByUserTelAndPwd(AuthSysUser user) {
        return foregroundUserMapper.findByUserTelAndPwd(user);
    }

    @Override
    public int addUser(AuthSysUser user) {
        return foregroundUserMapper.addUser(user);
    }
}
