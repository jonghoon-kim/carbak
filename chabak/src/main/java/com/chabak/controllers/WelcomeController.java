package com.chabak.controllers;


import com.chabak.services.ReviewService;
import com.chabak.vo.Member;
import com.chabak.vo.Review;
import lombok.SneakyThrows;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WelcomeController {

    @Autowired
    ReviewService reviewService;

    @SneakyThrows
    @RequestMapping(value= {"", "/", "index"})
    public ModelAndView index(HttpSession session){
        ModelAndView mv = new ModelAndView();

        String id = (String)session.getAttribute("id");
        List<Review> reviewList = null;
        if(id==null){
            reviewList = reviewService.selectReviewTop5(null);

            //리스트의 content에서 이미지 태그 지우기
            for(Review review:reviewList){
                String modifiedContent = reviewService.deleteImgTag(review.getContent());
                review.setContent(modifiedContent);
            }
            mv.setViewName("/index");
            mv.addObject("reviewList",reviewList);

            return mv;

        }
        else{//TODO:로그인 사용자는 별도의 리스트를 출력하므로 나중에 지우기
            try{
                ModelAndView mv1 = new ModelAndView();
                String requestURL = "http://localhost:5000/find_similar_users";
                String postBody =""+ "{" + "\"id\":"+ id+"}"+"";
                String sessionId = id;

                System.out.println("postBody : "+postBody);

                RequestBody formBody = new FormBody.Builder()
                        .add("id", sessionId)
                        .build();

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(requestURL)
                        .post(formBody)
                        .build();

                Map<String, Object> map = new HashMap<>();


                //비동기 처리 (enqueue 사용)
                client.newCall(request).enqueue(new Callback() {
                    //비동기 처리를 위해 Callback 구현
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println("error + Connect Server Error is " + e.toString());
                    }

                    @SneakyThrows
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        List<Review> reviewList = null;

                        String similarUsers = response.body().string();
                        //System.out.println(similarUsers);

                        String similarUsersId[] = similarUsers.split(", ");

                        map.put("id1", similarUsersId[0]);
                        map.put("id2", similarUsersId[1]);
                        map.put("id3", similarUsersId[2]);
                        map.put("id4", similarUsersId[3]);
                        map.put("id5", similarUsersId[4]);


//                        for(int i=0; i < similarUsersId.length; i++) {
//                            map.put(""+i+"", similarUsersId[i]);
//                            System.out.println(i+ similarUsersId[i]);
//                        }

                        map.put("sessionId", sessionId);

                        System.out.println("mapValues : "+ map.values());

                        reviewList = reviewService.selectSimilarUsersReview(map);

                        //리스트의 content에서 이미지 태그 지우기
                        for(Review review:reviewList){
                            String modifiedContent = reviewService.deleteImgTag(review.getContent());
                            review.setContent(modifiedContent);
                        }

                        mv1.setViewName("/index");
                        mv1.addObject("reviewList",reviewList);

                        System.out.println(reviewList);

                    }


                });
Thread.sleep(2000);
                return mv1;

            } catch (Exception e) {
                System.err.println(e.toString());
            }

        }

        System.out.println(mv);
        return mv;

    }

    @RequestMapping("/header")
    public String header() {
        return "header";
    }

    @RequestMapping("/footer")
    public String footer() {return "footer";}
}
