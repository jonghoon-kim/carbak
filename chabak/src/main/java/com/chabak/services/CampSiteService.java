package com.chabak.services;

import com.chabak.mapper.CampSiteMapper;
import com.chabak.vo.Campsite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("campSiteService")
public class CampSiteService {
    @Autowired
    private CampSiteMapper campSiteMapper;

    public List<Campsite> getlstSelectCampsitePlace(String latitude, String longitude){
        System.out.println(latitude);
        return campSiteMapper.getlstSelectCampsitePlace(latitude, longitude);
    }

}
