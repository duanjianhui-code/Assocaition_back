package com.assocaition.management.module.foreground.service.serviceimpl.assocaition;

import com.assocaition.management.module.assocaition.entity.Assocaition;
import com.assocaition.management.module.foreground.dao.assocaition.FAssocaitionMapper;
import com.assocaition.management.module.foreground.service.assocaition.FAssocaitionService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.util.StringUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Duanjianhui
 * @date 2021-05-01 11:59 上午
 * @describe
 */
@Service
@Transactional
public class FAssocaitionServiceImpl implements FAssocaitionService {
    @Autowired
    FAssocaitionMapper fAssocaitionMapper;
    @Override
    public List<Assocaition> selectAll() {
        return fAssocaitionMapper.selectAll();
    }

    @Override
    public Map<String, Object> selectAssocaition(Assocaition assocaition, RowBounds rowBounds) {
        HashMap<String, Object> map = new HashMap<>();
        List<Assocaition> assocaitionList = fAssocaitionMapper.selectAssocaition(assocaition,rowBounds);
        if (assocaitionList.size()>0){
            for (Assocaition assocaition1 : assocaitionList) {
                if (StringUtil.isEmpty(assocaition1.getIndexImageUrl())){
                    assocaition1.setIndexImageUrl("https://duanjianhui1998.oss-cn-beijing.aliyuncs.com/imager/20210501/ccfright2_img_1.png");
                }
            }
            int count = fAssocaitionMapper.countSelectAssocaition(assocaition);
            map.put("data",assocaitionList);
            map.put("count",count);
        }else {
            map.put("data",null);
            map.put("count",0);
        }
        return map;
    }

    @Override
    public int countAssocaition(Assocaition assocaition) {
        return fAssocaitionMapper.countSelectAssocaition(assocaition);
    }

    @Override
    public List<Assocaition> test(RowBounds rowBounds) {
        return fAssocaitionMapper.test(rowBounds);
    }

    @Override
    public int applyAssocaition(Assocaition assocaition) {
        return fAssocaitionMapper.applyAssocaition(assocaition);
    }

    @Override
    public List<Assocaition> selectChildAssocaition(String id) {
        return fAssocaitionMapper.selectChildAssocaition(id);
    }

    @Override
    public Assocaition selectAssocaitionById(String id) {
        return fAssocaitionMapper.selectAssocaitionById(id);
    }

    @Override
    public List<Assocaition> getApplyAssocaitionByUserId(Integer urId) {
        return fAssocaitionMapper.getApplyAssocaitionByUserId(urId);
    }

    @Override
    public List<Assocaition> selectMyAssocaition(Integer urId) {
        return fAssocaitionMapper.selectMyAssocaition(urId);
    }
}
