package com.chabak.services;

import com.chabak.mapper.AdminMapper;
import com.chabak.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("adminService")
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    /* 관리자 회원 조회 */
    public List<Member> getlstSelectAdminMemberlist(String startPageNo, String endPageNo){
        return adminMapper.getlstSelectAdminMemberlist(startPageNo, endPageNo);
    }

    //admin pagingcount
    public int adminCnt() {
        return adminMapper.adminCnt();
    }

    public void memberDelete(String deleteId) {
        adminMapper.memberDelete(deleteId);
    }
}
