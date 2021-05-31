package com.assocaition.management.module.foreground.controller.assocaition;

import com.alipay.api.AlipayApiException;
import com.assocaition.management.commons.ResponseResult;
import com.assocaition.management.module.activity.entity.Activity;
import com.assocaition.management.module.activity.service.ActivityService;
import com.assocaition.management.module.announcement.entity.Announcement;
import com.assocaition.management.module.announcement.service.AnnouncementService;
import com.assocaition.management.module.assocaition.entity.Assocaition;
import com.assocaition.management.module.authority.entity.AuthSysUser;
import com.assocaition.management.module.authority.service.IUserService;
import com.assocaition.management.module.file.entity.AssocaitionFile;
import com.assocaition.management.module.file.service.FileService;
import com.assocaition.management.module.foreground.service.assocaition.FAssocaitionService;
import com.assocaition.management.module.member.entity.AssocaitionMember;
import com.assocaition.management.module.member.service.MemberService;
import com.assocaition.management.module.pay.dao.AlipayTemplate;
import com.assocaition.management.module.pay.entity.PayVo;
import com.assocaition.management.module.task.entity.Task;
import com.assocaition.management.module.task.service.TaskService;
import com.assocaition.management.utils.UUIDUtils;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author Duanjianhui
 * @date 2021-05-01 11:55 上午
 * @describe
 */
@Controller
@RequestMapping("/fassociation")
public class FAssocaitionController {
    @Autowired
    FAssocaitionService fAssocaitionService;
    @Autowired
    FileService fileService;
    @Autowired
    ActivityService activityService;
    @Autowired
    MemberService memberService;
    @Autowired
    AnnouncementService announcementService;
    @Autowired
    IUserService userService;
    @Autowired
    TaskService taskService;
    @Autowired
    private AlipayTemplate alipayTemplate;

    /**
     * 条件查询社团列表
     * @param request
     * @param assocaition
     * @return
     */
    @PostMapping("/selectAssocaition.do")
    @ResponseBody
    public Map<String,Object> selectAssocaition(HttpServletRequest request, Assocaition assocaition){
        Map<String, Object> map = new HashMap<>();
        int offset = Integer.parseInt(request.getParameter("offset"));
        int limit = Integer.parseInt(request.getParameter("limit"));
        RowBounds rowBounds = new RowBounds((offset-1)*limit, limit);
        map = fAssocaitionService.selectAssocaition(assocaition, rowBounds);

        return map;
    }

    @GetMapping("/test.do")
    @ResponseBody
    public List<Assocaition>  selectAssocaition(){
        RowBounds rowBounds = new RowBounds(1, 2);
        List<Assocaition>  assocaitionList= fAssocaitionService.test(rowBounds);

        return assocaitionList;
    }

    /**
     * 计算社团在总量（审核通过的社团）
     * @param assocaition
     * @return
     */
    @PostMapping("/countAssocaition.do")
    @ResponseBody
    public Map<String,Object> countAssocaition(Assocaition assocaition){
        Map<String, Object> map = new HashMap<>();
        int count = fAssocaitionService.countAssocaition(assocaition);
        map.put("count",count);
        return map;
    }

    /**
     * 跳转社团申请页
     * @return
     */
    @GetMapping("/toAddAssocaition.do")
    public String toAddAssocaition(){
        return "view/assocaition/addassocaition";
    }

    @PostMapping("/applayAssocaition.do")
    public String applayAssocaition(Assocaition assocaition, HttpSession session,Model model){
        String assId = UUIDUtils.getUUID("ass");
        assocaition.setId(assId);
        Object userinfo = session.getAttribute("userinfo");
        if (userinfo !=null){
            AuthSysUser user = (AuthSysUser) userinfo;
            assocaition.setStatus("0");
            assocaition.setCreateTime(new Date());
            assocaition.setUserId(user.getUrId());
            assocaition.setCreateUser(user.getName());
            assocaition.setParentId("0");
            int i = fAssocaitionService.applyAssocaition(assocaition);
            if (i>0){
                List<Assocaition> assocaitions = fAssocaitionService.getApplyAssocaitionByUserId(user.getUrId());
                if (assocaitions.size()>0){
                    for (Assocaition assocaition2:assocaitions) {
                        String status = assocaition2.getStatus();
                        if ("0".equals(status)){
                            assocaition2.setStatus("审核中");
                        } else if ("1".equals(status)){
                            assocaition2.setStatus("审核通过");
                        }else {
                            assocaition2.setStatus("审核不通过");
                        }
                    }
                    //发送待办
                    String type="1";
                    List<Integer> userIds = userService.selectUserId(type);
                    for (Integer userId:userIds){
                        Task task = new Task();
                        task.setAssignTo(userId);
                        task.setBusinessId(assocaition.getId());
                        task.setBusinessName("社团创建申请");
                        task.setId(UUIDUtils.getUUID("task"));
                        task.setCreateName(user.getName());
                        task.setCreateTime(new Date());
                        task.setCreateManId(user.getUrId());
                        task.setState("0");
                        task.setBusinessUrl("/auditAssocaition");
                        taskService.addTask(task);
                    }
                    model.addAttribute("assocaitions",assocaitions);
                }
                return "view/assocaition/assocaitionapplyinfo";
            }
            return "view/erro";
        }
        return "view/erro";
    }

