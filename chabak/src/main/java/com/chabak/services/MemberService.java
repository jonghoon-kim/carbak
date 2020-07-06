package com.chabak.services;

import com.chabak.repositories.MemberDao;
import com.chabak.vo.Member;
import com.chabak.vo.Review;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@Service
public class MemberService {
    @Autowired
    MemberDao memberDao;


    public int insert(String id){
        return memberDao.insertMember(id);

    }


}
