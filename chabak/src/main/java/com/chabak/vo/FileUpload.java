package com.chabak.vo;

import lombok.Data;

@Data
public class FileUpload {
    private int fileNo;
    private int uploadKey;
    private String originalName;
    private String saveName;

}
