package com.assocaition.management.module.authority.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Data
public class AuthSysMenuMeta implements Serializable {
    private Integer id;

    private String title;

    private String icon;

    private Short noCache;

    private Short affix;

    private Short breadcrumb;

    private String activeMenu;

    private Integer mnId;

    private List<Integer> roles;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public Short getNoCache() {
        return noCache;
    }

    public void setNoCache(Short noCache) {
        this.noCache = noCache;
    }

    public Short getAffix() {
        return affix;
    }

    public void setAffix(Short affix) {
        this.affix = affix;
    }

    public Short getBreadcrumb() {
        return breadcrumb;
    }

    public void setBreadcrumb(Short breadcrumb) {
        this.breadcrumb = breadcrumb;
    }

    public String getActiveMenu() {
        return activeMenu;
    }

    public void setActiveMenu(String activeMenu) {
        this.activeMenu = activeMenu == null ? null : activeMenu.trim();
    }

    public Integer getMnId() {
        return mnId;
    }

    public void setMnId(Integer mnId) {
        this.mnId = mnId;
    }



    private List<AuthSysRole> roleList;

    public List<Integer> getRoles() {
        if(this.roleList != null){
            this.roles = new ArrayList<>();
            for(AuthSysRole role:roleList){
                this.roles.add(role.getRlId());
            }
        }
        return roles;
    }

    public void setRoles(List<Integer> roles) {
        this.roles = roles;
    }

    public List<AuthSysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<AuthSysRole> roleList) {
        this.roleList = roleList;
    }
}