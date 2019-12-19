package com.example.administrator.shixun.Entity;

import java.io.Serializable;

public class Methods implements Serializable{
    private String img;
    private String step;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public Methods(String img, String step) {
        this.img = img;
        this.step = step;
    }
}
