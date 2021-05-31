package com.assocaition.management.module.task;

import com.assocaition.management.commons.ResponseResult;
import com.assocaition.management.module.authority.entity.AuthSysUser;
import com.assocaition.management.module.task.entity.Task;
import com.assocaition.management.module.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Duanjianhui
 * @date 2021-05-22 12:30 下午
 * @describe
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping("/search/getTaskList.do")
    public ResponseResult getTaskList(HttpSession session){
        ResponseResult result = new ResponseResult();
        Object userInfo = session.getAttribute("userInfo");
        if (userInfo!=null){
            AuthSysUser authSysUser = (AuthSysUser) userInfo;
            List<Task> list = taskService.selectTaskListByUserId(authSysUser.getUrId());
            if (list.size()>0){
                result.setData(list);
                return result;
            }
        }
        return result;
    }
}
