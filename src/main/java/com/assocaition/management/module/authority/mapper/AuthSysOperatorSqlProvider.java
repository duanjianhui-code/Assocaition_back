package com.assocaition.management.module.authority.mapper;

import com.assocaition.management.module.authority.entity.AuthSysOperator;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;



public class AuthSysOperatorSqlProvider {

    public String insertSelective(AuthSysOperator record) {
        BEGIN();
        INSERT_INTO("authsys_operator");
        
        if (record.getOptId() != null) {
            VALUES("opt_id", "#{optId,jdbcType=INTEGER}");
        }
        
        if (record.getOptName() != null) {
            VALUES("opt_name", "#{optName,jdbcType=VARCHAR}");
        }
        
        if (record.getOptUrl() != null) {
            VALUES("opt_url", "#{optUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getOptDesc() != null) {
            VALUES("opt_desc", "#{optDesc,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(AuthSysOperator record) {
        BEGIN();
        UPDATE("authsys_operator");
        
        if (record.getOptName() != null) {
            SET("opt_name = #{optName,jdbcType=VARCHAR}");
        }
        
        if (record.getOptUrl() != null) {
            SET("opt_url = #{optUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getOptDesc() != null) {
            SET("opt_desc = #{optDesc,jdbcType=VARCHAR}");
        }
        
        WHERE("opt_id = #{optId,jdbcType=INTEGER}");
        
        return SQL();
    }
}