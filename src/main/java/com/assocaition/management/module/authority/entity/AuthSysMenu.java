package com.assocaition.management.module.authority.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class AuthSysMenu implements Serializable {
    private Integer id;

    private String path;

    private String name;

    private String component;

    private String redirect;

    private Short hidden;

    private Short alwaysShow;

    private Integer parentId;

    private AuthSysMenuMeta meta;

    private List<AuthSysMenu> children;

    private static final long serialVersionUID = 1L;

    public AuthSysMenuMeta getMeta() {
        return meta;
    }

    public void setMeta(AuthSysMenuMeta meta) {
        this.meta = meta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component == null ? null : component.trim();
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect == null ? null : redirect.trim();
    }

    public Short getHidden() {
        return hidden;
    }

    public void setHidden(Short hidden) {
        this.hidden = hidden;
    }

    public Short getAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(Short alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<AuthSysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<AuthSysMenu> children) {
        this.children = children;
    }
}