package com.chabak.controllers;


import com.chabak.services.FollowServiceImpl;
import com.chabak.services.MemberService;
import com.chabak.vo.Follow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/mypage")
public class MyPageController {
    @Autowired
    FollowServiceImpl followService;

    @Autowired
    MemberService memberService;

    @RequestMapping(value="/myInfo", method = RequestMethod.GET)
    public String myPageForm(){
        System.out.println("controller mypage");
        return "/mypage/myInformation";
    }

    // mypage화면에서 follow를 클릭했을 때
    @ResponseBody
    @RequestMapping(value={"", "/", "follower"}, method=RequestMethod.GET) //
    public String followerList(ModelMap model) throws Exception{ // return된 List<Follow> 데이터 형을 model에 넣음
        ModelAndView test = new ModelAndView();
        System.out.println("controller info");

        //HashMap<String, Object> hashMap = new HashMap<>();
/*        List<Follow> list = new ArrayList<>();*/

        List<Follow> list = followService.selectDataHashMapServiceList();
        //hashMap.put("HashMapList", list);

        System.out.println(list);
        test.addObject("HashMapList", list);
        return "/mypage/myInformation";
    }

    // mypage화면에서 follow를 클릭했을 때 // 수정 전 소스코드
//    @ResponseBody
//    @RequestMapping(value="follower", method=RequestMethod.GET) //
//    public String followerList(Model model, Follow follow){ // return된 List<Follow> 데이터 형을 model에 넣음
//        model.addAttribute("followerList", followService.getFollower(follow));
//        System.out.println("MaPageController");
//        return "/mypage/myInformation";
//    }
}