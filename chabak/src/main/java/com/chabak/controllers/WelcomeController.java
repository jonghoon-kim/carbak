package com.chabak.controllers;


import com.chabak.services.ReviewService;
import com.chabak.vo.Review;
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
        System.out.println("WelcomeController");

//        session.setAttribute("id","fakeId");
//        session.setAttribute("id","id1");
        String id = (String)session.getAttribute("id");
        List<Review> reviewList = null;
        if(id==null){
            reviewList = reviewService.selectReviewTop5(null);
        }
        else{//TODO:로그인 사용자는 별도의 리스트를 출력하므로 나중에 지우기
            reviewList = reviewService.selectReviewTop5(id);
        }
        System.out.println("index top5 list(before):"+reviewList);

        //리스트의 content에서 이미지 태그 지우기
        for(Review review:reviewList){
            String modifiedContent = reviewService.deleteImgTag(review.getContent());
            review.setContent(modifiedContent);
        }

        System.out.println("index top5 list(after):"+reviewList);

        mv.setViewName("/index");
        mv.addObject("reviewList",reviewList);

        return mv;
    }

    @RequestMapping("/header")
    public String header() {
        return "header";
    }

    @RequestMapping("/footer")
    public String footer() {return "footer";}
}
