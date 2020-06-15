package com.chabak.services;

import com.chabak.repositories.CampSiteDao;
import com.chabak.repositories.MemberDao;
import com.chabak.vo.Campsite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("CampSiteService")
public class CampSiteService {
    @Autowired
    private static CampSiteDao campSiteDao;

    public static List<Campsite> campSiteList(String campsitename, String sido, String gugun) {
        System.out.println(campsitename);
        System.out.println(sido);
        System.out.println(gugun);
        return campSiteDao.campSiteList(campsitename, sido, gugun);
    }

}
