package com.example.administrator.shixun.Entity;

public class Discuss {
    private String img_user;
    private String user;
    private String date;
    private String content;

    public String getImg_user() {
        return img_user;
    }

    public void setImg_user(String img_user) {
        this.img_user = img_user;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Discuss(String img_user, String user, String date, String content) {
        this.img_user = img_user;
        this.user = user;
        this.date = date;
        this.content = content;
    }

    public Discuss(){

    }
}
