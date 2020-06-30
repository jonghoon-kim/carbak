package com.chabak.services;

import com.chabak.repositories.ReviewLikeDao;
import com.chabak.vo.ReviewLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewLikeService {
    @Autowired
    ReviewLikeDao reviewLikeDao;

    public int toggleReviewLike(ReviewLike reviewLike){
        ReviewLike resultReviewLike = reviewLikeDao.selectReviewLike(reviewLike);

        if(resultReviewLike==null){

            reviewLikeDao.insertReviewLike(reviewLike);
            return 1;
        }
        reviewLikeDao.deleteReviewLike(reviewLike);
        return 0;

    }
}
