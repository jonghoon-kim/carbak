package com.chabak.services;

import com.chabak.repositories.MemberDao;
import com.chabak.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    @Autowired
    MemberDao memberDao;

    public int insert(Member member){
        return memberDao.insertMember(member);
    }

    public Member idCheck(String id) throws Exception {
        return memberDao.idCheck(id);
    }

    /* 이메일 중복 확인*/
    public Member emailCheck(String email) throws Exception {
        return memberDao.emailCheck(email);
    }
}
