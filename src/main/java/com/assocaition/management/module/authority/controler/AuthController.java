package com.assocaition.management.module.authority.controler;


import com.assocaition.management.commons.ResponseResult;
import com.assocaition.management.module.assocaition.entity.Assocaition;
import com.assocaition.management.module.assocaition.service.AssocaitionService;
import com.assocaition.management.module.authority.entity.AuthSysAccount;
import com.assocaition.management.module.authority.entity.AuthSysMenu;
import com.assocaition.management.module.authority.entity.AuthSysUser;
import com.assocaition.management.module.authority.service.IAuthService;
import com.assocaition.management.module.authority.service.IRoleService;
import com.assocaition.management.module.authority.service.IUserService;
import com.assocaition.management.utils.EncryptUtil;
import com.assocaition.management.utils.JwtUtil;
import com.assocaition.management.utils.RedisUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IAuthService authService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    AssocaitionService assocaitionService;

    @GetMapping("/search/{mnName}")
    public ResponseResult getOperateMenuRoleIds(@PathVariable("mnName") String mnName){
        ResponseResult result = new ResponseResult();
        List<Integer> idList = this.roleService.searchMenuRoleIds(mnName);
        result.setData(idList);
        return result;
    }

    @GetMapping("/routes")
    public ResponseResult getMenu(){
        ResponseResult result = new ResponseResult();
        List<AuthSysMenu> menuList = this.authService.getMenus();
        result.setData(menuList);
        return result;
    }
    @PostMapping("/login")
    public ResponseResult login(@RequestBody AuthSysAccount account, HttpSession session){
//    @RequestMapping("/login")
//    public ResponseResult login(AuthSysAccount account, HttpSession session){
        //System.out.println(session.getId());
        ResponseResult result = new ResponseResult();
        // 1 从Shiro框架中，获取一个Subject对象，代表当前会话的用户
        Subject subject = SecurityUtils.getSubject();
        // 2 认证subject的身份
        // 2.1 封装要认证的身份信息
        AuthenticationToken token = new UsernamePasswordToken(account.getUsername(), EncryptUtil.getMD5Str(account.getPassword()));
        // 2.2 认证：认证失败，抛异常
        try {
            subject.login(token);
            String resToken = JwtUtil.sign(account.getUsername());
            result.setData(result.new Token(resToken));
            // 在session中保存登录标记
//            session.setAttribute(resToken, account.getUsername());
            AuthSysUser authSysUser = authService.findUser(account.getUsername());
            session.setAttribute("userInfo",authSysUser);
            if ("2".equals(authSysUser.getType())){
                Assocaition assocaition = assocaitionService.selectAssocaitionUserId(authSysUser.getUrId());
                session.setAttribute("assocaition",assocaition);
            }else if("3".equals(authSysUser.getType())){
                Assocaition assocaitionChile = assocaitionService.selectAssocaitionUserId(authSysUser.getUrId());
                session.setAttribute("assocaition",assocaitionChile);
            }
            this.redisUtil.set(resToken, account.getUsername(),30*60);
        }catch(Exception e){
            e.printStackTrace();
            result.setData("用户名或密码错误");
        }
        return result;
    }
    @GetMapping("/login")
    public void loginPage(HttpServletResponse res){
        try {
            res.sendRedirect("http://localhost:9527");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/noLogin")
    public ResponseResult noLogin(){
        ResponseResult result = new ResponseResult();
        result.setCode(20001);
        result.setData("没有登录，请登录先");
        return result;
    }

    @RequestMapping("/noAuth")
    public ResponseResult noAuth(){
        ResponseResult result = new ResponseResult();
        result.setCode(20002);
        result.setData("没有权限访问");
        return result;
    }

    @GetMapping("/info")
    public ResponseResult info(@RequestHeader("X-Token") String token, HttpSession session){
        //System.out.println(session.getId());
        ResponseResult result = new ResponseResult();

        String userName = JwtUtil.verityToken(token, "loginName");
        if(userName == null){
            throw new RuntimeException("没有登录");
        }else{
            AuthSysUser user = this.userService.findByUserName(userName);
            List<String> userPermissions = userService.getUserPermssions(userName);
            if(user == null){
                throw new RuntimeException("没有登录");
            }else{
                user.setOperate(userPermissions);
                result.setData(user);
            }
        }
        return result;
    }
    @PostMapping("/logout")
    public ResponseResult logOut(@RequestHeader("X-Token") String token,HttpSession session){
        ResponseResult result = new ResponseResult();
        //session.removeAttribute(token);
        //session.invalidate();
        session.invalidate();
        SecurityUtils.getSubject().logout();
        // 删除redis中的标记

        this.redisUtil.del(token);
        return result;
    }
}
