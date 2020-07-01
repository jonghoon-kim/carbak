package com.chabak.controllers;

import com.chabak.repositories.ReplyDao;
import com.chabak.repositories.ReviewDao;
import com.chabak.services.MemberService;
import com.chabak.vo.Reply;
import com.chabak.vo.Review;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    ReviewDao reviewDao;
    @Autowired
    ReplyDao replyDao;

    @Autowired
    MemberService memberService;

    //댓글 달기
    @RequestMapping(value ="/writeReply", method=RequestMethod.POST)
    public ModelAndView writeReply(HttpServletRequest request, @ModelAttribute Reply reply, HttpSession session,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();

        //세션에서 로그인한 아이디 가져와 설정(return: id or null)
        String id = memberService.getIdForSessionOrMoveIndex(mv,session,response);
        if(id==null)
            return mv;

        reply.setId(id);
        System.out.println("reply:"+reply);


        replyDao.insertReply(reply);
        //리뷰 댓글 수 증가
        reviewDao.increaseReplyCount(reply.getReviewNo());

        //TODO:리다이렉트 시 조회수가 증가하므로 ajax로 변경을 고민 중...
        mv.setViewName("redirect:/review/detail?reviewNo="+reply.getReviewNo());
        return mv;//이동 주소는 수정할 것
    }

    //대댓글 달기
    @RequestMapping(value ="/writeReReply", method=RequestMethod.POST)
    public ModelAndView writeReReply(HttpSession session,HttpServletResponse response,@ModelAttribute Reply reply){

        ModelAndView mv = new ModelAndView();

        //세션에서 로그인한 아이디 가져와 설정(return: id or null)
        String id = memberService.getIdForSessionOrMoveIndex(mv,session,response);
        if(id==null)
            return mv;

        reply.setId(id);

        System.out.println("checkReReplyCondition reply:"+reply);

        //1번 쿼리: 대댓글 들어갈 위치 조건 결정(댓글 사이에 끼워넣을지,끝에 넣을지)
        int condition = replyDao.checkReReplyCondition(reply);

        if(condition==0){
            //2번 쿼리
            int groupOrder = replyDao.selectGroupOrder(reply);
            //groupOrder 값을 2번 쿼리값으로 설정정
            reply.setGroupOrder(groupOrder);
            //lv 값을 부모 lv+1로 설정
            reply.setLv(reply.getLv()+1);
            //대댓글 insert

        }
        else {//1번 쿼리값이 0 이 아니면

            //roupOrder 값을 1번 쿼리값으로 설정
            int groupOrder = condition;
            reply.setGroupOrder(groupOrder);

            //들어갈 댓글 아래의 groupOrder 1씩 증가
            replyDao.updateGroupOrder(reply);

            reply.setLv(reply.getLv()+1);


        }
        //대댓글 insert
        replyDao.insertReReply(reply);

        //리뷰 댓글 수 증가
        reviewDao.increaseReplyCount(reply.getReviewNo());

        //TODO:리다이렉트 시 조회수가 증가하므로 ajax로 변경을 고민 중...

        mv.setViewName("redirect:/review/detail?reviewNo="+reply.getReviewNo());
        System.out.println(mv.getViewName());
        return mv;
    }

    //댓글/대댓글 수정
    @RequestMapping(value ="/modify", method=RequestMethod.POST)
    public ModelAndView modifyReply(HttpSession session,HttpServletResponse response,@ModelAttribute Reply reply){


        ModelAndView mv = new ModelAndView();

        //세션에서 로그인한 아이디 가져와 설정(return: id or null)
        String id = memberService.getIdForSessionOrMoveIndex(mv,session,response);
        if(id==null)
            return mv;

        reply.setId(id);

        System.out.println("reply/modify reply:"+reply);
        //TODO:로직 작성
        replyDao.updateReply(reply);

        mv.setViewName("redirect:/review/detail?reviewNo="+reply.getReviewNo());
        System.out.println(mv.getViewName());
        return mv;
    }

    //하위 댓글의 수 count
    @SneakyThrows
    @ResponseBody
    @RequestMapping("/checkChildReply")
    public int checkChildReply(HttpServletRequest request,ModelAndView mv){


        int replyNo = Integer.parseInt(request.getParameter("replyNo"));

        //ReplyNo로 Reply 1개 확정
        Reply reply = replyDao.selectReply(replyNo);

        int countChild = replyDao.countChildReply(reply);

        System.out.println("countChild:"+countChild);
        return countChild;

    }


    //댓글 삭제(댓글,대댓글 공통)
    @RequestMapping(value ="/delete", method=RequestMethod.GET)
    public ModelAndView deleteReply(@RequestParam int replyNo){
        System.out.println("reply/delete replyNo:"+replyNo);

        //TODO:로직 작성
        //replyNo로 해당 리플 select
        Reply reply = replyDao.selectReply(replyNo);
        //reviewNo 얻어오기
        int reviewNo = reply.getReviewNo();

        //replyNo로 리플 삭제
        replyDao.deleteReplyWithReplyNo(replyNo);
        //리뷰 댓글 수 증가
        reviewDao.decreaseReplyCount(reply.getReviewNo());



        ModelAndView mv = new ModelAndView();


        mv.setViewName("redirect:/review/detail?reviewNo="+reviewNo);
        System.out.println(mv.getViewName());
        return mv;
    }


}
