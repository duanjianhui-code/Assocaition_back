package com.assocaition.management.module.authority.service.shiro.filter;


import cn.hutool.json.JSONObject;
import com.assocaition.management.utils.RedisUtil;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
public class TokenAuthFilter extends AccessControlFilter {
    @Autowired
    private RedisUtil redisUtil;
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        boolean bool = false;
        String token = ((HttpServletRequest)request).getHeader("X-Token");
        if(token == null || redisUtil.get(token) == null){
            try {
                request.getRequestDispatcher("/auth/noLogin").forward(request,response);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            bool = true;
        }

        return bool;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //WebUtils.issueRedirect(request,response,"http://localhost:9527");
        //((HttpServletRequest)request).getRequestDispatcher("/auth/noLogin").forward(request,response);
        return false;
    }

    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }
}
