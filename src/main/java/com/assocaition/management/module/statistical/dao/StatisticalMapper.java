package com.assocaition.management.module.statistical.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Duanjianhui
 * @date 2021-05-28 5:23 下午
 * @describe
 */
@Mapper
public interface StatisticalMapper {
    @Select("select downloadsCount from statistical where assId=#{assID}")
    public int countDownlocal(String assID);
}
