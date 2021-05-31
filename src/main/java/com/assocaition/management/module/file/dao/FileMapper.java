package com.assocaition.management.module.file.dao;


import com.assocaition.management.module.assocaition.entity.Assocaition;
import com.assocaition.management.module.file.entity.AssocaitionFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Duanjianhui
 * @date 2021-04-17 9:53 上午
 * @describe
 */
@Mapper
public interface FileMapper {
    public int insertFile(AssocaitionFile assocaitionFile);

    List<AssocaitionFile> selectShufflingByIdToShuffling(String id);

    AssocaitionFile selectImageByIdToHeard(String id);

    List<AssocaitionFile> selectImagerByIdToLive(String id);

    @Select("select count(*) from assocaition_file")
    int countFile();

    @Update("update statistical set downloadsCount=downloadsCount+1 where assId='default'")
    void addCount();
}
