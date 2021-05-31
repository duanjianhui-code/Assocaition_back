package com.assocaition.management.module.authority.mapper;

import com.assocaition.management.module.authority.entity.AuthSysUser;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;



public class AuthSysUserSqlProvider {

    public String insertSelective(AuthSysUser record) {
        BEGIN();
        INSERT_INTO("authsys_user");
        
        if (record.getUrId() != null) {
            VALUES("ur_id", "#{urId,jdbcType=INTEGER}");
        }
        
        if (record.getUrUserName() != null) {
            VALUES("ur_user_name", "#{urUserName,jdbcType=VARCHAR}");
        }
        
        if (record.getUrPassword() != null) {
            VALUES("ur_password", "#{urPassword,jdbcType=VARCHAR}");
        }
        
        if (record.getUrSalt() != null) {
            VALUES("ur_salt", "#{urSalt,jdbcType=INTEGER}");
        }
        
        if (record.getIntroduction() != null) {
            VALUES("introduction", "#{introduction,jdbcType=VARCHAR}");
        }
        
        if (record.getAvatar() != null) {
            VALUES("avatar", "#{avatar,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            VALUES("name", "#{name,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(AuthSysUser record) {
        BEGIN();
        UPDATE("authsys_user");
        
        if (record.getUrUserName() != null) {
            SET("ur_user_name = #{urUserName,jdbcType=VARCHAR}");
        }
        
        if (record.getUrPassword() != null) {
            SET("ur_password = #{urPassword,jdbcType=VARCHAR}");
        }
        
        if (record.getUrSalt() != null) {
            SET("ur_salt = #{urSalt,jdbcType=INTEGER}");
        }
        
        if (record.getIntroduction() != null) {
            SET("introduction = #{introduction,jdbcType=VARCHAR}");
        }
        
        if (record.getAvatar() != null) {
            SET("avatar = #{avatar,jdbcType=VARCHAR}");
        }
        
        if (record.getName() != null) {
            SET("name = #{name,jdbcType=VARCHAR}");
        }
        
        WHERE("ur_id = #{urId,jdbcType=INTEGER}");
        
        return SQL();
    }
}