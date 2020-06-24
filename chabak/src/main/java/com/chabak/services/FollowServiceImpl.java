package com.chabak.services;

import com.chabak.repositories.FollowDao;
import com.chabak.repositories.MemberDao;
import com.chabak.vo.Follow;
import com.chabak.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    FollowDao followDao;
    @Autowired
    MemberDao memberDao;

    @Override
    public List<Follow> selectDataHashMapServiceList(String id) throws Exception {
        return followDao.selectDataHashMapServiceList(id);
    }

    @Override
    public List<Follow> followingIdAndProfileTest(String id) throws Exception {

        System.out.println("----------------------------");
        return followDao.followingIdAndProfileTest(id);
    }

    //    //팔로워 정보 갖고오는 매서드    <- 수정전 코드
//    @Override
//    public List<Follow> getFollower(Follow follow) {
//        return followDao.getFollower(follow);
//    }
}
