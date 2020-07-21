package com.chabak.services;


import com.chabak.repositories.ReviewDao;
import com.chabak.util.Utility;
import com.chabak.vo.Pagination;
import com.chabak.vo.Review;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReviewService {

    @Autowired
    ReviewDao reviewDao;

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

    //index 페이지 top5 리뷰 content에서 이미지는 출력되지 않도록 제외
    public String deleteImgTag(String originalString){

        String regex = "<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>"; //img 태그 src 추출 정규표현식
        String resultString = originalString.replaceAll(regex,"");
        System.out.println("deleteImgTag resultString:"+resultString);
        return resultString;
    }

    //리뷰 리스트 select 관련 파라미터 설정 후 리턴
    public Pagination setReviewListParameterMap(Map map,HttpSession session,String sortType,String searchText,String pageOwnerId,int listCnt,int curPage) {
        map.put("sortType",sortType);
        map.put("searchText",searchText);
        map.put("pageOwnerId",pageOwnerId);
        map.put("id",Utility.getIdForSessionNotMoveIndex(session));//세션에서 가져온 id map에 넣기

        //페이징 관련 파라미터
        //reviewList 행의 수
        System.out.println("setReviewListParameterMap(Service) start listCnt:"+listCnt);
        Pagination pagination = new Pagination(listCnt,curPage);
        System.out.println("setReviewListParameterMap(Service) start pagination:"+pagination);
        int startIndex = pagination.getStartIndex();
        int pageSize = pagination.getPageSize();
        map.put("startIndex",startIndex);
        map.put("pageSize",pageSize);

        return pagination;
    }

    public int insertReview(Review review){
        int insertedCount = reviewDao.insertReview(review);
        return insertedCount;
    }

    public int maxReviewCount(Map map){
        int maxCount = reviewDao.maxReviewCount(map);
        return maxCount;
    }

    public List<Review> selectReviewTop5(String id){
        List<Review> reviewList = null;
        reviewList = reviewDao.selectReviewTop5(id);
        return reviewList;
    }
    public List<Review> selectReviewList(Map map){
        List<Review> reviewList = null;
        reviewList = reviewDao.selectReviewList(map);
        return reviewList;
    }

    public List<Review> selectReviewListMyPage(Map map){
        List<Review> reviewList = null;
        reviewList = reviewDao.selectReviewListMyPage(map);
        return reviewList;
    }

    public Review selectReviewDetail(int reviewNo){

        Review review = reviewDao.selectReviewDetail(reviewNo);
        return review;
    }

    public int updateReview(Review review) {

        int updateCount = reviewDao.updateReview(review);
        return updateCount;
    }

    public int updateReadCount(int reviewNo){
        int updateCount = reviewDao.updateReadCount(reviewNo);
        return updateCount;
    }

    public int increaseLikeCount(int reviewNo){
        int updateLikeCount = reviewDao.increaseLikeCount(reviewNo);
        return updateLikeCount;
    }

    public int decreaseLikeCount(int reviewNo){
        int updateLikeCount = reviewDao.decreaseLikeCount(reviewNo);
        return updateLikeCount;
    }

    public int deleteReview(int reviewNo){
        int deleteCount = reviewDao.deleteReview(reviewNo);
        return deleteCount;
    }

    public int increaseReplyCount(int reviewNo){
        int updateLikeCount = reviewDao.increaseReplyCount(reviewNo);
        return updateLikeCount;
    }

    public int decreaseReplyCount(int reviewNo){
        int updateLikeCount = reviewDao.decreaseReplyCount(reviewNo);
        return updateLikeCount;
    }
}