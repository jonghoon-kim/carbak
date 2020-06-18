package com.chabak.controllers;


import com.chabak.services.FollowServiceImpl;
import com.chabak.vo.Follow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping("/mypage")
public class MyPageController {
    @Autowired
    FollowServiceImpl followService;

    @RequestMapping(value="/myInfo", method = RequestMethod.GET)
    public String myPageForm(){
        System.out.println("controller mypage");
        return "/mypage/myInformation";
    }

    @ResponseBody
    @RequestMapping("follower")
    public String followerList(Model model, Follow follow){
        model.addAttribute("followerList", followService.getFollower(follow));
        return "/mypage/myInformation";
    }
}