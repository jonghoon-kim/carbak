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
//    @Autowired
//    MemberDao memberDao;

    @Override
    public List<Follow> selectDataHashMapServiceList(String id) throws Exception {
        return followDao.selectDataHashMapServiceList(id);
    }

    @Override
    public List<Follow> followingIdAndProfile(String id) throws Exception {
        return followDao.followingIdAndProfile(id);
    }

    @Override
    public List<Follow> followerIdAndProfile(String id) throws Exception {
        return followDao.followerIdAndProfile(id);
    }

    @Override
    public int deleteFollowingUser(String id, String followingUser) throws Exception {
        System.out.println("here is services -- ");
        return followDao.deleteFollowingUser(id, followingUser);
    }


    //    //팔로워 정보 갖고오는 매서드    <- 수정전 코드
//    @Override
//    public List<Follow> getFollower(Follow follow) {
//        return followDao.getFollower(follow);
//    }
}
