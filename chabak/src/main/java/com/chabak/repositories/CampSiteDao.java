package com.chabak.repositories;

import com.chabak.vo.Campsite;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CampSiteDao {

    public List<Campsite> campSiteList(@Param("campsitename") String campsitename, @Param("sido") String sido, @Param("gugun") String gugun);

}
