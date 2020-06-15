package com.chabak.controllers;

import com.chabak.repositories.CampSiteDao;
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
    CampSiteService CampSiteService;

    //campsite 경로 지정
    @RequestMapping(value= {"", "/", "campsite"}, method= RequestMethod.GET)
    public String campSite()
    {
        return "campsite/campsite";
    }

    //야영지 검색(select,text 데이터 가져오기)
    @RequestMapping(value ="/campsiteSearchPlaces", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView campsiteSearchPlaces(HttpServletRequest request, Model model) throws Exception{
        ModelAndView campsiteSearchPlaces = new ModelAndView();

        String sido = request.getParameter("sido");
        String gugun = request.getParameter("gugun");
        String campsitename = request.getParameter("campsitename");

        System.out.println("test3"+sido);
        System.out.println("test4"+gugun);
        System.out.println("campsitename:"+campsitename);

        List<Campsite> campSiteList = CampSiteService.campSiteList(campsitename, sido, gugun);

        System.out.println("test1"+sido);
        System.out.println("test2"+gugun);
        System.out.println("campsitename:"+campsitename);

        campsiteSearchPlaces.addObject("campsitename",campsitename);
        campsiteSearchPlaces.addObject("sido",sido);
        campsiteSearchPlaces.addObject("gugun",gugun);

        System.out.println("campSiteList = "+ campSiteList);

        campsiteSearchPlaces.setViewName("campsite/campsite");
        return campsiteSearchPlaces;
    }

}