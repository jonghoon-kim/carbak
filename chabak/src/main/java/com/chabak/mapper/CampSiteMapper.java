package com.chabak.mapper;

import com.chabak.vo.Campsite;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampSiteMapper {
    List<Campsite> getlstSelectCampsitePlace(@Param("latitude") String latitude, @Param("longitude") String longitude);
}
