package com.chabak.repositories;

import com.chabak.vo.Reply;
import com.chabak.vo.Review;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("reviewDao")
public class ReviewDao {
    @Autowired
    SqlSession sqlSession;

    public int insertReview(Review review){
        int insertedCount = sqlSession.insert("review.insertReview",review);
        return insertedCount;
    }

    public List<Review> selectReviewList(Map map){
        List<Review> reviewList = null;
        reviewList = sqlSession.selectList("review.selectReviewList",map);
        return reviewList;
    }
    public Review selectReviewDetail(int reviewNo){

        Review review = sqlSession.selectOne("review.selectReviewDetail",reviewNo);
        return review;
    }

    public int updateReview(Review review) {
        System.out.println("review in ReviewDao:"+review);
        int updateCount = sqlSession.update("review.updateReview",review);
        return updateCount;
    }

    public int updateReadCount(int reviewNo){
        int updateCount = sqlSession.update("review.updateReadCount",reviewNo);
        return updateCount;
    }

    public int updateLikeCount(int reviewNo){
        int updateCount = sqlSession.update("review.updateLikeCount",reviewNo);
        return updateCount;
    }

    public int updateReplyCount(int reviewNo){
        int updateCount = sqlSession.update("review.updateReplyCount",reviewNo);
        return updateCount;
    }

    public int deleteReview(int reviewNo){
        int deleteCount = sqlSession.delete("review.deleteReview",reviewNo);
        return deleteCount;
    }
}
