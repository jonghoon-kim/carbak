package com.chabak.services;

import com.chabak.vo.Follow;


import java.util.List;

public interface FollowService {

   List<Follow> selectDataHashMapServiceList(String id) throws Exception;

//    public List<Follow> getFollower(Follow follow); 수정전 코드
   List<Follow> followingIdAndProfile(String id) throws Exception;

   List<Follow> followerIdAndProfile(String id) throws Exception;

   int deleteFollowUser(String id, String followUserId) throws Exception;

   int deleteFollowerUser(String id, String followUserId) throws Exception;

   int followAddUser(String id, String followUserId) throws Exception;
}
