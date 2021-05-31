package com.assocaition.management.module.member.service.serviceimpl;

import com.assocaition.management.module.member.dao.MemberMapper;
import com.assocaition.management.module.member.entity.AssocaitionMember;
import com.assocaition.management.module.member.entity.MemberVo;
import com.assocaition.management.module.member.service.MemberService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Duanjianhui
 * @date 2021-05-09 10:24 上午
 * @describe
 */
@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberMapper memberMapper;
    @Override
    public int addMember(AssocaitionMember assocaitionMember) {
        return memberMapper.addMember(assocaitionMember);
    }

    @Override
    public List<AssocaitionMember> getApplyMember(Integer urId) {
        return memberMapper.getApplyMember(urId);
    }

    @Override
    public List<Map<String, String>> selectMemberEmails(String assId) {
        return memberMapper.selectMemberEmails(assId);
    }

    @Override
    public List<AssocaitionMember> selectAllMember(String id) {
        return null;
    }

    @Override
    public List<AssocaitionMember> selectMemberListByAssId(MemberVo memberVo) {
        int offset = memberVo.getPage();
        int limit = memberVo.getLimit();
        RowBounds rowBounds = new RowBounds(((offset-1)*limit), limit);
        return memberMapper.selectMemberListByAssId(memberVo,rowBounds);
    }

    @Override
    public int countMemberListByAssId(MemberVo memberVo) {
        return memberMapper.countMemberListByAssId(memberVo.getAssId());
    }

    @Override
    public int updateMember(AssocaitionMember assocaitionMember) {
        return memberMapper.updateMember(assocaitionMember);
    }

    @Override
    public Map<String, Object> selectAllMemberList(MemberVo memberVo) {
        Map<String, Object> map = new HashMap<>();
        int offset = memberVo.getPage();
        int limit = memberVo.getLimit();
        RowBounds rowBounds = new RowBounds(((offset-1)*limit), limit);
        List<AssocaitionMember> memberList = memberMapper.selectAllMemberList(memberVo,rowBounds);
        int count = memberMapper.countselectAllMemberList(memberVo);
        map.put("items",memberList);
        map.put("total",count);
        return map;
    }

    @Override
    public Map<String, Object> selectMemberList(MemberVo memberVo) {
        Map<String, Object> map = new HashMap<>();
        int offset = memberVo.getPage();
        int limit = memberVo.getLimit();
        RowBounds rowBounds = new RowBounds(((offset-1)*limit), limit);
        List<AssocaitionMember> memberList = memberMapper.selectMemberList(memberVo,rowBounds);
        int total = memberMapper.countselectMemberList(memberVo);
        map.put("items",memberList);
        map.put("total",total);
        return map;
    }

    @Override
    public int auditMember(AssocaitionMember member) {
        return memberMapper.auditMember(member);
    }

    @Override
    public int deleteMemberById(String id) {
        return memberMapper.deleteMemberById(id);
    }

    @Override
    public Map<String, Object> selectPassAllMemberList(MemberVo memberVo) {
        Map<String, Object> map = new HashMap<>();
        int offset = memberVo.getPage();
        int limit = memberVo.getLimit();
        RowBounds rowBounds = new RowBounds(((offset-1)*limit), limit);
        List<AssocaitionMember> memberList = memberMapper.selectPassAllMemberList(memberVo,rowBounds);
        int total = memberMapper.countPassAllselectMemberList(memberVo);
        map.put("items",memberList);
        map.put("total",total);
        return map;
    }

    @Override
    public Map<String, Object> selectPassMemberList(MemberVo memberVo) {
        Map<String, Object> map = new HashMap<>();
        int offset = memberVo.getPage();
        int limit = memberVo.getLimit();
        RowBounds rowBounds = new RowBounds(((offset-1)*limit), limit);
        List<AssocaitionMember> memberList = memberMapper.selectPassMemberList(memberVo,rowBounds);
        int total = memberMapper.countPassselectMemberList(memberVo);
        map.put("items",memberList);
        map.put("total",total);
        return map;
    }

    @Override
    public void updateMemberOrder(String orderNo) {
        memberMapper.updateMemberOrder(orderNo);
    }

    @Override
    public int countMem() {
        return memberMapper.countMem();
    }
}
