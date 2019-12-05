package com.example.administrator.shixun;

import android.app.Application;

public class MyApplication extends Application {
    private String phone="null";

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public void onCreate() {
//        phone = "a";
        super.onCreate();
    }

    public String getApplication() {
        return phone;
    }
}
