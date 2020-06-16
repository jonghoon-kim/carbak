package com.chabak.vo;

import lombok.Data;

@Data
public class TitleImg {
    private String titleImageSrc;

    //region singleton
    private TitleImg() {
    }

    private static TitleImg _instance;

    public static TitleImg getInstance(){
        if (_instance == null)
            _instance = new TitleImg();

        return _instance;
    }

    //endregion
}
