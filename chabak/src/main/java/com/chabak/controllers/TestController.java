package com.chabak.controllers;

import com.chabak.services.*;
import com.chabak.util.Utility;
import com.chabak.vo.Pagination;
import com.chabak.vo.Reply;
import com.chabak.vo.Review;
import com.chabak.vo.ReviewLike;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
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
@RequestMapping("/test")
public class TestController {

@Autowired
GenerateDataService generateDataService;

@SneakyThrows
    @RequestMapping(value ={"", "/", "/list"}, method=RequestMethod.GET)
    public ModelAndView test(@RequestParam(required = false,defaultValue = "-1") int flag,
                             @RequestParam(required = false,defaultValue = "0") int numMember,
                             @RequestParam(required = false,defaultValue = "0") int numReview,
                             @RequestParam(required = false,defaultValue = "0") double ratioReviewLike,
                             HttpServletResponse response) {

        ModelAndView mv = new ModelAndView();
        int complete = 0;
        System.out.println(String.format("flag :%d numMember:%d numReview:%d ratioReviewLike %f",flag,numMember,numReview,ratioReviewLike));
        if(flag!=-1){
            switch (flag){
                case 1:
                    complete = generateDataService.generateMemberData(numMember);
                    if(complete == 1){
                        response.setContentType("text/html; charset=UTF-8");
                        PrintWriter out = response.getWriter();
                        out.println("<script>");
                        out.println("alert(\"Member 생성 완료\") ;");
                        out.println("</script>");
                        out.flush();
                    }

                    break;
                case 2:
                    complete = generateDataService.generateReviewData(numReview);
                    if(complete == 1){
                        response.setContentType("text/html; charset=UTF-8");
                        PrintWriter out = response.getWriter();
                        out.println("<script>");
                        out.println("alert(\"Review 생성 완료\") ;");
                        out.println("</script>");
                        out.flush();
                    }
                    break;
                case 3:
                    complete = generateDataService.generateReadCountData(0,30);
                    if(complete == 1){
                        response.setContentType("text/html; charset=UTF-8");
                        PrintWriter out = response.getWriter();
                        out.println("<script>");
                        out.println("alert(\"ReadCount 생성 완료\") ;");
                        out.println("</script>");
                        out.flush();
                    }
                    break;
                case 4:
                    complete = generateDataService.generateReviewLikeData(ratioReviewLike);
                    if(complete == 1){
                        response.setContentType("text/html; charset=UTF-8");
                        PrintWriter out = response.getWriter();
                        out.println("<script>");
                        out.println("alert(\"ReviewLike 생성 완료\") ;");
                        out.println("</script>");
                        out.flush();
                    }
                    break;



            }          
        }
        else
            System.out.println("no parameter");


        mv.setViewName("/test");
        return mv;
    }


}