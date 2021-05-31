package com.assocaition.management.module.authority.controler;

import com.assocaition.management.commons.ResponseResult;
import com.assocaition.management.module.authority.entity.AuthSysUser;
import com.assocaition.management.module.authority.service.IUserService;
import com.assocaition.management.utils.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/search")
    public ResponseResult searchUsers(){
        ResponseResult result = new ResponseResult();
        List<AuthSysUser> userList = this.userService.findAll();
        result.setData(userList);
        return result;
    }

    @PutMapping("/update/roles")
    public ResponseResult assignUserRoles(@RequestBody AuthSysUser user){
        ResponseResult result = new ResponseResult();
        this.userService.assignUserRoles(user);
        return result;
    }

    @PostMapping("/update/user")
    public ResponseResult updateUser(@RequestBody AuthSysUser user){
        ResponseResult result = new ResponseResult();
        user.setUrPassword(EncryptUtil.getMD5Str(user.getUrPassword()));
        int i =userService.updateUser(user);
        if (i>0){
            return result;
        }
        result.setCode(10000);
        return result;
    }
}
