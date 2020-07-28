package com.chabak.repositories;

import com.chabak.vo.ReadCount;
import com.chabak.vo.Review;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("readCountDao")
public class ReadCountDao {
    @Autowired
    SqlSession sqlSession;

    public ReadCount selectReadCount(ReadCount readCount){
        ReadCount resultReadCount = sqlSession.selectOne("readCount.selectReadCount",readCount);
        return resultReadCount;
    }

    public int insertReadCount(ReadCount readCount){
        int insertCount = sqlSession.insert("readCount.insertReadCount",readCount);
        return insertCount;
    }

    public int updateReadCount(ReadCount readCount){
        int updateCount = sqlSession.insert("readCount.updateReadCount",readCount);
        return updateCount;
    }
}
