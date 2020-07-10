package com.chabak.controllers;


import com.chabak.services.ReviewService;
import com.chabak.vo.ReviewAndLike;
import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class WelcomeController {

    @Autowired
    ReviewService reviewService;

    @SneakyThrows
    @RequestMapping(value= {"", "/", "index"})
    public ModelAndView index(HttpSession session){
        ModelAndView mv = new ModelAndView();
        System.out.println("Controller");

//        session.setAttribute("id","fakeId");
        session.setAttribute("id","id1");
        String id = (String)session.getAttribute("id");
        List<ReviewAndLike> reviewList = null;
        if(id==null){
            reviewList = reviewService.selectReviewTop5(null);
        }
        System.out.println("index top5 list(before):"+reviewList);
        //TODO:나중에 지우기
        reviewList = reviewService.selectReviewTop5(null);
        for(ReviewAndLike review:reviewList){
            String modifiedContent = reviewService.deleteImgTag(review.getContent());
            review.setContent(modifiedContent);
        }

        System.out.println("index top5 list(after):"+reviewList);

        mv.setViewName("/index");
        mv.addObject("reviewList",reviewList);

        //조회수 1000 단위를 k로 바꿔서 화면으로 보내기(script로 해보려했으나 gg)
        List<String> readCountList = null;
        for(ReviewAndLike reviewAndLike:reviewList){
            int readCount = reviewAndLike.getReadCount();
            if(readCount >= 1000){
                readCountList.add((readCount/1000.0)+"K");
            }
            else{
                readCountList.add(""+readCount+"");
            }
        }
        mv.addObject("readCountList",readCountList);

        return mv;
    }

    @RequestMapping("/header")
    public String header() {
        return "header";
    }

    @RequestMapping("/footer")
    public String footer() {return "footer";}
}
