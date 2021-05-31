package com.assocaition.management.module.authority.mapper;

import com.assocaition.management.module.authority.entity.AuthSysMenuMeta;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;



public class AuthSysMenuMetaSqlProvider {

    public String insertSelective(AuthSysMenuMeta record) {
        BEGIN();
        INSERT_INTO("authsys_menu_meta");
        
        if (record.getId() != null) {
            VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getTitle() != null) {
            VALUES("title", "#{title,jdbcType=VARCHAR}");
        }
        
        if (record.getIcon() != null) {
            VALUES("icon", "#{icon,jdbcType=VARCHAR}");
        }
        
        if (record.getNoCache() != null) {
            VALUES("no_cache", "#{noCache,jdbcType=SMALLINT}");
        }
        
        if (record.getAffix() != null) {
            VALUES("affix", "#{affix,jdbcType=SMALLINT}");
        }
        
        if (record.getBreadcrumb() != null) {
            VALUES("breadcrumb", "#{breadcrumb,jdbcType=SMALLINT}");
        }
        
        if (record.getActiveMenu() != null) {
            VALUES("active_menu", "#{activeMenu,jdbcType=VARCHAR}");
        }
        
        if (record.getMnId() != null) {
            VALUES("mn_id", "#{mnId,jdbcType=INTEGER}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(AuthSysMenuMeta record) {
        BEGIN();
        UPDATE("authsys_menu_meta");
        
        if (record.getTitle() != null) {
            SET("title = #{title,jdbcType=VARCHAR}");
        }
        
        if (record.getIcon() != null) {
            SET("icon = #{icon,jdbcType=VARCHAR}");
        }
        
        if (record.getNoCache() != null) {
            SET("no_cache = #{noCache,jdbcType=SMALLINT}");
        }
        
        if (record.getAffix() != null) {
            SET("affix = #{affix,jdbcType=SMALLINT}");
        }
        
        if (record.getBreadcrumb() != null) {
            SET("breadcrumb = #{breadcrumb,jdbcType=SMALLINT}");
        }
        
        if (record.getActiveMenu() != null) {
            SET("active_menu = #{activeMenu,jdbcType=VARCHAR}");
        }
        
        if (record.getMnId() != null) {
            SET("mn_id = #{mnId,jdbcType=INTEGER}");
        }
        
        WHERE("id = #{id,jdbcType=INTEGER}");
        
        return SQL();
    }
}