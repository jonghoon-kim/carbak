package com.chabak.services;


import com.chabak.repositories.ReadCountDao;
import com.chabak.repositories.ReviewDao;
import com.chabak.util.Utility;
import com.chabak.vo.Pagination;
import com.chabak.vo.ReadCount;
import com.chabak.vo.Review;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReviewService {

    @Autowired
    ReviewDao reviewDao;
    @Autowired
    ReadCountDao readCountDao;

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
//        System.out.println("deleteImgTag resultString:"+resultString);
        return resultString;
    }

    public int selectReviewNo() {
        int reviewNo = reviewDao.selectReviewNo();
        return reviewNo;
    }

    public int insertReview(Review review){
        int insertedCount = reviewDao.insertReview(review);
        return insertedCount;
    }

    /**파라미터<br>1.isFollowerSearch : 값 있으면 팔로워 리뷰 검색<br>
     * 2.searchText : 검색어<br>3. pageOwnerId : 마이페이지 관련<br>4. id : 로그인 아이디**/
    public int maxReviewCount(String isFollowerSearch,String searchText,String pageOwnerId,String id){
        Map map = new HashMap<String,String>();
        map.put("isFollowerSearch",isFollowerSearch);
        map.put("searchText",searchText);
        map.put("pageOwnerId",pageOwnerId);
        map.put("id",id);

        int maxCount = reviewDao.maxReviewCount(map);
        return maxCount;
    }

    public List<Review> selectReviewTop5(String id){
        List<Review> reviewList = reviewDao.selectReviewTop5(id);
        return reviewList;
    }

    /**파라미터<br>1.isFollowerSearch : 값 있으면 팔로워 리뷰 검색<br>
     * 2.searchText : 검색어<br>3. pageOwnerId : 마이페이지 관련<br>
     * 4. id : 로그인 아이디<br>5. sortType 정렬타입(regDate||readCount||likeCount)<br>
     *6. startIndex : Pagination의 startIndex(필수)<br>7. pageSize : 한페이지에 들어가는 리뷰 개수(필수)**/
    public List<Review> selectReviewList(String isFollowerSearch,String searchText,String pageOwnerId,String id,
                                         String sortType,int startIndex,int pageSize){
        Map map = new HashMap<String,String>();
        map.put("isFollowerSearch",isFollowerSearch);
        map.put("searchText",searchText);
        map.put("pageOwnerId",pageOwnerId);
        map.put("id",id);//세션에서 가져온 id map에 넣기
        map.put("sortType",sortType);
        map.put("startIndex",startIndex);
        map.put("pageSize",pageSize);

        List<Review> reviewList = reviewDao.selectReviewList(map);
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

    // similarUsersReview
    public List<Review> selectSimilarUsersReview(Map map) {
        List<Review> reviewList = reviewDao.selectSimilarUsersReview(map);
        System.out.println("Service");
        System.out.println(map.values());
        return reviewList;
    }

    public int updateReview(Review review) {

        int updateCount = reviewDao.updateReview(review);
        return updateCount;
    }

    /**기능 : ReadCount 테이블,Review 테이블의 조회수 update <br>
     * Transaction 처리 <br>
     * 리턴 : boolean db 처리 이상 여부**/
    @Transactional
    public boolean updateReadCount(int reviewNo,String id) throws Exception{
        //readCount의 조회수 update,review의 조회수 update
        int count=0;
        if(id!=null){
            //로그인 상태면 해당 리뷰의 readCount 테이블 조회수를 확인
            // 조회수가 0이면 insert / 0이 아니면 update
            ReadCount readCount = new ReadCount();
            readCount.setId(id);
            readCount.setReviewNo(reviewNo);
            ReadCount selectedReadCount = readCountDao.selectReadCount(readCount);
            if(selectedReadCount==null){
                count = readCountDao.insertReadCount(readCount);
            }
            else{
                count = readCountDao.updateReadCount(readCount);
            }
        }
        //review의 조회수 update
        int updateReviewCount = reviewDao.updateReadCount(reviewNo);
        return (count ==1 && updateReviewCount ==1);
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

    //해쉬태그 변환 프로시저
    public String specialCharacter_replace(String str) {
        str = StringUtils.replaceChars(str, "-_+=!@#$%^&*()[]{}|\\;:'\"<>,.?/~`） ","");

        if(str.length() < 1) {
            return null;
        }

        return str;
    }
}