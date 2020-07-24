package com.chabak.controllers;

import com.chabak.services.*;
import com.chabak.util.Utility;
import com.chabak.vo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
    public ModelAndView messageList(HttpSession session,HttpServletResponse response,@RequestParam (defaultValue = "receive") String messageBox){

        ModelAndView mv = new ModelAndView();

        //세션에서 로그인한 아이디 가져와 설정(return: id or null)
        String id = Utility.getIdForSessionOrMoveIndex(mv,session,response);

        //messageBox값(send||receive)에 따라서 보낸메시지함||받은메시지함 메시지 리스트 출력
        List<Message> messageList = messageService.selectMessageList(id,messageBox);

        mv.addObject("messageList",messageList);
        mv.addObject("messageBox",messageBox);
        mv.setViewName("message/message");

        return mv;
    }

    @RequestMapping(value ={"/write"}, method=RequestMethod.GET)
    public ModelAndView writeForm(@RequestParam (required = false)String receiveId){

        ModelAndView mv = new ModelAndView();
        if(receiveId!=null){
            mv.addObject("receiveId",receiveId);
        }
        mv.setViewName("message/message_write");
        return mv;
    }

    @RequestMapping(value ={"/write"}, method=RequestMethod.POST)
    public ModelAndView writeMessage(@ModelAttribute Message message,HttpSession session,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();

        //세션에서 로그인한 아이디 가져와 설정(return: id or null)
        String id = Utility.getIdForSessionOrMoveIndex(mv,session,response);
        System.out.println("message:"+message);
        message.setSendId(id);
        try{
            messageService.insertMessage(message);
        }
        catch (Exception e){
            e.printStackTrace();
            Utility.printAlertMessage("작업 중 에러가 발생했습니다.",null,response);
        }

        //새창 닫기
        Utility.closeWindow(response);
        return null;
    } //리뷰 리스트 출력

    @RequestMapping(value ="/detail", method=RequestMethod.GET)
    public ModelAndView detailMessage(@RequestParam int messageNo,@RequestParam String messageBox, HttpSession session,HttpServletResponse response){

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
        mv.addObject("messageBox",messageBox);
        mv.setViewName("message/message_detail");
        return mv;
    }

    @Transactional
    @RequestMapping(value ="/delete", method=RequestMethod.GET)
    public ModelAndView deleteReview(@RequestParam int messageNo,@RequestParam String messageBox,HttpSession session,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();

        //region checkLogin(세션에서 로그인한 아이디 가져와 설정+비로그인시 로그인 페이지로 이동(return: id or null))
        String id = Utility.getIdForSessionOrMoveIndex(mv,session,response);

        //endregion

        Message message;
        
        //삭제 권한 체크
        try{
            //messageNo로 선택한 메시지 1개 불러옴
            message = messageService.selectMessageDetail(messageNo);
            
            //권한 체크용 변수
            boolean authorityYn;
            if(messageBox.equals("send")){
                authorityYn = id.equals(message.getSendId()) ? true:false;
            }
            else{ //messageBox 값이 send가 아니면
                authorityYn = id.equals(message.getReceiveId()) ? true:false;
            }
           
            System.out.println("authorityYn:"+authorityYn);
            //권한 없으면
            if(!authorityYn){
                Utility.printAlertMessage("권한이 없습니다.",null,response);
                return null;
            }
            else{
                messageService.updateBoxDeletedYn(messageBox,messageNo);
                //보낸메시지함,받은메시지함에서 해당 메시지가 지워졌는지 확인을 위해 다시 메시지 불러오기
                message = messageService.selectMessageDetail(messageNo);
                //해당 메시지가 받은편지함과 보낸편지함에서 둘다 지워지면(flag값이 y가 되면)
                if(message.getReceiveBoxDeletedYn().equals("y") && message.getSendBoxDeletedYn().equals("y")){
                    //메시지 완전 삭제
                    messageService.deleteMessage(messageNo);                   
                }

            }
        } //메시지 받은 아이디가 없으면
        catch (NullPointerException e){
            Utility.printAlertMessage("잘못된 접근입니다.",null,response);
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            Utility.printAlertMessage("작업 중 에러가 발생했습니다.",null,response);
        }

        mv.setViewName("redirect:/message/list");
        return mv;
    }
}