package com.example.lenovo.smstest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ForgetPassword extends AppCompatActivity {
    private EditText et_phone;
    private EditText et_code;
    private EditText et_password;
    private EditText et_repassword;
    private Button btn_getCode;
    private Button btn_update_password;
    private CustomeOnClickListener listener;
    public int T = 60; //倒计时时长
    private Handler mHandler = new Handler();
    private Handler handler = new Handler();
    private String wang_zhi;
    private RadioButton buyer_forget;
    private RadioButton seller_forget;
    private int radio =1;
    private int a = 0;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        wang_zhi=this.getString(R.string.wang_zhi);

        getViews();
        registListener();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            radio = bundle.getInt("radioStatus");
        }

        if(radio == 1){
            buyer_forget.setChecked(true);
        }else if(radio == 2){
            seller_forget.setChecked(true);
        }

        handler = new Handler(){
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        Toast.makeText(getApplicationContext(),"验证码发送成功",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(),"验证码发送失败",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        //Toast.makeText(getApplicationContext(),"验证码验证成功 调用更新函数",Toast.LENGTH_SHORT).show();
                        if(radio==1){
                            updateBuyerPassword();
                        }else if(radio==2){
                            updateSellerPassword();
                        }
                        break;
                    case 4:
                        Toast.makeText(getApplicationContext(),"验证码验证失败",Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                       // Toast.makeText(getApplicationContext(),"验证码验证失败",Toast.LENGTH_SHORT).show();
                        if(a==1){
                            //Toast.makeText(getApplicationContext(),"买家无重复注册 调用注册函数 此时a="+a,Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(),"该账号还没有注册哦。。。",Toast.LENGTH_SHORT).show();
                        }else if(a==2){
                            updatePassword();
                        }
                        break;

                    default:
                        Toast.makeText(getApplicationContext(),"错误",Toast.LENGTH_SHORT).show();
                        break;
                }
            }

        };
    }



    private void updateBuyerPassword() {
        new Thread(){
            public void run(){
                try {
                    String phone = et_phone.getText().toString().trim();
                    String password = et_password.getText().toString().trim();;
                    URL url = new URL(wang_zhi+"UpdataBuyerServlet?buyerId=" + phone+"&buyerPassword="+password);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        Toast.makeText(getApplicationContext(),"密码修改成功",Toast.LENGTH_SHORT).show();
    }
    private void updateSellerPassword() {
        new Thread(){
            public void run(){
                try {
                    String phone = et_phone.getText().toString().trim();
                    String password = et_password.getText().toString().trim();;
                    URL url = new URL(wang_zhi+"UpdataSellerServlet?sellerId=" + phone+"&sellerPassword="+password);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        Toast.makeText(getApplicationContext(),"密码修改成功",Toast.LENGTH_SHORT).show();
    }

    private void registListener() {
        listener = new CustomeOnClickListener();
        btn_getCode.setOnClickListener(listener);
        btn_update_password.setOnClickListener(listener);
        buyer_forget.setOnClickListener(listener);
        seller_forget.setOnClickListener(listener);

    }

    private void getViews() {
        et_phone = findViewById(R.id.et_phone);
        et_code = findViewById(R.id.et_code);
        et_password = findViewById(R.id.et_password);
        et_repassword = findViewById(R.id.et_repassword);
        btn_getCode = findViewById(R.id.btn_getCode);
        btn_update_password = findViewById(R.id.btn_update_password);
        buyer_forget = findViewById(R.id.buyer_forget);
        seller_forget = findViewById(R.id.seller_forget);
    }

    private class CustomeOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_getCode:
                    getCode();
                    break;
                case R.id.btn_update_password://更新密码
                    if(radio==1){
                        checkBuyer();//检查买家有无重复注册
                    }else if(radio == 2){
                        checkSeller();//检查有无重复注册
                    }
                    break;
                case R.id.buyer_forget://默认选择买家注册
                    radio = 1;
                    break;
                case R.id.seller_forget://卖家注册
                    radio = 2;
                    break;
            }
        }
    }

    private void checkSeller() {
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
                    msg.what=5;//提示已经注册 否则调用大更新函数 updatePassword();
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void checkBuyer() {
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
                    msg.what=5;//提示已经注册 否则调用大更新函数 updatePassword();
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void updatePassword() {
        String phone = et_phone.getText().toString().trim();
        String code = et_code.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String repassword = et_repassword.getText().toString().trim();
        if(phone.equals("")){
            Toast.makeText(getApplicationContext(),"手机号不能为空", Toast.LENGTH_SHORT).show();
        }else if(phone.length()!=11){
            Toast.makeText(getApplicationContext(),"手机号格式不正确", Toast.LENGTH_SHORT).show();
        }
        else if(code.equals("")){
            Toast.makeText(getApplicationContext(),"验证码不能为空", Toast.LENGTH_SHORT).show();
        }else if(password.length()<6){
            Toast.makeText(getApplicationContext(),"密码长度不能小于6位", Toast.LENGTH_SHORT).show();
        }else if(password.equals(repassword)){
            submitCode("+86",phone,code);
        }else {
            Toast.makeText(getApplicationContext(),"两次密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
        }
    }
    private void getCode() {
        String phone = et_phone.getText().toString().trim();
        if(phone.equals("")){
            Toast.makeText(getApplicationContext(),"手机号不能为空", Toast.LENGTH_SHORT).show();
        }else if(phone.length()!=11){
            Toast.makeText(getApplicationContext(),"手机号格式不正确", Toast.LENGTH_SHORT).show();
        } else{
            new Thread(new MyCountDownTimer()).start();//开始执行
            btn_getCode.setBackgroundColor(Color.parseColor("#FAFFF0"));
            sendCode("+86",phone);
//            Toast.makeText(getApplicationContext(),"已向"+phone+"发送验证码", Toast.LENGTH_SHORT).show();
        }
    }
    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
    public void sendCode(String country, final String phone) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    Message msg = Message.obtain();
                    msg.what=1;//提示验证码发送成功
                    handler.sendMessage(msg);
                } else{
                    // TODO 处理错误的结果
                    Message msg = Message.obtain();
                    msg.what=2;//提示验证码发送失败
                    handler.sendMessage(msg);
                }
            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(country, phone);
    }
    // 提交验证码，其中的code表示验证码，如“1357”
    public void submitCode(String country, final String phone, String code) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证成功的结果
                    Message msg = Message.obtain();
                    msg.what=3;//验证码验证成功 调用具体更新password函数
                    handler.sendMessage(msg);
                } else{
                    // TODO 处理错误的结果
                    Message msg = Message.obtain();
                    msg.what=4;//验证码验证失败
                    handler.sendMessage(msg);

                }
            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code);
    }

    class MyCountDownTimer implements Runnable {
        @Override
        public void run() {
            //倒计时开始，循环
            while (T > 0) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        btn_getCode.setClickable(false);
                        btn_getCode.setText("获取验证码"+"("+T+")");
                    }
                });
                try {
                    Thread.sleep(1000); //强制线程休眠1秒，就是设置倒计时的间隔时间为1秒。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                T--;
            }
            //倒计时结束，也就是循环结束
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    btn_getCode.setClickable(true);
                    btn_getCode.setText("获取验证码");
                    btn_getCode.setBackgroundColor(Color.parseColor("#DCDCDC"));
                }
            });
            T = 60; //最后再恢复倒计时时长
        }
    }
}
