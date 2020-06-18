package com.chabak.services;

import com.chabak.vo.Follow;

import java.util.List;

public interface FollowService {

    public List<Follow> getFollower(Follow follow);

}
