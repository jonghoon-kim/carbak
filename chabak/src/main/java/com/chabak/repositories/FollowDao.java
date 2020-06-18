package com.chabak.repositories;

import com.chabak.vo.Follow;
import com.chabak.vo.Member;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

// 프로필 수정(사진 수정 + 개인정보 창 링크 이동)

    // 게시글 갖고오기 + 뿌리기

    // 팔로워 정보 갖고오기 + 뿌리기
    @Repository("MyPageDao")
    public class FollowDao {
        @Autowired
        SqlSession sqlSession;

        public List<Follow> getFollower(Follow follow){
           // int insertedCount = sqlSession.insert("member.insertMember", member);
            return sqlSession.selectList("follow.getFollower", follow);
        }

    // 팔로잉 정보 갖고오기 + 뿌리기
}




