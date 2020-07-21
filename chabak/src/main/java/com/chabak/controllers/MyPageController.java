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

    //마이페이지화면에서 follower, following 리스트 출력
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


    //마이페이지 follow list에서 삭제 버튼 이벤트
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

    // 팔로잉, 팔로우 버튼 컨트롤러
    @ResponseBody
    @RequestMapping(value={"", "/", "btnFollowStatus"}, method = {RequestMethod.GET, RequestMethod.POST})
    public HashMap<String, String> btnFollowStatus(HttpSession session, @RequestParam String userId) throws Exception {
        String sessionId = (String)session.getAttribute("id");
        HashMap<String, String> map = new HashMap<>();
        System.out.println("btnFollowStatus controller sessionId : " + sessionId);

        String followerId = followService.btnFollowStatus(sessionId, userId);

        System.out.println(followerId);
        map.put("followerId", followerId);
        map.put("sessionId", sessionId);

        System.out.println("btnFollowStatus controller");

        return map;
    }

    //follow 버튼 클릭 이벤트 컨트롤러(follow -> following)
    @ResponseBody
    @RequestMapping(value={"", "/", "clickFollowBtn"}, method={RequestMethod.GET,RequestMethod.POST}) //
    public HashMap<String, List<Follow>> clickFollowUser(HttpSession session, @RequestParam String followUserId, @RequestParam String option, @RequestParam String pageOwnerId)throws Exception{
        String id = (String)session.getAttribute("id");
        HashMap<String, List<Follow>> map  = new HashMap<>();

        if(option.equals("follower")){
            followService.clickFollowBtn(id, followUserId);
            List<Follow> list = followService.followerIdAndProfile(pageOwnerId);

            map.put("HashMapList", list);

            System.out.println(list);
            return map;

        }
        else if(option.equals("following")){
            followService.clickFollowBtn(id, followUserId);
            List<Follow> list = followService.followingIdAndProfile(pageOwnerId);

            map.put("HashMapList", list);

            System.out.println(list);
            return map;
        }
        return map;
    }

    //following 버튼 클릭 이벤트 컨트롤러(following -> follow)
    @ResponseBody
    @RequestMapping(value={"", "/", "clickFollowingBtn"}, method={RequestMethod.GET,RequestMethod.POST}) //
    public HashMap<String, List<Follow>> clickFollowingBtn(HttpSession session, @RequestParam String followUserId, @RequestParam String option, @RequestParam String pageOwnerId)throws Exception{
        String id = (String)session.getAttribute("id");
        HashMap<String, List<Follow>> map  = new HashMap<>();

        if(option.equals("follower")){
            followService.clickFollowingBtn(id, followUserId);
            List<Follow> list = followService.followerIdAndProfile(pageOwnerId);

            map.put("HashMapList", list);

            System.out.println(list);
            return map;

        }
        else if(option.equals("following")){
            followService.clickFollowingBtn(id, followUserId);
            List<Follow> list = followService.followingIdAndProfile(pageOwnerId);

            map.put("HashMapList", list);

            System.out.println(list);
            return map;
        }
        return map;
    }

}

