package com.chabak.services;

import com.chabak.repositories.FollowDao;
import com.chabak.vo.Follow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("followService")
public class FollowServiceImpl implements FollowService {
    @Autowired
    FollowDao followDao;

    @Override
    public List<Follow> followingIdAndProfile(String id) throws Exception {
        return followDao.followingIdAndProfile(id);
    }

    @Override
    public List<Follow> followerIdAndProfile(String id) throws Exception {
        return followDao.followerIdAndProfile(id);
    }

    @Override
    public int deleteFollowingUser(String id, String followUserId) throws Exception {
        return followDao.deleteFollowingUser(id, followUserId);
    }

    @Override
    public int deleteFollowerUser(String id, String followUserId) throws Exception {
        return followDao.deleteFollowerUser(id, followUserId);
    }

    @Override
    public int clickFollowBtn(String id, String selectedUserId) throws Exception {
        return followDao.clickFollowBtn(id, selectedUserId);
    }

    @Override
    public int clickFollowingBtn(String id, String selectedUserId) throws Exception {
        return followDao.clickFollowingBtn(id, selectedUserId);
    }

    @Override
    public String btnFollowStatus(String sessionId, String userId) throws Exception {
        return followDao.btnFollowStatus(sessionId, userId);
    }

    @Override
    public int countFollower(String pageOwnerId) throws Exception {
        return followDao.countFollower(pageOwnerId);
    }

    @Override
    public int countFollowing(String pageOwnerId) throws Exception {
        return followDao.countFollowing(pageOwnerId);
    }
}
