package com.assocaition.management.module.authority.service;


import com.assocaition.management.module.authority.entity.AuthSysOperator;

import java.util.List;

public interface IOperateService {
    List<AuthSysOperator> selectAll();
}
