package com.chabak.controllers;

import com.chabak.repositories.ReplyDao;
import com.chabak.repositories.ReviewDao;
import com.chabak.vo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    ReviewDao reviewDao;
    ReplyDao replyDao;

    //댓글 달기
    @RequestMapping(value ="/writeReply", method=RequestMethod.POST)
    public ModelAndView writeReply(HttpServletRequest request,@ModelAttribute Reply reply){
        HttpSession session = request.getSession();

        //TODO:세션에서 id 가져오기
        //임시 코드(나중에 수정)
        reply.setId("id1");
        System.out.println("reply:"+reply);


        replyDao.insertReply(reply);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/review/detail?reviewNo="+reply.getReviewNo());
        return mv;//이동 주소는 수정할 것
    }

    //대댓글 달기
    @RequestMapping(value ="/writeReReply", method=RequestMethod.POST)
    public ModelAndView writeReReply(HttpServletRequest request,@ModelAttribute Reply reply){
        HttpSession session = request.getSession();

        //TODO:세션에서 id 가져오기
        //임시 코드(나중에 수정)
        reply.setId("id1");
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

        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/review/detail?reviewNo="+reply.getReviewNo());
        return mv;//이동 주소는 수정할 것
    }

    //댓글/대댓글 수정
    @RequestMapping(value ="/modifyReply", method=RequestMethod.POST)
    public String modifyReply(@ModelAttribute Reply reply){

        //TODO:세션에서 id 가져오기
        //임시 코드(나중에 수정)
        reply.setId("id1");

        //TODO:로직 작성
        replyDao.updateReply(reply);

        return "community/community";//이동 주소는 수정할 것
    }



}
