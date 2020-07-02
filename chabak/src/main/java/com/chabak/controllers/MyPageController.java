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

    // main 화면에서 mypage 클릭 경로 이동
    @RequestMapping(value="/myInfo", method = RequestMethod.GET)
    public String myPageForm(){

        System.out.println("controller mypage");
        return "/mypage/myInformation";
    }

    // mypage화면에서 follower를 클릭 이벤트 : print follower
    @ResponseBody
    @RequestMapping(value={"", "/", "follower"}, method={RequestMethod.GET,RequestMethod.POST}) //
    public HashMap<String, List<Follow>> followerList(HttpServletRequest request, HttpSession session)throws Exception{ // return된 List<Follow> 데이터 형을 model에 넣
        String id = (String)session.getAttribute("id");
        System.out.println("follower controller---"+id);

        HashMap<String, List<Follow>> map  = new HashMap<>();
        List<Follow> list = followService.followerIdAndProfile(id);
        System.out.println(list);

        map.put("HashMapList", list); // key-value 추가

        System.out.println(list);
        return map;
    }


    // mypage화면에서 following를 클릭 이벤트 : print following
    @ResponseBody
    @RequestMapping(value={"", "/", "following"}, method={RequestMethod.GET,RequestMethod.POST}) //
    public HashMap<String, List<Follow>> followingList(HttpServletRequest request, HttpSession session)throws Exception{
        String id = (String)session.getAttribute("id");
        System.out.println("following controller -- "+id);

        HashMap<String, List<Follow>> map  = new HashMap<>();
        List<Follow> list = followService.followingIdAndProfile(id);
        System.out.println("following controller -- " +list);

        map.put("HashMapList", list);

        System.out.println(list);
        //getFollowerId.addObject("HashMapList", list); // addObject는 (key, value) 형태로 데이터 담아 보내는 매서드
        return map;
    }


    //unfollow 버튼 이벤트 : unfollow controller
    @ResponseBody
    @RequestMapping(value={"", "/", "deleteFollowUser"}, method={RequestMethod.GET,RequestMethod.POST}) //
    public HashMap<String, List<Follow>> deleteFollowUser(HttpServletRequest request, HttpSession session, @RequestParam String followUserId)throws Exception{
        String id = (String)session.getAttribute("id");
        String deleteFollowUserId = followUserId;

        System.out.println("following controller -- "+id);
        System.out.println("test controller -- "+followService.deleteFollowUser(id, deleteFollowUserId));

        HashMap<String, List<Follow>> map  = new HashMap<>();
        List<Follow> list = followService.followingIdAndProfile(id);

        System.out.println("following controller -- " +list);
        map.put("HashMapList", list);

        System.out.println(list);
        return map;
    }

    // following 버튼 이벤트 : following controller
    @ResponseBody
    @RequestMapping(value={"", "/", "followAddUser"}, method={RequestMethod.GET,RequestMethod.POST}) //
    public HashMap<String, List<Follow>> followAddUser(HttpServletRequest request, HttpSession session, @RequestParam String followId)throws Exception{
        String id = (String)session.getAttribute("id");
        String followAddUser = followId;

        System.out.println("following controller -- "+id);
        System.out.println("test controller -- "+followService.followAddUser(id, followAddUser));

        HashMap<String, List<Follow>> map  = new HashMap<>();
        List<Follow> list = followService.followingIdAndProfile(id); // todo: followerIdAnd~~??

        System.out.println("following controller -- " +list);
        map.put("HashMapList", list);

        System.out.println(list);
        return map;
    }

    //todo: 방문객이 홈에 들어올 경우 보여지는 화면
    @RequestMapping(value={"", "/", "visitHome"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String visitUserHome(HttpServletRequest request, @RequestParam String visitUserId){
        String UserId = visitUserId;
        System.out.println(UserId);

        System.out.println("controller visitPage");
        return "/mypage/guestVisitHome";
    }

}