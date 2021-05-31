package com.assocaition.management.module.foreground.controller;

import com.assocaition.management.commons.ResponseResult;
import com.assocaition.management.module.activity.entity.Activity;
import com.assocaition.management.module.activity.service.ActivityService;
import com.assocaition.management.module.assocaition.entity.Assocaition;
import com.assocaition.management.module.authority.entity.AuthSysUser;
import com.assocaition.management.module.examine.entity.Examine;
import com.assocaition.management.module.examine.service.ExamineService;
import com.assocaition.management.module.foreground.service.ForegroundIUserService;
import com.assocaition.management.module.foreground.service.assocaition.FAssocaitionService;
import com.assocaition.management.module.member.entity.AssocaitionMember;
import com.assocaition.management.utils.EncryptUtil;
import com.assocaition.management.utils.RedisUtil;
import com.assocaition.management.utils.SmsUtils;
import com.assocaition.management.utils.UUIDUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Duanjianhui
 * @date 2021-04-26 9:10 下午
 * @describe
 */
@Controller
@RequestMapping("/foreground")
public class ForegroundController {

    @Autowired
    ForegroundIUserService foregroundIUserService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    FAssocaitionService fAssocaitionService;

    @Autowired
    ExamineService examineService;

    @Autowired
    ActivityService activityService;

    @GetMapping({"/toIndex","/"})
    public String toIndex(){
        return "view/index";
    }

    @GetMapping("/toAssociation")
    public String toAssociation(Model model){
        List<Assocaition> assocaitionList = fAssocaitionService.selectAll();
        model.addAttribute("assocaitionList",assocaitionList);
        return "view/association";
    }
    @PostMapping("/toAssociation")
    @ResponseBody
    public Map<String,Object> toAssociation(Assocaition assocaition,HttpServletRequest request){
        int offset = Integer.parseInt(request.getParameter("offset"));
        int limit = Integer.parseInt(request.getParameter("limit"));

        RowBounds rowBounds = new RowBounds(offset, limit);
        Map<String,Object> map= fAssocaitionService.selectAssocaition(assocaition,rowBounds);

        return map;
    }

    @GetMapping("/toMyAssociation")
    public String toMyAssociation(HttpSession session,Model model){
        Object userinfo = session.getAttribute("userinfo");
        if (userinfo !=null){
            AuthSysUser user = (AuthSysUser) userinfo;
            List<Assocaition> assocaitions = fAssocaitionService.selectMyAssocaition(user.getUrId());
            model.addAttribute("assocaitions",assocaitions);
            return "view/myAssociation";
        }
        return "/view/erro";
    }

    @GetMapping("/toPersonal")
    public String toPersonal(){
        return "view/personal";
    }

    @GetMapping("/toOur")
    public String toOur(){
        return "view/our";
    }

    @GetMapping("/toLogin")
    public String toLogin(){
        return "view/login";
    }

    @GetMapping("/toRegister")
    public String toRegister(){
        return "view/register";
    }

