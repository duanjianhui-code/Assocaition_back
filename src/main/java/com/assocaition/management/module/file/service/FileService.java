package com.assocaition.management.module.file.service;


import com.assocaition.management.module.file.entity.AssocaitionFile;

import java.util.List;

/**
 * @author Duanjianhui
 * @date 2021-04-17 9:47 上午
 * @describe
 */
public interface FileService {
    int insertFile(AssocaitionFile assocaitionFile);

    List<AssocaitionFile> selectShufflingByIdToShuffling(String id);

    AssocaitionFile selectImageByIdToHeard(String id);

    List<AssocaitionFile> selectImagerByIdToLive(String id);

    int countFile();

    void addCount();
}
