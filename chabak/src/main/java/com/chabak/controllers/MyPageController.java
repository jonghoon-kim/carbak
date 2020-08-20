package com.chabak.controllers;

import com.chabak.services.FollowServiceImpl;
import com.chabak.services.MemberService;
import com.chabak.services.ReviewService;
import com.chabak.vo.Follow;
import com.chabak.vo.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
public class MyPageController {
    @Autowired
    FollowServiceImpl followService;

    @Autowired
    MemberService memberService;

    @Autowired
    ReviewService reviewService;

    // main 화면에서 mypage 클릭 경로 이동
    @RequestMapping(value = "/myInfo", method = RequestMethod.GET)
    public ModelAndView myPageForm(HttpSession session, HttpServletResponse response) throws Exception {

        String loginId = (String) session.getAttribute("id");
        ModelAndView mv = new ModelAndView();
        Map map = new HashMap();

        if (loginId == null) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>");
            out.println("alert('로그인 후 사용 가능합니다.')");
            out.println("</script>");
            out.flush();

            mv.setViewName("/member/login");
            return mv;
        } else {
            map.put("id", loginId);
            map.put("pageOwnerId", loginId);
            List<Review> list = reviewService.selectReviewListMyPage(map);

            mv.setViewName("/mypage/myInformation");
            mv.addObject("countFollower",followService.countFollower(loginId));
            mv.addObject("countFollowing", followService.countFollowing(loginId));
            mv.addObject("member", memberService.getMember(loginId));
            mv.addObject("countReview", list.size());
            mv.addObject("reviewList", list);
            return mv;
        }
    }

    //마이페이지화면에서 follower, following 리스트 출력
    @ResponseBody
    @RequestMapping(value = {"", "/", "followList"}, method = {RequestMethod.GET, RequestMethod.POST}) //
    public HashMap<String, List<Follow>> followerList(@RequestParam String clickedId, @RequestParam String option) throws Exception { // return된 List<Follow> 데이터 형을 model에 넣
        HashMap<String, List<Follow>> map = new HashMap<>();

        if (option.equals("follower")) {
            List<Follow> list = followService.followerIdAndProfile(clickedId);
            map.put("HashMapList", list);

            return map;

        } else if (option.equals("following")) {
            List<Follow> list = followService.followingIdAndProfile(clickedId);
            map.put("HashMapList", list);
            System.out.println(map);

            return map;
        }
        return map;
    }


    //마이페이지 follow list에서 삭제 버튼 이벤트
    @ResponseBody
    @RequestMapping(value = {"", "/", "deleteFollowUser"}, method = {RequestMethod.GET, RequestMethod.POST}) //
    public HashMap<String, List<Follow>> deleteFollowUser(HttpSession session, @RequestParam String clickedId, @RequestParam String option) throws Exception {
        String loginId = (String) session.getAttribute("id");
        HashMap<String, List<Follow>> map = new HashMap<>();

        if (option.equals("follower")) {
            followService.deleteFollowerUser(loginId, clickedId);
            List<Follow> list = followService.followerIdAndProfile(loginId);

            map.put("HashMapList", list);
            return map;
        } else if (option.equals("following")) {
            followService.deleteFollowingUser(loginId, clickedId);
            List<Follow> list = followService.followingIdAndProfile(loginId);


            map.put("HashMapList", list);

            return map;
        }
        return map;
    }

    // 방문객이 홈에 들어올 경우 보여지는 화면
    @RequestMapping(value = {"", "/", "guestVisit"}, method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView guestVisit(@RequestParam("id") String clickedId) throws Exception{
        ModelAndView mv = new ModelAndView();
        Map map = new HashMap();

        map.put("id", clickedId);
        map.put("pageOwnerId", clickedId);
        List<Review> list = reviewService.selectReviewListMyPage(map);

        mv.setViewName("/mypage/myInformation");
        mv.addObject("countFollower", followService.countFollower(clickedId));
        mv.addObject("countFollowing", followService.countFollowing(clickedId));
        mv.addObject("countReview", list.size());
        mv.addObject("pageOwner", memberService.getMember(clickedId));
        mv.addObject("reviewList", list);

        return mv;
    }

    // 팔로잉, 팔로우 버튼 컨트롤러
    @ResponseBody
    @RequestMapping(value = {"", "/", "btnFollowStatus"}, method = {RequestMethod.GET, RequestMethod.POST})
    public HashMap<String, String> btnFollowStatus(HttpSession session, @RequestParam String clickedId) throws Exception {
        String loginId = (String) session.getAttribute("id");
        HashMap<String, String> map = new HashMap<>();

        String followerId = followService.btnFollowStatus(loginId, clickedId);

        map.put("followerId", followerId);
        map.put("sessionId", loginId);

        return map;
    }

    //follow 버튼 클릭 이벤트 컨트롤러(follow -> following)
    @ResponseBody
    @RequestMapping(value = {"", "/", "clickFollowBtn"}, method = {RequestMethod.GET, RequestMethod.POST}) //
    public HashMap<String, List<Follow>> clickFollowUser(HttpSession session, @RequestParam String clickedId, @RequestParam String option, @RequestParam String pageOwnerId) throws Exception {
        String loginId = (String) session.getAttribute("id");
        HashMap<String, List<Follow>> map = new HashMap<>();

        if (option.equals("follower")) {
            followService.clickFollowBtn(loginId, clickedId);
            List<Follow> list = followService.followerIdAndProfile(pageOwnerId);
            int count = followService.countFollower(loginId);

            map.put("HashMapList", list);

            return map;

        } else if (option.equals("following")) {
            followService.clickFollowBtn(loginId, clickedId);
            List<Follow> list = followService.followingIdAndProfile(pageOwnerId);

            map.put("HashMapList", list);

            return map;
        }
        return map;
    }

    //following 버튼 클릭 이벤트 컨트롤러(following -> follow)
    @ResponseBody
    @RequestMapping(value = {"", "/", "clickFollowingBtn"}, method = {RequestMethod.GET, RequestMethod.POST}) //
    public HashMap<String, List<Follow>> clickFollowingBtn(HttpSession session, @RequestParam String clickedId, @RequestParam String option, @RequestParam String pageOwnerId) throws Exception {
        String loginId = (String) session.getAttribute("id");
        HashMap<String, List<Follow>> map = new HashMap<>();

        if (option.equals("follower")) {
            followService.clickFollowingBtn(loginId, clickedId);
            List<Follow> list = followService.followerIdAndProfile(pageOwnerId);

            map.put("HashMapList", list);

            return map;

        } else if (option.equals("following")) {
            followService.clickFollowingBtn(loginId, clickedId);
            List<Follow> list = followService.followingIdAndProfile(pageOwnerId);

            map.put("HashMapList", list);

            return map;
        }
        return map;
    }

    //following 버튼 클릭 이벤트 컨트롤러(following -> follow)
    @ResponseBody
    @RequestMapping(value = {"", "/", "clickProfileFollowing"}, method = {RequestMethod.GET, RequestMethod.POST}) //
    public void clickProfileFollowing(HttpSession session, @RequestParam String clickedId) throws Exception {
        String loginId = (String) session.getAttribute("id");

        followService.clickFollowingBtn(loginId, clickedId);
    }

    //follow 버튼 클릭 이벤트 컨트롤러(follow -> following)
    @ResponseBody
    @RequestMapping(value = {"", "/", "clickProfileFollow"}, method = {RequestMethod.GET, RequestMethod.POST}) //
    public void clickProfileFollow(HttpSession session, @RequestParam String clickedId) throws Exception {
        String loginId = (String) session.getAttribute("id");

        followService.clickFollowBtn(loginId, clickedId);
    }

    //follow 버튼 클릭 이벤트 컨트롤러(follow -> following)
    @ResponseBody
    @RequestMapping(value = {"", "/", "printReviewList"}, method = {RequestMethod.GET, RequestMethod.POST}) //
    public HashMap<String, List<Review>> printReviewList(HttpSession session, @RequestParam String pageOwnerId) throws Exception {
        String loginId = (String) session.getAttribute("id");
        Map map = new HashMap();
        HashMap<String, List<Review>> resultMap = new HashMap<>();

        if (loginId == pageOwnerId) {
            map.put("id", loginId);
            map.put("pageOwnerId", loginId);
            List<Review> list =  reviewService.selectReviewListMyPage(map);

            resultMap.put("reviewList", list);
            return resultMap;
        }
        else{
            System.out.println(pageOwnerId);
            map.put("id", pageOwnerId);
            map.put("pageOwnerId", pageOwnerId);
            List<Review> list =  reviewService.selectReviewListMyPage(map);

            resultMap.put("reviewList", list);
            return resultMap;
        }
    }
}

