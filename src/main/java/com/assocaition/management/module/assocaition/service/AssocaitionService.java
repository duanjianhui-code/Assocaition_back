package com.assocaition.management.module.assocaition.service;

import com.assocaition.management.module.assocaition.entity.Assocaition;
import com.assocaition.management.module.assocaition.entity.AssocationVo;

import java.util.List;
import java.util.Map;
import java.util.stream.LongStream;

/**
 * @author Duanjianhui
 * @date 2021-04-22 12:16 下午
 * @describe
 */
public interface AssocaitionService {

    public int countAss();

    /**
     * 添加社团
     * @param assocaition
     * @return
     */
    public int addAssocition(Assocaition assocaition);

    /**
     * 单个删除社团
     * @param id
     * @return
     */
    public  int deleteAssocaitionById(String id);

    /**
     * 批量删除社团
     * @param ids
     * @return
     */
    public  int deleteAssocaitions(List<String> ids);

    /**
     * 查询社团
     * @param param
     * @return
     */
    public Map<String,Object> selectAssocaitions(Map<String,String> param);

    /**
     * 通过ID查询社团
     * @param id
     * @return
     */
    public Assocaition selectAssocaitionById(String id);

    /**
     * 更新社团
     * @param assocaition
     * @return
     */
    public int updateAssocaition(Assocaition assocaition);

    List<Assocaition> selectAssocaitions(AssocationVo assocationVo);

    int countAssocaitions(AssocationVo assocationVo);

    int auditAssocaition(Assocaition assocaition);

    List<Assocaition> selectPassAssocaitions(AssocationVo assocationVo);

    int countPassAssocaitions(AssocationVo assocationVo);

    Assocaition selectAssocaitionUserId(Integer urId);

    List<Map<String, String>> selectAssocaitionEmails();

    int addAssocitionChil(Assocaition assocaition);

    List<Assocaition> selectAssocaitionChil(String assId);

    int updateStatus(Assocaition assocaition);

    void updateAssocaition1(Assocaition assocaition);

    void updateAssocaition2(Assocaition assocaition);
}
