package com.assocaition.management.module.authority.controler;


import com.assocaition.management.commons.ResponseResult;
import com.assocaition.management.module.authority.entity.AuthSysRole;
import com.assocaition.management.module.authority.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;
    @GetMapping("/search")
    public ResponseResult search(String roleName, HttpSession session){
        ResponseResult responseResult = new ResponseResult();
        List<AuthSysRole> roleList = this.roleService.search(roleName);
        responseResult.setData(roleList);
        return responseResult;
    }

    @GetMapping("/search/{rlId}")
    public ResponseResult search(@PathVariable("rlId") int rlId){
        ResponseResult responseResult = new ResponseResult();
        AuthSysRole role = this.roleService.findById(rlId);
        if(role == null){
            responseResult.setStatus("not exist");
        }else{
            responseResult.setData(role);
        }
        return responseResult;
    }
    @PostMapping("/create")
    public ResponseResult add(@RequestBody AuthSysRole role){
        ResponseResult responseResult = new ResponseResult();
        this.roleService.add(role);
        responseResult.setData(role);
        return responseResult;
    }

    @PutMapping("/update/{rlId}")
    // 角色的基本信息， 分配角色的路由
    public ResponseResult update(@PathVariable("rlId") int rlId, @RequestBody AuthSysRole role){
        ResponseResult responseResult = new ResponseResult();
        this.roleService.update(rlId, role);
        return responseResult;
    }
    @PutMapping("/update/perms")
    // 给角色分配后台的权限
    public ResponseResult updatePermssion(@RequestBody AuthSysRole role){
        ResponseResult responseResult = new ResponseResult();
        this.roleService.updateRolePermissions(role);
        return responseResult;
    }

    @DeleteMapping("/delete/{rlId}")
    public ResponseResult delete(@PathVariable("rlId") int rlId){
        ResponseResult result = new ResponseResult();
        this.roleService.deleteRole(rlId);
        return result;
    }
}
