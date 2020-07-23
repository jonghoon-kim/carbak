package com.chabak.vo;

public class Image {

    private String title;
    private String link;
    private String thumbnail;
    private String sizeheight;
    private String sizewidth;
    private String total;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSizeheight() {
        return sizeheight;
    }

    public void setSizeheight(String sizeheight) {
        this.sizeheight = sizeheight;
    }

    public String getSizewidth() {
        return sizewidth;
    }

    public void setSizewidth(String sizewidth) {
        this.sizewidth = sizewidth;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    @Override
    public String toString() {
        return "Image [title=" + title + ", link=" + link + ", thumbnail=" + thumbnail + ", sizeheight=" + sizeheight + ", sizewidth="
                + sizewidth + ", total=" + total + "]";
    }
}
