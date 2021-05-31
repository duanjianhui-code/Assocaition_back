package com.assocaition.management.module.authority.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AuthSysUser implements Serializable {
    private Integer urId;

    private String urUserName;
    
    private String urPassword;
    private Integer urSalt;

    private String introduction;

    private String avatar;

    private String name;

    private List<AuthSysRole> roleList;

    private List<Integer> roles;

    private List<String> operate;

    private String telephone;

    private String type;

    private String email;

    private String age;

    private String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<String> getOperate() {
        return operate;
    }

    public void setOperate(List<String> operate) {
        this.operate = operate;
    }

    private static final long serialVersionUID = 1L;

    public List<AuthSysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<AuthSysRole> roleList) {
        this.roleList = roleList;
    }

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

    public Integer getUrId() {
        return urId;
    }

    public void setUrId(Integer urId) {
        this.urId = urId;
    }

    public String getUrUserName() {
        return urUserName;
    }

    public void setUrUserName(String urUserName) {
        this.urUserName = urUserName == null ? null : urUserName.trim();
    }

    public String getUrPassword() {
        return urPassword;
    }

    public void setUrPassword(String urPassword) {
        this.urPassword = urPassword == null ? null : urPassword.trim();
    }

    public Integer getUrSalt() {
        return urSalt;
    }

    public void setUrSalt(Integer urSalt) {
        this.urSalt = urSalt;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}