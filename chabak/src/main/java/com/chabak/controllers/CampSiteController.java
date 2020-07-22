package com.chabak.controllers;

import com.chabak.services.BlogService;
import com.chabak.services.CampSiteService;
import com.chabak.services.ImageService;
import com.chabak.services.ReviewService;
import com.chabak.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/campsite")
public class CampSiteController {
    @Autowired
    CampSiteService campSiteService;
    @Autowired
    BlogService blogService;
    @Autowired
    ImageService imageService;
    @Autowired
    ReviewService reviewService;

    //campsite 경로 지정
    @RequestMapping(value= {"", "/", "/campsite"}, method= {RequestMethod.GET,RequestMethod.POST})
    public String campSite(HttpServletRequest request, Model model)
    {
        String keyword = request.getParameter("keyword");
        if(keyword == null || keyword == " " || keyword == ""){
            keyword = "차박";
        }
        model.addAttribute("keyword", keyword);



        return "campsite/campsite";
    }

    //야영지 선택 경로 지정(selectPlaceDetail)
    @RequestMapping(value = "/campsitePlaceDetail", method= {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView campsitePlaceDetail(HttpServletRequest request, HttpSession session) {
        ModelAndView campsitePlaceDetail = new ModelAndView();

        String lat = request.getParameter("latitude"); //위도
        String lon = request.getParameter("longitude");//경도
        String plname = request.getParameter("plname");//db에 없는 야영장이름

        try {
            //시작 페이지 설정
            String startPageNo = "1";
            int pageNo = Integer.parseInt(startPageNo);

            String latitude = lat.substring(0, 7);
            String longitude = lon.substring(0, 8);

            //선택된 야영지 데이터 조회
            List<Campsite> lstSelectCampsitePlace = campSiteService.getlstSelectCampsitePlace(latitude, longitude);
            String keyword;

            if(lstSelectCampsitePlace.size()!=0){
                keyword = lstSelectCampsitePlace.get(0).getCampsitename();
            }
            else{
                keyword = plname;
            }

            //커뮤니티 호출
            Map map = new HashMap<String,String>();
            Pagination pagination = new Pagination();
            map.put("sortType", "readCount");
            map.put("searchText", keyword);
            map.put("startIndex", pagination.getStartIndex());
            int listCnt = reviewService.maxReviewCount(map);
            map.put("pageSize",listCnt);

            List<Review> reviewList = reviewService.selectReviewList(map);
            System.out.println(reviewList);
            campsitePlaceDetail.addObject("reviewList",reviewList);

            //블로그와 이미지 호출
            BlogService service = new BlogService();
            ImageService serviceTest = new ImageService();
            List<Blog> b = service.searchBlog(keyword, 15, pageNo);
            List<Image> a = serviceTest.searchImage(keyword, 15, pageNo);

            campsitePlaceDetail.addObject("startPageNo", startPageNo);                       //naver blog, 썸네일 시작 페이지
            campsitePlaceDetail.addObject("blogInfo", b);                                    //naver blog정보
            campsitePlaceDetail.addObject("imgInfo", a);                                     //naver 썸네일 정보
            campsitePlaceDetail.addObject("latitude", latitude);                             //위도
            campsitePlaceDetail.addObject("longitude", longitude);                           //경도
            campsitePlaceDetail.addObject("plname", plname);                             //DB에 없는 데이터야영지 이름
            campsitePlaceDetail.addObject("lstSelectCampsitePlace", lstSelectCampsitePlace); //선택된 야영지 위치+상세정보
            campsitePlaceDetail.setViewName("campsite/campsitePlaceDetail");
            return campsitePlaceDetail;
        }
        catch (Exception e){
            System.out.println("3. IndexOutOfBoundsException 에러 : ");
            e.printStackTrace(System.out);
            System.out.println("==============================================");

            return campsitePlaceDetail;
        }
    }

    @RequestMapping(value = "/blogPaging", produces = "application/json; charset=utf8")
    @ResponseBody
    public Map<String, List> blogPaging(HttpServletRequest request){
        Map<String, List> blogPageNumber = new HashMap<String, List>();

        //현재 페이지, 야영지명 함수값 호출
        String pageNo = request.getParameter("startPageNo");
        String keyword = request.getParameter("keyword");
        int nowPageNo = Integer.parseInt(pageNo);
        int pageBoolean = 1;

        if(nowPageNo >= 16){
            pageBoolean = 0;
            nowPageNo=11;
        }

        BlogService service = new BlogService();
        ImageService serviceTest = new ImageService();
        List<Blog> b = service.searchBlog(keyword, 15, nowPageNo);
        List<Image> a = serviceTest.searchImage(keyword, 15, nowPageNo);

        blogPageNumber.put("blogInfo", b);
        blogPageNumber.put("imageInfo", a);
        blogPageNumber.put("campsitekeyword", Collections.singletonList(keyword));
        blogPageNumber.put("nowPageNo", Collections.singletonList(nowPageNo));
        blogPageNumber.put("pageBoolean", Collections.singletonList(pageBoolean));
        return blogPageNumber;
    }
}