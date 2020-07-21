package com.chabak.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class SmartEditorVo {
    private MultipartFile filedata;
    private String callback;
    private String callback_func;
}
