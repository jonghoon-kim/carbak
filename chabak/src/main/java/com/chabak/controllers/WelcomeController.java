package com.chabak.controllers;


import com.chabak.services.AdminService;
import com.chabak.services.ReviewService;
import com.chabak.vo.AdminPaging;
import com.chabak.vo.Member;
import com.chabak.vo.Review;
import lombok.SneakyThrows;

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
            if (id == null) {
                reviewList = reviewService.selectReviewTop5(null);
            } else {//TODO:로그인 사용자는 별도의 리스트를 출력하므로 나중에 지우기
                reviewList = reviewService.selectReviewTop5(id);
            }

            //리스트의 content에서 이미지 태그 지우기
            for (Review review : reviewList) {
                String modifiedContent = reviewService.deleteImgTag(review.getContent());
                review.setContent(modifiedContent);
            }
            mv.setViewName("/index");
            mv.addObject("reviewList", reviewList);

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
