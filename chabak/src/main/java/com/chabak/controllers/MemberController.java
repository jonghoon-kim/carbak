package com.chabak.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/member")
public class MemberController {

    @RequestMapping(value= {"", "/", "login"}, method= RequestMethod.GET)
    public String loginForm() {
        return "member/login";
    }

    @RequestMapping(value ="/loginAction", method=RequestMethod.POST)
    public String loginAction(@RequestParam String id,@RequestParam String password){
        System.out.println("id:"+id);
        System.out.println("password:"+password);
        return "community/community";//이동 주소는 수정할 것
    }
}
