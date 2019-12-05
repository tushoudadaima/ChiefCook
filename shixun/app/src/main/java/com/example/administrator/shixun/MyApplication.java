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


}
