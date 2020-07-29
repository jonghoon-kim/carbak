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
import java.util.*;

@Controller
@RequestMapping("/campsite")
public class CampSiteController {
    @Autowired
    CampSiteService campSiteService;
    int campsiteReviewCnt, campsiteDetailReviewCnt;

    @Autowired
    BlogService blogService;
    @Autowired
    ImageService imageService;
    @Autowired
    ReviewService reviewService;

    //campsite 경로 지정
    @RequestMapping(value= {"", "/", "/campsite"}, method= {RequestMethod.GET,RequestMethod.POST})
    public String campSite(HttpServletRequest request, Model model) {
        String keyword = request.getParameter("keyword");
        if (keyword == null || keyword == " " || keyword == "") {
            keyword = "차박";
        }
        String crntpageNo = request.getParameter("crnt");						//페이지 번호
        String startPageNo = request.getParameter("start");					    //시작페이지 번호
        String endPageNo = request.getParameter("end");							//마지막페이지 번호
        if(crntpageNo == null || crntpageNo == "undefined") {
            crntpageNo = "1";						//페이지 번호
            startPageNo = "1";					    //시작페이지 번호
            endPageNo = "5";						//마지막페이지 번호
        }

        List<Review> lstSelectCampsiteReview = campSiteService.getlstSelectCampsiteReview(startPageNo,endPageNo);
        campsiteReviewCnt = campSiteService.pagingCnt();

        model.addAttribute("lstSelectCampsiteReview", lstSelectCampsiteReview);
        model.addAttribute("keyword", keyword);
        model.addAttribute("paging",Paging(Integer.parseInt(crntpageNo),5, campsiteReviewCnt));
        return "campsite/campsite";
    }
    /*
     * 야영장 커뮤니티 페이징
     */
    @RequestMapping(value = "/campsiteCommunityPaging", produces = "application/json; charset=utf8")
    @ResponseBody
    public Map<String, Object> changePage(HttpServletRequest request) {
        String crntpageNo = request.getParameter("crntpageNo");						//페이지 번호
        String startPageNo = request.getParameter("startPageNo");					//시작페이지 번호
        String endPageNo = request.getParameter("endPageNo");						//마지막페이지 번호

        int lstSize=5;
        Map<String, Object> campsiteCommunityPageChange = new HashMap<String, Object>();

        List<Review> lstSelectCampsiteReview = campSiteService.getlstSelectCampsiteReview(startPageNo,endPageNo);
        Object reviewBoolean;
        if(campsiteReviewCnt<1){
            reviewBoolean = "false";
        }
        else if (lstSelectCampsiteReview.size()==0){
            crntpageNo = Integer.toString(Integer.parseInt(crntpageNo)-1);					//페이지 번호
            startPageNo = Integer.toString(Integer.parseInt(startPageNo)-5);					//시작페이지 번호
            endPageNo = Integer.toString(Integer.parseInt(endPageNo)-5);						//마지막페이지 번호
            lstSelectCampsiteReview = campSiteService.getlstSelectCampsiteReview(startPageNo,endPageNo);
            lstSize = lstSelectCampsiteReview.size();
            reviewBoolean = "true";
        }
        else{
            lstSize = lstSelectCampsiteReview.size();
            reviewBoolean = "true";
        }
        campsiteCommunityPageChange.put("lstSize",lstSize);
        campsiteCommunityPageChange.put("lstSelectCampsiteReview",lstSelectCampsiteReview);
        campsiteCommunityPageChange.put("reviewBoolean", reviewBoolean);
        campsiteCommunityPageChange.put("paging", Paging(Integer.parseInt(crntpageNo), 5, campsiteReviewCnt));
        return campsiteCommunityPageChange;
    }

