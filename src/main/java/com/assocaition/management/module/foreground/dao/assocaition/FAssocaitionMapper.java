package com.assocaition.management.module.foreground.dao.assocaition;

import com.assocaition.management.module.assocaition.entity.Assocaition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * @author Duanjianhui
 * @date 2021-05-01 11:56 上午
 * @describe
 */
@Mapper
public interface FAssocaitionMapper {
    List<Assocaition> selectAll();

    List<Assocaition> selectAssocaition(@Param("assocaition")Assocaition assocaition, RowBounds rowBounds);

    int countSelectAssocaition(@Param("assocaition") Assocaition assocaition);

    List<Assocaition>  test(RowBounds rowBounds);

    int applyAssocaition(Assocaition assocaition);

    List<Assocaition> selectChildAssocaition(String id);

    Assocaition selectAssocaitionById(String id);

    List<Assocaition> getApplyAssocaitionByUserId(Integer urId);

    List<Assocaition> selectMyAssocaition(Integer urId);
}
