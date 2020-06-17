package com.chabak.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mypage")
public class MyPageController {
    @RequestMapping(value="/myInfo", method = RequestMethod.GET)
    public String myPageForm(){
        System.out.println("hi");
        return "/mypage/myInformation";
    }

    @ResponseBody
    @RequestMapping("follower")
    public String follower(){
        System.out.println("bye");
        return "success";
    }
}