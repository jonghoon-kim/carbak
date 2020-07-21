package com.chabak.services;

import com.chabak.repositories.ReviewDao;
import com.chabak.repositories.ReviewLikeDao;
import com.chabak.vo.ReviewLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewLikeService {
    @Autowired
    ReviewLikeDao reviewLikeDao;

    @Autowired
    ReviewDao reviewDao;

    @Transactional
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

    public int checkReviewLike(ReviewLike reviewLike) {
        int likeCount = reviewLikeDao.checkReviewLike(reviewLike);
        return likeCount;
    }

    public int insertReviewLike(ReviewLike reviewLike) {
        int insertCount = reviewLikeDao.insertReviewLike(reviewLike);
        return insertCount;
    }

    public int deleteReviewLike(ReviewLike reviewLike) {
        int deleteCount = reviewLikeDao.deleteReviewLike(reviewLike);
        return deleteCount;
    }
}