    @GetMapping("/getApplyAssocaition.do")
    public String getApplyAssocaition(HttpSession session,Model model){
        Object userinfo = session.getAttribute("userinfo");
        if (userinfo !=null){
            AuthSysUser user = (AuthSysUser) userinfo;
            List<Assocaition> assocaitions = fAssocaitionService.getApplyAssocaitionByUserId(user.getUrId());
            if (assocaitions.size()>0){
                for (Assocaition assocaition2:assocaitions) {
                    String status = assocaition2.getStatus();
                    if ("0".equals(status)){
                        assocaition2.setStatus("审核中");
                    } else if ("1".equals(status)){
                        assocaition2.setStatus("审核通过");
                    }else {
                        assocaition2.setStatus("审核不通过");
                    }
                }
                model.addAttribute("assocaitions",assocaitions);
            }
            return "view/assocaition/assocaitionapplyinfo";
        }
        return "view/erro";
    }

    @GetMapping("/getApplyMember.do")
    public String getApplyMember(HttpSession session,Model model){
        Object userinfo = session.getAttribute("userinfo");
        if (userinfo !=null){
            AuthSysUser user = (AuthSysUser) userinfo;
            List<AssocaitionMember> memberList = memberService.getApplyMember(user.getUrId());
            if (memberList.size()>0){
                for (AssocaitionMember assocaition2:memberList) {
                    String status = assocaition2.getStatus();
                    if ("0".equals(status)){
                        assocaition2.setStatus("审核中");
                    } else if ("1".equals(status)){
                        assocaition2.setStatus("审核通过");
                    }else {
                        assocaition2.setStatus("审核不通过");
                    }
                }
                model.addAttribute("memberList",memberList);
            }
            return "view/member/memberapplyinfo";
        }
        return "view/erro";
    }

    @GetMapping("/toAssocaitionInfos.do")
    public String toAssocaitionInfos(String id, Model model){

        //联播图图片
        List<AssocaitionFile> Shufflings = fileService.selectShufflingByIdToShuffling(id);

        //获取子部门
        List<Assocaition>  childAssocaitions = fAssocaitionService.selectChildAssocaition(id);

        //获取社团信息
        Assocaition assocaition = fAssocaitionService.selectAssocaitionById(id);

        model.addAttribute("Shufflings",Shufflings);
        model.addAttribute("childAssocaitions",childAssocaitions);
        model.addAttribute("assocaition",assocaition);

        return "view/assocaition/assocaitioninfos";
    }

    @GetMapping("/toAssocaitionInfo.do")
    public String toAssocaitionInfo(String id, Model model){

        //获取生活图2张
        List<AssocaitionFile> live = fileService.selectImagerByIdToLive(id);

        //获取子部门
        Assocaition  childAssocaitions = fAssocaitionService.selectAssocaitionById(id);

        //获取最新活动信息
        List<Activity> activities = activityService.selectActivityById(id);
        for (Activity activity:activities){
            if (StringUtil.isEmpty(activity.getActivityImg())){
                activity.setActivityImg("https://duanjianhui1998.oss-cn-beijing.aliyuncs.com/imager/20210430/news-1.jpg");
            }
        }

        model.addAttribute("live",live);
        model.addAttribute("assocaition",childAssocaitions);
        model.addAttribute("activities",activities);

        return "view/assocaition/assocaitioninfo";
    }

