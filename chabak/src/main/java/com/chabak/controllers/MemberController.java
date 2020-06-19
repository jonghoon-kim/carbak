package com.chabak.controllers;

import com.chabak.services.MemberService;
import com.chabak.vo.Member;

import com.sun.org.apache.xpath.internal.operations.Mult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Random;


@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired
    ServletContext servletContext;

    @Autowired
    MemberService memberService;

    /* 로그인 */
    @GetMapping(value= {"", "/", "login"})
    public String loginForm() {
        return "member/login";
    }

    @RequestMapping(value ="/loginAction", method=RequestMethod.POST)
    public String loginAction(@RequestParam String id,@RequestParam String password){
        System.out.println("id:"+id);
        System.out.println("password:"+password);
        return "community/community";//이동 주소는 수정할 것
    }

    /* 회원가입 */
    @GetMapping("/signup")
    public String signup() {
        System.out.println("sign up");
        return "/member/signup";
    }
    @PostMapping("/signup")
    public String signupCheck(Member member) throws Exception {
        System.out.println("----------"+member.toString());

        MultipartFile f = member.getFile();

        if(!f.isEmpty()) {
            String path = servletContext.getRealPath("/profileImages");
            String saveName = f.getOriginalFilename() + System.currentTimeMillis() + f.getSize();
            member.setSaveName(saveName);
            member.setSavePath(path);

           // System.out.println("path " + path + " saveName " + saveName);

            File file = new File(path + File.separator + saveName);

            f.transferTo(file);
        }
        memberService.insert(member);
        return "/member/login";
    }

    /* 아이디 중복 체크 */
    @ResponseBody
    @RequestMapping("/idCheck")
    public int idCheck(HttpServletRequest request) throws Exception {
        String id = request.getParameter("id");
        Member idCheck= memberService.idCheck(id);

        int result =0;

        if(idCheck != null) {
            result = 1;
        }

        return result;
    }

    /* 이메일 중복 체크 */
    @ResponseBody
    @RequestMapping("/emailCheck")
    public int emailCheck(HttpServletRequest request) throws Exception {
        String email = request.getParameter("email");
        Member emailCheck = memberService.emailCheck(email);

        int result = 0;

        if(emailCheck != null) {
            result = 1;
        }

        return result;
    }

    /* 이메일 인증 팝업창 */
    @GetMapping("/sendEmail")
    public String sendEmail() {
        return "/member/sendEmail";
    }
    @ResponseBody
    @PostMapping(value= "/sendEmail", produces = "application/json")
    public boolean sendEmail(HttpSession session, @RequestParam String email) {
        int randomCode = new Random().nextInt(10000) + 1000;
        String checkCode = String.valueOf(randomCode);
        session.setAttribute("checkCode", checkCode);

        String subject="'슬기로운 차박생활', 이메일 인증 코드입니다.";
        StringBuilder sb = new StringBuilder();
        sb.append("이메일 인증 코드는 ").append(checkCode).append(" 입니다.");
        return memberService.send(subject, sb.toString(), "zxxexn@gmail.com", email);
    }

    /* 아이디 비밀번호 찾기*/
    @GetMapping("/idpw_find")
    public String idpw_find() {
        return "/member/idpw_find";
    }
}
