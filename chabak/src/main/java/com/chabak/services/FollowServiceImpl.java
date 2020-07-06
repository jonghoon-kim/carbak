package com.chabak.services;

import com.chabak.repositories.FollowDao;
import com.chabak.repositories.MemberDao;
import com.chabak.vo.Follow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    FollowDao followDao;

    @Override
    public List<Follow> followingIdAndProfile(String id) throws Exception {

        return followDao.followingIdAndProfile(id);
    }

    @Override
    public List<Follow> followerIdAndProfile(String id) throws Exception {
        System.out.println("service : "+ followDao.followerIdAndProfile(id));
        return followDao.followerIdAndProfile(id);
    }

    @Override
    public int deleteFollowingUser(String id, String followUserId) throws Exception {
        System.out.println("here is services -- ");
        return followDao.deleteFollowingUser(id, followUserId);
    }

    @Override
    public int deleteFollowerUser(String id, String followUserId) throws Exception {
        System.out.println("here is services -- ");
        return followDao.deleteFollowerUser(id, followUserId);
    }

    @Override
    public int followAddUser(String id, String selectedUserId) throws Exception {
        System.out.println("followAddUser here is services -- ");
        return followDao.followAddUser(id, selectedUserId);
    }

    @Override
    public int followDeleteUser(String id, String selectedUserId) throws Exception {
        System.out.println("followDeleteUser here is services -- ");
        return followDao.followDeleteUser(id, selectedUserId);
    }

    @Override
    public String decisionFollowStatus(String sessionId, String userId) throws Exception {
        System.out.println(" decisionFollowStatus here is services -- ");
        return followDao.decisionFollowStatus(sessionId, userId);
    }
}
