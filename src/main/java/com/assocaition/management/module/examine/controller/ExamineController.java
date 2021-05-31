package com.assocaition.management.module.examine.controller;

import com.assocaition.management.commons.ResponseResult;
import com.assocaition.management.module.assocaition.entity.Assocaition;
import com.assocaition.management.module.assocaition.service.AssocaitionService;
import com.assocaition.management.module.authority.entity.AuthSysUser;
import com.assocaition.management.module.examine.entity.Examine;
import com.assocaition.management.module.examine.entity.ExamineVo;
import com.assocaition.management.module.examine.service.ExamineService;
import com.assocaition.management.module.member.entity.AssocaitionMember;
import com.assocaition.management.module.member.entity.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author Duanjianhui
 * @date 2021-05-19 2:28 下午
 * @describe
 */
@RestController
@RequestMapping("/examine")
public class ExamineController {
    @Autowired
    ExamineService examineService;
    @Autowired
    AssocaitionService assocaitionService;

    @PostMapping("/search/getExamineList.do")
    public ResponseResult getExamineList(@RequestBody ExamineVo examineVo, HttpSession session){
        ResponseResult result = new ResponseResult();
        Object userInfo = session.getAttribute("userInfo");
        if (userInfo!=null){
            AuthSysUser authSysUser = (AuthSysUser) userInfo;
            if ("2".equals(authSysUser.getType())){
                //管理员
                Assocaition assocaition = (Assocaition) session.getAttribute("assocaition");
                examineVo.setAssId(assocaition.getId());
                Map<String,Object> map = examineService.selectAllExamineList(examineVo);
                result.setData(map);
            }else {
                //部长
                Assocaition assocaition = assocaitionService.selectAssocaitionUserId(authSysUser.getUrId());
                examineVo.setAssId(assocaition.getId());
                Map<String,Object> map = examineService.selectExamineList(examineVo);
                result.setData(map);
            }
            return result;
        }
        return result;
    }

    @PostMapping("/search/getPassExamineList.do")
    public ResponseResult getPassExamineList(@RequestBody ExamineVo examineVo, HttpSession session){
        ResponseResult result = new ResponseResult();
        Object userInfo = session.getAttribute("userInfo");
        if (userInfo!=null){
            AuthSysUser authSysUser = (AuthSysUser) userInfo;
            if ("2".equals(authSysUser.getType())){
                //管理员
                Assocaition assocaition = (Assocaition) session.getAttribute("assocaition");
                examineVo.setAssId(assocaition.getId());
                Map<String,Object> map = examineService.selectPassAllExamineList(examineVo);
                result.setData(map);
            }else {
                //部长
                Assocaition assocaition = assocaitionService.selectAssocaitionUserId(authSysUser.getUrId());
                examineVo.setAssId(assocaition.getId());
                Map<String,Object> map = examineService.selectPassExamineList(examineVo);
                result.setData(map);
            }
            return result;
        }
        return result;
    }

    @PostMapping("/update/auditExamine.do")
    public ResponseResult auditExamine(@RequestBody Examine examine){
        ResponseResult result = new ResponseResult();
        int i = examineService.auditExamine(examine);
        if (i>0){
            return result;
        }
        result.setCode(10000);
        result.setStatus("更新失败");
        return result;
    }

    @GetMapping("/delete/deleteExamineById.do/{id}")
    public ResponseResult deleteMemberById(@PathVariable("id") String id){
        ResponseResult result = new ResponseResult();
        int i = examineService.deleteMemberById(id);
        if (i>0){
            return result;
        }
        result.setCode(10000);
        result.setStatus("更新失败");
        return result;
    }
}
