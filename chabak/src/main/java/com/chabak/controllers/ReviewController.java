package com.chabak.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLDecoder;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @RequestMapping(value ={"", "/", "/list"}, method=RequestMethod.GET)
    public String reviewList(){
        //TODO:로직 작성
        System.out.println("review/list");
        return "community/community";//이동 주소는 수정할 것
    }

    @RequestMapping(value ="/writeForm", method=RequestMethod.GET)
    public String writeReviewForm(){
        return "community/community_write";
    }

    @RequestMapping(value ="/write", method=RequestMethod.POST)
    public String writeReview(@RequestParam String title,String sido,String gugun){
        //TODO:로직 작성


        String testString = String.format("title: %s sido: %s gugun: %s",title,sido,gugun);
        System.out.println(testString);
        return "community/community";//이동 주소는 수정할 것
    }

    @RequestMapping(value ="/modify", method=RequestMethod.POST)
    public String modifyReview(){
        //TODO:로직 작성
        return "community/community";//이동 주소는 수정할 것
    }


}
