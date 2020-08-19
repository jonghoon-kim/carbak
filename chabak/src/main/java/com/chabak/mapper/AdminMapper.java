package com.chabak.mapper;

import com.chabak.vo.Member;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMapper {

    List<Member> getlstSelectAdminMemberlist(@Param("startPageNo")String startPageNo, @Param("endPageNo")String endPageNo);

    //admin pagingcount
    int adminCnt();

    //member delete
    void  memberDelete(@Param("deleteId")String deleteId);
}
