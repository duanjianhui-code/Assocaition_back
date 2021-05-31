package com.assocaition.management.module.statistical.service.serviceimpl;

import com.assocaition.management.module.statistical.dao.StatisticalMapper;
import com.assocaition.management.module.statistical.service.StatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Duanjianhui
 * @date 2021-05-27 4:08 下午
 * @describe
 */
@Service
public class StatisticalServiceImpl implements StatisticalService {
    @Autowired
    StatisticalMapper statisticalMapper;
    @Override
    public int countDownlocal(String assID) {
        return statisticalMapper.countDownlocal(assID);
    }
}
