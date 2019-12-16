package com.example.administrator.shixun.Entity;


public class Typev {
    private String pic;
    private String videos;
    private String name;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Typev() {
    }

    public Typev(String pic, String videos, String name) {
        this.pic = pic;
        this.videos = videos;
        this.name = name;
    }
}
