package com.assocaition.management.module.authority.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class AuthSysResources implements Serializable {
    private Integer rscId;

    private String rscName;

    private String rscUrl;

    private String rscDesc;

    private List<AuthSysPermission> permissionList;

    private static final long serialVersionUID = 1L;

    public Integer getRscId() {
        return rscId;
    }

    public void setRscId(Integer rscId) {
        this.rscId = rscId;
    }

    public String getRscName() {
        return rscName;
    }

    public void setRscName(String rscName) {
        this.rscName = rscName == null ? null : rscName.trim();
    }

    public String getRscUrl() {
        return rscUrl;
    }

    public void setRscUrl(String rscUrl) {
        this.rscUrl = rscUrl == null ? null : rscUrl.trim();
    }

    public String getRscDesc() {
        return rscDesc;
    }

    public void setRscDesc(String rscDesc) {
        this.rscDesc = rscDesc == null ? null : rscDesc.trim();
    }

    public List<AuthSysPermission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<AuthSysPermission> permissionList) {
        this.permissionList = permissionList;
    }
}