package com.assocaition.management.module.examine.service.serviceimpl;

import com.assocaition.management.module.examine.dao.ExamineMapper;
import com.assocaition.management.module.examine.entity.Examine;
import com.assocaition.management.module.examine.entity.ExamineVo;
import com.assocaition.management.module.examine.service.ExamineService;
import com.assocaition.management.module.member.entity.AssocaitionMember;
import com.assocaition.management.module.member.entity.MemberVo;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Duanjianhui
 * @date 2021-05-09 10:32 下午
 * @describe
 */
@Service
@Transactional
public class ExamineServiceImpl implements ExamineService {
    @Autowired
    ExamineMapper examineMapper;

    @Override
    public int addExamine(Examine examine) {
        return examineMapper.addExamine(examine);
    }

    @Override
    public List<Examine> selectExamine(Examine examine) {
        return examineMapper.selectExamine(examine);
    }

    @Override
    public int auditExamine(Examine examine) {
        return examineMapper.auditExamine(examine);
    }

    @Override
    public Map<String, Object> selectAllExamineList(ExamineVo memberVo) {
        Map<String, Object> map = new HashMap<>();
        int offset = memberVo.getPage();
        int limit = memberVo.getLimit();
        RowBounds rowBounds = new RowBounds(((offset-1)*limit), limit);
        List<AssocaitionMember> memberList = examineMapper.selectAllExamineList(memberVo,rowBounds);
        int count = examineMapper.countselectAllExamineList(memberVo);
        map.put("items",memberList);
        map.put("total",count);
        return map;
    }

    @Override
    public Map<String, Object> selectExamineList(ExamineVo examineVo) {
        Map<String, Object> map = new HashMap<>();
        int offset = examineVo.getPage();
        int limit = examineVo.getLimit();
        RowBounds rowBounds = new RowBounds(((offset-1)*limit), limit);
        List<AssocaitionMember> memberList = examineMapper.selectMemberList(examineVo,rowBounds);
        int count = examineMapper.countselectMemberList(examineVo);
        map.put("items",memberList);
        map.put("total",count);
        return map;
    }

    @Override
    public int deleteMemberById(String id) {
        return examineMapper.deleteMemberById(id);
    }

    @Override
    public Map<String, Object> selectPassAllExamineList(ExamineVo examineVo) {
        Map<String, Object> map = new HashMap<>();
        int offset = examineVo.getPage();
        int limit = examineVo.getLimit();
        RowBounds rowBounds = new RowBounds(((offset-1)*limit), limit);
        List<AssocaitionMember> memberList = examineMapper.selectPassAllExamineList(examineVo,rowBounds);
        int count = examineMapper.countselectPassAllExamineList(examineVo);
        map.put("items",memberList);
        map.put("total",count);
        return map;
    }

    @Override
    public Map<String, Object> selectPassExamineList(ExamineVo examineVo) {
        Map<String, Object> map = new HashMap<>();
        int offset = examineVo.getPage();
        int limit = examineVo.getLimit();
        RowBounds rowBounds = new RowBounds(((offset-1)*limit), limit);
        List<AssocaitionMember> memberList = examineMapper.selectPassMemberList(examineVo,rowBounds);
        int count = examineMapper.countPassselectMemberList(examineVo);
        map.put("items",memberList);
        map.put("total",count);
        return map;
    }
}
