package com.chabak.controllers;

import com.chabak.services.*;
import com.chabak.util.Utility;
import com.chabak.vo.*;
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
    @Autowired
    MemberService memberService;

    @RequestMapping(value ={"", "/", "/list"}, method=RequestMethod.GET)
    public ModelAndView messageList(HttpSession session,HttpServletResponse response,@RequestParam (defaultValue = "receive") String messageBox){

        ModelAndView mv = new ModelAndView();

        //세션에서 로그인한 아이디 가져와 설정.없으면 로그인 페이지로(return: id or null)
        String id = Utility.getIdForSessionOrMoveIndex(mv,session,response);

        //messageBox값(send||receive)에 따라서 보낸메시지함||받은메시지함 메시지 리스트 출력
        List<Message> messageList = messageService.selectMessageList(id,messageBox,null);

        //각 메시지함의 메시지 수 출력
        int sendCount = messageService.countMessageList(id,"send",null);
        int receiveCount = messageService.countMessageList(id,"receive",null);
        int toMeCount = messageService.countMessageList(id,"toMe",null);
        Member member = memberService.getMember(id);

        mv.addObject("member",member);
        mv.addObject("messageList",messageList);
        mv.addObject("messageBox",messageBox);
        mv.addObject("sendCount",sendCount);
        mv.addObject("receiveCount",receiveCount);
        mv.addObject("toMeCount",toMeCount);
        mv.setViewName("message/message");

        return mv;
    }

    @RequestMapping(value ={"/write"}, method=RequestMethod.GET)
    public ModelAndView writeForm(@RequestParam (required = false) String receiveId,HttpSession session,HttpServletResponse response){

        ModelAndView mv = new ModelAndView();
        //세션에서 로그인한 아이디 가져와 설정.없으면 로그인 페이지로(return: id or null)
        Utility.getIdForSessionOrMoveIndex(mv,session,response);

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
        //부모창 새로고침
        Utility.parentReload(response);
        //새창 닫기
        Utility.closeWindow(response);
        return null;
    } //리뷰 리스트 출력

    @RequestMapping(value ="/detail", method=RequestMethod.GET)
    public ModelAndView detailMessage(@RequestParam (defaultValue = "-1") int messageNo,
                                      @RequestParam (defaultValue = "-1") String messageBox,
                                      HttpSession session,HttpServletResponse response){
        ModelAndView mv = new ModelAndView();

        //세션에서 로그인한 아이디 가져와 설정(return: id or null)
        String id = Utility.getIdForSessionOrMoveIndex(mv,session,response);

        //메시지 선택
        Message message = messageService.selectMessageDetail(messageNo);

        //권한 체크용 변수
        boolean authorityYn;
        if(messageBox.equals("send")){
            authorityYn = id.equals(message.getSendId());
        }
        else if(messageBox.equals("receive")){ //messageBox 값이 receive
            authorityYn = id.equals(message.getReceiveId());
        }
        else if(messageBox.equals("toMe")){ //messageBox 값이 toMe
            authorityYn = id.equals(message.getReceiveId());
        }
        else{  //그 외의 경우
            authorityYn = false;
        }

        //해당 메시지가 존재하지 않거나 messageBox 값이 들어오지 않으면
        if(message == null || !authorityYn){
            Utility.printAlertMessage("잘못된 접근입니다.",null,response);
            return null;
        }

        //메시지 읽음 여부 y로 업데이트
        try{
            //받은 메시지함이고 이전에 해당 메시지를 읽은 적 없으면 
            if(messageBox.equals("receive") && message.getReadYn().equals("n")){
                //메시지를 읽음 상태로 변경
                messageService.updateReadYn(messageNo);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Utility.printAlertMessage("작업 중 에러가 발생했습니다.",null,response);
            return null;
        }

        mv.addObject("message",message);
        mv.addObject("messageBox",messageBox);
        mv.setViewName("message/message_detail");
        return mv;
    }

    @RequestMapping(value ="/delete", method=RequestMethod.GET)
    public ModelAndView deleteReview(@RequestParam (defaultValue = "-1") int messageNo,
                                     @RequestParam (defaultValue = "-1") String messageBox,
                                     HttpSession session,HttpServletResponse response){
        ModelAndView mv = new ModelAndView();

        //region checkLogin(세션에서 로그인한 아이디 가져와 설정+비로그인시 로그인 페이지로 이동(return: id or null))
        String id = Utility.getIdForSessionOrMoveIndex(mv,session,response);
        //endregion

        //삭제 권한 체크
        try{
            //messageNo로 선택한 메시지 1개 불러옴
            Message message = messageService.selectMessageDetail(messageNo);

            //해당 메시지가 존재하지 않거나 messageBox 값이 들어오지 않으면
            if(message == null || messageBox.equals("-1")){
                Utility.printAlertMessage("잘못된 접근입니다.",null,response);
                return null;
            }

            //권한 체크용 변수
            boolean authorityYn;
            if(messageBox.equals("send")){
                authorityYn = id.equals(message.getSendId());
            }
            else if(messageBox.equals("receive")){ //messageBox 값이 receive
                authorityYn = id.equals(message.getReceiveId());
            }
            else if(messageBox.equals("toMe")){ //messageBox 값이 receive
                authorityYn = id.equals(message.getSendId());
            }            
            else{  //messageBox 값이 receive/send/toMe 전부 아니면 잘못된 접근
                Utility.printAlertMessage("잘못된 접근입니다.",null,response);
                return null;
            }
           
            System.out.println("authorityYn:"+authorityYn);
            //권한 없으면
            if(!authorityYn){
                Utility.printAlertMessage("권한이 없습니다.",null,response);
                return null;
            }
            else{
                //메시지 삭제
                messageService.deleteMessage(messageBox,messageNo);

            }
        } //메시지 받은 아이디가 없으면
        catch (NullPointerException e){
            Utility.printAlertMessage("잘못된 접근입니다.",null,response);
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
            Utility.printAlertMessage("작업 중 에러가 발생했습니다.",null,response);
            throw e; //트랜젝션을 위해 예외 던짐
        }
        mv.addObject("messageBox",messageBox);
        mv.setViewName("redirect:/message/list");
        return mv;
    }
}