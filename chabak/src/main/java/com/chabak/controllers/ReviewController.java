package com.chabak.controllers;

import com.chabak.services.*;
import com.chabak.util.Utility;
import com.chabak.vo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    ReplyService replyService;

    @Autowired
    ReviewLikeService reviewLikeService;

    @Autowired
    MemberService memberService;

    @Autowired
    ReadCountService readCountService;

    @RequestMapping(value ={"", "/", "/list"}, method=RequestMethod.GET)
    public ModelAndView reviewList(HttpSession session,
                                   @RequestParam (required = false,defaultValue = "regDate") String sortType,
                                   @RequestParam (required = false,defaultValue = "") String searchText,
                                   @RequestParam (required = false,defaultValue = "") String pageOwnerId,
                                   @RequestParam (required = false,defaultValue = "") String isFollowerSearch) {

        ModelAndView mv = new ModelAndView();

        //세션에서 가져온 id
        String id = (String)(session.getAttribute("id"));

        //페이징 설정
        int listCnt = reviewService.maxReviewCount(isFollowerSearch,searchText,pageOwnerId,id);
        Pagination pagination = new Pagination(listCnt,1);
        int startIndex = pagination.getStartIndex();
        int pageSize = pagination.getPageSize();

        //리뷰 리스트 select
        List<Review> reviewList = reviewService.selectReviewList(isFollowerSearch,searchText,pageOwnerId,id,sortType,startIndex,pageSize);


        mv.setViewName("community/community");
        mv.addObject("reviewList",reviewList);
        mv.addObject("pagination",pagination);

        mv.addObject("sortType",sortType);
        mv.addObject("searchText",searchText);
        mv.addObject("pageOwnerId",pageOwnerId);

//        System.out.println("/list parameter map:"+map);
//        System.out.println("(/review/list)result reviewList:"+reviewList);
//        System.out.println("review/list result pagination:"+pagination);

        return mv;
    } //리뷰 리스트 출력

    @SneakyThrows
    @ResponseBody
    @RequestMapping("/listAjax")
    public String listAjax(HttpSession session,
                           @RequestParam (required = false,defaultValue = "regDate") String sortType,
                           @RequestParam (required = false,defaultValue = "") String searchText,
                           @RequestParam (required = false,defaultValue = "") String pageOwnerId,
                           @RequestParam (required = false,defaultValue = "1") int curPage,
                           @RequestParam (required = false,defaultValue = "") String isFollowerSearch){

        System.out.println("in ajax Controller isFollowerSearch:"+isFollowerSearch);

        //세션에서 가져온 id
        String id = (String)(session.getAttribute("id"));

        //페이징 설정
        int listCnt = reviewService.maxReviewCount(isFollowerSearch,searchText,pageOwnerId,id);
        Pagination pagination = new Pagination(listCnt,curPage);
        int startIndex = pagination.getStartIndex();
        int pageSize = pagination.getPageSize();

        //리뷰 리스트 select
        List<Review> reviewList = reviewService.selectReviewList(isFollowerSearch,searchText,pageOwnerId,id,sortType,startIndex,pageSize);

        //화면으로 보낼 맵
        Map resultMap = new HashMap<String,String>();
        resultMap.put("reviewList",reviewList);
        resultMap.put("pagination",pagination);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(resultMap);

        System.out.println("in listAjax(resultMap):"+resultMap);
        return jsonString;
    }

    @RequestMapping(value ="/writeForm", method=RequestMethod.GET)
    public ModelAndView writeReviewForm(HttpSession session,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();

        //region checkLogin(세션에서 로그인한 아이디 가져와 설정+비로그인시 로그인 페이지로 이동(return: id or null))
        Utility.getIdForSessionOrMoveIndex(mv,session,response);

        //endregion

        mv.setViewName("community/community_write");
        return mv;
    } //리뷰 작성폼 이동

    @RequestMapping(value ="/write", method=RequestMethod.POST)
    public ModelAndView writeReview(@ModelAttribute Review review, HttpSession session, HttpServletResponse response){

        ModelAndView mv = new ModelAndView();

        //region checkLogin(세션에서 로그인한 아이디 가져와 설정+비로그인시 로그인 페이지로 이동(return: id or null))
        String id = Utility.getIdForSessionOrMoveIndex(mv,session,response);
        //endregion

        //작성자 설정
        review.setId(id);

        System.out.println("review(Bean modified):"+review);

        reviewService.setTitleImg(review);

        int reviewNo = reviewService.selectReviewNo();

        System.out.println("reviewNo"+reviewNo);

        review.setReviewNo(reviewNo);



        try{
            reviewService.insertReview(review);

            System.out.println("review Controller : ");
        }

        catch (Exception e){
            e.printStackTrace();
            Utility.printAlertMessage("작업 중 에러가 발생했습니다.",null,response);
            return null;
        }
        //리뷰 저장


        mv.setViewName("redirect:/review/list");

        return mv;
    } //리뷰 저장

    //리뷰 수정 페이지로 이동
    @SneakyThrows
    @RequestMapping(value ="/modify", method=RequestMethod.GET)
    public ModelAndView modifyForm(@RequestParam int reviewNo,HttpSession session,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();

        //region checkLogin(세션에서 로그인한 아이디 가져와 설정+비로그인시 로그인 페이지로 이동(return: id or null))
        String id = Utility.getIdForSessionOrMoveIndex(mv,session,response);
        //endregion

        System.out.println("/modify(GET) reviewNo:"+reviewNo);


        //수정 권한 체크
        try{
            Review review = reviewService.selectReviewDetail(reviewNo);
            boolean authorityYn = id.equals(review.getId());
            //권한 없으면
            if(!authorityYn){
                Utility.printAlertMessage("권한이 없습니다.",null,response);
                return null;
            }
            //정상일 때 수정 페이지 이동
            mv.addObject("review",review);
            mv.setViewName("community/community_update");

            return mv;

        } //해당 리뷰번호에 해당하는 작성자가 없으면
        catch (NullPointerException e){
            Utility.printAlertMessage("잘못된 접근입니다.",null,response);
            return null;
        }
    }

    @RequestMapping(value ="/modify", method=RequestMethod.POST)
    public ModelAndView modifyReview(@ModelAttribute Review review,HttpSession session,HttpServletResponse response){
        System.out.println("review/modify(POST)");

        ModelAndView mv = new ModelAndView();

        //region checkLogin(세션에서 로그인한 아이디 가져와 설정+비로그인시 로그인 페이지로 이동(return: id or null))
        String id = Utility.getIdForSessionOrMoveIndex(mv,session,response);

        //endregion

        //작성자 설정
        review.setId(id);

        reviewService.setTitleImg(review);

        try{
            reviewService.updateReview(review);
        }
        catch (Exception e){
            e.printStackTrace();
            Utility.printAlertMessage("작업 중 에러가 발생했습니다.",null,response);
            return null;
        }

        mv.setViewName("redirect:/review/list");
        return mv;
    }

    @RequestMapping(value ="/delete", method=RequestMethod.GET)
    public ModelAndView deleteReview(@RequestParam int reviewNo,HttpSession session,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();

        //region checkLogin(세션에서 로그인한 아이디 가져와 설정+비로그인시 로그인 페이지로 이동(return: id or null))
        String id = Utility.getIdForSessionOrMoveIndex(mv,session,response);
        //endregion

        //삭제 권한 체크
        try{
            Review review = reviewService.selectReviewDetail(reviewNo);
            boolean authorityYn = id.equals(review.getId());
            //권한 없으면
            if(!authorityYn){
                Utility.printAlertMessage("권한이 없습니다.",null,response);
                return null;
            }
            //리뷰 삭제
            reviewService.deleteReview(reviewNo);
            mv.setViewName("redirect:/review/list");
            return mv;

        } //해당 리뷰번호에 해당하는 작성자가 없으면
        catch (NullPointerException e){
            Utility.printAlertMessage("잘못된 접근입니다.",null,response);
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            Utility.printAlertMessage("작업 중 에러가 발생했습니다.",null,response);
            return null;
        }
    }

    @RequestMapping(value ="/detail", method=RequestMethod.GET)
    public ModelAndView detailReview(@RequestParam int reviewNo,HttpSession session,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();

        //session에서 id 가져오기
        String id = (String)(session.getAttribute("id"));

        //리뷰 선택
        Review review = reviewService.selectReviewDetail(reviewNo);

        //해당 리뷰가 존재하지 않으면
        if(review == null){
            Utility.printAlertMessage("잘못된 접근입니다.",null,response);
            return null;
        }
        try{
            //ReadCount 테이블,Review 테이블의 조회수 update
            reviewService.updateReadCount(reviewNo,id);
        }
        catch (Exception e){
            e.printStackTrace();
            Utility.printAlertMessage("작업 중 에러가 발생했습니다.",null,response);
            return null;
        }

        //리뷰의 좋아요
        //로그인시
        if(id !=null){
            System.out.println("reviewDetail");

            //좋아요 bean 값 설정(아이디는 세션 로그인 아이디)
            ReviewLike reviewLike = new ReviewLike();
            reviewLike.setReviewNo(reviewNo);
            reviewLike.setId(id);

            //로그인시 사용자의 좋아요 누름 여부(1/0) check
            int likeYn = reviewLikeService.checkReviewLike(reviewLike);
            System.out.println("likeYn:"+likeYn);

            //로그인시 사용자의 좋아요 누름 여부(1/0)
            mv.addObject("likeYn",likeYn);
            mv.addObject("session", memberService.getMember(id));
        }

        //해당 리뷰에 달린 댓글들

        List<Reply> replyList = replyService.selectReplyList(reviewNo);

        mv.addObject("review",review);
        mv.addObject("replyList",replyList);
        mv.setViewName("community/community_detail");
        return mv;
    }

    // 추천 리뷰 리스트
    @RequestMapping(value ="/recommend", method={RequestMethod.GET,RequestMethod.POST})
    public ModelAndView recommend(HttpServletRequest request, HttpSession session) {
        ModelAndView mv = new ModelAndView();
        Map<String, Object> map = new HashMap<>();
        String similarUsers = request.getParameter("similarUsers");
        String sessionId = (String)session.getAttribute("id");
        System.out.println(similarUsers);

        String similarUsersId[] = similarUsers.split(", ");

        map.put("id1", similarUsersId[0]);
        map.put("id2", similarUsersId[1]);
        map.put("id3", similarUsersId[2]);
        map.put("id4", similarUsersId[3]);
        map.put("id5", similarUsersId[4]);
        map.put("sessionId", sessionId);

        List<Review> recommendReviewList = reviewService.selectRecommendReview(map);

        System.out.println(recommendReviewList);
        mv.addObject("recommendReviewList", recommendReviewList);
        mv.setViewName("community/recommendReview");
        return mv;

    }

}