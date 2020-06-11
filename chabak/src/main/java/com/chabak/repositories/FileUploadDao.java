package com.chabak.repositories;


import com.chabak.vo.FileUpload;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("fileUploadDao")
public class FileUploadDao {
    @Autowired
    SqlSession sqlSession;

    public int insertFile(FileUpload fileUpload){

        int intCount = sqlSession.insert("fileUpload.insertUpload",fileUpload);

        return intCount;
    }

    public List<FileUpload> selectFileList(){
        List<FileUpload> fileList = null;
        fileList = sqlSession.selectList("fileUpload.selectFileList");
        return fileList;

    }

}
