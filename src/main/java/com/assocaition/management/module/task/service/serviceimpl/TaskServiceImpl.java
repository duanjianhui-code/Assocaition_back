package com.assocaition.management.module.task.service.serviceimpl;

import com.assocaition.management.module.task.dao.TaskMapper;
import com.assocaition.management.module.task.entity.Task;
import com.assocaition.management.module.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Duanjianhui
 * @date 2021-04-22 11:24 下午
 * @describe
 */
@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskMapper taskMapper;

    @Override
    public int addTask(Task task) {
        return taskMapper.addTask(task);
    }

    @Override
    public int deleteTask(Task task) {
        return 0;
    }

    @Override
    public Map<String, Object> selectTask(Map<String, String> param) {
        return null;
    }

    @Override
    public List<Task> selectTaskListByUserId(Integer urId) {
        return taskMapper.selectTaskListByUserId(urId);
    }
}
