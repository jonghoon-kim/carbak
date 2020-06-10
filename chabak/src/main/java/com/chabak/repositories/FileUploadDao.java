package com.chabak.repositories;

import com.chabak.vo.TestFileVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("fileUploadDao")
public class FileUploadDao {
    @Autowired
    SqlSession sqlSession;

    public int insertFile(TestFileVo testFileVo){

        int intCount = sqlSession.insert("fileUpload.insertSaveName",testFileVo);

        return intCount;
    }

    public List<TestFileVo> selectFileList(){
        List<TestFileVo> fileList = null;
        fileList = sqlSession.selectList("fileUpload.selectFileList");
        return fileList;

    }

}
