package com.chabak.repositories;

import com.chabak.vo.Member;


import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository("memberDao")
public class MemberDao {
    @Autowired
    SqlSession sqlSession;

    public int insertMember(Member member){
        int insertedCount = sqlSession.insert("member.insertMember", member);
        return insertedCount;
    }

    public Member getMember(Member member) {
        Member getMember= sqlSession.selectOne("member.getMember", member);
        return getMember;
    }


}
