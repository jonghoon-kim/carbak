package com.chabak.services;

import com.chabak.vo.Follow;


import java.util.List;

public interface FollowService {
   List<Follow> followingIdAndProfile(String id) throws Exception;

   List<Follow> followerIdAndProfile(String id) throws Exception;

   int deleteFollowingUser(String id, String followUserId) throws Exception;

   int deleteFollowerUser(String id, String followUserId) throws Exception;

   int clickFollowBtn(String id, String selectedUserId) throws Exception;

   int clickFollowingBtn(String id, String selectedUserId) throws Exception;

   String btnFollowStatus(String sessionId, String userId) throws Exception;
}
