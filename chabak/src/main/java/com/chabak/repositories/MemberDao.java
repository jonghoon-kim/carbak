package com.chabak.repositories;

import com.chabak.vo.Member;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.swing.plaf.metal.MetalMenuBarUI;

@Repository("memberDao")
public class MemberDao {
    @Autowired
    SqlSession sqlSession;

    public int insertMember(Member member) {
        int insertCount = sqlSession.insert("member.insertMember", member);
        return insertCount;
    }

    public Member idCheck(String id) throws Exception {
        return sqlSession.selectOne("member.idCheck", id);
    }

    public Member emailCheck(String email) throws Exception {
        return sqlSession.selectOne("member.emailCheck", email);
    }
}
