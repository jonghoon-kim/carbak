package com.chabak.repositories;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("MyPageDao")
public class MyPageDao {
    @Autowired
    SqlSession sqlSession;

    // 프로필 수정(사진 수정 + 개인정보 창 링크 이동)

    // 게시글 갖고오기 + 뿌리기

    // 팔로워 정보 갖고오기 + 뿌리기

    // 팔로잉 정보 갖고오기 + 뿌리기
}




