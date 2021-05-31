package com.assocaition.management.module.authority.mapper;

import com.assocaition.management.module.authority.entity.AuthSysPermission;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;



public class AuthSysPermissionSqlProvider {

    public String insertSelective(AuthSysPermission record) {
        BEGIN();
        INSERT_INTO("authsys_permission");
        
        if (record.getPrmId() != null) {
            VALUES("prm_id", "#{prmId,jdbcType=INTEGER}");
        }
        
        if (record.getPrmName() != null) {
            VALUES("prm_name", "#{prmName,jdbcType=VARCHAR}");
        }
        
        if (record.getRscId() != null) {
            VALUES("rsc_id", "#{rscId,jdbcType=INTEGER}");
        }
        
        if (record.getOptId() != null) {
            VALUES("opt_id", "#{optId,jdbcType=INTEGER}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(AuthSysPermission record) {
        BEGIN();
        UPDATE("authsys_permission");
        
        if (record.getPrmName() != null) {
            SET("prm_name = #{prmName,jdbcType=VARCHAR}");
        }
        
        if (record.getRscId() != null) {
            SET("rsc_id = #{rscId,jdbcType=INTEGER}");
        }
        
        if (record.getOptId() != null) {
            SET("opt_id = #{optId,jdbcType=INTEGER}");
        }
        
        WHERE("prm_id = #{prmId,jdbcType=INTEGER}");
        
        return SQL();
    }
}