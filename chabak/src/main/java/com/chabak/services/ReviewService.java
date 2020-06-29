package com.chabak.services;


import com.chabak.vo.Review;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReviewService {
    public boolean setTitleImg(Review review){
        //대표 이미지 저장
        Pattern pattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>"); //img 태그 src 추출 정규표현식
        Matcher matcher = pattern.matcher(review.getContent());

        //리뷰 대표이미지 디폴트 이미지(이미지 등록 안 했을때 보여줄 이미지)
        String titleImage = "/reviewImages/reviewTitleDefault.png";

        while(matcher.find()){
            titleImage = matcher.group(1);
        }

        if(titleImage!=null){
            review.setTitleImageSrc(titleImage);
            return true;
        }
        return false;
    }




}
