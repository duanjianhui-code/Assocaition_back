package com.assocaition.management.module.activity.service;

import com.assocaition.management.module.activity.entity.Activity;
import com.assocaition.management.module.activity.entity.ActivityVo;

import java.util.List;
import java.util.Map;

/**
 * @author Duanjianhui
 * @date 2021-05-08 10:14 下午
 * @describe
 */
public interface ActivityService {

    List<Activity> selectActivityById(String id);

    int addActivity(Activity activity);

    List<Activity> selectActivitysById(String id);

    Map<String, Object> selectAllActivityList(ActivityVo activityVo);

    Map<String, Object> selectActivityList(ActivityVo activityVo);

    int auditActivity(Activity activity);

    int deleteActivityById(String id);

    Map<String, Object> selectPassAllActivityList(ActivityVo activityVo);

    Map<String, Object> selectPassActivityList(ActivityVo activityVo);
}
