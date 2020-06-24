package com.chabak.controllers;


import com.chabak.services.FollowServiceImpl;
import com.chabak.services.MemberService;
import com.chabak.vo.Follow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    // mypage화면에서 follower를 클릭했을 때 Controller
    @ResponseBody
    @RequestMapping(value={"", "/", "follower"}, method={RequestMethod.GET,RequestMethod.POST}) //
    public HashMap<String, List<Follow>> followerList(HttpServletRequest request, HttpSession session)throws Exception{ // return된 List<Follow> 데이터 형을 model에 넣
        String id = (String)session.getAttribute("id");

        System.out.println("follower controller---"+id);

        HashMap<String, List<Follow>> map  = new HashMap<>();
        List<Follow> list = followService.selectDataHashMapServiceList(id);

        //todo: 세션 아이디가 갖고 있는 데이터
        map.put("HashMapList", list);

        System.out.println(list);
        //getFollowerId.addObject("HashMapList", list); // addObject는 (key, value) 형태로 데이터 담아 보내는 매서드
        return map;
    }
    // mypage화면에서 follow를 클릭했을 때 // 수정 전 소스코드
//    @ResponseBody
//    @RequestMapping(value="follower", method=RequestMethod.GET) //
//    public String followerList(Model model, Follow follow){ // return된 List<Follow> 데이터 형을 model에 넣음
//        model.addAttribute("followerList", followService.getFollower(follow));
//        System.out.println("MaPageController");
//        return "/mypage/myInformation";
//    }

    // mypage화면에서 following를 클릭했을 때 Controller
    @ResponseBody
    @RequestMapping(value={"", "/", "following"}, method={RequestMethod.GET,RequestMethod.POST}) //
    public HashMap<String, List<Follow>> followerProfile(HttpServletRequest request, HttpSession session)throws Exception{
    //public HashMap<String, HashMap<String, List<Follow>>> followerProfile(HttpServletRequest request, HttpSession session)throws Exception{ // return된 List<Follow> 데이터 형을 model에 넣
        String id = (String)session.getAttribute("id");

        System.out.println("following controller -- "+id);

        HashMap<String, List<Follow>> map  = new HashMap<>();
        List<Follow> list = followService.followingIdAndProfileTest(id);

        System.out.println("------------------------");
        System.out.println("following controller -- " +list);
        //todo: 세션 아이디가 갖고 있는 데이터
        map.put("HashMapList", list);

        System.out.println(list);
        //getFollowerId.addObject("HashMapList", list); // addObject는 (key, value) 형태로 데이터 담아 보내는 매서드
        return map;
    }
}