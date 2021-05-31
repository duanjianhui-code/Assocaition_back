package com.assocaition.management.module.authority.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class AuthSysOperator implements Serializable {
    private Integer optId;

    private String optName;

    private String optUrl;

    private String optDesc;

    private static final long serialVersionUID = 1L;

    public Integer getOptId() {
        return optId;
    }

    public void setOptId(Integer optId) {
        this.optId = optId;
    }

    public String getOptName() {
        return optName;
    }

    public void setOptName(String optName) {
        this.optName = optName == null ? null : optName.trim();
    }

    public String getOptUrl() {
        return optUrl;
    }

    public void setOptUrl(String optUrl) {
        this.optUrl = optUrl == null ? null : optUrl.trim();
    }

    public String getOptDesc() {
        return optDesc;
    }

    public void setOptDesc(String optDesc) {
        this.optDesc = optDesc == null ? null : optDesc.trim();
    }
}