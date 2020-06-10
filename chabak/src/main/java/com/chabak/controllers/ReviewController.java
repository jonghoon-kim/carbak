package com.chabak.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @RequestMapping(value ="/write", method=RequestMethod.POST)
    public String writeReview(){
        //TODO:로직 작성
        return "community/community";//이동 주소는 수정할 것
    }

    @RequestMapping(value ="/modify", method=RequestMethod.POST)
    public String modifyReview(){
        //TODO:로직 작성
        return "community/community";//이동 주소는 수정할 것
    }


}
