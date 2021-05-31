package com.assocaition.management.module.authority.mapper;

import com.assocaition.management.module.authority.entity.AuthSysMenu;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;


public class AuthSysMenuSqlProvider {

    public String insertSelective(AuthSysMenu record) {
        BEGIN();
        INSERT_INTO("authsys_menu");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getPath() != null) {
            VALUES("path", "#{path,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        
        if (record.getComponent() != null) {
            VALUES("component", "#{component,jdbcType=VARCHAR}");
        }
        
        if (record.getRedirect() != null) {
            VALUES("redirect", "#{redirect,jdbcType=VARCHAR}");
        }
        
        if (record.getHidden() != null) {
            VALUES("hidden", "#{hidden,jdbcType=SMALLINT}");
        }
        
        if (record.getAlwaysShow() != null) {
            VALUES("always_show", "#{alwaysShow,jdbcType=SMALLINT}");
        }
        
        if (record.getParentId() != null) {
            VALUES("parent_id", "#{parentId,jdbcType=INTEGER}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(AuthSysMenu record) {
        BEGIN();
        UPDATE("authsys_menu");
        
        if (record.getPath() != null) {
            SET("path = #{path,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            SET("name = #{name,jdbcType=VARCHAR}");
        }
        
        if (record.getComponent() != null) {
            SET("component = #{component,jdbcType=VARCHAR}");
        }
        
        if (record.getRedirect() != null) {
            SET("redirect = #{redirect,jdbcType=VARCHAR}");
        }
        
        if (record.getHidden() != null) {
            SET("hidden = #{hidden,jdbcType=SMALLINT}");
        }
        
        if (record.getAlwaysShow() != null) {
            SET("always_show = #{alwaysShow,jdbcType=SMALLINT}");
        }
        
        if (record.getParentId() != null) {
            SET("parent_id = #{parentId,jdbcType=INTEGER}");
        }
        
        WHERE("id = #{id,jdbcType=INTEGER}");
        
        return SQL();
    }
}