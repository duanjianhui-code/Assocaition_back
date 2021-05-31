package com.assocaition.management.module.foreground.controller.person;

import com.assocaition.management.commons.ResponseResult;
import com.assocaition.management.module.authority.entity.AuthSysUser;
import com.assocaition.management.module.foreground.service.person.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author Duanjianhui
 * @date 2021-05-01 12:19 上午
 * @describe
 */
@Controller
@RequestMapping("/person")
public class PersonUserController {
    @Autowired
    PersonService personService;

    @RequestMapping("/updateUser.do")
    @ResponseBody
    public ResponseResult updateUser(AuthSysUser  user, HttpSession session){
        ResponseResult result = new ResponseResult();
        AuthSysUser authSysUser = (AuthSysUser) session.getAttribute("userinfo");
        user.setUrId(authSysUser.getUrId());

        int i = personService.updateUser(user);
        if (i==0){
            result.setStatus("更新失败");
            result.setCode(10000);
            return result;
        }
        user=personService.selectUserById(authSysUser.getUrId());
        session.removeAttribute("userinfo");
        session.setAttribute("userinfo",user);
        return result;
    }

}
