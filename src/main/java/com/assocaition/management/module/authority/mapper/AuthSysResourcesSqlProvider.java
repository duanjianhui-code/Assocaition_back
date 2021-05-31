package com.assocaition.management.module.authority.mapper;

import com.assocaition.management.module.authority.entity.AuthSysResources;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;



public class AuthSysResourcesSqlProvider {

    public String insertSelective(AuthSysResources record) {
        BEGIN();
        INSERT_INTO("authsys_resources");
        
        if (record.getRscId() != null) {
            VALUES("rsc_id", "#{rscId,jdbcType=INTEGER}");
        }
        
        if (record.getRscName() != null) {
            VALUES("rsc_name", "#{rscName,jdbcType=VARCHAR}");
        }
        
        if (record.getRscUrl() != null) {
            VALUES("rsc_url", "#{rscUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getRscDesc() != null) {
            VALUES("rsc_desc", "#{rscDesc,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(AuthSysResources record) {
        BEGIN();
        UPDATE("authsys_resources");
        
        if (record.getRscName() != null) {
            SET("rsc_name = #{rscName,jdbcType=VARCHAR}");
        }
        
        if (record.getRscUrl() != null) {
            SET("rsc_url = #{rscUrl,jdbcType=VARCHAR}");
        }
        
        if (record.getRscDesc() != null) {
            SET("rsc_desc = #{rscDesc,jdbcType=VARCHAR}");
        }
        
        WHERE("rsc_id = #{rscId,jdbcType=INTEGER}");
        
        return SQL();
    }
}