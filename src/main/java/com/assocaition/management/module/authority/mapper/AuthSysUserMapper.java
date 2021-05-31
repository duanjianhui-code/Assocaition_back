package com.assocaition.management.module.authority.mapper;


import com.assocaition.management.module.authority.entity.AuthSysUser;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;


import java.util.List;

public interface AuthSysUserMapper {
    @Delete({
        "delete from authsys_user",
        "where ur_id = #{urId,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer urId);

    @Insert({
        "insert into authsys_user (ur_id, ur_user_name, ",
        "ur_password, ur_salt, ",
        "introduction, avatar, ",
        "name)",
        "values (#{urId,jdbcType=INTEGER}, #{urUserName,jdbcType=VARCHAR}, ",
        "#{urPassword,jdbcType=VARCHAR}, #{urSalt,jdbcType=INTEGER}, ",
        "#{introduction,jdbcType=VARCHAR}, #{avatar,jdbcType=VARCHAR}, ",
        "#{name,jdbcType=VARCHAR})"
    })
    int insert(AuthSysUser record);

    @InsertProvider(type=AuthSysUserSqlProvider.class, method="insertSelective")
    int insertSelective(AuthSysUser record);

    @Select({
        "select",
        "ur_id, ur_user_name, ur_password, ur_salt, introduction, avatar, name",
        "from authsys_user",
        "where ur_id = #{urId,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="ur_id", property="urId", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="ur_user_name", property="urUserName", jdbcType=JdbcType.VARCHAR),
        @Result(column="ur_password", property="urPassword", jdbcType=JdbcType.VARCHAR),
        @Result(column="ur_salt", property="urSalt", jdbcType=JdbcType.INTEGER),
        @Result(column="introduction", property="introduction", jdbcType=JdbcType.VARCHAR),
        @Result(column="avatar", property="avatar", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR)
    })
    AuthSysUser selectByPrimaryKey(Integer urId);

    @UpdateProvider(type=AuthSysUserSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AuthSysUser record);

    @Update({
        "update authsys_user",
        "set ur_user_name = #{urUserName,jdbcType=VARCHAR},",
          "ur_password = #{urPassword,jdbcType=VARCHAR},",
          "ur_salt = #{urSalt,jdbcType=INTEGER},",
          "introduction = #{introduction,jdbcType=VARCHAR},",
          "avatar = #{avatar,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR}",
        "where ur_id = #{urId,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(AuthSysUser record);

    @Results(id="UserMap", value = {
            @Result(column="ur_id", property="urId", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="ur_user_name", property="urUserName", jdbcType=JdbcType.VARCHAR),
            @Result(column="ur_password", property="urPassword", jdbcType=JdbcType.VARCHAR),
            @Result(column="ur_salt", property="urSalt", jdbcType=JdbcType.INTEGER),
            @Result(column="introduction", property="introduction", jdbcType=JdbcType.VARCHAR),
            @Result(column="avatar", property="avatar", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),

            @Result(column="ur_id", property = "roleList", many = @Many(select = "com.assocaition.management.module.authority.mapper.AuthSysRoleMapper.selectByUrId"))
    })
    @Select("select * from authsys_user where ur_user_name=#{username} and type !='4'")
    AuthSysUser selectByUserName(String username);

    @ResultMap(value = "UserMap")
    @Select("select * from authsys_user")
    List<AuthSysUser> selectAll();

    @Delete("delete from authsys_ur_rl where ur_id=#{urId}")
    void unassignRoles(Integer urId);

    @Insert("insert into authsys_ur_rl values (#{urId}, #{rlId})")
    void assignRole(@Param("urId") Integer urId,@Param("rlId") Integer rlId);

    @Update("update authsys_user set ur_password=#{urPassword},name=#{name},email=#{email} where ur_id=#{urId}")
    int updateUser(AuthSysUser user);

    @Select("select ur_id as urId from authsys_user where ur_id=#{userId}")
    AuthSysUser selectUserById(Integer userId);

    @Delete("delete from authsys_ur_rl where ur_id=#{urId} and rl_id=9")
    int deleteUserByUserId(Integer urId);

    @Update("update authsys_user set type=#{type} where ur_id=#{urId} ")
    int updateUser1(AuthSysUser authSysUser);

    @Insert("insert into authsys_ur_rl values (#{urId},9)")
    void updateUserByUserId(Integer urId);

    @Select("select ur_id from authsys_user where type='1' ")
    List<Integer> selectUserId(String type);
}