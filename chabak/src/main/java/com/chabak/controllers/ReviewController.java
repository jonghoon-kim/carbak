package com.chabak.controllers;

import com.chabak.repositories.ReplyDao;
import com.chabak.repositories.ReviewDao;
import com.chabak.services.MemberService;
import com.chabak.services.ReviewService;
import com.chabak.vo.Reply;
import com.chabak.vo.Review;
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

    @RequestMapping(value ={"", "/", "/list"}, method=RequestMethod.GET)
    public ModelAndView reviewList(){

        //파라미터를 저장할 맵 생성+값(정렬타입)
        Map map = new HashMap<String,String>();
        map.put("sortType","regDate");
        //리뷰 리스트 select
        List<Review> reviewList = reviewDao.selectReviewList(map);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("community/community");
        mv.addObject("reviewList",reviewList);

        System.out.println("(/review/list)reviewList:"+reviewList);
        return mv;
    } //리뷰 리스트 출력

    //검색 버튼 클릭시
@RequestMapping(value ={"", "/", "/list"}, method=RequestMethod.POST)
public ModelAndView searchReviewList(@RequestParam String search_text){

    //파라미터를 저장할 맵 생성+값(정렬타입,검색텍스트)
    Map map = new HashMap<String,String>();
    map.put("sortType","regDate");
    map.put("search_text",search_text);

    //리뷰 리스트 select
    List<Review> reviewList = reviewDao.selectReviewList(map);

    ModelAndView mv = new ModelAndView();
    mv.setViewName("community/community");
    mv.addObject("reviewList",reviewList);

    System.out.println("(/review/list)reviewList:"+reviewList);
    return mv;
} //리뷰 리스트 출력

    @SneakyThrows
    @ResponseBody
    @RequestMapping("/listAjax")
    public String listAjax(HttpServletRequest request,ModelAndView mv){

        String sortType = request.getParameter("sortType");
        String search_text = request.getParameter("search_text");

        //파라미터를 저장할 맵 생성+값(정렬타입,검색텍스트)
        Map map = new HashMap<String,String>();
        map.put("sortType",sortType);
        map.put("search_text",search_text);

        List<Review> reviewList = reviewDao.selectReviewList(map);

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
        //세션에서 로그인한 아이디 가져와 설정(return: id or null)
        String id = memberService.getIdForSessionOrMoveIndex(mv,session,response);
        if(id==null)
            return mv;

        mv.setViewName("community/community_write");
        return mv;
    } //리뷰 작성폼 이동

    @RequestMapping(value ="/write", method=RequestMethod.POST)
    public ModelAndView writeReview(@ModelAttribute Review review, HttpSession session, HttpServletResponse response){

        ModelAndView mv = new ModelAndView();

        //세션에서 로그인한 아이디 가져와 설정(return: id or null)
        String id = memberService.getIdForSessionOrMoveIndex(mv,session,response);
        if(id==null)
            return mv;


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
        //세션에서 로그인한 아이디 가져와 설정(return: id or null)
        String id = memberService.getIdForSessionOrMoveIndex(mv,session,response);
        if(id==null)
            return mv;


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
        //세션에서 로그인한 아이디 가져와 설정(return: id or null)
        String id = memberService.getIdForSessionOrMoveIndex(mv,session,response);
        if(id==null)
            return mv;



        reviewService.setTitleImg(review);

        System.out.println("review:"+review);
        reviewDao.updateReview(review);

        mv.setViewName("redirect:/review");

        return mv;
    }

    @RequestMapping(value ="/delete", method=RequestMethod.GET)
    public ModelAndView deleteReview(@RequestParam int reviewNo,HttpSession session,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();
        //세션에서 로그인한 아이디 가져와 설정(return: id or null)
        String id = memberService.getIdForSessionOrMoveIndex(mv,session,response);
        if(id==null)
            return mv;


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
        //세션에서 로그인한 아이디 가져와 설정(return: id or null)
        String id = memberService.getIdForSessionOrMoveIndex(mv,session,response);
        if(id==null)
            return mv;

        System.out.println("reviewNo:"+reviewNo);

        //조회수 1 증가
        reviewDao.updateReadCount(reviewNo);
        //리뷰 선택
        Review review = reviewDao.selectReviewDetail(reviewNo);
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
