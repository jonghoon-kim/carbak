package com.chabak.vo;

public class Blog {

    private String title;       //블로그 제목
    private String link;        //블로그 link
    private String bloggername; //블로거 이름
    private String bloggerlink; //블로거 link
    private String description; //블로그 요약 내용
    private String total;       //검색된 검색 결과 개수
    private String postdate;    //블로그 포스트를 작성한 날짜

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

    public String getBloggername() {
        return bloggername;
    }

    public void setBloggername(String bloggername) {
        this.bloggername = bloggername;
    }

    public String getBloggerlink() {
        return bloggerlink;
    }

    public void setBloggerlink(String bloggerlink) {
        this.bloggerlink = bloggerlink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPostdate() {
        return postdate;
    }

    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }

    @Override
    public String toString() {
        return "Blog [title=" + title + ", link=" + link + ", bloggername=" + bloggername + ", bloggerlink=" + bloggerlink + ", description="
                + description + ", total=" + total + ", postdate=" + postdate + "]";
    }

}
