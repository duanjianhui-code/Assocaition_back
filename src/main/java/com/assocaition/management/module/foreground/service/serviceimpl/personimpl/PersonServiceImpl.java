package com.assocaition.management.module.foreground.service.serviceimpl.personimpl;

import com.assocaition.management.module.authority.entity.AuthSysUser;
import com.assocaition.management.module.foreground.dao.person.PersonMapper;
import com.assocaition.management.module.foreground.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Duanjianhui
 * @date 2021-05-01 12:23 上午
 * @describe
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonMapper personMapper;
    @Override
    public int updateUser(AuthSysUser user) {
        return personMapper.updateUser(user);
    }

    @Override
    public AuthSysUser selectUserById(Integer urId) {
        return personMapper.selectUserById(urId);
    }
}
