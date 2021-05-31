package com.assocaition.management.module.task.service;

import com.assocaition.management.module.task.entity.Task;

import java.util.List;
import java.util.Map;

/**
 * @author Duanjianhui
 * @date 2021-04-23 02:23 上午
 * @describe
 */
public interface TaskService {
    /**
     * 添加待办
     * @param task
     * @return
     */
    public int addTask(Task task);

    /**
     * 删除待办，实际为更新
     * @param task
     * @returno9
     */
    public int deleteTask(Task task);

    /**
     * 待办条件查询
     * @param param
     * @return
     */
    public Map<String,Object> selectTask(Map<String,String> param);

    List<Task> selectTaskListByUserId(Integer urId);
}
