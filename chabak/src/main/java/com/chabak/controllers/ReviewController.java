package com.chabak.controllers;

import com.chabak.repositories.ReviewDao;
import com.chabak.vo.Review;
import com.chabak.vo.TitleImg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLDecoder;
import java.util.List;

@Controller
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    ReviewDao reviewDao;

    @RequestMapping(value ={"", "/", "/list"}, method=RequestMethod.GET)
    public ModelAndView reviewList(){
        List<Review> reviewList = reviewDao.selectReviewList();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("community/community");
        mv.addObject("reviewList",reviewList);
        System.out.println("reviewList:"+reviewList);
        return mv;
    }

    @RequestMapping(value ="/writeForm", method=RequestMethod.GET)
    public String writeReviewForm(){
        return "community/community_write";
    }

    @RequestMapping(value ="/write", method=RequestMethod.POST)
    public ModelAndView writeReview(@ModelAttribute Review review){

        String testString = String.format("title: %s sido: %s gugun: %s content: %s",review.getTitle(),review.getSido(),review.getGugun(),review.getContent());
        System.out.println(testString);
        System.out.println("review:"+review);
        ////////////////
        String titleImage = TitleImg.getInstance().getTitleImageSrc();
        if(titleImage!=null){
            review.setTitleImageSrc(titleImage);
        }
        //////////////////////
        reviewDao.insertReview(review);


        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/review");

        return mv;
    }

    @RequestMapping(value ="/detail", method=RequestMethod.GET)
    public ModelAndView detailReview(@RequestParam int reviewNo){
        System.out.println("reviewNo:"+reviewNo);

        Review review = reviewDao.selectReviewDetail(reviewNo);
        System.out.println("review:"+review);

        ModelAndView mv = new ModelAndView();
        mv.addObject("review",review);
        mv.setViewName("community/community_detail");
        return mv;//이동 주소는 수정할 것
    }

    @RequestMapping(value ="/modify", method=RequestMethod.POST)
    public String modifyReview(){
        //TODO:로직 작성
        return "community/community";//이동 주소는 수정할 것
    }


}
