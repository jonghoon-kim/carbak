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


    @Repository("FollowDao")
    public class FollowDao {
        @Autowired
        SqlSession sqlSession;

        // 팔로워 정보 갖고오기 + 뿌리기  <- 수정전 코드
//        public List<Follow> getFollower(Follow follow){
//            // follow.getFllower는 follow.xml에 있는 매서드
//            return sqlSession.selectList("follow.getFollower", follow);
//        }

    // 팔로잉 정보 갖고오기 + 뿌리기
        public List<Follow> selectDataHashMapServiceList() throws Exception {
            return sqlSession.selectList("follow.selectDataHashMapServiceList");
        }
}




