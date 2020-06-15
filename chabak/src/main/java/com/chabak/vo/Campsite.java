package com.chabak.vo;

import java.util.List;

public class Campsite {
    private String campsitename;			//야영지 이름
    private String sido;            		//도,시
    private String gugun;                   //구,군

    public String getCampsitename() {
        return campsitename;
    }

    public void setCampsitename(String campsitename) {
        this.campsitename = campsitename;
    }

    public String getSido() {
        return sido;
    }

    public void setSido(String sido) {
        this.sido = sido;
    }

    public String getGugun() {
        return gugun;
    }

    public void setGugun(String gugun) {
        this.gugun = gugun;
    }
}
