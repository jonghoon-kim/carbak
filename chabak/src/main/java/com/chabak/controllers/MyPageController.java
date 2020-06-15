package com.chabak.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/mypage")
public class MyPageController {
    @RequestMapping(value="/myInfo", method = RequestMethod.GET)
    public String myPageForm(){
        return "/mypage/myInformation";
    }


}
