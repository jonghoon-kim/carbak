package com.chabak.services;

import com.chabak.mapper.AdminMapper;
import com.chabak.repositories.MemberDao;
import com.chabak.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.List;

@Service("memberService")
public class MemberService {
    @Autowired
    MemberDao memberDao;

    public int insert(Member member){
        return memberDao.insertMember(member);
    }

    public Member getMember(String id) {
        return memberDao.getMember(id);
    }

    public List<String> getAllMemberId(String searchText) { return memberDao.getAllMemberId(searchText); }

    /* 아이디 중복 체크 */
    public Member idCheck(String id) throws Exception {
        return memberDao.idCheck(id);
    }

    /* 이메일 중복 확인*/
    public Member emailCheck(String email) throws Exception {
        return memberDao.emailCheck(email);
    }

    /* 로그인 아이디 비밀번호 확인 */
    public boolean loginCheck(Member member){
        // member : 입력값
        // dbMember : db에 있는 값
        Member dbMember = memberDao.getMember(member.getId());

        if(dbMember != null) {
            if(dbMember.getPassword().contentEquals(member.getPassword())) {
              //  System.out.println(dbMember.getSavePath());
//                String saveName = (memberDao.getMember(member.getId())).getSaveName();
//                String savePath = (memberDao.getMember(member.getId())).getSavePath();
                return true;
            }else {
                return false;
            }
        } else {
            return false;
        }
    }

    /* 아이디 찾기 - 이메일 아이디 확인 */
    public boolean idFindFlag(Member member){
        // member : 입력값
        // dbMember : db에 있는 값
        Member dbMember = memberDao.find(member.getEmail());
       // System.out.println(memberDao.id_find(member.getEmail()));

        if(dbMember != null) {
            if(dbMember.getName().contentEquals(member.getName())) {
                return true;
            }else {
                return false;
            }
        } else {
            return false;
        }
    }

    /* 아이디 찾기 */
    public Member find(String email) {
        return memberDao.find(email);
    }

    /* 비밀번호 찾기 - 이메일 아이디 비밀번호 확인 */
    public boolean pwFindFlag(Member member){
        // member : 입력값
        // dbMember : db에 있는 값
        Member dbMember = memberDao.find(member.getEmail());

        HttpServletResponse response;

        if(dbMember != null) {
            if(dbMember.getName().contentEquals(member.getName()) && dbMember.getId().contentEquals(member.getId())) {
                return true;
            } else{
                return false;
            }
        } else {
            return false;
        }
    }
    /* 비밀번호 변경 */
    public int pw_update(Member member) {
        return memberDao.pw_update(member);
    }
    public int memberUpdate(Member member) {
        return memberDao.memberUpdate(member);
    }

    // 비밀번호 확인
    public boolean confirmPassword(Member member){
        // member : 입력값
        // dbMember : db에 있는 값
        Member dbMember = memberDao.find(member.getEmail());
        // System.out.println(memberDao.id_find(member.getEmail()));

        if(dbMember != null) {
            if(dbMember.getName().contentEquals(member.getName())) {
                return true;
            }else {
                return false;
            }
        } else {
            return false;
        }
    }

    // 회원 삭제
    public int memberDelete(String loginId){
        return memberDao.memberDelete(loginId);
    }
}
