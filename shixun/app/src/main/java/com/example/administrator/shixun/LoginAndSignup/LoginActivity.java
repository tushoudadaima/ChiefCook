package com.example.administrator.shixun.LoginAndSignup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.shixun.MainActivity;
import com.example.administrator.shixun.MyApplication;
import com.example.administrator.shixun.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class LoginActivity extends AppCompatActivity {
    private EditText et_phone;
    private EditText et_password;
    private Button btn_rigestered;
    private Button btn_signin;
    private TextView btn_forget;
    private RadioButton buyer_res;
    private RadioButton seller_res;
    private CustomeOnClickListener listener;
    private Handler handler = new Handler();
    private int radio =1;
    private String wang_zhi ;
    private int a =0;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        wang_zhi=this.getString(R.string.wang_zhi);

        getViews();
        registListener();

        handler = new Handler(){
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        Toast.makeText(getApplicationContext(),"验证码发送成功",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(),"验证码发送失败，请重新尝试",Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getApplicationContext(),"验证码错误 验证失败",Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        if(a==1){
                            Toast.makeText(getApplicationContext(),"该用户未注册",Toast.LENGTH_SHORT).show();
                        }else if(a==2){
                            if(radio == 1) {
                                BuyerLogin();
                            }else {
                                SellerLogin();
                            }
                        }
                        break;
                    case 8:
                        Toast.makeText(getApplicationContext(),"账号或密码不正确",Toast.LENGTH_SHORT).show();
                        break;
                    case 9:
                        MyApplication myApplication = (MyApplication) getApplication();
                        String phone = et_phone.getText().toString().trim();
                        myApplication.setPhone(phone);

                        Intent intent = new Intent();
                        intent.putExtra("index","fs");
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),"登录成功 欢迎您 "+phone,Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"错误",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    private void registListener() {
        listener = new CustomeOnClickListener();
        btn_signin.setOnClickListener(listener);
        btn_rigestered.setOnClickListener(listener);
        btn_forget.setOnClickListener(listener);
        buyer_res.setOnClickListener(listener);
        seller_res.setOnClickListener(listener);
    }

    private void getViews() {
        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        btn_rigestered = findViewById(R.id.btn_registered);
        btn_signin = findViewById(R.id.btn_signin);
        btn_forget = findViewById(R.id.btn_forget);
        buyer_res = findViewById(R.id.buyer_res);
        seller_res = findViewById(R.id.seller_res);
    }

    private class CustomeOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_signin://点击登录
                    String phone = et_phone.getText().toString().trim();
                    String password = et_password.getText().toString().trim();
                    if(phone.equals("")){
                        Toast.makeText(getApplicationContext(),"手机号不能为空",Toast.LENGTH_SHORT).show();
                    }else if(password.equals("")){
                        Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
                    }else  if(radio==1){
                        checkBuyerLogin();//检查该买家有无注册
                    }else if(radio == 2){
                        checkSellerLogin();//检查该卖家有无注册
                    }
                    break;
                case R.id.btn_registered://点击注册按钮
                    Intent intent1 = new Intent();
                    intent1.setClass(LoginActivity.this, SignUp.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("radioStatus",radio);
                    intent1.putExtras(bundle1);
                    startActivity(intent1);
                    break;
                case R.id.btn_forget:
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, ForgetPassword.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("radioStatus",radio);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case R.id.buyer_res://默认选择买家注册
                    radio = 1;
                    break;
                case R.id.seller_res://卖家注册
                    radio = 2;
                    break;
            }
        }
    }
    private void BuyerLogin() {
        new Thread(){
            public void run(){
                try {
                    String phone = et_phone.getText().toString().trim();
                    String password = et_password.getText().toString().trim();
                    URL url = new URL(wang_zhi+"BuyerLoginServlet?buyerId=" + phone+"&buyerPassword="+password);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info=reader.readLine();
                    if(info!=null) {
                        Message msg = Message.obtain();
                        msg.what=9;//登录成功
                        handler.sendMessage(msg);
                    }else{
                        Message msg = Message.obtain();
                        msg.what=8;//账号密码错误
                        handler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void SellerLogin() {
        new Thread(){
            public void run(){
                try {
                    String phone = et_phone.getText().toString().trim();
                    String password = et_password.getText().toString().trim();;
                    URL url = new URL(wang_zhi+"SellerLoginServlet?sellerId=" + phone+"&sellerPassword="+password);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info=reader.readLine();
                    if(info!=null) {
                        Message msg = Message.obtain();
                        msg.what=9;//登录成功
                        handler.sendMessage(msg);
                    }else{
                        Message msg = Message.obtain();
                        msg.what=8;//账号或密码不正确
                        handler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void checkBuyerLogin() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String phone = et_phone.getText().toString().trim();
                    URL url = new URL(wang_zhi+"CheckBuyerServlet?buyerId=" + phone);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String str="";
                    String info=reader.readLine();
                    while(info!=null) {
                        str = info;
                        info=reader.readLine();
                    }
                    if(str ==""){
                        a = 1;
                    }else{
                        a = 2;
                    }
                    Message msg = Message.obtain();
                    msg.what=7;//提示未注册或者调用详细买家卖家登录函数 BuyerLogin();/SellerLogin();
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void checkSellerLogin() {
        new Thread() {
            @Override
            public void run() {
                try {
                    String phone = et_phone.getText().toString().trim();
                    URL url = new URL(wang_zhi+"CheckSellerServlet?sellerId=" + phone);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String str="";
                    String info=reader.readLine();
                    while(info!=null) {
                        str = info;
                        info=reader.readLine();
                    }
                    if(str ==""){
                        a = 1;
                    }else{
                        a = 2;
                    }
                    Message msg = Message.obtain();
                    msg.what=7;//提示未注册或者买家/卖家登录
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}

