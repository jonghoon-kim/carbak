package com.chabak.controllers;

import com.chabak.repositories.ReplyDao;
import com.chabak.repositories.ReviewDao;
import com.chabak.repositories.ReviewLikeDao;
import com.chabak.services.ReviewService;
import com.chabak.util.Utility;
import com.chabak.vo.*;
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
    ReviewLikeDao reviewLikeDao;

    @RequestMapping(value ={"", "/", "/list"}, method=RequestMethod.GET)
    public ModelAndView reviewList(HttpSession session,
                                   @RequestParam (required = false,defaultValue = "regDate") String sortType,
                                   @RequestParam (required = false,defaultValue = "") String searchText,
                                   @RequestParam (required = false,defaultValue = "") String pageOwnerId){

        ModelAndView mv = new ModelAndView();

        //페이징을 위한 리뷰 리스트 갯수를 가져올 파라미터 맵
        Map countReviewMap = new HashMap<String,String>();
        countReviewMap.put("pageOwnerId",pageOwnerId);
        countReviewMap.put("searchText",searchText);

        //리스트 출력을 위한 파라미터를 저장할 맵
        Map map = new HashMap<String,String>();

        int listCnt = reviewDao.maxReviewCount(countReviewMap);


        //리뷰 리스트의 모든 파라미터 설정 후 Pagination 반환
        Pagination pagination = reviewService.setReviewListParameterMap(map,session,sortType,searchText,pageOwnerId,listCnt,1);

        System.out.println("pagination:"+pagination);
        //리뷰 리스트 select
        List<ReviewAndLike> reviewList = reviewDao.selectReviewList(map);


        mv.setViewName("community/community");
        mv.addObject("reviewList",reviewList);
        mv.addObject("pagination",pagination);

        mv.addObject("sortType",sortType);
        mv.addObject("searchText",searchText);
        mv.addObject("pageOwnerId",pageOwnerId);

        System.out.println("/list parameter map:"+map);
        System.out.println("(/review/list)result reviewList:"+reviewList);
        System.out.println("review/list result pagination:"+pagination);

        return mv;
    } //리뷰 리스트 출력

    @SneakyThrows
    @ResponseBody
    @RequestMapping("/listAjax")
    public String listAjax(HttpServletRequest request,HttpSession session,
                           @RequestParam (required = false,defaultValue = "regDate") String sortType,
                           @RequestParam (required = false,defaultValue = "") String searchText,
                           @RequestParam (required = false,defaultValue = "") String pageOwnerId,
                           @RequestParam (required = false,defaultValue = "1") int curPage){

        //TODO:pageOwnerId ajax 함수에서 파라미터로 보내도록 수정해야 함.
        System.out.println("in ajax Controller pageOwnerId:"+pageOwnerId);
        //페이징을 위한 리뷰 리스트 갯수를 가져올 파라미터 맵
        Map countReviewMap = new HashMap<String,String>();
        countReviewMap.put("pageOwnerId",pageOwnerId);
        countReviewMap.put("searchText",searchText);

        //파라미터를 저장할 맵 생성
        Map map = new HashMap<String,String>();

        int listCnt = reviewDao.maxReviewCount(countReviewMap);

        //리뷰 리스트의 모든 파라미터 설정 후 Pagination 반환
        Pagination pagination = reviewService.setReviewListParameterMap(map,session,sortType,searchText,pageOwnerId,listCnt,curPage);

        //리뷰 리스트 select
        List<ReviewAndLike> reviewList = reviewDao.selectReviewList(map);


        //화면으로 보낼 맵
        Map resultMap = new HashMap<String,String>();
        resultMap.put("reviewList",reviewList);
        resultMap.put("pagination",pagination);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(resultMap);

        System.out.println("parameterMap:"+map);
        System.out.println("in listAjax(resultMap):"+resultMap);
        System.out.println("jsonString:"+jsonString);
        return jsonString;
    }



    @RequestMapping(value ="/writeForm", method=RequestMethod.GET)
    public ModelAndView writeReviewForm(HttpSession session,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();

        //region checkLogin(세션에서 로그인한 아이디 가져와 설정+비로그인시 로그인 페이지로 이동(return: id or null))
        String id = Utility.getIdForSessionOrMoveIndex(mv,session,response);

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
        String id = Utility.getIdForSessionOrMoveIndex(mv,session,response);
        //endregion

        System.out.println("/modify(GET) reviewNo:"+reviewNo);
        Review review = null;

        //수정 권한 체크
        try{
            review = reviewDao.selectReviewDetail(reviewNo);
            reviewService.compareSessionAndWriterId(id,review.getId(),response);
        } //해당 리뷰번호에 해당하는 작성자가 없으면
        catch (NullPointerException e){
            Utility.printAlertMessage(response,"잘못된 접근입니다.");
//            Utility.pageBackward(response);
            mv.setViewName("/review");
            return mv;
        }

        mv.addObject("review",review);
        mv.setViewName("community/community_update");

        return mv;
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

        System.out.println("review:"+review);

        reviewDao.updateReview(review);

        mv.setViewName("redirect:/review");

        return mv;
    }

    @RequestMapping(value ="/delete", method=RequestMethod.GET)
    public ModelAndView deleteReview(@RequestParam int reviewNo,HttpSession session,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();

        //region checkLogin(세션에서 로그인한 아이디 가져와 설정+비로그인시 로그인 페이지로 이동(return: id or null))
        String id = Utility.getIdForSessionOrMoveIndex(mv,session,response);

        //endregion

        Review review;

        //삭제 권한 체크
        try{
            review = reviewDao.selectReviewDetail(reviewNo);
            reviewService.compareSessionAndWriterId(id,review.getId(),response);
        } //해당 리뷰번호에 해당하는 작성자가 없으면
        catch (NullPointerException e){
            Utility.printAlertMessage(response,"잘못된 접근입니다.");
            Utility.pageBackward(response);
        }

        //리뷰 삭제
        reviewDao.deleteReview(reviewNo);

        mv.setViewName("redirect:/review");

        return mv;
    }

    @RequestMapping(value ="/detail", method=RequestMethod.GET)
    public ModelAndView detailReview(@RequestParam int reviewNo,HttpSession session,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();

        //region checkLogin(세션에서 로그인한 아이디 가져와 설정+비로그인시 로그인 페이지로 이동(return: id or null))
        String id = Utility.getIdForSessionNotMoveIndex(session);

        //endregion

        System.out.println("reviewNo:"+reviewNo);

        //조회수 1 증가
        reviewDao.updateReadCount(reviewNo);
        //리뷰 선택
        Review review = reviewDao.selectReviewDetail(reviewNo);

        //해당 리뷰가 존재하지 않으면
        if(review == null){
            Utility.printAlertMessage(response,"잘못된 접근입니다.");
            Utility.pageBackward(response);
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