package com.assocaition.management.module.authority.mapper;


import com.assocaition.management.module.authority.entity.AuthSysMenu;
import com.assocaition.management.module.authority.entity.AuthSysRole;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface AuthSysRoleMapper {
    @Delete({
        "delete from authsys_role",
        "where rl_id = #{rlId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer rlId);

    @Insert({
        "insert into authsys_role (rl_id, rl_name, ",
        "rl_desc)",
        "values (#{rlId,jdbcType=INTEGER}, #{rlName,jdbcType=VARCHAR}, ",
        "#{rlDesc,jdbcType=VARCHAR})"
    })
    int insert(AuthSysRole record);

    @InsertProvider(type=AuthSysRoleSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys = true, keyColumn = "rl_id", keyProperty = "rlId")
    int insertSelective(AuthSysRole record);

    @Select({
        "select",
        "rl_id, rl_name, rl_desc",
        "from authsys_role",
        "where rl_id = #{rlId,jdbcType=INTEGER}"
    })
    @Results(id = "RoleMap", value = {
        @Result(column="rl_id", property="rlId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="rl_name", property="rlName", jdbcType=JdbcType.VARCHAR),
        @Result(column="rl_desc", property="rlDesc", jdbcType=JdbcType.VARCHAR),

        @Result(column="rl_id", property = "routes", many = @Many(select = "com.assocaition.management.module.authority.mapper.AuthSysMenuMapper.selectByRlId")),
        @Result(column = "rl_id", property = "permissionList", many = @Many(select = "com.assocaition.management.module.authority.mapper.AuthSysPermissionMapper.selectByRlId"))
    })
    AuthSysRole selectByPrimaryKey(Integer rlId);

    @ResultMap("RoleMap")
    @Select("select * from authsys_role rl, authsys_mn_rl mp where mp.mn_id=#{mtId} and mp.rl_id=rl.rl_id")
    List<AuthSysRole> selectRolesByMnId(Integer mtId);

    @UpdateProvider(type=AuthSysRoleSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AuthSysRole record);

    @Update({
        "update authsys_role",
        "set rl_name = #{rlName,jdbcType=VARCHAR},",
          "rl_desc = #{rlDesc,jdbcType=VARCHAR}",
        "where rl_id = #{rlId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(AuthSysRole record);

    @ResultMap(value = "RoleMap")
    @SelectProvider(type = AuthSysRoleSqlProvider.class, method = "selectByCondition")
    List<AuthSysRole> selectByCondition(String roleName, String roleDesc);

    @Insert("insert into authsys_mn_rl values (#{menu.id}, #{rlId})")
    public void assignMenu(@Param("rlId") int rlId, @Param("menu") AuthSysMenu menu);

    @Insert("insert into authsys_mn_rl values (#{mnId}, #{rlId})")
    public void assignMenuId(@Param("rlId") int rlId, @Param("mnId") int mnId);

    @Delete(("delete from authsys_mn_rl where rl_id=#{rlId}"))
    void unassignMenus(int rlId);

    @Results(id = "RoleSimpleMap", value = {
            @Result(column="rl_id", property="rlId", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="rl_name", property="rlName", jdbcType=JdbcType.VARCHAR),
            @Result(column="rl_desc", property="rlDesc", jdbcType=JdbcType.VARCHAR)
    })
    @Select("select * from authsys_role rl, authsys_mn_rl mn where mn.mn_id=#{mnId} and mn.rl_id=rl.rl_id")
    List<AuthSysRole> selectByMnId(int mnId);

    @ResultMap(value = "RoleSimpleMap")
    @Select("select * from authsys_role rl, authsys_ur_rl urrl where urrl.ur_id=#{urId} and urrl.rl_id=rl.rl_id")
    List<AuthSysRole> selectByUrId(int urId);

    @Select("select rl.rl_id from authsys_role rl, authsys_mn_rl mnrl, authsys_menu mn where mn.name=#{mnId} and mn.id=mnrl.mn_id and mnrl.rl_id=rl.rl_id")
    List<Integer> selectRolesByMnName(String mnName);

    @Delete("delete from authsys_rl_prm where rl_id=#{rlId}")
    void unassignPermssions(Integer rlId);

    @Insert("insert into authsys_rl_prm values(#{rlId}, #{prmId})")
    void assignPermssion(@Param("rlId") Integer rlId,@Param("prmId") Integer prmId);

    @ResultMap(value = "RoleMap")
    @Select("select rl.* from authsys_role rl, authsys_ur_rl urrl, authsys_user ur where ur.ur_user_name=#{userName} and urrl.ur_id=ur.ur_id and rl.rl_id=urrl.rl_id")
    List<AuthSysRole> selectByUserName(String username);

    @Insert("insert into authsys_ur_rl(ur_id,rl_id) values(#{urId},2)")
    int addRole(Integer urId);

    @Update("update authsys_user set type='2' where ur_id=#{userId} ")
    int updateUserType(Integer userId);
}