package com.chabak.services;

import com.chabak.repositories.MemberDao;
import com.chabak.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    MemberDao memberDao;


    public int insert(String id){
        return memberDao.insertMember(id);

    }
}
