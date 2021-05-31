package com.assocaition.management.module.authority.mapper;


import com.assocaition.management.module.authority.entity.AuthSysResources;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface AuthSysResourcesMapper {
    @Delete({
        "delete from authsys_resources",
        "where rsc_id = #{rscId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer rscId);

    @Insert({
        "insert into authsys_resources (rsc_id, rsc_name, ",
        "rsc_url, rsc_desc)",
        "values (#{rscId,jdbcType=INTEGER}, #{rscName,jdbcType=VARCHAR}, ",
        "#{rscUrl,jdbcType=VARCHAR}, #{rscDesc,jdbcType=VARCHAR})"
    })
    int insert(AuthSysResources record);

    @InsertProvider(type=AuthSysResourcesSqlProvider.class, method="insertSelective")
    int insertSelective(AuthSysResources record);

    @Select({
        "select",
        "rsc_id, rsc_name, rsc_url, rsc_desc",
        "from authsys_resources",
        "where rsc_id = #{rscId,jdbcType=INTEGER}"
    })
    @Results(id="ResourcesSimpleMap", value = {
        @Result(column="rsc_id", property="rscId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="rsc_name", property="rscName", jdbcType=JdbcType.VARCHAR),
        @Result(column="rsc_url", property="rscUrl", jdbcType=JdbcType.VARCHAR),
        @Result(column="rsc_desc", property="rscDesc", jdbcType=JdbcType.VARCHAR)
    })
    AuthSysResources selectByPrimaryKey(Integer rscId);

    @UpdateProvider(type=AuthSysResourcesSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AuthSysResources record);

    @Update({
        "update authsys_resources",
        "set rsc_name = #{rscName,jdbcType=VARCHAR},",
          "rsc_url = #{rscUrl,jdbcType=VARCHAR},",
          "rsc_desc = #{rscDesc,jdbcType=VARCHAR}",
        "where rsc_id = #{rscId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(AuthSysResources record);

    @Results(id="ResourcesMap", value = {
            @Result(column="rsc_id", property="rscId", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="rsc_name", property="rscName", jdbcType=JdbcType.VARCHAR),
            @Result(column="rsc_url", property="rscUrl", jdbcType=JdbcType.VARCHAR),
            @Result(column="rsc_desc", property="rscDesc", jdbcType=JdbcType.VARCHAR),

            @Result(column = "rsc_id", property = "permissionList", many = @Many(select = "com.assocaition.management.module.authority.mapper.AuthSysPermissionMapper.selectByRscId"))
    })
    @Select("select * from authsys_resources")
    List<AuthSysResources> selectAllResources();

    @Insert({
            "insert into authsys_resources (rsc_id, rsc_name, ",
            "rsc_url, rsc_desc)",
            "values (#{rscId,jdbcType=INTEGER}, #{rscName,jdbcType=VARCHAR}, ",
            "#{rscUrl,jdbcType=VARCHAR}, #{rscDesc,jdbcType=VARCHAR})"
    })
    @Options(useGeneratedKeys = true, keyColumn = "rsc_id", keyProperty = "rscId")
    void insertResource(AuthSysResources resources);
}