    //야영지 선택 경로 지정(selectPlaceDetail)
    @RequestMapping(value = "/campsitePlaceDetail", method= {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView campsitePlaceDetail(HttpServletRequest request) {
        ModelAndView campsitePlaceDetail = new ModelAndView();

        String lat = request.getParameter("latitude"); //위도
        String lon = request.getParameter("longitude");//경도
        String plname = request.getParameter("plname");//db에 없는 야영장이름

        try {
            //시작 페이지 설정
            String startPageNo = request.getParameter("startPageNo");					//시작페이지 번호
            String crntpageNo = request.getParameter("crnt");						//페이지 번호
            String endPageNo = request.getParameter("end");							//마지막페이지 번호
            if(crntpageNo == null || crntpageNo == "undefined") {
                crntpageNo = "1";						//페이지 번호
                startPageNo = "1";					    //시작페이지 번호
                endPageNo = "5";						//마지막페이지 번호
            }
            int pageNo = Integer.parseInt(startPageNo);

            String latitude = lat.substring(0, 7);
            String longitude = lon.substring(0, 8);

            //선택된 야영지 데이터 조회
            List<Campsite> lstSelectCampsitePlace = campSiteService.getlstSelectCampsitePlace(latitude, longitude);
            String keyword;

            if(lstSelectCampsitePlace.size()!=0){
                keyword = lstSelectCampsitePlace.get(0).getCampsitename();
            }
            else if(plname ==""|| plname==null || plname==" "){
                keyword = "야영장";
            }
            else{
                keyword = plname;
            }

            //커뮤니티 호출
            List<Review> getlstSelectCampsiteDetailReview = campSiteService.getlstSelectCampsiteDetailReview(keyword,startPageNo,endPageNo);
            campsiteDetailReviewCnt = campSiteService.detailPagingCnt(keyword);
            String campsiteDetailReviewBoolean = "true";
            if(campsiteDetailReviewCnt<=1){
                campsiteDetailReviewBoolean = "false";
            }

            campsitePlaceDetail.addObject("campsiteDetailReviewBoolean", campsiteDetailReviewBoolean);
            campsitePlaceDetail.addObject("getlstSelectCampsiteDetailReview", getlstSelectCampsiteDetailReview); //review 정보(조회수 순)
            campsitePlaceDetail.addObject("keyword", keyword);
            campsitePlaceDetail.addObject("paging",Paging(Integer.parseInt(crntpageNo),5, campsiteDetailReviewCnt));

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


    /*
     * 페이징
     */
    public CampsiteCommunityPagingVO Paging(int crntpageNo, int PageSize, int TotCount) { // 페이징
        CampsiteCommunityPagingVO paging = new CampsiteCommunityPagingVO();
        paging.setPageNo(crntpageNo); // 페이지번호
        paging.setPageSize(PageSize); // 한페이지에 출력 개수
        paging.setTotalCount(TotCount); // 총개수
        return paging;
    }

    //야영장 선택 후 커뮤니티 리뷰 페이징
    @RequestMapping(value = "/campsiteDetailCommunityPaging", produces = "application/json; charset=utf8")
    @ResponseBody
    public Map<String, Object> campsiteDetailCommunityPaging(HttpServletRequest request) {
        String crntpageNo = request.getParameter("crntpageNo");						//페이지 번호
        String startPageNo = request.getParameter("startPageNo");					//시작페이지 번호
        String endPageNo = request.getParameter("endPageNo");						//마지막페이지 번호
        String keyword = request.getParameter("keyword");                            //야영장 키워드

        int lstSize=5;
        Map<String, Object> campsiteCommunityDetailPageChange = new HashMap<String, Object>();

        List<Review> lstSelectCampsiteDetailReview = campSiteService.getlstSelectCampsiteDetailReview(keyword,startPageNo,endPageNo);
        Object reviewBoolean;
        if(campsiteDetailReviewCnt<1){
            reviewBoolean = "false";
        }
        else if (lstSelectCampsiteDetailReview.size()==0){
            crntpageNo = Integer.toString(Integer.parseInt(crntpageNo)-1);					//페이지 번호
            startPageNo = Integer.toString(Integer.parseInt(startPageNo)-5);					//시작페이지 번호
            endPageNo = Integer.toString(Integer.parseInt(endPageNo)-5);						//마지막페이지 번호
            lstSelectCampsiteDetailReview = campSiteService.getlstSelectCampsiteDetailReview(keyword,startPageNo,endPageNo);
            lstSize = lstSelectCampsiteDetailReview.size();
            reviewBoolean = "true";
        }
        else{
            lstSize = lstSelectCampsiteDetailReview.size();
            reviewBoolean = "true";
        }
        System.out.println("lstSize : " + lstSize);
        System.out.println("campsiteDetailReviewCnt : " + campsiteDetailReviewCnt);
        System.out.println("lstSelectCampsiteDetailReview : " + lstSelectCampsiteDetailReview);
        System.out.println("getlstSelectCampsiteDetailReview : " + lstSelectCampsiteDetailReview.size());
        campsiteCommunityDetailPageChange.put("lstSelectCampsiteDetailReview",lstSelectCampsiteDetailReview);
        campsiteCommunityDetailPageChange.put("keyword", keyword);
        campsiteCommunityDetailPageChange.put("reviewBoolean", reviewBoolean);
        campsiteCommunityDetailPageChange.put("lstSize", lstSize);
        campsiteCommunityDetailPageChange.put("paging", Paging(Integer.parseInt(crntpageNo), 5, campsiteDetailReviewCnt));
        return campsiteCommunityDetailPageChange;
    }
    //야영장 선택 후 블로그 페이징
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