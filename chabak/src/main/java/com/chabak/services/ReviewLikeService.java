package com.chabak.services;

import com.chabak.repositories.ReviewDao;
import com.chabak.repositories.ReviewLikeDao;
import com.chabak.vo.ReviewLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewLikeService {
    @Autowired
    ReviewLikeDao reviewLikeDao;

    @Autowired
    ReviewDao reviewDao;

    public int toggleReviewLike(ReviewLike reviewLike){
        int checkReviewLike = reviewLikeDao.checkReviewLike(reviewLike);

        if(checkReviewLike==0){

            reviewLikeDao.insertReviewLike(reviewLike);
            reviewDao.increaseLikeCount(reviewLike.getReviewNo());
            return 1;
        }
        reviewLikeDao.deleteReviewLike(reviewLike);
        reviewDao.decreaseLikeCount(reviewLike.getReviewNo());
        return 0;

    }
}
