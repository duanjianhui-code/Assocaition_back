package com.assocaition.management.module.authority.service.impl;


import com.assocaition.management.module.authority.entity.AuthSysOperator;
import com.assocaition.management.module.authority.mapper.AuthSysOperatorMapper;
import com.assocaition.management.module.authority.service.IOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OperateServiceImpl implements IOperateService {
    @Autowired
    private AuthSysOperatorMapper operatorMapper;

    @Override
    public List<AuthSysOperator> selectAll() {
        return this.operatorMapper.selectAll();
    }
}
