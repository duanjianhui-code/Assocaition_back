package com.assocaition.management.module.activity.service.serviceimpl;

import com.assocaition.management.module.activity.dao.ActivityMapper;
import com.assocaition.management.module.activity.entity.Activity;
import com.assocaition.management.module.activity.entity.ActivityVo;
import com.assocaition.management.module.activity.service.ActivityService;
import com.assocaition.management.module.member.entity.AssocaitionMember;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Duanjianhui
 * @date 2021-05-08 10:15 下午
 * @describe
 */
@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    ActivityMapper activityMapper;

    @Override
    public List<Activity> selectActivityById(String id) {
        return activityMapper.selectActivityById(id);
    }

    @Override
    public int addActivity(Activity activity) {
        return activityMapper.addActivity(activity);
    }

    @Override
    public List<Activity> selectActivitysById(String id) {
        return activityMapper.selectActivitysById(id);
    }

    @Override
    public Map<String, Object> selectAllActivityList(ActivityVo activityVo) {
        Map<String, Object> map = new HashMap<>();
        int offset = activityVo.getPage();
        int limit = activityVo.getLimit();
        RowBounds rowBounds = new RowBounds(((offset-1)*limit), limit);
        List<AssocaitionMember> memberList = activityMapper.selectAllActivityList(activityVo,rowBounds);
        int count = activityMapper.countselectAllActivityList(activityVo);
        map.put("items",memberList);
        map.put("total",count);
        return map;
    }

    @Override
    public Map<String, Object> selectActivityList(ActivityVo activityVo) {
        Map<String, Object> map = new HashMap<>();
        int offset = activityVo.getPage();
        int limit = activityVo.getLimit();
        RowBounds rowBounds = new RowBounds(((offset-1)*limit), limit);
        List<AssocaitionMember> memberList = activityMapper.selectActivityList(activityVo,rowBounds);
        int count = activityMapper.countselectActivityList(activityVo);
        map.put("items",memberList);
        map.put("total",count);
        return map;
    }

    @Override
    public int auditActivity(Activity activity) {
        return activityMapper.auditActivity(activity);
    }

    @Override
    public int deleteActivityById(String id) {
        return activityMapper.deleteActivityById(id);
    }

    @Override
    public Map<String, Object> selectPassAllActivityList(ActivityVo activityVo) {
        Map<String, Object> map = new HashMap<>();
        int offset = activityVo.getPage();
        int limit = activityVo.getLimit();
        RowBounds rowBounds = new RowBounds(((offset-1)*limit), limit);
        List<AssocaitionMember> memberList = activityMapper.selectPassAllActivityList(activityVo,rowBounds);
        int count = activityMapper.countselectPassAllActivityList(activityVo);
        map.put("items",memberList);
        map.put("total",count);
        return map;
    }

    @Override
    public Map<String, Object> selectPassActivityList(ActivityVo activityVo) {
        Map<String, Object> map = new HashMap<>();
        int offset = activityVo.getPage();
        int limit = activityVo.getLimit();
        RowBounds rowBounds = new RowBounds(((offset-1)*limit), limit);
        List<AssocaitionMember> memberList = activityMapper.selectPassActivityList(activityVo,rowBounds);
        int count = activityMapper.countselectPassActivityList(activityVo);
        map.put("items",memberList);
        map.put("total",count);
        return map;
    }
}
