package com.example.lenovo.smstest;

import android.app.Application;

public class MyApplication extends Application {
    private String wangzhi;

    public String getWangzhi() {
        return wangzhi;
    }

    public void setWangzhi(String wangzhi) {
        this.wangzhi = wangzhi;
    }

    @Override
    public void onCreate() {
        //wangzhi = "http://10.7.89.36:8080/canMouZhangSignTest/";//学院网
        wangzhi=this.getString(R.string.wang_zhi);
        super.onCreate();
    }
}
