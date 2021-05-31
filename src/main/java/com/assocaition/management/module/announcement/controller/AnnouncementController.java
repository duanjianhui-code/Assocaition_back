package com.assocaition.management.module.announcement.controller;

import com.assocaition.management.commons.ResponseResult;
import com.assocaition.management.module.announcement.entity.Announcement;
import com.assocaition.management.module.announcement.entity.EmailVo;
import com.assocaition.management.module.announcement.service.AnnouncementService;
import com.assocaition.management.module.assocaition.entity.Assocaition;
import com.assocaition.management.module.assocaition.service.AssocaitionService;
import com.assocaition.management.module.authority.entity.AuthSysUser;
import com.assocaition.management.module.member.service.MemberService;
import com.assocaition.management.utils.EmailUtils;
import com.assocaition.management.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Duanjianhui
 * @date 2021-05-13 2:58 下午
 * @describe
 */
@RestController
@RequestMapping("/announcement")
public class AnnouncementController {
    @Autowired
    AnnouncementService announcementService;
    @Autowired
    AssocaitionService assocaitionService;
    @Autowired
    MemberService memberService;
    @Autowired
    EmailUtils emailUtils;

    @PostMapping("/create/addAnnouncement.do")
    public ResponseResult addAnnouncement(HttpSession session, @RequestBody Announcement announcement){
        ResponseResult result = new ResponseResult();
        Object userInfo = session.getAttribute("userInfo");
        if (userInfo!=null){
            AuthSysUser authSysUser = (AuthSysUser) userInfo;
            if ("1".equals(authSysUser.getType())){
                //系统管理员
                announcement.setAssId("default");
                announcement.setCreateTime(new Date());
                announcement.setId(UUIDUtils.getUUID("ann"));
                announcement.setCreateUser(authSysUser.getName());
                announcement.setIsEnable("1");
                //数据库操作
                int i = announcementService.addAssocaitionAnnouncement(announcement);
                if (i>0){
                    return result;
                }else {
                    result.setCode(10000);
                    result.setStatus("未登录");
                    return result;
                }
            } else {
                //社团管理员
               Assocaition assocaition = assocaitionService.selectAssocaitionUserId(authSysUser.getUrId());
                announcement.setAssId(assocaition.getId());
                announcement.setCreateTime(new Date());
                announcement.setId(UUIDUtils.getUUID("ann"));
                announcement.setCreateUser(authSysUser.getName());
                announcement.setIsEnable("1");
                //数据库操作
                int i = announcementService.addAssocaitionAnnouncement(announcement);
                if (i>0){
                    return result;
                }else {
                    result.setCode(10000);
                    result.setStatus("未登录");
                    return result;
                }
            }
        }
        result.setCode(10000);
        result.setStatus("未登录");
        return result;
    }

    @GetMapping("/search/getEmails.do")
    public ResponseResult getEmails(HttpSession session){
        ResponseResult result = new ResponseResult();
        Object userInfo = session.getAttribute("userInfo");
        if (userInfo!=null){
            AuthSysUser authSysUser = (AuthSysUser) userInfo;
            if ("1".equals(authSysUser.getType())){
                //系统管理员
                List<Map<String,String>> emailList = assocaitionService.selectAssocaitionEmails();
                if (emailList.size()>0){
                    result.setData(emailList);
                    return result;
                }else {
                    result.setCode(10000);
                    result.setStatus("未登录");
                    return result;
                }
            } else{
                //社团管理员
                Assocaition assocaition = assocaitionService.selectAssocaitionUserId(authSysUser.getUrId());
                List<Map<String,String>> emailList =memberService.selectMemberEmails(assocaition.getId());
                if (emailList.size()>0){
                    result.setData(emailList);
                    return result;
                }else {
                    result.setCode(10000);
                    result.setStatus("未登录");
                    return result;
                }
            }
        }
        result.setCode(10000);
        result.setStatus("未登录");
        return result;
    }

    @PostMapping("/create/sendEmail.do")
    public ResponseResult sendEmail(@RequestBody EmailVo emailVo){
        ResponseResult result = new ResponseResult();
        File file=null;
        emailUtils.doSendHtmlEmail(emailVo.getEmail(),emailVo.getSendHtml(),new String[]{emailVo.getEmail()},file);
        return result;
    }
}
