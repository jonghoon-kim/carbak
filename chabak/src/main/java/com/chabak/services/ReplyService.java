package com.chabak.services;


import com.chabak.repositories.ReplyDao;
import com.chabak.repositories.ReviewDao;
import com.chabak.vo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ReplyService {

    @Autowired
    ReplyDao replyDao;
    @Autowired
    ReviewDao reviewDao;

    //댓글

    //댓글 1개 select

    public Reply selectReply(int replyNo){
        Reply reply = replyDao.selectReply(replyNo);
        return reply;
    }

    @Transactional
    public boolean insertReply(Reply reply) {
        //reply insert
        int count1 = replyDao.insertReply(reply);
        //리뷰 댓글 수 증가
        int count2 = reviewDao.increaseReplyCount(reply.getReviewNo());
        //insert,update 중 하나라도 잘못되면 false
        return (count1 == 1 && count2 == 1);
    }

    public List<Reply> selectReplyList(int reviewNo) {
        List<Reply> replyList = null;
        replyList = replyDao.selectReplyList(reviewNo);
        return replyList;
    }

    public int countChildReply(Reply reply){
        int count = replyDao.countChildReply(reply);
        return count;
    }

    //대댓글
    @Transactional
    public boolean insertReReply(Reply reply) {
        int count1,count2; //쿼리 결과 행수 저장할 변수
        //1번 쿼리: 대댓글 들어갈 위치 조건 결정(댓글 사이에 끼워넣을지,끝에 넣을지)
        int condition = replyDao.checkReReplyCondition(reply);
        int groupOrder = 0;
        if(condition==0){
            //2번 쿼리
            groupOrder = replyDao.selectGroupOrder(reply);
            //groupOrder 값을 2번 쿼리값으로 설정정
            reply.setGroupOrder(groupOrder);
            //lv 값을 부모 lv+1로 설정
            reply.setLv(reply.getLv()+1);
        }
        else {//1번 쿼리값이 0 이 아니면

            //roupOrder 값을 1번 쿼리값으로 설정
            groupOrder = condition;
            reply.setGroupOrder(groupOrder);

            //들어갈 댓글 아래의 groupOrder 1씩 증가
            replyDao.updateGroupOrder(reply);

            reply.setLv(reply.getLv()+1);
        }
        //대댓글 insert
        count1 = replyDao.insertReReply(reply);

        //리뷰 댓글 수 증가
        count2 = reviewDao.increaseReplyCount(reply.getReviewNo());
        return (count1==1 && count2==1);
    }

    public boolean updateReply(Reply reply) {
        int updateCount = replyDao.updateReply(reply);
        return updateCount==1;
    }

    @Transactional
    public boolean deleteReplyWithReplyNo(int replyNo,int reviewNo){
        int count1 = replyDao.deleteReplyWithReplyNo(replyNo);
        //리뷰 댓글 수 감소
        int count2 = reviewDao.decreaseReplyCount(reviewNo);
        return (count1==1 && count2==1);
    }

}