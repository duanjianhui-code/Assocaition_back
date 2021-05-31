package com.assocaition.management.module.member.controller;

import com.assocaition.management.commons.ResponseResult;
import com.assocaition.management.module.assocaition.entity.Assocaition;
import com.assocaition.management.module.assocaition.service.AssocaitionService;
import com.assocaition.management.module.authority.entity.AuthSysUser;
import com.assocaition.management.module.authority.service.IUserService;
import com.assocaition.management.module.member.entity.AssocaitionMember;
import com.assocaition.management.module.member.entity.MemberVo;
import com.assocaition.management.module.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Duanjianhui
 * @date 2021-05-19 2:06 下午
 * @describe
 */
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    MemberService memberService;
    @Autowired
    IUserService userService;
    @Autowired
    AssocaitionService assocaitionService;

//    @PostMapping("/search/getMemberList.do")
//    public ResponseResult getMemberList(HttpSession session){
//        ResponseResult result = new ResponseResult();
//        Object userInfo = session.getAttribute("userInfo");
//        AuthSysUser authSysUser = null;
//        if (userInfo !=null){
//            authSysUser = (AuthSysUser) userInfo;
//            if ("2".equals(authSysUser.getType())){
//                //社团管理员操作
//                Assocaition assocaition1 = (Assocaition) session.getAttribute("assocaition");
//                List<AssocaitionMember>  assocaitionMemberList = memberService.selectAllMember(assocaition1.getId());
//            } else{
//                //部长操作
//            }
//        }
//        return result;
//    }

    @PostMapping("/search/getMemberListByAssId.do")
    public ResponseResult getMemberListByAssId(@RequestBody MemberVo memberVo){
        ResponseResult result = new ResponseResult();
        List<AssocaitionMember> item=memberService.selectMemberListByAssId(memberVo);
        int count = memberService.countMemberListByAssId(memberVo);
        Map<String, Object> map = new HashMap<>();
        if (item.size()>0){
            map.put("items",item);
            map.put("total",count);
            result.setData(map);
            return result;
        } else {
            map.put("items",null);
            map.put("total",count);
            result.setData(map);
            return result;
        }
    }

    @PostMapping("/create/addAssocaitionManage.do")
    public ResponseResult addAssocaitionManage(@RequestBody AssocaitionMember assocaitionMember){
        ResponseResult result = new ResponseResult();
        AuthSysUser authSysUser = userService.selectUserById(assocaitionMember.getUserId());
        Assocaition assocaition = assocaitionService.selectAssocaitionById(assocaitionMember.getAssId());
        if ("普通成员".equals(assocaitionMember.getPosition())){
            //更新用户
            authSysUser.setType("4");
            userService.updateUser1(authSysUser);
            //更新member表
            int i = memberService.updateMember(assocaitionMember);
            //需要删除中间表
            userService.deleteUserByUserId(authSysUser.getUrId());
            //更新社团表
            assocaitionService.updateAssocaition1(assocaition);
            return result;
        }else {
            //更新社团
            if (assocaition.getUserId()!=null){
                result.setCode(10000);
                result.setStatus("该部门已有部长，请撤销后在授权");
                return result;
            }
            //更新用户
            authSysUser.setType("3");
            userService.updateUser1(authSysUser);
            //更新member表
            //更新member表
            int i = memberService.updateMember(assocaitionMember);
            //更新社团表
            assocaition.setCreateUser(assocaitionMember.getMemberName());
            assocaition.setUserId(assocaitionMember.getUserId());
            assocaition.setAssociationEmail(assocaitionMember.getMemberEmail());
            assocaition.setTelephone(assocaitionMember.getMemberPhone());

            assocaitionService.updateAssocaition2(assocaition);


            //更新中件表
            userService.updateUserByUserId(authSysUser.getUrId());
            result.setCode(20000);
            result.setStatus("该部门已有部长，请撤销后在授权111111");
            return result;

        }
    }

    @PostMapping("/search/getMemberList.do")
    public ResponseResult getMemberList(@RequestBody MemberVo memberVo,HttpSession session){
        ResponseResult result = new ResponseResult();
        Object userInfo = session.getAttribute("userInfo");
        if (userInfo!=null){
            AuthSysUser authSysUser = (AuthSysUser) userInfo;
            if ("2".equals(authSysUser.getType())){
                //管理员
                Assocaition assocaition = (Assocaition) session.getAttribute("assocaition");
                memberVo.setAssId(assocaition.getId());
                Map<String,Object> map = memberService.selectAllMemberList(memberVo);
                result.setData(map);
            }else {
                //部长
                Assocaition assocaition = assocaitionService.selectAssocaitionUserId(authSysUser.getUrId());
                memberVo.setAssId(assocaition.getId());
                Map<String,Object> map = memberService.selectMemberList(memberVo);
                result.setData(map);

            }
            return result;
        }
        return result;
    }


    @PostMapping("/search/getPassMemberList.do")
    public ResponseResult getPassMemberList(@RequestBody MemberVo memberVo,HttpSession session){
        ResponseResult result = new ResponseResult();
        Object userInfo = session.getAttribute("userInfo");
        if (userInfo!=null){
            AuthSysUser authSysUser = (AuthSysUser) userInfo;
            if ("2".equals(authSysUser.getType())){
                //管理员
                Assocaition assocaition = (Assocaition) session.getAttribute("assocaition");
                memberVo.setAssId(assocaition.getId());
                Map<String,Object> map = memberService.selectPassAllMemberList(memberVo);
                result.setData(map);
            }else {
                //部长
                Assocaition assocaition = assocaitionService.selectAssocaitionUserId(authSysUser.getUrId());
                memberVo.setAssId(assocaition.getId());
                Map<String,Object> map = memberService.selectPassMemberList(memberVo);
                result.setData(map);

            }
            return result;
        }
        return result;
    }

    @PostMapping("/update/auditMember.do")
    public ResponseResult auditMember(@RequestBody AssocaitionMember member){
        ResponseResult result = new ResponseResult();
        int i = memberService.auditMember(member);
        if (i>0){
            return result;
        }
        result.setCode(10000);
        result.setStatus("更新失败");
        return result;
    }

    @GetMapping("/delete/deleteMemberById.do/{id}")
    public ResponseResult deleteMemberById(@PathVariable("id") String id){
        ResponseResult result = new ResponseResult();
        int i = memberService.deleteMemberById(id);
        if (i>0){
            return result;
        }
        result.setCode(10000);
        result.setStatus("更新失败");
        return result;
    }
}
