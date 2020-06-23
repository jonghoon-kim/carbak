package com.chabak.vo;

import lombok.Data;

import java.sql.Date;
@Data
public class Member {
    private String id;
    private String password;
    private String name;
    private String gender;
    private String sido;
    private String gugun;
    private String email;
    private String saveName;
    private Date regDate;
}
