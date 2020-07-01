package com.chabak.repositories;

import com.chabak.vo.Member;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("memberDao")
public class MemberDao {
    @Autowired
    SqlSession sqlSession;

    /* 회원 가입 */
    public int insertMember(Member member) {
        int insertCount = sqlSession.insert("member.insertMember", member);
        return insertCount;
    }

    /* 아이디 중복 체크 */
    public Member idCheck(String id) throws Exception {
        return sqlSession.selectOne("member.idCheck", id);
    }

    /* 이메일 중복 체크 */
    public Member emailCheck(String email) throws Exception {
        return sqlSession.selectOne("member.emailCheck", email);
    }

    /* 로그인한 회원 뽑아오기 - 멤버 1 */
    public Member getMember(String id) {
        return sqlSession.selectOne("member.getMember", id);
    }

    /* 아이디 찾기 */
    public Member find(String email) {

        return sqlSession.selectOne("member.find", email);
    }
    /* 비밀번호 변경 */
    public int pw_update(String password) {
        return sqlSession.update("member.pw_update", password);
    }
}
