package com.assocaition.management.module.activity.controller;

import com.assocaition.management.commons.ResponseResult;
import com.assocaition.management.module.activity.entity.Activity;
import com.assocaition.management.module.activity.entity.ActivityVo;
import com.assocaition.management.module.activity.service.ActivityService;
import com.assocaition.management.module.assocaition.entity.Assocaition;
import com.assocaition.management.module.assocaition.service.AssocaitionService;
import com.assocaition.management.module.authority.entity.AuthSysUser;
import com.assocaition.management.module.examine.entity.Examine;
import com.assocaition.management.module.examine.entity.ExamineVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author Duanjianhui
 * @date 2021-05-19 1:05 下午
 * @describe
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    ActivityService activityService;
    @Autowired
    AssocaitionService assocaitionService;

    @PostMapping("/search/getActivityList.do")
    public ResponseResult getActivityList(@RequestBody ActivityVo activityVo, HttpSession session){
        ResponseResult result = new ResponseResult();
        Object userInfo = session.getAttribute("userInfo");
        if (userInfo!=null){
            AuthSysUser authSysUser = (AuthSysUser) userInfo;
            if ("2".equals(authSysUser.getType())){
                //管理员
                Assocaition assocaition = (Assocaition) session.getAttribute("assocaition");
                activityVo.setAssId(assocaition.getId());
                Map<String,Object> map = activityService.selectAllActivityList(activityVo);
                result.setData(map);
            }else {
                //部长
                Assocaition assocaition = assocaitionService.selectAssocaitionUserId(authSysUser.getUrId());
                activityVo.setAssId(assocaition.getId());
                Map<String,Object> map = activityService.selectActivityList(activityVo);
                result.setData(map);
            }
            return result;
        }
        return result;
    }

    @PostMapping("/search/getPassActivityList.do")
    public ResponseResult getPassActivityList(@RequestBody ActivityVo activityVo, HttpSession session){
        ResponseResult result = new ResponseResult();
        Object userInfo = session.getAttribute("userInfo");
        if (userInfo!=null){
            AuthSysUser authSysUser = (AuthSysUser) userInfo;
            if ("2".equals(authSysUser.getType())){
                //管理员
                Assocaition assocaition = (Assocaition) session.getAttribute("assocaition");
                activityVo.setAssId(assocaition.getId());
                Map<String,Object> map = activityService.selectPassAllActivityList(activityVo);
                result.setData(map);
            }else {
                //部长
                Assocaition assocaition = assocaitionService.selectAssocaitionUserId(authSysUser.getUrId());
                activityVo.setAssId(assocaition.getId());
                Map<String,Object> map = activityService.selectPassActivityList(activityVo);
                result.setData(map);
            }
            return result;
        }
        return result;
    }



    @PostMapping("/update/auditActivity.do")
    public ResponseResult auditActivity(@RequestBody Activity activity){
        ResponseResult result = new ResponseResult();
        int i = activityService.auditActivity(activity);
        if (i>0){
            return result;
        }
        result.setCode(10000);
        result.setStatus("更新失败");
        return result;
    }

    @GetMapping("/delete/deleteActivityById.do/{id}")
    public ResponseResult deleteActivityById(@PathVariable("id") String id){
        ResponseResult result = new ResponseResult();
        int i = activityService.deleteActivityById(id);
        if (i>0){
            return result;
        }
        result.setCode(10000);
        result.setStatus("更新失败");
        return result;
    }

}
