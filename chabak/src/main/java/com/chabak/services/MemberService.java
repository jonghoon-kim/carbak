package com.chabak.services;

import com.chabak.repositories.MemberDao;
import com.chabak.vo.Member;
//import com.sun.org.apache.bcel.internal.ExceptionConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Service
public class MemberService {
    @Autowired
    MemberDao memberDao;

    public int insert(Member member){
        return memberDao.insertMember(member);
    }

    public Member getMember(String id) {
        return memberDao.getMember(id);
    }

    /* 아이디 중복 체크 */
    public Member idCheck(String id) throws Exception {
        return memberDao.idCheck(id);
    }

    /* 로그아웃 */
    public void logout(HttpServletResponse response) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("location.href=document.referrer;");
        out.println("</script>");
        out.close();
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
                System.out.println(dbMember.getSavePath());
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
}
