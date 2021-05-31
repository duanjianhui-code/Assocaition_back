package com.assocaition.management.module.examine.dao;

import com.assocaition.management.module.examine.entity.Examine;
import com.assocaition.management.module.examine.entity.ExamineVo;
import com.assocaition.management.module.member.entity.AssocaitionMember;
import com.assocaition.management.module.member.entity.MemberVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author Duanjianhui
 * @date 2021-05-09 10:31 下午
 * @describe
 */
@Mapper
public interface ExamineMapper {

    int addExamine(Examine examine);

    List<Examine> selectExamine(Examine examine);

    List<AssocaitionMember> selectAllExamineList(@Param("memberVo") ExamineVo memberVo, RowBounds rowBounds);

    int countselectAllExamineList(@Param("memberVo")ExamineVo memberVo);

    List<AssocaitionMember> selectMemberList( @Param("memberVo")ExamineVo examineVo, RowBounds rowBounds);

    int countselectMemberList(@Param("memberVo")ExamineVo examineVo);

    int auditExamine(Examine examine);

    int deleteMemberById(String id);

    List<AssocaitionMember> selectPassAllExamineList(@Param("memberVo")ExamineVo examineVo, RowBounds rowBounds);

    int countselectPassAllExamineList(@Param("memberVo")ExamineVo examineVo);

    List<AssocaitionMember> selectPassMemberList(@Param("memberVo")ExamineVo examineVo, RowBounds rowBounds);

    int countPassselectMemberList(@Param("memberVo")ExamineVo examineVo);
}
