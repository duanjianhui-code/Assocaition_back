package com.assocaition.management.module.foreground.service.assocaition;

import com.assocaition.management.module.assocaition.entity.Assocaition;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * @author Duanjianhui
 * @date 2021-05-01 11:57 上午
 * @describe
 */
public interface FAssocaitionService{
    List<Assocaition> selectAll();

    Map<String, Object> selectAssocaition(Assocaition assocaition, RowBounds rowBounds);

    int countAssocaition(Assocaition assocaition);

    List<Assocaition>  test(RowBounds rowBounds);

    int applyAssocaition(Assocaition assocaition);

    List<Assocaition> selectChildAssocaition(String id);

    Assocaition selectAssocaitionById(String id);

    List<Assocaition> getApplyAssocaitionByUserId(Integer urId);

    List<Assocaition> selectMyAssocaition(Integer urId);
}
