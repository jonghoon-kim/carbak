package com.chabak.controllers;

import com.chabak.services.BlogService;
import com.chabak.services.CampSiteService;
import com.chabak.services.ImageService;
import com.chabak.vo.Blog;
import com.chabak.vo.Campsite;
import com.chabak.vo.Image;
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
    @Autowired
    BlogService blogService;
    @Autowired
    ImageService imageService;

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

        String keyword = lstSelectCampsitePlace.get(0).getCampsitename();

        System.out.println("lstSelectCampsitePlace :" + keyword);
        //
        BlogService service = new BlogService();
        ImageService serviceTest = new ImageService();
        List<Blog> b = service.searchBlog(keyword, 20, 1);
        List<Image> a = serviceTest.searchImage(keyword, 20, 1);
        //for(Blog b : service.searchBlog("어반슬로우시티", 20, 1))
            for(int i=0; i<a.size(); i++) {
                System.out.println(i+1 +"test : "+ a);
            }
        //    System.out.println(b.title);

        //
        campsitePlaceDetail.addObject("blogInfo",b);
        campsitePlaceDetail.addObject("imgInfo",a);
        campsitePlaceDetail.addObject("latitude",latitude);
        campsitePlaceDetail.addObject("longitude",longitude);
        campsitePlaceDetail.addObject("lstSelectCampsitePlace",lstSelectCampsitePlace);
        campsitePlaceDetail.setViewName("campsite/campsitePlaceDetail");
        return campsitePlaceDetail;
    }
}