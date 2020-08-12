package com.chabak.repositories;

import com.chabak.vo.Member;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import java.util.HashMap;
import java.util.Map;

import java.util.List;

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

    /* 관리자 회원 정보 조회 */
    public Member adminMembers() {
        return (Member) sqlSession.selectList("member.adminMembers");
    }

    /* 메시지 보낼 아이디 검색시(ajax) 출력할 리스트*/
    public List<String> getAllMemberId(String searchText){ return  sqlSession.selectList("member.getAllMemberId",searchText);  }

    /* 아이디 찾기 */
    public Member find(String email) {

        return sqlSession.selectOne("member.find", email);
    }
    /* 비밀번호 변경 */
    public int pw_update(Member member) {
        return sqlSession.update("member.pw_update", member);
    }

    /* 회원 정보 수정 */
    public int memberUpdate(Member member) {
        System.out.println("dao :"+member);
        return sqlSession.update("member.memberUpdate", member);
    }

    /* 회원 삭제 */
    public int memberDelete(String loginId) {
        return sqlSession.delete("member.memberDelete", loginId);
    }
}
