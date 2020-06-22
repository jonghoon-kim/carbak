package com.chabak.repositories;

import com.chabak.vo.Reply;
import com.chabak.vo.Review;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("replyDao")
public class ReplyDao {
    @Autowired
    SqlSession sqlSession;

    //댓글
    public int insertReply(Reply reply) {
        int insertCount = sqlSession.insert("reply.insertReply",reply);
        return insertCount;
    }
    public List<Reply> selectReplyList(int reviewNo) {
        List<Reply> replyList = null;
        replyList = sqlSession.selectList("reply.selectReplyList",reviewNo);
        return replyList;
    }

    //대댓글
    public int insertReReply(Reply reply) {
        int insertCount = sqlSession.insert("reply.insertReReply",reply);
        return insertCount;
    }

    public int checkReReplyCondition(Reply reply){
        int condition = sqlSession.selectOne("reply.checkReReplyCondition",reply);
        return condition;
    }

    public int selectGroupOrder(Reply reply){
        int groupOrderCount = sqlSession.selectOne("reply.selectGroupOrder",reply);
        return groupOrderCount;
    }

    public int updateGroupOrder(Reply reply){
        int updateCount = sqlSession.update("reply.updateGroupOrder",reply);
        return updateCount;
    }

    public int updateReply(Reply reply) {
        int updateCount = sqlSession.update("reply.updateReply",reply);
        return updateCount;
    }

    public int deleteReply(int reviewNo){
        int deleteCount = sqlSession.delete("reply.deleteReply",reviewNo);
        return deleteCount;
    }
}
