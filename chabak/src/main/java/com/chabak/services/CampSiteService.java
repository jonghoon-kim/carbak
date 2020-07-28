package com.chabak.services;

import com.chabak.mapper.CampSiteMapper;
import com.chabak.vo.Campsite;
import com.chabak.vo.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("campSiteService")
public class CampSiteService {
    @Autowired
    private CampSiteMapper campSiteMapper;

    public List<Campsite> getlstSelectCampsitePlace(String latitude, String longitude){
        return campSiteMapper.getlstSelectCampsitePlace(latitude, longitude);
    }

    public List<Review> getlstSelectCampsiteReview(String startPageNo, String endPageNo){
        return campSiteMapper.getlstSelectCampsiteReview(startPageNo, endPageNo);
    }

    public List<Review> getlstSelectCampsiteDetailReview(String keyword,String startPageNo, String endPageNo){
        return campSiteMapper.getlstSelectCampsiteDetailReview(keyword, startPageNo, endPageNo);
    }

    public int pagingCnt() {
        return campSiteMapper.pagingCnt();
    }
}
