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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/campsite")
public class CampSiteController {
    @Autowired
    CampSiteService campSiteService;
//    @Autowired
//    BlogService blogService;
//    @Autowired
//    ImageService imageService;

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

        //시작 페이지 설정
       String startPageNo = "1";
        int pageNo = Integer.parseInt(startPageNo);

        String latitude = lat.substring(0, 7);
        String longitude = lon.substring(0, 8);

        //선택된 야영지 데이터 조회
        List<Campsite> lstSelectCampsitePlace = campSiteService.getlstSelectCampsitePlace(latitude,longitude);
        String keyword = lstSelectCampsitePlace.get(0).getCampsitename();


        //블로그와 이미지 호출
        BlogService service = new BlogService();
        ImageService serviceTest = new ImageService();
        List<Blog> b = service.searchBlog(keyword, 20, pageNo);
        List<Image> a = serviceTest.searchImage(keyword, 20, pageNo);

        campsitePlaceDetail.addObject("startPageNo",startPageNo);                       //naver blog, 썸네일 시작 페이지
        campsitePlaceDetail.addObject("blogInfo",b);                                    //naver blog정보
        campsitePlaceDetail.addObject("imgInfo",a);                                     //naver 썸네일 정보
        campsitePlaceDetail.addObject("latitude",latitude);                             //위도
        campsitePlaceDetail.addObject("longitude",longitude);                           //경도
        campsitePlaceDetail.addObject("lstSelectCampsitePlace",lstSelectCampsitePlace); //선택된 야영지 위치+상세정보
        campsitePlaceDetail.setViewName("campsite/campsitePlaceDetail");
        return campsitePlaceDetail;
    }

    @RequestMapping(value = "blogPaging", produces = "application/json; charset=utf8")
    @ResponseBody
    public Map<String, List> blogPaging(HttpServletRequest request) {
        Map<String, List> blogPageNumber = new HashMap<String, List>();

        //현재 페이지, 야영지명 함수값 호출
        String pageNo = request.getParameter("startPageNo");
        String keyword = request.getParameter("keyword");
        int nowPageNo = Integer.parseInt(pageNo);

        BlogService service = new BlogService();
        ImageService serviceTest = new ImageService();
        List<Blog> b = service.searchBlog(keyword, 14, nowPageNo);
        List<Image> a = serviceTest.searchImage(keyword, 14, nowPageNo);

        System.out.printf("test a : " + a);

        blogPageNumber.put("blogInfo", b);
        blogPageNumber.put("imageInfo", a);
        blogPageNumber.put("nowPageNo", Collections.singletonList(nowPageNo));
        return blogPageNumber;
    }
}