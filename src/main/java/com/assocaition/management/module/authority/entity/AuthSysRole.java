package com.assocaition.management.module.authority.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AuthSysRole implements Serializable {
    private Integer rlId;

    private String rlName;

    private String rlDesc;

    private List<AuthSysMenu> routes;

    private List<AuthSysPermission> permissionList;

    private static final long serialVersionUID = 1L;

    public Integer getRlId() {
        return rlId;
    }

    public void setRlId(Integer rlId) {
        this.rlId = rlId;
    }

    public String getRlName() {
        return rlName;
    }

    public void setRlName(String rlName) {
        this.rlName = rlName == null ? null : rlName.trim();
    }

    public String getRlDesc() {
        return rlDesc;
    }

    public void setRlDesc(String rlDesc) {
        this.rlDesc = rlDesc == null ? null : rlDesc.trim();
    }

    public List<AuthSysMenu> getRoutes() {
        return routes;
    }

    public void setRoutes(List<AuthSysMenu> routes) {
        this.routes = routes;
    }

    public List<AuthSysPermission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<AuthSysPermission> permissionList) {
        this.permissionList = permissionList;
    }
}