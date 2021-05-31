package com.assocaition.management.module.authority.controler;


import com.assocaition.management.commons.ResponseResult;
import com.assocaition.management.module.authority.entity.AuthSysOperator;
import com.assocaition.management.module.authority.service.IOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/operate")
public class OperateController {
    @Autowired
    private IOperateService operateService;
    @GetMapping("/search")
    public ResponseResult search(){
        ResponseResult result = new ResponseResult();
        List<AuthSysOperator> operatorList = this.operateService.selectAll();
        result.setData(operatorList);
        return result;
    }
}
