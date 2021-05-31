package com.assocaition.management.module.authority.service.shiro;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShiroUser implements Serializable {
    private String userName;
    private int id;
    private String authCacheKey;
    private String password;

    public String toString(){
        return this.authCacheKey;
    }
}