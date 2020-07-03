package com.chabak.controllers;

import com.chabak.services.MemberService;
import com.chabak.vo.Member;

import com.sun.deploy.net.protocol.javascript.JavaScriptURLConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.view.RedirectView;

import javax.inject.Inject;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Controller
@RequestMapping("/member")
public class MemberController {
    @Autowired
    ServletContext servletContext;

    @Autowired
    MemberService memberService;

    @Inject
    JavaMailSender mailSender;

    /* 로그인 */
    @GetMapping(value = {"", "/", "login"})
    public String loginForm() {
        return "member/login";
    }

    @RequestMapping("/loginAction")
    public String loginAction(Member member, HttpSession session, HttpServletResponse response) throws Exception {
        boolean loginFlag = memberService.loginCheck(member);

        if (loginFlag) {
            session.setAttribute("id", member.getId());
            session.setAttribute("password", member.getPassword());
            session.setAttribute("name", member.getName());
            session.setAttribute("profile", (memberService.getMember(member.getId())).getSavePath() + (memberService.getMember(member.getId())).getSaveName());
            session.setAttribute("savePath", (memberService.getMember(member.getId())).getSavePath());
            session.setAttribute("saveName", (memberService.getMember(member.getId())).getSaveName());


            System.out.println("id : " + member.getId());
            System.out.println((memberService.getMember(member.getId())).getSavePath() + (memberService.getMember(member.getId())).getSaveName());

            return "redirect:/index";
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('아이디와 비밀번호를 확인해주세요.')");
            out.println("</script>");
            out.flush();

            return "/member/login";
        }
    }

    /* 로그아웃 */
    @RequestMapping("/logout")
    public void logout(HttpSession session, HttpServletResponse response) throws Exception {
        session.invalidate();

        memberService.logout(response);
    }

    /* 회원가입 */
    @GetMapping("/signup")
    public String signup() {
        System.out.println("sign up");
        return "/member/signup";
    }

    @PostMapping("/signup")
    public String signupCheck(Member member) throws Exception {
        System.out.println("----------" + member.toString());

        MultipartFile f = member.getFile();

        if (!f.isEmpty()) {
            String path = servletContext.getRealPath("/");
            System.out.println(path);
            String saveName = System.currentTimeMillis() + f.getSize() + f.getOriginalFilename();
            member.setSaveName(saveName);
            member.setSavePath("/profileImages/");

            File file = new File(path + "resources/img/profileImages" + File.separator + saveName);

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
        Member idCheck = memberService.idCheck(id);

        int result = 0;

        if (idCheck != null) {
            result = 1;
        }

        return result;
    }

    /* 이메일 중복 체크 */
    @ResponseBody
    @RequestMapping(value = "/emailCheck", method = {RequestMethod.POST, RequestMethod.GET})
    public int emailCheck(HttpServletRequest request) throws Exception {
        String email = request.getParameter("email");
        Member emailCheck = memberService.emailCheck(email);

        return (emailCheck != null) ? 1 : 0;
    }

    /* 이메일 인증 팝업창 + 메일 전송 */
    @GetMapping("/sendEmail")
    public ModelAndView sendEmail(Member member, @RequestParam("email") String email, HttpServletResponse response) throws IOException {

        Random r = new Random();
        int dice = r.nextInt(4589362) + 493311; // 이메일로 받는 인증코드 부분 (난수)

        member.setEmail(email);

        String setFrom = "zxxexn@gmail.com";
        String setTo = email;
        String title = "'슬기로운 차박생활' 이메일 인증코드입니다.";
        String content = System.getProperty("line.separator") + // 한줄씩 줄 간격을 두기 위해 작성
                System.getProperty("line.separator") +
                "안녕하세요. '슬기로운 차박생활' 입니다." +
                System.getProperty("line.separator") +
                System.getProperty("line.separator") +
                "저희 홈페이지를 찾아주셔서 감사합니다." +
                System.getProperty("line.separator") +
                System.getProperty("line.separator") +
                "인증 번호는 '" + dice + "' 입니다." +
                System.getProperty("line.separator") +
                System.getProperty("line.separator") +
                "받으신 인증 번호를 홈페이지로 입력해주세요."; // 내용

        System.out.println(setTo);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            messageHelper.setFrom(setFrom);
            messageHelper.setTo(setTo);
            messageHelper.setSubject(title);
            messageHelper.setText(content);

            mailSender.send(message);
        } catch (Exception e) {
            System.out.println(e);
        }

        ModelAndView mv = new ModelAndView(); // ModelAndView로 보낼 페이지를 지정하고, 보낼 값을 지정함.
        mv.setViewName("/member/sendEmail");
        mv.addObject("dice", dice);

        System.out.println("mv : " + mv);

        return mv;
    }

