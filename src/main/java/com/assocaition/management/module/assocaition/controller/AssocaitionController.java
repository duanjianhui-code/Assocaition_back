package com.assocaition.management.module.assocaition.controller;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.assocaition.management.commons.ResponseResult;
import com.assocaition.management.commons.aliyun.OSSUtils;
import com.assocaition.management.module.assocaition.entity.Assocaition;
import com.assocaition.management.module.assocaition.entity.AssocationVo;
import com.assocaition.management.module.assocaition.service.AssocaitionService;
import com.assocaition.management.module.authority.entity.AuthSysUser;
import com.assocaition.management.module.authority.service.IRoleService;
import com.assocaition.management.utils.RedisUtil;
import com.assocaition.management.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Duanjianhui
 * @date 2021-04-22 12:15 下午
 * @describe
 */
@RestController
@RequestMapping("/assocaition")
public class AssocaitionController {
    @Autowired
    AssocaitionService assocaitionService;
    @Autowired
    private IRoleService roleService;

    @Autowired
    OSSUtils ossUtils;

    @Autowired
    RedisUtil redisUtil;

    @PostMapping("/search/getAssocaitionList.do")
    public ResponseResult getAssocaitionList(@RequestBody AssocationVo assocationVo){
        //查询redis
        Object assocaitionList = redisUtil.get("AssocaitionList");
        ResponseResult result = new ResponseResult();
        if (assocaitionList == null){
            List<Assocaition> item=assocaitionService.selectAssocaitions(assocationVo);
            int count = assocaitionService.countAssocaitions(assocationVo);

            Map<String, Object> map = new HashMap<>();
            if (item.size()>0){
                map.put("items",item);
                map.put("total",count);
                result.setData(map);
                return result;
            }
            map.put("items",item);
            map.put("total",0);
            result.setData(map);
            result.setStatus("false");
            return result;
        } else {
            result.setData(assocaitionList);
            return result;
        }

    }

    @DeleteMapping("/delete/deleteAssocationById.do/{id}")
    public ResponseResult deleteAssocationById(@PathVariable("id") String id){
        ResponseResult result = new ResponseResult();
        int i = assocaitionService.deleteAssocaitionById(id);
        if (i>0){
            return result;
        }
        result.setCode(10000);
        result.setStatus("false");
        return result;
    }

    @PostMapping("/update/auditAssocation.do")
    public ResponseResult auditAssocation(@RequestBody Assocaition assocaition, HttpSession session){
        ResponseResult result = new ResponseResult();
        int i = assocaitionService.auditAssocaition(assocaition);
        if (i>0){
            if ("1".equals(assocaition.getStatus())){
                AuthSysUser authSysUser = (AuthSysUser)session.getAttribute("userInfo");
                int j = roleService.addRole(assocaition.getUserId());
                int m = roleService.updateUserType(assocaition.getUserId());
            }
            return result;
        }
        result.setCode(10000);
        result.setStatus("false");
        return result;
    }

    @PostMapping("/search/getPassAssocaitionList.do")
    public ResponseResult getPassAssocaitionList(@RequestBody AssocationVo  assocationVo){
        List<Assocaition> item=assocaitionService.selectPassAssocaitions(assocationVo);
        int count = assocaitionService.countPassAssocaitions(assocationVo);
        ResponseResult result = new ResponseResult();
        Map<String, Object> map = new HashMap<>();
        if (item.size()>0){
            map.put("items",item);
            map.put("total",count);
            result.setData(map);
            return result;
        }
        result.setCode(10000);
        result.setStatus("false");
        return result;
    }

    @GetMapping("/search/getpolicy.do")
    public ResponseResult getpolicy(){

        String endpoint ="oss-cn-beijing.aliyuncs.com";

        String bucket="duanjianhui1998";

        String accessId="";

        ResponseResult result = new ResponseResult();
        String host = "https://" + bucket + "." + endpoint; // host的格式为 bucketname.endpoint
        // callbackUrl为 上传回调服务器的URL，请将下面的IP和Port配置为您自己的真实信息。
        //  tring callbackUrl = "http://88.88.88.88:8888";
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dir = format + "/"; // 用户上传文件时指定的前缀。
        OSSClient ossClient = ossUtils.getOSSClient();
        Map<String, String> respMap = null;
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            respMap = new LinkedHashMap<String, String>();
            respMap.put("accessid", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            // respMap.put("expire", formatISO8601Date(expiration));
            result.setData(respMap);

        } catch (Exception e) {
            // Assert.fail(e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping("/create/addAssocaitionChil.do")
    public ResponseResult addAssocaitionChil(@RequestBody Assocaition assocaition,HttpSession session){
        ResponseResult result = new ResponseResult();
        Assocaition assocaition1 = (Assocaition) session.getAttribute("assocaition");
        assocaition.setParentId(assocaition1.getId());
        assocaition.setId(UUIDUtils.getUUID("asschil"));
        assocaition.setStatus("0");
        assocaition.setCreateTime(new Date());
        int i = assocaitionService.addAssocitionChil(assocaition);
        if (i>0){
            return  result;
        }
        result.setCode(10000);
        result.setStatus("创建失败");
        return result;
    }

    @GetMapping("/search/getAssocaitionChil.do")
    public ResponseResult getAssocaitionChil(HttpSession session){
        ResponseResult result = new ResponseResult();
        Assocaition assocaition1 = (Assocaition) session.getAttribute("assocaition");
        String assId = assocaition1.getId();
        List<Assocaition> assocaitionList = assocaitionService.selectAssocaitionChil(assId);
        if (assocaitionList.size()>0){
            result.setData(assocaitionList);
        }
        return result;
    }

    @PostMapping("/update/updateStatus.do")
    public ResponseResult updateStatus(@RequestBody Assocaition assocaition){
        ResponseResult result = new ResponseResult();
        int i = assocaitionService.updateStatus(assocaition);
        if (i>0){
            return result;
        }
        result.setCode(10000);
        return result;
    }

    @GetMapping("/search/getMyAssocation.do")
    public ResponseResult getMyAssocation(HttpSession session){
        ResponseResult result = new ResponseResult();
        Assocaition assocaition1 = (Assocaition) session.getAttribute("assocaition");
        if (assocaition1 !=null){
            Assocaition assocaition = assocaitionService.selectAssocaitionUserId(assocaition1.getUserId());
            result.setData(assocaition);
            return result;
        }
        result.setCode(10000);
        return result;
    }

    @PostMapping("/update/upAssocation.do")
    public ResponseResult upAssocation(@RequestBody Assocaition assocaition,HttpSession session){
        ResponseResult result = new ResponseResult();
        Assocaition assocaition1 = (Assocaition) session.getAttribute("assocaition");
        if (assocaition1 !=null){
            assocaition.setId(assocaition1.getId());
            int i = assocaitionService.updateAssocaition(assocaition);
            return result;
        }
        result.setCode(10000);
        return result;
    }
}
