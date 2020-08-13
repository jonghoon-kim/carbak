package com.chabak.util;

import lombok.SneakyThrows;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;


public class Utility {

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

    //창 닫기
    @SneakyThrows
    public static void closeWindow(HttpServletResponse response){
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<script>");
        out.println("window.close();");
        out.println("</script>");
        out.flush();
    }

    //부모창 새로고침
    @SneakyThrows
    public static void parentReload(HttpServletResponse response){
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<script>");
        out.println("window.opener.location.reload();");
        out.println("</script>");
        out.flush();
    }

    /**기능:비로그인 상태일때 로그인 페이지로 이동<br>리턴 : 로그인 아이디(session)**/
    @SneakyThrows
    public static String getIdForSessionOrMoveIndex(ModelAndView mv, HttpSession session, HttpServletResponse response){
        //세션에서 아이디 가져오기
        String id = (String)(session.getAttribute("id"));
        if(id==null){
            //아이디 없으면 alert 띄우고 로그인 페이지로 이동
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
        return id;
    }

    /**파라미터로 받은 에러 메시지 띄우고 이전 페이지로 이동<br>주의:이전 페이지로 이동하기 때문에 ModelAndView 객체를 null로 리턴해야 함
     * <br>파라미터: String alert 띄울 메시지,String 이동할 페이지 주소(null이면 이전페이지),HttpServletResponse 객체<br>*현재 url이동 작동 오류 중**/
    @SneakyThrows
    public static void printAlertMessage(String alertMessage,String url,HttpServletResponse response) {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<script>");
        out.println("alert(\""+alertMessage+"\") ;");
        if(url==null){
            out.println("history.back();");
        }
        else{
            System.out.println("url not null-url:"+url);
            out.println("location.href='"+url+"'");
        }
        out.println("</script>");
        out.flush();
    }
}
