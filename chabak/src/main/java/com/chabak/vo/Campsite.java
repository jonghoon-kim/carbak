package com.chabak.vo;

import java.util.List;

public class Campsite {
    private String campsitename;			//야영지 이름
    private String category;                //카테고리
    private String latitude;                //위도
    private String longitude;               //경도
    private String sido;            		//도,시
    private String gugun;                   //구,군
    private String address;                 //상세 주소
    private String convenience1;            //편의 시설
    private String convenience2;            //부가 시설
    private int dNUM;				        //페이지 컬럼 수

    public int getdNUM() {
        return dNUM;
    }
    public void setdNUM(int dNUM) {
        this.dNUM = dNUM;
    }

    public String getCampsitename() {
        return campsitename;
    }

    public void setCampsitename(String campsitename) {
        this.campsitename = campsitename;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getConvenience1() {
        return convenience1;
    }

    public void setConvenience1(String convenience1) {
        this.convenience1 = convenience1;
    }

    public String getConvenience2() {
        return convenience2;
    }

    public void setConvenience2(String convenience2) {
        this.convenience2 = convenience2;
    }
}
