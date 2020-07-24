package com.chabak.services;

import com.chabak.repositories.MessageDao;
import com.chabak.vo.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {
    @Autowired
    MessageDao messageDao;

    /**파라미터<br>1.sessionId:로그인한 유저 id,<br>2.messageBox: send(출력할 메시지가 보낸메시지함일 경우),
     *<br>receive 또는 그외(받은 메시지함일 경우)**/
    public List<Message> selectMessageList(String sessionId,String messageBox){

        //출력할 메시지가 보낸메시지함인지 받은메시지함인지에 따라 map에 파라미터 설정
        Map map = new HashMap<String,String>();
        if(messageBox.equals("send")){
            map.put("sendId",sessionId);
            map.put("columnName","sendBoxDeletedYn");
        }
        else{
            map.put("receiveId",sessionId);
            map.put("columnName","receiveBoxDeletedYn");
        }

        List<Message> messageList = messageDao.selectMessageList(map);
        return messageList;
    }

    public Message selectMessageDetail(int messageNo){
        Message message = messageDao.selectMessageDetail(messageNo);
        return message;
    }

    public int insertMessage(Message message){
        int insertCount = messageDao.insertMessage(message);
        return insertCount;
    }

    /**파라미터 :<br>String messageBox(view 에서 보낸 messageBox 값 : "send" 또는 그 외의 값),<br>int messageNo**/
    public int updateBoxDeletedYn(String messageBox,int messageNo){
        Map map = new HashMap<String,String>();

        //삭제 처리(flag 수정)할 칼럼 이름을 map에 넣기
        if(messageBox.equals("send")){
            map.put("columnName","sendBoxDeletedYn");
        }
        else{
            map.put("columnName","receiveBoxDeletedYn");
        }

        map.put("messageNo",messageNo);
        int updateCount = messageDao.updateBoxDeletedYn(map);
        return updateCount;
    }
    
    public int updateReadYn(int messageNo){
        int updateCount = messageDao.updateReadYn(messageNo);
        return updateCount;
    }


    public int deleteMessage(int messageNo){
        int deleteCount = messageDao.deleteMessage(messageNo);
        return deleteCount;
    }
}