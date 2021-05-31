package com.assocaition.management.module.member.service;

import com.assocaition.management.module.member.entity.AssocaitionMember;
import com.assocaition.management.module.member.entity.MemberVo;

import java.util.List;
import java.util.Map;

/**
 * @author Duanjianhui
 * @date 2021-05-09 10:24 上午
 * @describe
 */
public interface MemberService {

    int addMember(AssocaitionMember assocaitionMember);

    List<AssocaitionMember> getApplyMember(Integer urId);

    List<Map<String, String>> selectMemberEmails(String assId);

    List<AssocaitionMember> selectAllMember(String id);

    List<AssocaitionMember> selectMemberListByAssId(MemberVo memberVo);

    int countMemberListByAssId(MemberVo memberVo);

    int updateMember(AssocaitionMember assocaitionMember);

    Map<String, Object> selectAllMemberList(MemberVo memberVo);

    Map<String, Object> selectMemberList(MemberVo memberVo);

    int auditMember(AssocaitionMember member);

    int deleteMemberById(String id);

    Map<String, Object> selectPassAllMemberList(MemberVo memberVo);

    Map<String, Object> selectPassMemberList(MemberVo memberVo);

    void updateMemberOrder(String orderNo);

    int countMem();
}
