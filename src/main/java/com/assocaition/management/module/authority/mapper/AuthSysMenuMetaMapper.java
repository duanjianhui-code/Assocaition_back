package com.assocaition.management.module.authority.mapper;


import com.assocaition.management.module.authority.entity.AuthSysMenuMeta;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface AuthSysMenuMetaMapper {
    @Delete({
        "delete from authsys_menu_meta",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into authsys_menu_meta (id, title, ",
        "icon, no_cache, ",
        "affix, breadcrumb, ",
        "active_menu, mn_id)",
        "values (#{id,jdbcType=INTEGER}, #{title,jdbcType=VARCHAR}, ",
        "#{icon,jdbcType=VARCHAR}, #{noCache,jdbcType=SMALLINT}, ",
        "#{affix,jdbcType=SMALLINT}, #{breadcrumb,jdbcType=SMALLINT}, ",
        "#{activeMenu,jdbcType=VARCHAR}, #{mnId,jdbcType=INTEGER})"
    })
    int insert(AuthSysMenuMeta record);

    @InsertProvider(type=AuthSysMenuMetaSqlProvider.class, method="insertSelective")
    int insertSelective(AuthSysMenuMeta record);

    @Select({
        "select",
        "id, title, icon, no_cache, affix, breadcrumb, active_menu, mn_id",
        "from authsys_menu_meta",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results(id="MetaMap",value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR),
        @Result(column="icon", property="icon", jdbcType=JdbcType.VARCHAR),
        @Result(column="no_cache", property="noCache", jdbcType=JdbcType.SMALLINT),
        @Result(column="affix", property="affix", jdbcType=JdbcType.SMALLINT),
        @Result(column="breadcrumb", property="breadcrumb", jdbcType=JdbcType.SMALLINT),
        @Result(column="active_menu", property="activeMenu", jdbcType=JdbcType.VARCHAR),
        @Result(column="mn_id", property="mnId", jdbcType=JdbcType.INTEGER),

        @Result(column = "mn_id", property = "roleList", many = @Many(select = "com.assocaition.management.module.authority.mapper.AuthSysRoleMapper.selectRolesByMnId"))
    })
    AuthSysMenuMeta selectByPrimaryKey(Integer id);

    @Select("select * from authsys_menu_meta where mn_id=#{mnId}")
    @ResultMap("MetaMap")
    AuthSysMenuMeta selectByMnId(int mnId);

    @UpdateProvider(type=AuthSysMenuMetaSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(AuthSysMenuMeta record);

    @Update({
        "update authsys_menu_meta",
        "set title = #{title,jdbcType=VARCHAR},",
          "icon = #{icon,jdbcType=VARCHAR},",
          "no_cache = #{noCache,jdbcType=SMALLINT},",
          "affix = #{affix,jdbcType=SMALLINT},",
          "breadcrumb = #{breadcrumb,jdbcType=SMALLINT},",
          "active_menu = #{activeMenu,jdbcType=VARCHAR},",
          "mn_id = #{mnId,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(AuthSysMenuMeta record);

    @Delete("delete from authsys_menu_meta where mn_id=#{id}")
    void deleteByMnId(int id);
}