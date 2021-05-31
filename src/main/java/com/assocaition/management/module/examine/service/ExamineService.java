package com.assocaition.management.module.examine.service;

import com.assocaition.management.module.examine.entity.Examine;
import com.assocaition.management.module.examine.entity.ExamineVo;
import com.assocaition.management.module.member.entity.MemberVo;

import java.util.List;
import java.util.Map;

/**
 * @author Duanjianhui
 * @date 2021-05-09 10:32 下午
 * @describe
 */
public interface ExamineService {
    int addExamine(Examine examine);

    List<Examine> selectExamine(Examine examine);

    int auditExamine(Examine examine);

    Map<String, Object> selectAllExamineList(ExamineVo memberVo);

    Map<String, Object> selectExamineList(ExamineVo memberVo);

    int deleteMemberById(String id);

    Map<String, Object> selectPassAllExamineList(ExamineVo examineVo);

    Map<String, Object> selectPassExamineList(ExamineVo examineVo);
}
