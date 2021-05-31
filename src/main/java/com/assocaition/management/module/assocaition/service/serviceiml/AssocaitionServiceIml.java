package com.assocaition.management.module.assocaition.service.serviceiml;

import com.assocaition.management.module.assocaition.dao.AssocaitionMapper;
import com.assocaition.management.module.assocaition.entity.Assocaition;
import com.assocaition.management.module.assocaition.entity.AssocationVo;
import com.assocaition.management.module.assocaition.service.AssocaitionService;
import com.assocaition.management.module.file.dao.FileMapper;
import com.assocaition.management.module.file.entity.AssocaitionFile;
import com.assocaition.management.utils.UUIDUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Duanjianhui
 * @date 2021-04-22 12:17 下午
 * @describe
 */
@Service
@Transactional
public class AssocaitionServiceIml implements AssocaitionService {

    @Autowired
    private AssocaitionMapper assocaitionMapper;

    @Autowired
    FileMapper fileMapper;

    @Override
    public int countAss() {
        return assocaitionMapper.countAss();
    }

    @Override
    public int addAssocition(Assocaition assocaition) {
        return 0;
    }

    @Override
    public int deleteAssocaitionById(String id) {
        return assocaitionMapper.deleteAssocaitionById(id);
    }

    @Override
    public int deleteAssocaitions(List<String> ids) {
        return 0;
    }

    @Override
    public Map<String, Object> selectAssocaitions(Map<String, String> param) {
        return null;
    }

    @Override
    public Assocaition selectAssocaitionById(String id) {
        return assocaitionMapper.selectAssocaitionById(id);
    }

    @Override
    public int updateAssocaition(Assocaition assocaition) {
        int i = assocaitionMapper.updateAssocaition(assocaition);
        List<Map<String,String>> maps = assocaition.getFileList();
        for (Map<String,String> map: maps){
            AssocaitionFile assocaitionFile = new AssocaitionFile();
            assocaitionFile.setId(UUIDUtils.getUUID("file"));
            assocaitionFile.setAssId(assocaition.getId());
            assocaitionFile.setOssUrl(map.get("name"));
            assocaitionFile.setIsDelete("0");
            assocaitionFile.setType("2");
            assocaitionFile.setUploadTime(new Date());
            String fileName = String.valueOf(map.get("uid"));

            assocaitionFile.setFileName(fileName);
            fileMapper.insertFile(assocaitionFile);
        }
        return i;
    }

    @Override
    public List<Assocaition> selectAssocaitions(AssocationVo assocationVo) {
        int offset = assocationVo.getPage();
        int limit = assocationVo.getLimit();
        RowBounds rowBounds = new RowBounds(((offset-1)*limit), limit);
        return assocaitionMapper.selectAssocaitions(assocationVo,rowBounds);
    }

    @Override
    public int countAssocaitions(AssocationVo assocationVo) {
        int offset = assocationVo.getPage();
        int limit = assocationVo.getLimit();
        RowBounds rowBounds = new RowBounds(((offset-1)*limit), limit);
        return assocaitionMapper.countAssocaitions(assocationVo,rowBounds);
    }

    @Override
    public int auditAssocaition(Assocaition assocaition) {
        return assocaitionMapper.auditAssocaition(assocaition);
    }

    @Override
    public List<Assocaition> selectPassAssocaitions(AssocationVo assocationVo) {
        int offset = assocationVo.getPage();
        int limit = assocationVo.getLimit();
        RowBounds rowBounds = new RowBounds(((offset-1)*limit), limit);
        return assocaitionMapper.selectPassAssocaitions(assocationVo,rowBounds);
    }

    @Override
    public int countPassAssocaitions(AssocationVo assocationVo) {
        int offset = assocationVo.getPage();
        int limit = assocationVo.getLimit();
        RowBounds rowBounds = new RowBounds(((offset-1)*limit), limit);
        return assocaitionMapper.countPassAssocaitions(assocationVo,rowBounds);
    }

    @Override
    public Assocaition selectAssocaitionUserId(Integer urId) {
        return assocaitionMapper.selectAssocaitionUserId(urId);
    }

    @Override
    public List<Map<String, String>> selectAssocaitionEmails() {
        return assocaitionMapper.selectAssocaitionEmails();
    }

    @Override
    public int addAssocitionChil(Assocaition assocaition) {
        int i = assocaitionMapper.addAssocitionChil(assocaition);
        List<Map<String,String>> maps = assocaition.getFileList();
        for (Map<String,String> map: maps){
            AssocaitionFile assocaitionFile = new AssocaitionFile();
            assocaitionFile.setId(UUIDUtils.getUUID("file"));
            assocaitionFile.setAssId(assocaition.getId());
            assocaitionFile.setOssUrl(map.get("name"));
            assocaitionFile.setIsDelete("0");
            assocaitionFile.setType("3");
            assocaitionFile.setUploadTime(new Date());
            String fileName = String.valueOf(map.get("uid"));

            assocaitionFile.setFileName(fileName);
            fileMapper.insertFile(assocaitionFile);
        }
        return i;
    }

    @Override
    public List<Assocaition> selectAssocaitionChil(String assId) {
        return assocaitionMapper.selectAssocaitionChil(assId);
    }

    @Override
    public int updateStatus(Assocaition assocaition) {
        return assocaitionMapper.updateStatus(assocaition);
    }

    @Override
    public void updateAssocaition1(Assocaition assocaition) {
        assocaitionMapper.updateAssocaition1(assocaition);
    }

    @Override
    public void updateAssocaition2(Assocaition assocaition) {
        assocaitionMapper.updateAssocaition2(assocaition);
    }
}
