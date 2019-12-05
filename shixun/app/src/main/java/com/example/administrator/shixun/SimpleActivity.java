package com.example.administrator.shixun;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class SimpleActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView t;
    private String astr;
    private Handler handler;
    private String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        imageView = findViewById(R.id.img_big);
        t = findViewById(R.id.test);
        Intent intent = getIntent();
        astr = intent.getStringExtra("name");
        showAllMsg();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 100:
                        str = (String) msg.obj;
                        t.setText(str);
//                        Glide.with(SimpleActivity.this).load(str).into(imageView);
                        break;
                }
            }
        };

    }

    private void showAllMsg() {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://10.7.89.30:8080/OurProject/image?food="+astr);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,"GB2312"));
                    String info1 = "";
                    while((info1 = reader.readLine()) != null){
                        wrapMessage1(info1);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void wrapMessage1(String info1) {
        Message message = Message.obtain();
        message.obj = info1;
        message.what = 100;
        handler.sendMessage(message);
    }
}
