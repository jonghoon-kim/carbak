package com.chabak.repositories;

import com.chabak.vo.Follow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 프로필 수정(사진 수정 + 개인정보 창 링크 이동)

    // 게시글 갖고오기 + 뿌리기


    @Repository("FollowDao")
    public class FollowDao {
        @Autowired
        SqlSession sqlSession;

        // 팔로워 정보 갖고오기 + 뿌리기  <- 수정전 코드
//        public List<Follow> getFollower(Follow follow){
//            // follow.getFllower는 follow.xml에 있는 매서드
//            return sqlSession.selectList("follow.getFollower", follow);
//        }

        // 팔로워 정보 갖고오기 + 뿌리기
        public List<Follow> selectDataHashMapServiceList(String id) throws Exception {
            return sqlSession.selectList("follow.selectDataHashMapServiceList", id);
        }

        public List<Follow> followingIdAndProfile(String id) throws Exception {

            return sqlSession.selectList("followingIdAndProfile", id);
        }

        public List<Follow> followerIdAndProfile(String id) throws Exception {
            return sqlSession.selectList("followerIdAndProfile", id);
        }

        public int deleteFollowingUser(String id, String followUserId)  throws Exception {
            Map<String , String> map = new HashMap<String, String>();
            map.put("id" , id);
            map.put("followUserId", followUserId);
            System.out.println("map test followDao:" + map);
            return sqlSession.delete("deleteFollowingUser", map);
        }

        public int deleteFollowerUser(String id, String followerUserId)  throws Exception {
            Map<String , String> map = new HashMap<String, String>();
            map.put("id" , id);
            map.put("followerUserId", followerUserId);
            System.out.println("map test followerDao:" + map);
            return sqlSession.delete("deleteFollowerUser", map);
        }

        public int followAddUser(String id, String followUserId)  throws Exception {
            Map<String , String> map = new HashMap<String, String>();
            map.put("id" , id);
            map.put("followUserId", followUserId);
            System.out.println("map test :" + map);
            return sqlSession.insert("addUserFollow", map);
        }


        /*
        @Mapper
        public int deleteFollowingUser(@Param("deleteId")String id, @Param("followingUser")String followingUser)  throws Exception {
//            HashMap map = new HashMap();
//            map.put("id", id);
//            map.put("followingUser", followingUser);
            System.out.println("here is dao");
            return sqlSession.delete("deleteFollowingUser");
        }
*/


        // 팔로잉 정보 갖고오기 + 뿌리기
}




