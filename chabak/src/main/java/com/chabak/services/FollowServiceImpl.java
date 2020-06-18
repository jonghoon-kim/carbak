package com.chabak.services;

import com.chabak.repositories.FollowDao;
import com.chabak.vo.Follow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    FollowDao followDao;

    @Override
    public List<Follow> getFollower(Follow follow) {
        return followDao.getFollower(follow);
    }
}
