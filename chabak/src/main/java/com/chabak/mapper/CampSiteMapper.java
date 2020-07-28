package com.chabak.mapper;

import com.chabak.vo.Campsite;
import com.chabak.vo.Review;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampSiteMapper {
    List<Campsite> getlstSelectCampsitePlace(@Param("latitude") String latitude, @Param("longitude") String longitude);

    List<Review> getlstSelectCampsiteReview(@Param("startPageNo")String startPageNo, @Param("endPageNo")String endPageNo);

    List<Review> getlstSelectCampsiteDetailReview(@Param("keyword")String keyword, @Param("startPageNo")String startPageNo, @Param("endPageNo")String endPageNo);
    int pagingCnt();
}