    @GetMapping("/toReset")
    public String toReset(){
        return "view/reset";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseResult login(AuthSysUser user, HttpSession session,HttpServletRequest request){
        user.setUrPassword(EncryptUtil.getMD5Str(user.getUrPassword()));
        AuthSysUser authSysUser =  foregroundIUserService.findByUserTelAndPwd(user);
        String varcode = request.getParameter("varcode");
        Object verification = redisUtil.get("verification");

        ResponseResult result = new ResponseResult();
        Map<String, String> map = new HashMap<>();
        if (authSysUser==null){
            map.put("msg","账号或密码错误");
            result.setData(map);
            result.setCode(10000);
        } else{
            if (verification!=null){
                String code = (String) verification;
                if (code.equalsIgnoreCase(varcode)){
                    session.setAttribute("userinfo",authSysUser);
                    redisUtil.del("verification");
                }else {
                    map.put("msg","验证码错误");
                    result.setData(map);
                    result.setCode(10000);
                    redisUtil.del("verification");
                }
            }
        }
        return result;
    }

    @GetMapping("/logout")
    public String register(HttpSession session){
        session.invalidate();
        return "view/index";
    }

    @GetMapping("/toPerson")
    public String toPerson(){
        return "view/personal/person";
    }

    @GetMapping("/sendSmsCode")
    @ResponseBody
    public ResponseResult sendSmsCode(String mobile,String type){
        ResponseResult result = new ResponseResult();
        String smsCode = SmsUtils.getSmsCode();
        //发送验证码给用户
        Map<String, String> map = SmsUtils.sendSms(mobile, smsCode);
        String errcode = map.get("errcode");
        if (!"00000".equals(errcode)){
            result.setStatus("发送失败");
            result.setCode(11111);
            return result;
        }
        redisUtil.set("sms:"+mobile,smsCode,60000);
        return result;
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseResult register(AuthSysUser user, HttpServletRequest request){
        ResponseResult result = new ResponseResult();
        String code = request.getParameter("code");
        String password1 = request.getParameter("password1");
        user.setUrPassword(EncryptUtil.getMD5Str(password1));
        user.setUrUserName(user.getTelephone());
        user.setName(user.getTelephone());
        user.setType("5");

        String redisKey = "sms:"+user.getTelephone();
        Object o = redisUtil.get(redisKey);
        if (o!=null){
            String redisCode = (String) o;
            if (redisCode.equals(code)){
                //存储到数据库
                int i = foregroundIUserService.addUser(user);
                return result;
            } else {
                //验证错误
                result.setCode(11111);
                result.setStatus("验证码错误");
                return result;
            }
        } else {
            //redis没有值
            result.setCode(11111);
            result.setStatus("注册失败，请稍后尝试！！！");
            return result;
        }
    }


    @GetMapping("/toexamine.do")
    public String toexamine(String id,Model model){
        model.addAttribute("id",id);
        return "view/examine/addexamine";
    }

    @GetMapping("/toactivity.do")
    public String toactivity(String id,Model model){
        model.addAttribute("id",id);

        return "view/activity/addactivity";
    }

    @PostMapping("/addexamine.do")
    public String addexamine(Examine examine, Model model,HttpSession session){
        Object userinfo = session.getAttribute("userinfo");
        if (userinfo !=null){
            AuthSysUser user = (AuthSysUser) userinfo;
            examine.setId(UUIDUtils.getUUID("exa"));
            examine.setSubmitTime(new Date());
            examine.setStatus("0");
            examine.setSubmitUser(user.getName());
            examine.setUserId(user.getUrId());
            int i = examineService.addExamine(examine);
            if (i>0){
                List<Examine> examineList = examineService.selectExamine(examine);
                if (examineList.size()>0){
                    for (Examine assocaition2:examineList) {
                        String status = assocaition2.getStatus();
                        if ("0".equals(status)){
                            assocaition2.setStatus("审核中");
                        } else if ("1".equals(status)){
                            assocaition2.setStatus("审核通过");
                        }else {
                            assocaition2.setStatus("审核不通过");
                        }
                    }
                    model.addAttribute("examineList",examineList);
                    return "view/examine/examineapplyinfo";
                }
                return "/view/erro";
            }
            return "view/erro";
        }
        return "view/erro";
    }

    @GetMapping("/getexamineapply.do")
    public String getexamineapply(String id,HttpSession session,Model model){
        Object userinfo = session.getAttribute("userinfo");
        if (userinfo !=null){
            AuthSysUser user = (AuthSysUser) userinfo;
            Examine examine = new Examine();
            examine.setAssId(id);
            examine.setUserId(user.getUrId());
            List<Examine> examineList = examineService.selectExamine(examine);
            if (examineList.size()>0){
                for (Examine assocaition2:examineList) {
                    String status = assocaition2.getStatus();
                    if ("0".equals(status)){
                        assocaition2.setStatus("审核中");
                    } else if ("1".equals(status)){
                        assocaition2.setStatus("审核通过");
                    }else {
                        assocaition2.setStatus("审核不通过");
                    }
                }
                model.addAttribute("examineList",examineList);
                return "view/examine/examineapplyinfo";
            }
            return "/view/erro";
        }
        return "/view/erro";
    }

    @PostMapping("/addActivity.do")
    @ResponseBody
    public ResponseResult addActivity(Activity activity,HttpSession session){
        ResponseResult result = new ResponseResult();
        Object userinfo = session.getAttribute("userinfo");
        if (userinfo !=null){
            AuthSysUser user = (AuthSysUser) userinfo;
            activity.setApplyDate(new Date());
            activity.setApplyUser(user.getName());
            activity.setStatus("0");
            activity.setId(UUIDUtils.getUUID("act"));
            int i = activityService.addActivity(activity);
            if (i>0){
                result.setStatus("提交成功");
                return result;
            }
            result.setCode(10000);
            result.setStatus("提交失败");
            return result;
        }
        result.setCode(10000);
        result.setStatus("提交失败");
        return result;
    }

    @GetMapping("/getactivityapply.do")
    public String getactivityapply(String id ,Model model,HttpSession session){
        Object userinfo = session.getAttribute("userinfo");
        if (userinfo !=null){
            AuthSysUser user = (AuthSysUser) userinfo;
            List<Activity> activityList = activityService.selectActivitysById(id);
            if (activityList.size()>0){
                for (Activity assocaition2:activityList) {
                    String status = assocaition2.getStatus();
                    if ("0".equals(status)){
                        assocaition2.setStatus("审核中");
                    } else if ("1".equals(status)){
                        assocaition2.setStatus("审核通过");
                    }else {
                        assocaition2.setStatus("审核不通过");
                    }
                }
                model.addAttribute("activityList",activityList);
                return "/view/activity/activityapplyinfo";
            }
            return "/view/erro";
        }
        return "/view/erro";
    }


}
