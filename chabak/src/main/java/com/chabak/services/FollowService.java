package com.chabak.services;

import com.chabak.vo.Follow;


import java.util.List;

public interface FollowService {
   List<Follow> followingIdAndProfile(String id) throws Exception;

   List<Follow> followerIdAndProfile(String id) throws Exception;

   int deleteFollowingUser(String id, String followUserId) throws Exception;

   int deleteFollowerUser(String id, String followUserId) throws Exception;

   int followAddUser(String id, String selectedUserId) throws Exception;

   int followDeleteUser(String id, String selectedUserId) throws Exception;

   String decisionFollowStatus(String sessionId, String userId) throws Exception;
}
