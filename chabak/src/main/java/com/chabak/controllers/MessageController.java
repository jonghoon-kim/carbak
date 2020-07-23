package com.chabak.controllers;

import com.chabak.services.*;
import com.chabak.util.Utility;
import com.chabak.vo.*;
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
@RequestMapping("/message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @RequestMapping(value ={"", "/", "/list"}, method=RequestMethod.GET)
    public ModelAndView messageList(HttpSession session,HttpServletResponse response,@RequestParam (required = false,defaultValue = "receive") String type){

        ModelAndView mv = new ModelAndView();

        //세션에서 로그인한 아이디 가져와 설정(return: id or null)
        String id = Utility.getIdForSessionOrMoveIndex(mv,session,response);

        Map map = new HashMap<String,String>();
        if(type.equals("receive")){
            map.put("receiveId",id);
        }
        else{
            map.put("sendId",id);
        }
        List<Message> messageList = messageService.selectMessageList(map);

        mv.addObject("messageList",messageList);
        mv.addObject("type",type);
        mv.setViewName("message/message");

        return mv;
    }

    @RequestMapping(value ={"/write"}, method=RequestMethod.GET)
    public ModelAndView writeForm(){

        ModelAndView mv = new ModelAndView();
        mv.setViewName("message/message_write");
        return mv;
    }

    @RequestMapping(value ={"/write"}, method=RequestMethod.POST)
    public ModelAndView writeMessage(@ModelAttribute Message message,HttpSession session,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();

        //세션에서 로그인한 아이디 가져와 설정(return: id or null)
        String id = Utility.getIdForSessionOrMoveIndex(mv,session,response);

        message.setSendId(id);
        messageService.insertMessage(message);
        //새창 띄우므로 null이어도 됨
        mv.setViewName("redirect:message/list");
        return mv;
    } //리뷰 리스트 출력

    @RequestMapping(value ="/detail", method=RequestMethod.GET)
    public ModelAndView detailMessage(@RequestParam int messageNo,HttpSession session,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();

        //region checkLogin(세션에서 로그인한 아이디 가져와 설정+비로그인시 로그인 페이지로 이동(return: id or null))
        String id = Utility.getIdForSessionNotMoveIndex(session);

        //endregion

        //메시지 선택
        Message message = messageService.selectMessageDetail(messageNo);

        //해당 메시지가 존재하지 않으면
        if(message == null){
            Utility.printAlertMessage("잘못된 접근입니다.",null,response);
            return null;
        }

        //메시지 읽음 여부 y로 업데이트
        try{
            messageService.updateReadYn(messageNo);
        }
        catch (Exception e){
            e.printStackTrace();
            Utility.printAlertMessage("작업 중 에러가 발생했습니다.",null,response);
        }

        mv.addObject("message",message);
        mv.setViewName("message/message_detail");
        return mv;
    }

    @RequestMapping(value ="/delete", method=RequestMethod.GET)
    public ModelAndView deleteReview(@RequestParam int messageNo,HttpSession session,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();

        //region checkLogin(세션에서 로그인한 아이디 가져와 설정+비로그인시 로그인 페이지로 이동(return: id or null))
        String id = Utility.getIdForSessionOrMoveIndex(mv,session,response);

        //endregion

        Message message;

        //삭제 권한 체크
        try{
            message = messageService.selectMessageDetail(messageNo);
            boolean authorityYn = id.equals(message.getReceiveId()) ? true:false;
            System.out.println("authorityYn:"+authorityYn);
            //권한 없으면
            if(!authorityYn){
                Utility.printAlertMessage("권한이 없습니다.",null,response);
                return null;
            }
            else{
                //메시지 삭제
                messageService.deleteMessage(messageNo);
            }
        } //메시지 받은 아이디가 없으면
        catch (NullPointerException e){
            Utility.printAlertMessage("잘못된 접근입니다.",null,response);
            return null;
        }

        mv.setViewName("redirect:/message/list");
        return mv;
    }
}