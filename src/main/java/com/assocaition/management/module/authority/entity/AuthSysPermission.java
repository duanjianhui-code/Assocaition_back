package com.assocaition.management.module.authority.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class AuthSysPermission implements Serializable,Comparable<AuthSysPermission> {
    private Integer prmId;

    private String prmName;

    private Integer rscId;

    private Integer optId;

    private AuthSysOperator operator;

    private AuthSysResources resources;

    private static final long serialVersionUID = 1L;

    public Integer getPrmId() {
        return prmId;
    }

    public void setPrmId(Integer prmId) {
        this.prmId = prmId;
    }

    public String getPrmName() {
        return prmName;
    }

    public void setPrmName(String prmName) {
        this.prmName = prmName == null ? null : prmName.trim();
    }

    public Integer getRscId() {
        return rscId;
    }

    public void setRscId(Integer rscId) {
        this.rscId = rscId;
    }

    public Integer getOptId() {
        return optId;
    }

    public void setOptId(Integer optId) {
        this.optId = optId;
    }

    public AuthSysOperator getOperator() {
        return operator;
    }

    public void setOperator(AuthSysOperator operator) {
        this.operator = operator;
    }

    public AuthSysResources getResources() {
        return resources;
    }

    public void setResources(AuthSysResources resources) {
        this.resources = resources;
    }

    @Override
    public int compareTo(AuthSysPermission o) {
        int result = this.rscId-o.getRscId();
        if(result == 0){
            result = this.optId - o.optId;
        }
        return result;
    }
}