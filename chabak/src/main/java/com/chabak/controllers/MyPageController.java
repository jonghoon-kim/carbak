package com.chabak.controllers;


import com.chabak.services.FollowServiceImpl;
import com.chabak.services.MemberService;
import com.chabak.vo.Follow;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
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
    public ModelAndView myPageForm(HttpSession session, HttpServletResponse response) throws Exception{

        String id = (String)session.getAttribute("id");
        ModelAndView mv = new ModelAndView();

        if(id == null) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('로그인 후 사용 가능합니다.')");
            out.println("</script>");
            out.flush();

            mv.setViewName("/member/login");
            return mv;
        }else {
        mv.setViewName("/mypage/myInformation");
        mv.addObject("member", memberService.getMember(id));
        System.out.println(memberService.getMember(id).toString());
        return mv;
        }
    }

    // mypage화면에서 follower를 클릭 이벤트 : print follower
    @ResponseBody
    @RequestMapping(value={"", "/", "followList"}, method={RequestMethod.GET,RequestMethod.POST}) //
    public HashMap<String, List<Follow>> followerList(@RequestParam String id, @RequestParam String option)throws Exception{ // return된 List<Follow> 데이터 형을 model에 넣
        System.out.println("follower controller---"+id);

        HashMap<String, List<Follow>> map  = new HashMap<>();

        if(option.equals("follower")){
            List<Follow> list = followService.followerIdAndProfile(id);
            map.put("HashMapList", list); // key-value 추가

            System.out.println("1 : " + list);
            return map;
        }
        else if(option.equals("following")){
            List<Follow> list = followService.followingIdAndProfile(id);
            map.put("HashMapList", list); // key-value 추가

            System.out.println(list);
            return map;
        }

        return map;
    }


    //unfollow 버튼 이벤트 : unfollow controller
    @ResponseBody
    @RequestMapping(value={"", "/", "deleteFollowUser"}, method={RequestMethod.GET,RequestMethod.POST}) //
    public HashMap<String, List<Follow>> deleteFollowUser(HttpSession session, @RequestParam String followUserId, @RequestParam String option)throws Exception{
        String id = (String)session.getAttribute("id");
        HashMap<String, List<Follow>> map  = new HashMap<>();

        if(option.equals("follower")){
            followService.deleteFollowerUser(id, followUserId);
            List<Follow> list = followService.followerIdAndProfile(id);

            map.put("HashMapList", list);

            System.out.println(list);
            return map;
        }
        else if(option.equals("following")){
            followService.deleteFollowingUser(id, followUserId);
            List<Follow> list = followService.followingIdAndProfile(id);

            map.put("HashMapList", list);

            System.out.println(list);
            return map;
        }
        return map;
    }

    // 방문객이 홈에 들어올 경우 보여지는 화면
    @RequestMapping(value={"", "/", "guestVisit"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView guestVisit(HttpServletRequest request, HttpSession session, @RequestParam("id") String userId){
        String id = (String)session.getAttribute("id");
        String visitorId = userId;

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/mypage/myInformation");
        mv.addObject("visitor", memberService.getMember(visitorId));

        return mv;
    }

    @ResponseBody
    @RequestMapping(value={"", "/", "followStatus"}, method = {RequestMethod.GET, RequestMethod.POST})
    public HashMap<String, List<Follow>> followStatus(HttpSession session, @RequestParam String id, @RequestParam String option){
        String sessionId = (String)session.getAttribute("id");

        HashMap<String, List<Follow>> map = new HashMap<>();

        //todo: ---------------- 리스트에 나온 유저가 팔로잉 안했으면 follower로 팔로잉 중이면 following 버튼이 나오게 한다.
  /*      if(option.equals("follower")){
            followService.followAddUser()
            List<Follow> list = followService.followerIdAndProfile(id);

            map.put("HashMapList", list);

            System.out.println(list);
            return map;
        }else if(option.equals("following")){

            List<Follow> list = followService.followingIdAndProfile(id);

            map.put("HashMapList", list);

            System.out.println(list);
            return map;
        }*/

        return map;
    }

}

