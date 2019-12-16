package com.example.administrator.shixun;

import android.app.Application;

public class MyApplication extends Application {

    private static MyApplication mInstance;

    public static MyApplication getmInstance() {
        if(mInstance == null){
            mInstance = new MyApplication();
        }
        return mInstance;
    }

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
