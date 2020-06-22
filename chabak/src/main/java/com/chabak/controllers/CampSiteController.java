package com.chabak.controllers;

import com.chabak.services.CampSiteService;
import com.chabak.vo.Campsite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/campsite")
public class CampSiteController {
    @Autowired
    CampSiteService campSiteService;

    //campsite 경로 지정
    @RequestMapping(value= {"", "/", "campsite"}, method= {RequestMethod.GET,RequestMethod.POST})
    public String campSite(HttpServletRequest request, Model model)
    {
        String keyword = request.getParameter("keyword");
        if(keyword == null || keyword == " " || keyword == ""){
            keyword = "차박";
        }
        model.addAttribute("keyword", keyword);
        System.out.println(keyword);
        return "campsite/campsite";
    }

    //야영지 선택 경로 지정(selectPlaceDetail)
    @RequestMapping(value = "campsitePlaceDetail", method= {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView campsitePlaceDetail(HttpServletRequest request) {
        ModelAndView campsitePlaceDetail = new ModelAndView();
        String lat = request.getParameter("latitude"); //위도
        String lon = request.getParameter("longitude");//경도

        String latitude = lat.substring(0, 7);
        String longitude = lon.substring(0, 8);

        List<Campsite> lstSelectCampsitePlace = campSiteService.getlstSelectCampsitePlace(latitude,longitude);

        System.out.println("lstSelectCampsitePlace :" + lstSelectCampsitePlace.toString());

        campsitePlaceDetail.addObject("latitude",latitude);
        campsitePlaceDetail.addObject("longitude",longitude);
        campsitePlaceDetail.addObject("lstSelectCampsitePlace",lstSelectCampsitePlace);
        campsitePlaceDetail.setViewName("campsite/campsitePlaceDetail");
        return campsitePlaceDetail;
    }
}