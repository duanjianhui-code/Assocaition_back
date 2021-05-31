package com.assocaition.management.module.authority.mapper;


import com.assocaition.management.module.authority.entity.AuthSysOperator;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface AuthSysOperatorMapper {
    @Delete({
        "delete from authsys_operator",
        "where opt_id = #{optId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer optId);

    @Insert({
        "insert into authsys_operator (opt_id, opt_name, ",
        "opt_url, opt_desc)",
        "values (#{optId,jdbcType=INTEGER}, #{optName,jdbcType=VARCHAR}, ",
        "#{optUrl,jdbcType=VARCHAR}, #{optDesc,jdbcType=VARCHAR})"
    })
    int insert(AuthSysOperator record);

    @InsertProvider(type=AuthSysOperatorSqlProvider.class, method="insertSelective")
    int insertSelective(AuthSysOperator record);

    @Select({
        "select",
        "opt_id, opt_name, opt_url, opt_desc",
        "from authsys_operator",
        "where opt_id = #{optId,jdbcType=INTEGER}"
    })
    @Results(id="SimpleMap", value = {
        @Result(column="opt_id", property="optId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="opt_name", property="optName", jdbcType=JdbcType.VARCHAR),
        @Result(column="opt_url", property="optUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="opt_desc", property="optDesc", jdbcType=JdbcType.VARCHAR)
    })
    AuthSysOperator selectByPrimaryKey(Integer optId);

    @UpdateProvider(type=AuthSysOperatorSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AuthSysOperator record);

    @Update({
        "update authsys_operator",
        "set opt_name = #{optName,jdbcType=VARCHAR},",
          "opt_url = #{optUrl,jdbcType=VARCHAR},",
          "opt_desc = #{optDesc,jdbcType=VARCHAR}",
        "where opt_id = #{optId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(AuthSysOperator record);

    @ResultMap(value = "SimpleMap")
    @Select("select * from authsys_operator")
    List<AuthSysOperator> selectAll();
}