package com.assocaition.management.module.activity.dao;

import com.assocaition.management.module.activity.entity.Activity;
import com.assocaition.management.module.activity.entity.ActivityVo;
import com.assocaition.management.module.member.entity.AssocaitionMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * @author Duanjianhui
 * @date 2021-05-08 10:13 下午
 * @describe
 */
@Mapper
public interface ActivityMapper {

    List<Activity> selectActivityById(String id);

    int addActivity(Activity activity);

    List<Activity> selectActivitysById(String id);

    List<AssocaitionMember> selectAllActivityList(@Param("memberVo") ActivityVo activityVo, RowBounds rowBounds);

    int countselectAllActivityList(@Param("memberVo")ActivityVo activityVo);

    List<AssocaitionMember> selectActivityList(@Param("memberVo")ActivityVo activityVo, RowBounds rowBounds);

    int countselectActivityList(@Param("memberVo")ActivityVo activityVo);

    int auditActivity(Activity activity);

    int deleteActivityById(String id);

    List<AssocaitionMember> selectPassAllActivityList(@Param("memberVo")ActivityVo activityVo, RowBounds rowBounds);

    int countselectPassAllActivityList(@Param("memberVo")ActivityVo activityVo);

    List<AssocaitionMember> selectPassActivityList(@Param("memberVo")ActivityVo activityVo, RowBounds rowBounds);

    int countselectPassActivityList(@Param("memberVo")ActivityVo activityVo);
}
