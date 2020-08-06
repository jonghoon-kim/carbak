package com.chabak.repositories;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("hashTagDao")
public class HashTagDao {
    @Autowired
    SqlSession sqlSession;

    public int insertHashTag(String Hashtag){
        return sqlSession.selectOne("insertHashTag", Hashtag);
    }

}
