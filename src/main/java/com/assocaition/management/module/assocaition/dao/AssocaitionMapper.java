package com.assocaition.management.module.assocaition.dao;

import com.assocaition.management.module.assocaition.entity.Assocaition;
import com.assocaition.management.module.assocaition.entity.AssocationVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * @author Duanjianhui
 * @date 2021-04-22 12:16 下午
 * @describe
 */
@Mapper
public interface AssocaitionMapper {
    
    List<Assocaition> selectAssocaitions(@Param("assocaition")AssocationVo assocaition, RowBounds rowBounds);

    int countAssocaitions(@Param("assocaition") AssocationVo assocationVo, RowBounds rowBounds);

    int deleteAssocaitionById(String id);

    int auditAssocaition(Assocaition assocaition);

    List<Assocaition> selectPassAssocaitions(@Param("assocaition") AssocationVo assocationVo,RowBounds rowBounds);

    int countPassAssocaitions(@Param("assocaition")  AssocationVo assocationVo,RowBounds rowBounds);

    Assocaition selectAssocaitionUserId(Integer urId);

    List<Map<String, String>> selectAssocaitionEmails();

    int addAssocitionChil(Assocaition assocaition);

    List<Assocaition> selectAssocaitionChil(String assId);

    int updateStatus(Assocaition assocaition);

    int updateAssocaition(Assocaition assocaition);

    Assocaition selectAssocaitionById(String id);

    void updateAssocaition1(Assocaition assocaition);

    void updateAssocaition2(Assocaition assocaition);
    @Select("select count(*) from assocaition where status='1'")
    int countAss();
}
