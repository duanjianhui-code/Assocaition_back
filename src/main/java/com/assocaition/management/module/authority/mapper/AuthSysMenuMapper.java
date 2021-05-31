package com.assocaition.management.module.authority.mapper;


import com.assocaition.management.module.authority.entity.AuthSysMenu;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface AuthSysMenuMapper {
    @Delete({
        "delete from authsys_menu",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into authsys_menu (id, path, ",
        "name, component, ",
        "redirect, hidden, ",
        "always_show, parent_id)",
        "values (#{id,jdbcType=INTEGER}, #{path,jdbcType=VARCHAR}, ",
        "#{name,jdbcType=VARCHAR}, #{component,jdbcType=VARCHAR}, ",
        "#{redirect,jdbcType=VARCHAR}, #{hidden,jdbcType=SMALLINT}, ",
        "#{alwaysShow,jdbcType=SMALLINT}, #{parentId,jdbcType=INTEGER})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(AuthSysMenu record);

    @InsertProvider(type=AuthSysMenuSqlProvider.class, method="insertSelective")
    int insertSelective(AuthSysMenu record);

    @Select({
        "select",
        "id, path, name, component, redirect, hidden, always_show, parent_id",
        "from authsys_menu",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results(id = "MenuMap",value={
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="path", property="path", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="component", property="component", jdbcType=JdbcType.VARCHAR),
        @Result(column="redirect", property="redirect", jdbcType=JdbcType.VARCHAR),
        @Result(column="hidden", property="hidden", jdbcType=JdbcType.SMALLINT),
        @Result(column="always_show", property="alwaysShow", jdbcType=JdbcType.SMALLINT),
        @Result(column="parent_id", property="parentId", jdbcType=JdbcType.INTEGER),

        @Result(column = "id", property = "meta", one = @One(select = "com.assocaition.management.module.authority.mapper.AuthSysMenuMetaMapper.selectByMnId")),
        @Result(column = "id", property = "children", many = @Many(select = "com.assocaition.management.module.authority.mapper.AuthSysMenuMapper.selectAllByParentId"))
    })
    AuthSysMenu selectByPrimaryKey(Integer id);

    @UpdateProvider(type=AuthSysMenuSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AuthSysMenu record);

    @Update({
        "update authsys_menu",
        "set path = #{path,jdbcType=VARCHAR},",
          "name = #{name,jdbcType=VARCHAR},",
          "component = #{component,jdbcType=VARCHAR},",
          "redirect = #{redirect,jdbcType=VARCHAR},",
          "hidden = #{hidden,jdbcType=SMALLINT},",
          "always_show = #{alwaysShow,jdbcType=SMALLINT},",
          "parent_id = #{parentId,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(AuthSysMenu record);

    @Select("select * from authsys_menu where parent_id is null or parent_id=0")
    @ResultMap("MenuMap")
    List<AuthSysMenu> selectAll();

    @Select("select * from authsys_menu where parent_id = #{parentId}")
    @ResultMap("MenuMap")
    List<AuthSysMenu> selectAllByParentId(@Param("parentId") int parentId);

    @Select("select mn.* from authsys_menu mn, authsys_mn_rl mnrl where mnrl.rl_id=#{rlId} and mn.id=mnrl.mn_id")
    @Results(id="MenuSimpleMap",value={
            @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
            @Result(column="path", property="path", jdbcType=JdbcType.VARCHAR),
            @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
            @Result(column="component", property="component", jdbcType=JdbcType.VARCHAR),
            @Result(column="redirect", property="redirect", jdbcType=JdbcType.VARCHAR),
            @Result(column="hidden", property="hidden", jdbcType=JdbcType.SMALLINT),
            @Result(column="always_show", property="alwaysShow", jdbcType=JdbcType.SMALLINT),
            @Result(column="parent_id", property="parentId", jdbcType=JdbcType.INTEGER)
    })
    List<AuthSysMenu> selectByRlId(@Param("rlId") int rlId);

}