package com.assocaition.management.module.authority.service.shiro.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
public class PermissionAuthFilter extends AccessControlFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        boolean bool = true;
        // 拼凑当前请求url对应的权限字符串   /user/create?xxxxx
        HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
        String requestURI = httpRequest.getRequestURI();

        String[] permStrs = requestURI.split("/");
        if(permStrs != null && permStrs.length>2) {
            String perm = permStrs[1]+":"+permStrs[2];
            // 资源名称:操作:对象id
            // user:create,update,delete,search,other:*
            // shop:create,update,delete...:*
            // who what how
            // 获取当前用户对应的subject对象
            Subject subject = this.getSubject(servletRequest,servletResponse);
            //Object primary = subject.getPrincipals().getPrimaryPrincipal();
            // 调用subject对象的方法，判断是否有权限
            // 返回判断结果
            bool = subject.isPermitted(perm);
        }
        return bool;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        ((HttpServletRequest)servletRequest).getRequestDispatcher("/auth/noAuth").forward(servletRequest,servletResponse);
        return false;
    }
}