    /* sendEmail에서 오는 인증 코드 검사*/
    // 인증번호가 정확히 작성되었는지 확인
    // 맞으면 회원가입 페이지로 , 틀리면 다시 원래 페이지로
    @RequestMapping(value = "/email_certify", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public HashMap<String, String> email_certify(String code, String dice, HttpServletRequest request) {
        HashMap<String, String> re = new HashMap<String, String>();
        System.out.println("마지막 : dice : " + dice);

        code = request.getParameter("query");
        dice = request.getParameter("dice");
        System.out.println(code);

        int resul = 0;

        if (code.equals(dice)) {

            resul = 1;
            String result = Integer.toString(resul);
            re.put("result", result);
            return re;

        }
        return re;
    }

    /* 아이디 비밀번호 찾기*/
    @GetMapping(value = {"", "/", "idpw_find"})
    public String idpw_find() {
        return "/member/idpw_find";
    }

    /* 아이디 찾기 - 이메일 아이디 확인 */
    @RequestMapping(value = "/idFindFlag", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public HashMap<String, String> idFindFlag(Member member, String email, HttpServletResponse response) throws Exception {
        HashMap<String, String> map = new HashMap<String, String>();

        boolean idFindFlag = memberService.idFindFlag(member);

        if (idFindFlag) {
            String id = memberService.find(email).getId();

            String substr = id.substring(0, 3);
            String star = "";


            for (int i = 0; i < (id.length() - 3); i++) {
                star += "*";
            }

            System.out.println(substr + star);

            map.put("id", substr + star);

            return map;
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('가입된 이메일의 사용자 이름을 정확히 입력해 주세요.')");
            out.println("</script>");
            out.flush();

            return map;
        }
    }

    /* 아이디 보여주는 페이지 */
    @GetMapping("/idFind")
    public String idFind() {

        return "/member/idFind";
    }

    /* 비밀번호 찾기 - 이메일 아이디 이름 확인 */
    @RequestMapping(value = "/pwFindFlag", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public HashMap<String, String> pwFindFlag(Member member, String email, HttpServletResponse response) throws Exception {
        HashMap<String, String> map = new HashMap<String, String>();

        boolean idFindFlag = memberService.idFindFlag(member);

        if (idFindFlag) {
            String id = memberService.find(email).getId();

            String substr = id.substring(0, 3);
            String star = "";


            for (int i = 0; i < (id.length() - 3); i++) {
                star += "*";
            }

            System.out.println(substr + star);

            map.put("id", substr + star);

            return map;
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('가입된 이메일의 사용자 이름을 정확히 입력해 주세요.')");
            out.println("</script>");
            out.flush();

            return map;
        }
    }

    /* 비밀번호 재설정 페이지 */
    @GetMapping("/pwUpdate")
    public String pwUpdate() {
        return "/member/pwUpdate";
    }

    @RequestMapping(value = "/pwUpdateAction", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public HashMap<String, String> pwUpdate(@RequestParam("password") String password) throws Exception {
        HashMap<String, String> map = new HashMap<String, String>();

        memberService.pw_update(password);

        String result = Integer.toString(memberService.pw_update(password));

        map.put("result", result);

        return map;
    }
}