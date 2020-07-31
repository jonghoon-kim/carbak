package com.chabak.vo;

import lombok.Data;

@Data
public class ReadCount {
    private String id;
    private int reviewNo;
    private int readCount;

    public ReadCount(String id,int reviewNo){
        this.id = id;
        this.reviewNo = reviewNo;
    }
    public ReadCount(){

    }
}
