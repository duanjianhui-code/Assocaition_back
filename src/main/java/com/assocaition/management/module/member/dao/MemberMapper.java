package com.assocaition.management.module.member.dao;

import com.assocaition.management.module.member.entity.AssocaitionMember;
import com.assocaition.management.module.member.entity.MemberVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

/**
 * @author Duanjianhui
 * @date 2021-05-09 10:23 上午
 * @describe
 */
@Mapper
public interface MemberMapper {

    int addMember(AssocaitionMember assocaitionMember);

    List<AssocaitionMember> getApplyMember(Integer urId);

    List<Map<String, String>> selectMemberEmails(String assId);

    List<AssocaitionMember> selectMemberListByAssId(@Param("memberVo") MemberVo memberVo, RowBounds rowBounds);

    int countMemberListByAssId(String assId);

    int updateMember(AssocaitionMember assocaitionMember);

    List<AssocaitionMember> selectAllMemberList(@Param("memberVo") MemberVo memberVo, RowBounds rowBounds);

    int countselectAllMemberList(@Param("memberVo") MemberVo memberVo);

    List<AssocaitionMember> selectMemberList(@Param("memberVo") MemberVo memberVo, RowBounds rowBounds);

    int countselectMemberList(@Param("memberVo") MemberVo memberVo);

    int auditMember(AssocaitionMember member);

    int deleteMemberById(String id);

    int countPassselectMemberList(@Param("memberVo") MemberVo memberVo);

    List<AssocaitionMember> selectPassMemberList(@Param("memberVo") MemberVo memberVo, RowBounds rowBounds);

    List<AssocaitionMember> selectPassAllMemberList(@Param("memberVo") MemberVo memberVo, RowBounds rowBounds);

    int countPassAllselectMemberList(@Param("memberVo") MemberVo memberVo);

    @Update("update member set isOrder='1' where orderNo=#{orderNo}")
    void updateMemberOrder(String orderNo);
    @Select("select count(*) from member where status='1' ")
    int countMem();
}
