package com.chabak.util;

import lombok.SneakyThrows;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;


public class Utility {
    //화면에 파라미터로 전달받은 메시지를 alert로 띄운다
    @SneakyThrows
    public static void printAlertMessage(HttpServletResponse response,String message){
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<script>");
        out.println("alert(\""+message+"\") ;");
        out.println("</script>");
        out.flush();
    }

    //이전 페이지로 돌아감
    @SneakyThrows
    public static void pageBackward(HttpServletResponse response){
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<script>");
        out.println("history.back();");
        out.println("</script>");
        out.flush();
    }

    //특정 페이지로 이동함
    @SneakyThrows
    public static void pageMove(HttpServletResponse response,String url){
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<script>");
        out.println("location.href=\""+url+"\"");
        out.println("</script>");
        out.flush();
    }

    @SneakyThrows
    public static String getIdForSessionOrMoveIndex(ModelAndView mv, HttpSession session, HttpServletResponse response){
        //세션에서 아이디 가져오기
        String id = (String)(session.getAttribute("id"));
        if(id==null){
            //아이디 없으면 alert 띄우고 로그인 페이지로 이동
            System.out.println("no session");
            mv.setViewName("member/login");

            //화면에 로그인 할지 여부 confirm 띄우기
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();

            out.println("<script>");
            out.println("var confirmYn = confirm(\"로그인이 필요한 서비스입니다. 로그인 하시겠습니까?\") ;");
            out.println("if(confirmYn)location.href=\"/member/login\"");
            out.println("else history.back();");
            out.println("</script>");
            out.flush();

            return null;
        }
        System.out.println("id from session:"+id);
        return id;
    }

    @SneakyThrows
    public static String getIdForSessionNotMoveIndex(HttpSession session){
        String id = (String)(session.getAttribute("id"));
        System.out.println("in getIdForSessionNotMoveIndex id:"+id);
        if(id==null){
            return null;
        }
        System.out.println("id from session:"+id);
        return id;
    }
}