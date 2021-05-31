package com.assocaition.management.module.authority.mapper;


import com.assocaition.management.module.authority.entity.AuthSysPermission;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface AuthSysPermissionMapper {
    @Delete({
        "delete from authsys_permission",
        "where prm_id = #{prmId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer prmId);

    @Insert({
        "insert into authsys_permission (prm_id, prm_name, ",
        "rsc_id, opt_id)",
        "values (#{prmId,jdbcType=INTEGER}, #{prmName,jdbcType=VARCHAR}, ",
        "#{rscId,jdbcType=INTEGER}, #{optId,jdbcType=INTEGER})"
    })
    int insert(AuthSysPermission record);

    @InsertProvider(type=AuthSysPermissionSqlProvider.class, method="insertSelective")
    int insertSelective(AuthSysPermission record);

    @Select({
        "select",
        "prm_id, prm_name, rsc_id, opt_id",
        "from authsys_permission",
        "where prm_id = #{prmId,jdbcType=INTEGER}"
    })
    @Results(id="SimpleMap",value={
        @Result(column="prm_id", property="prmId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="prm_name", property="prmName", jdbcType=JdbcType.VARCHAR),
        @Result(column="rsc_id", property="rscId", jdbcType=JdbcType.INTEGER),
        @Result(column="opt_id", property="optId", jdbcType=JdbcType.INTEGER)
    })
    AuthSysPermission selectByPrimaryKey(Integer prmId);

    @UpdateProvider(type=AuthSysPermissionSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AuthSysPermission record);

    @Update({
        "update authsys_permission",
        "set prm_name = #{prmName,jdbcType=VARCHAR},",
          "rsc_id = #{rscId,jdbcType=INTEGER},",
          "opt_id = #{optId,jdbcType=INTEGER}",
        "where prm_id = #{prmId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(AuthSysPermission record);

    @Results(id="PermissionMap",value={
            @Result(column="prm_id", property="prmId", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="prm_name", property="prmName", jdbcType=JdbcType.VARCHAR),
            @Result(column="rsc_id", property="rscId", jdbcType=JdbcType.INTEGER),
            @Result(column="opt_id", property="optId", jdbcType=JdbcType.INTEGER),

            @Result(column = "opt_id", property = "operator", one = @One(select = "com.assocaition.management.module.authority.mapper.AuthSysOperatorMapper.selectByPrimaryKey")),
            @Result(column = "rsc_id", property = "resources", one = @One(select = "com.assocaition.management.module.authority.mapper.AuthSysResourcesMapper.selectByPrimaryKey"))
    })
    @Select("select * from authsys_permission where rsc_id=#{rscId}")
    List<AuthSysPermission> selectByRscId(int rscId);

    @ResultMap(value = "PermissionMap")
    @Select("select perm.* from authsys_permission perm, authsys_rl_prm rlprm where rlprm.rl_id=#{rlId} and rlprm.prm_id=perm.prm_id")
    List<AuthSysPermission> selectByRlId(int rlId);

    @Delete("delete from authsys_permission where rsc_id=#{rscId}")
    void deleteByRscId(Integer rscId);

    @ResultMap(value = "PermissionMap")
    @Select("select * from authsys_permission")
    List<AuthSysPermission> selectAll();

    @ResultMap(value = "SimpleMap")
    @Select("select * from authsys_permission")
    List<AuthSysPermission> selectAllSimplePermissions();

    @ResultMap(value = "PermissionMap")
    @Select("select prm.* from authsys_permission prm, authsys_rl_prm rlprm, authsys_ur_rl urrl, authsys_user ur where ur.ur_user_name=#{userName} and ur.ur_id=urrl.ur_id and urrl.rl_id=rlprm.rl_id and prm.prm_id=rlprm.prm_id")
    List<AuthSysPermission> selectByUserName(String userName);
}