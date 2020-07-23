package com.chabak.services;


import com.chabak.repositories.ReplyDao;
import com.chabak.vo.Reply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ReplyService {

    @Autowired
    ReplyDao replyDao;

    //댓글

    //댓글 1개 select

    public Reply selectReply(int replyNo){
        Reply reply = replyDao.selectReply(replyNo);
        return reply;
    }
    public int insertReply(Reply reply) {
        int insertCount = replyDao.insertReply(reply);
        return insertCount;
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
    public int insertReReply(Reply reply) {
        int insertCount = replyDao.insertReReply(reply);
        return insertCount;
    }

    public int checkReReplyCondition(Reply reply){
        int condition = replyDao.checkReReplyCondition(reply);
        return condition;
    }

    public int selectGroupOrder(Reply reply){
        int groupOrderCount = replyDao.selectGroupOrder(reply);
        return groupOrderCount;
    }

    public int updateGroupOrder(Reply reply){
        int updateCount = replyDao.updateGroupOrder(reply);
        return updateCount;
    }

    public int updateReply(Reply reply) {
        int updateCount = replyDao.updateReply(reply);
        return updateCount;
    }

    public int deleteReplyWithReviewNo(int reviewNo){
        int deleteCount = replyDao.deleteReplyWithReviewNo(reviewNo);
        return deleteCount;
    }

    public int deleteReplyWithReplyNo(int replyNo){
        int deleteCount = replyDao.deleteReplyWithReplyNo(replyNo);
        return deleteCount;
    }

}