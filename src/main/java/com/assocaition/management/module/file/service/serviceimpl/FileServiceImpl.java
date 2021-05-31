package com.assocaition.management.module.file.service.serviceimpl;


import com.assocaition.management.module.assocaition.entity.Assocaition;
import com.assocaition.management.module.file.dao.FileMapper;
import com.assocaition.management.module.file.entity.AssocaitionFile;
import com.assocaition.management.module.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Duanjianhui
 * @date 2021-04-17 9:48 上午
 * @describe
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {

    @Autowired
    FileMapper fileMapper;

    @Override
    public int insertFile(AssocaitionFile assocaitionFile) {
        return fileMapper.insertFile(assocaitionFile);
    }

    @Override
    public List<AssocaitionFile> selectShufflingByIdToShuffling(String id) {
        return fileMapper.selectShufflingByIdToShuffling(id);
    }

    @Override
    public AssocaitionFile selectImageByIdToHeard(String id) {
        return fileMapper.selectImageByIdToHeard(id);
    }

    @Override
    public List<AssocaitionFile> selectImagerByIdToLive(String id) {
        return fileMapper.selectImagerByIdToLive(id);
    }

    @Override
    public int countFile() {
        return fileMapper.countFile();
    }

    @Override
    public void addCount() {
        fileMapper.addCount();
    }
}
