package com.chabak.repositories;


import com.chabak.vo.Message;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("messageDao")
public class MessageDao {
    @Autowired
    SqlSession sqlSession;

    public List<Message> selectMessageList(Map map){
        List<Message> messageList = sqlSession.selectList("message.selectMessageList",map);
        return messageList;
    }

    public Message selectMessageDetail(int messageNo){
        Message message = sqlSession.selectOne("message.selectMessageDetail",messageNo);
        return message;
    }

    public int insertMessage(Message message){
        int insertCount = sqlSession.insert("message.insertMessage",message);
        return insertCount;
    }

    public int deleteMessage(int messageNo){
        int deleteCount = sqlSession.delete("message.deleteMessage",messageNo);
        return deleteCount;
    }


}
