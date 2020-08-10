package com.chabak.controllers;


import com.chabak.services.AdminService;
import com.chabak.services.ReviewService;
import com.chabak.vo.AdminPaging;
import com.chabak.vo.Member;
import com.chabak.vo.Review;
import lombok.SneakyThrows;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WelcomeController {
    int AdminMembersCnt;
    @Autowired
    ReviewService reviewService;

    @Autowired
    AdminService adminService;

    @SneakyThrows
    @RequestMapping(value= {"", "/", "index"})
    public ModelAndView index(HttpSession session){
        ModelAndView mv = new ModelAndView();

        String id = (String)session.getAttribute("id");
        List<Review> reviewList = null;
        if(id==null){
            reviewList = reviewService.selectReviewTop5(null);

            //리스트의 content에서 이미지 태그 지우기
            for(Review review:reviewList){
                String modifiedContent = reviewService.deleteImgTag(review.getContent());
                review.setContent(modifiedContent);
            }
            mv.setViewName("/index");
            mv.addObject("reviewList",reviewList);

            return mv;

        }
        else{
            try{
                ModelAndView mv1 = new ModelAndView();
                String requestURL = "http://localhost:5000/find_similar_users";
                String postBody =""+ "{" + "\"id\":"+ id+"}"+"";
                String sessionId = id;

                System.out.println("postBody : "+postBody);

                RequestBody formBody = new FormBody.Builder()
                        .add("id", sessionId)
                        .build();

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(requestURL)
                        .post(formBody)
                        .build();

                Map<String, Object> map = new HashMap<>();

                //동기 처리시 execute함수 사용
                Response response = client.newCall(request).execute();

                //출력
                String similarUsers = response.body().string();
                System.out.println("test : " +similarUsers);

                if(similarUsers.contentEquals("null")) {
                    reviewList = reviewService.selectReviewTop5(null);

                    //리스트의 content에서 이미지 태그 지우기
                    for(Review review:reviewList){
                        String modifiedContent = reviewService.deleteImgTag(review.getContent());
                        review.setContent(modifiedContent);
                    }
                    mv1.setViewName("/index");
                    mv1.addObject("reviewList",reviewList);
                    System.out.println(reviewList);

                }else {
                    String similarUsersId[] = similarUsers.split(", ");

                    map.put("id1", similarUsersId[0]);
                    map.put("id2", similarUsersId[1]);
                    map.put("id3", similarUsersId[2]);
                    map.put("id4", similarUsersId[3]);
                    map.put("id5", similarUsersId[4]);
                    map.put("sessionId", sessionId);

                    System.out.println("mapValues : "+ similarUsers);

                    reviewList = reviewService.selectSimilarUsersReview(map);

                    //리스트의 content에서 이미지 태그 지우기
                    for(Review review:reviewList){
                        String modifiedContent = reviewService.deleteImgTag(review.getContent());
                        review.setContent(modifiedContent);
                    }
                    System.out.println(reviewList);
                    mv1.setViewName("/index");
                    mv1.addObject("similarUsers", similarUsers);
                    mv1.addObject("reviewList",reviewList);
                }

                return mv1;

            } catch (Exception e) {
                System.err.println(e.toString());
            }

        }

        System.out.println(mv);
        return mv;

    }

    @RequestMapping(value= "/admin")
    public ModelAndView admin(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();

        String crntpageNo = request.getParameter("crnt");						//페이지 번호
        String startPageNo = request.getParameter("start");					    //시작페이지 번호
        String endPageNo = request.getParameter("end");							//마지막페이지 번호
        if(crntpageNo == null || crntpageNo == "undefined") {
            crntpageNo = "1";						//페이지 번호
            startPageNo = "1";					    //시작페이지 번호
            endPageNo = "5";						//마지막페이지 번호
        }
        int lstSize=10;
        List<Member> adminMemberlist = adminService.getlstSelectAdminMemberlist(startPageNo,endPageNo);
        AdminMembersCnt = adminService.adminCnt();
        String adminwBoolean;
        if(AdminMembersCnt<1){
            adminwBoolean = "false";
        }
        else if (adminMemberlist.size()==0){
            crntpageNo = Integer.toString(Integer.parseInt(crntpageNo)-1);					//페이지 번호
            startPageNo = Integer.toString(Integer.parseInt(startPageNo)-10);					//시작페이지 번호
            endPageNo = Integer.toString(Integer.parseInt(endPageNo)-10);						//마지막페이지 번호
            adminMemberlist = adminService.getlstSelectAdminMemberlist(startPageNo,endPageNo);
            System.out.println(adminMemberlist);
            lstSize = adminMemberlist.size()-1;
            adminwBoolean = "true";
        }
        else{
            lstSize = adminMemberlist.size()-1;
            adminwBoolean = "true";
        }
        mv.addObject("lstSize", lstSize);
        mv.addObject("adminwBoolean", adminwBoolean);
        mv.addObject("adminMemberlist", adminMemberlist);
        mv.addObject("adminPaging",AdminPaging(Integer.parseInt(crntpageNo),10, AdminMembersCnt));
        mv.setViewName("/admin");

        return mv;
    }
    /*
     * 페이징
     */
    public AdminPaging AdminPaging(int crntpageNo, int PageSize, int TotCount) { // 페이징
        AdminPaging paging = new AdminPaging();
        paging.setPageNo(crntpageNo); // 페이지번호
        paging.setPageSize(PageSize); // 한페이지에 출력 개수
        paging.setTotalCount(TotCount); // 총개수
        return paging;
    }

    @RequestMapping(value = "/adminMembersPaging", produces = "application/json; charset=utf8")
    @ResponseBody
    public Map<String, Object> adminMembersPaging(HttpServletRequest request) {
        String crntpageNo = request.getParameter("crntpageNo");						//페이지 번호
        String startPageNo = request.getParameter("startPageNo");					//시작페이지 번호
        String endPageNo = request.getParameter("endPageNo");						//마지막페이지 번호

        int lstSize=10;
        Map<String, Object> adminPageChange = new HashMap<String, Object>();

        List<Member> adminMemberlist = adminService.getlstSelectAdminMemberlist(startPageNo,endPageNo);
        Object adminwBoolean;

        if(AdminMembersCnt<1){
            adminwBoolean = "false";
        }
        else if (adminMemberlist.size()==0){
            crntpageNo = Integer.toString(Integer.parseInt(crntpageNo)-1);					//페이지 번호
            startPageNo = Integer.toString(Integer.parseInt(startPageNo)-10);					//시작페이지 번호
            endPageNo = Integer.toString(Integer.parseInt(endPageNo)-10);						//마지막페이지 번호
            adminMemberlist = adminService.getlstSelectAdminMemberlist(startPageNo,endPageNo);
            lstSize = adminMemberlist.size();
            adminwBoolean = "true";
        }
        else{
            lstSize = adminMemberlist.size();
            adminwBoolean = "true";
        }
        adminPageChange.put("adminMemberlist",adminMemberlist);
        adminPageChange.put("adminwBoolean", adminwBoolean);
        adminPageChange.put("lstSize", lstSize);
        adminPageChange.put("adminPaging",AdminPaging(Integer.parseInt(crntpageNo),10, AdminMembersCnt));
        return adminPageChange;
    }
    @RequestMapping(value = "/adminDel", method= {RequestMethod.GET, RequestMethod.POST}, produces = "application/json; charset=utf8")
    @ResponseBody
    public ModelAndView adminDel(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        System.out.println("controller success");
        String deleteId = request.getParameter("deleteId");
        System.out.println("deleteId : " + deleteId);
        adminService.memberDelete(deleteId);
        mv.setViewName("redirect:/admin");
        return mv;
    }

    @RequestMapping("/header")
    public String header() {
        return "header";
    }

    @RequestMapping("/footer")
    public String footer() {return "footer";}
}
