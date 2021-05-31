package com.assocaition.management.module.task.dao;

import com.assocaition.management.module.task.entity.Task;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Duanjianhui
 * @date 2021-04-22 11:20 下午
 * @describe
 */
@Mapper
public interface TaskMapper {

    @Insert("insert into task(id,businessName,businessId,businessUrl,assignTo,createTime,createManId,createName,state) values (#{id},#{businessName},#{businessId},#{businessUrl},#{assignTo},#{createTime},#{createManId},#{createName},#{state})")
    int addTask(Task task);


    @Select("select * from task where assignTo=#{urId} and state='0'")
    List<Task> selectTaskListByUserId(Integer urId);
}
