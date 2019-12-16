package com.example.administrator.shixun.Entity;

public class Show {
    private String name;
    private String description;
    private String imgUrl;
    private String eye;
    private String zan;

    public String getEye() {
        return eye;
    }

    public void setEye(String eye) {
        this.eye = eye;
    }

    public String getZan() {
        return zan;
    }

    public void setZan(String zan) {
        this.zan = zan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Show(String name, String description, String imgUrl,String eye,String zan) {
        this.name = name;
        this.description = description;
        this.imgUrl = imgUrl;
        this.eye = eye;
        this.zan = zan;
    }
}
