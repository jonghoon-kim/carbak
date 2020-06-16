package com.chabak.repositories;

import com.chabak.vo.Review;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("reviewDao")
public class ReviewDao {
    @Autowired
    SqlSession sqlSession;

    public int insertReview(Review review){
        int insertedCount = sqlSession.insert("review.insertReview",review);
        return insertedCount;
    }

    public List<Review> selectReviewList(){
        List<Review> reviewList = null;
        reviewList = sqlSession.selectList("review.selectReviewList",null);
        return reviewList;
    }
    public Review selectReviewDetail(int reviewNo){

        Review review = sqlSession.selectOne("review.selectReviewDetail",reviewNo);
        return review;
    }
}