    @GetMapping("/toMyAssocaitionInfo.do")
    public String toMyAssocaitionInfo(String id, Model model){

        //获取生活图2张
        List<AssocaitionFile> live = fileService.selectImagerByIdToLive(id);

        //获取子部门
        Assocaition  childAssocaitions = fAssocaitionService.selectAssocaitionById(id);

        //获取最新活动信息
        List<Activity> activities = activityService.selectActivityById(id);
        for (Activity activity:activities){
            if (StringUtil.isEmpty(activity.getActivityImg())){
                activity.setActivityImg("https://duanjianhui1998.oss-cn-beijing.aliyuncs.com/imager/20210430/news-1.jpg");
            }
        }

        model.addAttribute("live",live);
        model.addAttribute("assocaition",childAssocaitions);
        model.addAttribute("activities",activities);

        return "view/assocaition/myassocaitioninfo";
    }

    @GetMapping("/joinassocation")
    public String joinassocation(String id,Model model){
        model.addAttribute("id",id);
        return "view/assocaition/joinassocaition";
    }

    @PostMapping("/applyJoinAssocaition.do")
    public String applyJoinAssocaition(AssocaitionMember assocaitionMember,HttpSession session,Model model) throws AlipayApiException {
        Assocaition assocaition = fAssocaitionService.selectAssocaitionById(assocaitionMember.getAssId());
        assocaitionMember.setId(UUIDUtils.getUUID("mem"));
        assocaitionMember.setStatus("0");
        assocaitionMember.setAssName(assocaition.getAssName());
        Object userinfo = session.getAttribute("userinfo");
        if (userinfo !=null){
            AuthSysUser user = (AuthSysUser) userinfo;
            assocaitionMember.setUserId(user.getUrId());
            assocaitionMember.setAssId(assocaition.getId());
            assocaitionMember.setPosition("普通成员");
            if (!StringUtils.isEmpty(assocaition.getEntryFee())){
                PayVo payVo = new PayVo();
                payVo.setSubject("社团报名费");
                payVo.setTotal_amount(assocaition.getEntryFee());
                String no = String.valueOf(System.currentTimeMillis());
                payVo.setOut_trade_no(no);
                assocaitionMember.setIsOrder("0");
                assocaitionMember.setOrderNo(no);
                assocaitionMember.setCreateTime(new Date());
                int i = memberService.addMember(assocaitionMember);
                return "redirect:/fassociation/toPay.do？"+"out_trade_no="+payVo.getOut_trade_no()+"&subject="+payVo.getSubject()+"&total_amount="+payVo.getTotal_amount();
            }
            assocaitionMember.setIsOrder("0");
            assocaitionMember.setOrderNo("0");
            assocaitionMember.setCreateTime(new Date());
            int i = memberService.addMember(assocaitionMember);
            if (i>0) {
                List<AssocaitionMember> memberList = memberService.getApplyMember(user.getUrId());
                if (memberList.size()>0){
                    for (AssocaitionMember assocaition2:memberList) {
                        String status = assocaition2.getStatus();
                        if ("0".equals(status)){
                            assocaition2.setStatus("审核中");
                        } else if ("1".equals(status)){
                            assocaition2.setStatus("审核通过");
                        }else {
                            assocaition2.setStatus("审核不通过");
                        }
                    }
                    model.addAttribute("memberList",memberList);
                    return "view/member/memberapplyinfo";
                }
                return "view/erro";
            }
            return "view/erro";
        }
        return "view/erro";
    }

    @GetMapping("/getAnnouncement.do")
    @ResponseBody
    public ResponseResult getAnnouncement(String id){
        ResponseResult result = new ResponseResult();
        Announcement announcement = announcementService.selectAnnouncementById(id);
        if (announcement != null ){
            result.setData(announcement.getContent());
            return result;
        }
        result.setCode(10000);
        return result;
    }

    @ResponseBody
    @GetMapping(value = "/toPay.do",produces = "text/html")
    public String toPay(PayVo payVo) throws AlipayApiException {
        return alipayTemplate.pay(payVo);
    }

    @GetMapping("/paySuccess.do")
    public String paySuccess(HttpServletRequest request){
        Map<String, String[]> parameterMap = request.getParameterMap();
        String orderNo =parameterMap.get("out_trade_no")[0];
        memberService.updateMemberOrder(orderNo);
        return "view/index";
    }
}
