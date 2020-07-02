package com.chabak.controllers;

import com.chabak.repositories.ReplyDao;
import com.chabak.repositories.ReviewDao;
import com.chabak.repositories.ReviewLikeDao;
import com.chabak.services.MemberService;
import com.chabak.services.ReviewService;
import com.chabak.vo.Reply;
import com.chabak.vo.Review;
import com.chabak.vo.ReviewAndLike;
import com.chabak.vo.ReviewLike;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    ReviewDao reviewDao;

    @Autowired
    ReplyDao replyDao;

    @Autowired
    MemberService memberService;

    @Autowired
    ReviewLikeDao reviewLikeDao;

    @RequestMapping(value ={"", "/", "/list"}, method=RequestMethod.GET)
    public ModelAndView reviewList(HttpSession session){

        ModelAndView mv = new ModelAndView();

        //파라미터를 저장할 맵 생성+값(정렬타입)
        Map map = new HashMap<String,String>();
        map.put("sortType","regDate");
        map.put("id",memberService.getIdForSessionNotMoveIndex(mv,session));//세션에서 가져온 id map에 넣기
        //리뷰 리스트 select
        List<ReviewAndLike> reviewList = reviewDao.selectReviewList(map);

        System.out.println("/list map:"+map);

        mv.setViewName("community/community");
        mv.addObject("reviewList",reviewList);

        System.out.println("(/review/list)reviewList:"+reviewList);
        return mv;
    } //리뷰 리스트 출력

    @SneakyThrows
    @ResponseBody
    @RequestMapping("/listAjax")
    public String listAjax(HttpServletRequest request,ModelAndView mv,HttpSession session){

        String sortType = request.getParameter("sortType");
        String search_text = request.getParameter("search_text");


        //파라미터를 저장할 맵 생성+값(정렬타입,검색텍스트)
        Map map = new HashMap<String,String>();
        map.put("sortType",sortType);
        map.put("search_text",search_text);
        map.put("id",memberService.getIdForSessionNotMoveIndex(mv,session));//세션에서 가져온 id map에 넣기

        List<ReviewAndLike> reviewList = reviewDao.selectReviewList(map);

        //Jacson 라이브러리로 자바->json 변환
       ObjectMapper mapper = new ObjectMapper();
       String jsonString = mapper.writeValueAsString(reviewList);

        System.out.println("in listAjax(reviewList):"+reviewList);
        System.out.println("jsonString:"+jsonString);

        return jsonString;
    }

    @RequestMapping(value ="/writeForm", method=RequestMethod.GET)
    public ModelAndView writeReviewForm(HttpSession session,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();

        //region checkLogin(세션에서 로그인한 아이디 가져와 설정+비로그인시 로그인 페이지로 이동(return: id or null))
        String id = memberService.getIdForSessionOrMoveIndex(mv,session,response);
        if(id==null)
            return mv;
        //endregion

        mv.setViewName("community/community_write");
        return mv;
    } //리뷰 작성폼 이동

    @RequestMapping(value ="/write", method=RequestMethod.POST)
    public ModelAndView writeReview(@ModelAttribute Review review, HttpSession session, HttpServletResponse response){

        ModelAndView mv = new ModelAndView();

        //region checkLogin(세션에서 로그인한 아이디 가져와 설정+비로그인시 로그인 페이지로 이동(return: id or null))
        String id = memberService.getIdForSessionOrMoveIndex(mv,session,response);
        if(id==null)
            return mv;
        //endregion

        //작성자 설정
        review.setId(id);


        System.out.println("review:"+review);

        reviewService.setTitleImg(review);

        //리뷰 저장
        reviewDao.insertReview(review);



        mv.setViewName("redirect:/review");

        return mv;
    } //리뷰 저장

    //리뷰 수정 페이지로 이동
    @RequestMapping(value ="/modify", method=RequestMethod.GET)
    public ModelAndView modifyForm(@RequestParam int reviewNo,HttpSession session,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();

        //region checkLogin(세션에서 로그인한 아이디 가져와 설정+비로그인시 로그인 페이지로 이동(return: id or null))
        String id = memberService.getIdForSessionOrMoveIndex(mv,session,response);
        if(id==null)
            return mv;
        //endregion

        System.out.println("/modify(GET) reviewNo:"+reviewNo);
        Review review = reviewDao.selectReviewDetail(reviewNo);

        mv.addObject("review",review);
        mv.setViewName("community/community_update");

        return mv;
    }

    @RequestMapping(value ="/modify", method=RequestMethod.POST)
    public ModelAndView modifyReview(@ModelAttribute Review review,HttpSession session,HttpServletResponse response){
        System.out.println("review/modify(POST)");

        ModelAndView mv = new ModelAndView();

        //region checkLogin(세션에서 로그인한 아이디 가져와 설정+비로그인시 로그인 페이지로 이동(return: id or null))
        String id = memberService.getIdForSessionOrMoveIndex(mv,session,response);
        if(id==null)
            return mv;
        //endregion

        //작성자 설정
        review.setId(id);

        reviewService.setTitleImg(review);

        System.out.println("review:"+review);

        reviewDao.updateReview(review);

        mv.setViewName("redirect:/review");

        return mv;
    }

    @RequestMapping(value ="/delete", method=RequestMethod.GET)
    public ModelAndView deleteReview(@RequestParam int reviewNo,HttpSession session,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();

        //region checkLogin(세션에서 로그인한 아이디 가져와 설정+비로그인시 로그인 페이지로 이동(return: id or null))
        String id = memberService.getIdForSessionOrMoveIndex(mv,session,response);
        if(id==null)
            return mv;
        //endregion


        //리뷰 삭제
        reviewDao.deleteReview(reviewNo);

        //해당 리뷰에 달린 리플 전부 삭제(제약조건때문에 불필요)
        //replyDao.deleteReplyWithReviewNo(reviewNo);

        mv.setViewName("redirect:/review");

        return mv;
    }

    @RequestMapping(value ="/detail", method=RequestMethod.GET)
    public ModelAndView detailReview(@RequestParam int reviewNo,HttpSession session,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();

        //region checkLogin(세션에서 로그인한 아이디 가져와 설정+비로그인시 로그인 페이지로 이동(return: id or null))
        String id = memberService.getIdForSessionNotMoveIndex(mv,session);

        //endregion

        System.out.println("reviewNo:"+reviewNo);

        //조회수 1 증가
        reviewDao.updateReadCount(reviewNo);
        //리뷰 선택
        Review review = reviewDao.selectReviewDetail(reviewNo);

        //리뷰의 좋아요

        //로그인시
        if(id !=null){
            System.out.println("reviewDetail(비로그인)");

            //좋아요 bean 값 설정(아이디는 세션 로그인 아이디)
            ReviewLike reviewLike = new ReviewLike();
            reviewLike.setReviewNo(reviewNo);
            reviewLike.setId(id);

            //로그인시 사용자의 좋아요 누름 여부(1/0) check
            int likeYn = reviewLikeDao.checkReviewLike(reviewLike);
            System.out.println("likeYn:"+likeYn);

            //로그인시 사용자의 좋아요 누름 여부(1/0)
            mv.addObject("likeYn",likeYn);
        }


        System.out.println("review:"+review);


        //해당 리뷰에 달린 댓글들

        List<Reply> replyList = replyDao.selectReplyList(reviewNo);

        System.out.println("replyList:"+replyList);



        mv.addObject("review",review);
        mv.addObject("replyList",replyList);


        mv.setViewName("community/community_detail");
        return mv;
    }




}
