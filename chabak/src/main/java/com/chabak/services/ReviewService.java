package com.chabak.services;


import com.chabak.repositories.ReviewDao;
import com.chabak.util.Utility;
import com.chabak.vo.Pagination;
import com.chabak.vo.Review;
import com.chabak.vo.ReviewAndLike;
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

    //세션 아이디와 작성자 아이디를 파라미터로 받아 다르면 경고창을 띄우고 이전 페이지로 이동
    @SneakyThrows
    public boolean compareSessionAndWriterId(String sessionId, String writerId, HttpServletResponse response){

        if(sessionId.equals(writerId)){
            return true;
        }
        Utility.printAlertMessage(response,"해당 권한이 없습니다.");

        Utility.pageBackward(response);
        return false;
    }

    public List<ReviewAndLike> selectReviewTop5(String id){
        return reviewDao.selectReviewTop5(id);
    }
}