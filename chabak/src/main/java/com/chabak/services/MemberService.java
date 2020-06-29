package com.chabak.services;

import com.chabak.repositories.MemberDao;
import com.chabak.vo.Member;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Service
public class MemberService {
    @Autowired
    MemberDao memberDao;


    public int insert(String id){
        return memberDao.insertMember(id);

    }

    @SneakyThrows
    public String getIdForSessionOrMoveIndex(ModelAndView mv, HttpSession session,HttpServletResponse response){
        //region session check
        String id = (String)(session.getAttribute("id"));
        if(id==null){
            System.out.println("no session");
            mv.setViewName("member/login");

            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('로그인이 필요한 서비스입니다.')");
            out.println("</script>");
            out.flush();

            return null;
        }
        System.out.println("id from session:"+id);
        return id;
    }
}
