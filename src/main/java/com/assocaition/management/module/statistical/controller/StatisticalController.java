package com.assocaition.management.module.statistical.controller;

import com.assocaition.management.commons.ResponseResult;
import com.assocaition.management.module.assocaition.entity.Assocaition;
import com.assocaition.management.module.assocaition.service.AssocaitionService;
import com.assocaition.management.module.authority.entity.AuthSysUser;
import com.assocaition.management.module.file.service.FileService;
import com.assocaition.management.module.member.service.MemberService;
import com.assocaition.management.module.statistical.service.StatisticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Duanjianhui
 * @date 2021-05-27 4:07 下午
 * @describe
 */
@RestController
@RequestMapping("/statistical")
public class StatisticalController {
    @Autowired
    StatisticalService statisticalService;
    @Autowired
    AssocaitionService assocaitionService;
    @Autowired
    MemberService memberService;
    @Autowired
    FileService fileService;

    @GetMapping("/search/getStatistical.do")
    public ResponseResult getStatistical(HttpSession session) {
        ResponseResult result = new ResponseResult();
        Object userInfo = session.getAttribute("userInfo");
        if (userInfo != null) {
            AuthSysUser authSysUser = (AuthSysUser) userInfo;

                //系统管理员

                //获取社团数量
                int assCount = assocaitionService.countAss();

                //获取人数
                int memCount = memberService.countMem();

                //获取文件量
                int fileCount = fileService.countFile();

                //获取下载量
                String assID = "default";
                int downlocalCount = statisticalService.countDownlocal(assID);

                Map<String, Object> map = new HashMap<>();
                map.put("assCount",assCount);
                map.put("memCount",memCount);
                map.put("fileCount",fileCount);
                map.put("downlocalsCount",downlocalCount);

                result.setData(map);
        }
        return result;
    }

}
