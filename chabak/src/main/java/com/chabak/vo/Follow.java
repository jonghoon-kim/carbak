package com.chabak.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Data
public class Follow {
    private String followingId;
    private String followerId;
}
