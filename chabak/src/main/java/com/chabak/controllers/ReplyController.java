package com.chabak.controllers;

import com.chabak.services.ReplyService;
import com.chabak.services.ReviewService;
import com.chabak.util.Utility;
import com.chabak.vo.Reply;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    ReviewService reviewService;
    @Autowired
    ReplyService replyService;

    //댓글 달기
    @RequestMapping(value ="/writeReply", method=RequestMethod.POST)
    public ModelAndView writeReply( @ModelAttribute Reply reply, HttpSession session,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();

        //region checkLogin(세션에서 로그인한 아이디 가져와 설정+비로그인시 로그인 페이지로 이동(return: id or null))
        String id = Utility.getIdForSessionOrMoveIndex(mv,session,response);
        //endregion

        reply.setId(id);
        System.out.println("reply:"+reply);

        replyService.insertReply(reply);

        mv.setViewName("redirect:/review/detail?reviewNo="+reply.getReviewNo());
        return mv;//이동 주소는 수정할 것
    }

    //대댓글 달기
    @RequestMapping(value ="/writeReReply", method=RequestMethod.POST)
    public ModelAndView writeReReply(HttpSession session,HttpServletResponse response,@ModelAttribute Reply reply){

        ModelAndView mv = new ModelAndView();

        //region checkLogin(세션에서 로그인한 아이디 가져와 설정+비로그인시 로그인 페이지로 이동(return: id or null))
        String id = Utility.getIdForSessionOrMoveIndex(mv,session,response);
        //endregion

        reply.setId(id);
        replyService.insertReReply(reply);

        mv.setViewName("redirect:/review/detail?reviewNo="+reply.getReviewNo());
        System.out.println(mv.getViewName());
        return mv;
    }

    //댓글/대댓글 수정
    @RequestMapping(value ="/modify", method=RequestMethod.POST)
    public ModelAndView modifyReply(HttpSession session,HttpServletResponse response,@ModelAttribute Reply reply){
        ModelAndView mv = new ModelAndView();

        //region checkLogin(세션에서 로그인한 아이디 가져와 설정+비로그인시 로그인 페이지로 이동(return: id or null))
        String id = Utility.getIdForSessionOrMoveIndex(mv,session,response);
        //endregion

        reply.setId(id);

        System.out.println("reply/modify reply:"+reply);
        replyService.updateReply(reply);

        mv.setViewName("redirect:/review/detail?reviewNo="+reply.getReviewNo());
        System.out.println(mv.getViewName());
        return mv;
    }

    //하위 댓글의 수 count
    @SneakyThrows
    @ResponseBody
    @RequestMapping("/checkChildReply")
    public int checkChildReply(HttpServletRequest request){

        int replyNo = Integer.parseInt(request.getParameter("replyNo"));

        //ReplyNo로 Reply 1개 확정
        Reply reply = replyService.selectReply(replyNo);

        //해당 Reply을 부모로 갖는 자녀 리플 수
        int countChild = replyService.countChildReply(reply);

        System.out.println("countChild:"+countChild);
        return countChild;

    }


    //댓글 삭제(댓글,대댓글 공통)
    @RequestMapping(value ="/delete", method=RequestMethod.GET)
    public ModelAndView deleteReply(@RequestParam int replyNo,HttpSession session,HttpServletResponse response){
        ModelAndView mv = new ModelAndView();

        //region checkLogin(세션에서 로그인한 아이디 가져와 설정+비로그인시 로그인 페이지로 이동(return: id or null))
        String id = Utility.getIdForSessionOrMoveIndex(mv,session,response);
        //endregion

        //replyNo로 해당 리플 select
        Reply reply = replyService.selectReply(replyNo);
        //reviewNo 얻어오기
        int reviewNo = reply.getReviewNo();

        //replyNo로 리플 삭제
        replyService.deleteReplyWithReplyNo(replyNo,reviewNo);

        mv.setViewName("redirect:/review/detail?reviewNo="+reviewNo);
        System.out.println(mv.getViewName());
        return mv;
    }


}