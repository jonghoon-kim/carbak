package com.chabak.repositories;

import com.chabak.vo.Member;
import com.chabak.vo.ReadCount;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository("generateDataDao")
public class GenerateDataDao {
    @Autowired
    SqlSession sqlSession;

    public List<Member> getAllMember(){
        return sqlSession.selectList("generateData.getAllMember",null);
    }

    public List<ReadCount> selectAllReadCount(){
        return sqlSession.selectList("generateData.selectAllReadCount",null);
    }

    public int insertReadCountForTest(ReadCount readCount){
        return sqlSession.insert("generateData.insertReadCountForTest",readCount);
    }
    public int updateReadCountForTest(ReadCount readCount){
        return sqlSession.update("generateData.updateReadCountForTest",readCount);
    }
}
