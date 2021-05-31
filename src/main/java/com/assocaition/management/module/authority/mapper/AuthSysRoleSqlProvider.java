package com.assocaition.management.module.authority.mapper;



import com.assocaition.management.module.authority.entity.AuthSysRole;
import com.assocaition.management.module.authority.entity.AuthSysUser;
import org.apache.ibatis.annotations.Update;
import org.apache.shiro.web.servlet.ShiroHttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

@Component
public class AuthSysRoleSqlProvider {
    @Autowired
    HttpSession session;

    public String insertSelective(AuthSysRole record) {
        BEGIN();
        INSERT_INTO("authsys_role");
        
        if (record.getRlId() != null) {
            VALUES("rl_id", "#{rlId,jdbcType=INTEGER}");
        }
        
        if (record.getRlName() != null) {
            VALUES("rl_name", "#{rlName,jdbcType=VARCHAR}");
        }
        
        if (record.getRlDesc() != null) {
            VALUES("rl_desc", "#{rlDesc,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(AuthSysRole record) {
        BEGIN();
        UPDATE("authsys_role");
        
        if (record.getRlName() != null) {
            SET("rl_name = #{rlName,jdbcType=VARCHAR}");
        }
        
        if (record.getRlDesc() != null) {
            SET("rl_desc = #{rlDesc,jdbcType=VARCHAR}");
        }
        
        WHERE("rl_id = #{rlId,jdbcType=INTEGER}");
        
        return SQL();
    }

    public String selectByCondition(String roleName, String roleDesc){
        BEGIN();
        SELECT("*");
        FROM("authsys_role");
        if(roleName != null && !"".equals(roleName)){
            WHERE(" rl_name like concat('%',#{roleName},'%')");
        }
        if(roleDesc != null && !"".equals(roleDesc)){
            WHERE(" rl_desc like concat('%', #{roleDesc}, '%')");
        }
        ORDER_BY("rl_id");
        return SQL();
    }
}