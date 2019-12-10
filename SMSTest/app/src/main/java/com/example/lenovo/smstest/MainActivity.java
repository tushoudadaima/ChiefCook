package com.example.lenovo.smstest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Matrix;
import android.nfc.FormatException;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TooManyListenersException;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

import static com.mob.tools.utils.DeviceHelper.getApplication;

public class MainActivity extends AppCompatActivity  {
    private EditText et_phone;
    private EditText et_code;
    private EditText et_password;
    private EditText et_repassword;
    private Button btn_rigestered;
    private Button btn_signin;
//    private Button btn_getCode;
    private TextView btn_getCode;
    private Button btn_forget;
    private RadioButton buyer_res;
    private RadioButton seller_res;
    private CustomeOnClickListener listener;
    public int T = 60; //倒计时时长
    private Handler mHandler = new Handler();
    private Handler handler = new Handler();
    private int radio =1;
    private String wang_zhi ;
    private String wang_zhi2 ;
    private String wang_zhi3 ;
    private int a =0;
    private TextView text2;
    private int internet = 1;
    private TextView text3;
    private Button save_data;
    private Button restore_data;
    private Button clear_data;


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        wang_zhi=this.getString(R.string.wang_zhi);
        wang_zhi = this.getString(R.string.wang_zhi4);
        wang_zhi2 = this.getString(R.string.wang_zhi2);
        wang_zhi3 = this.getString(R.string.wang_zhi3);
//        MyApplication myApplication = (MyApplication) getApplication();
//        wang_zhi = myApplication.getWangzhi();
//        if(myApplication.getWangzhi()!=wang_zhi){
//            wang_zhi = wang_zhi2;
//
//        }

//        Toast.makeText(getApplicationContext(),"验证码发送成功",Toast.LENGTH_SHORT).show();
//        Message msg = Message.obtain();
//        msg.what=10;//先切换网络 不行的话 最后提示无网络
//        handler.sendMessage(msg);

        getViews();
        registListener();

        SharedPreferences buyerSP = getSharedPreferences("buyerData",MODE_PRIVATE);
        SharedPreferences sellerSP = getSharedPreferences("sellerData",MODE_PRIVATE);
        String buyerId = buyerSP.getString("buyerId","");
        String sellerId = sellerSP.getString("sellerId","");
        String buyerTime = buyerSP.getString("time","");
        String sellerTime = sellerSP.getString("time","");

        if (!buyerId.equals("")&&!sellerId.equals("")){
                @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy:mm:dd:HH:mm:ss");
                try {
                    Date buyerData = df.parse(buyerTime);
                    Date sellerData = df.parse(sellerTime);
                    if(buyerData.getTime()>sellerData.getTime()){
                        BuyerLogin2();
                    }else {
                        SellerLogin2();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
        }else if(buyerId.equals("")&&!sellerId.equals("")){
            SellerLogin2();
        }else if(!buyerId.equals("")){
            BuyerLogin2();
        }


        et_password.addTextChangedListener(textWatcher);
        et_repassword.addTextChangedListener(textWatcher2);

        handler = new Handler(){
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        Toast.makeText(getApplicationContext(),"验证码发送成功",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(),"验证码发送失败，请重新尝试",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        //Toast.makeText(getApplicationContext(),"验证码正确 验证成功 开始调用注册函数",Toast.LENGTH_SHORT).show();
                        if(radio == 1) {
                            BuyerSignUp();
                        }else {
                            SellerSignUp();
                        }
                        Toast.makeText(getApplicationContext(),"注册成功！请记住账号和密码",Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getApplicationContext(),"验证码错误 验证失败",Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        //Toast.makeText(getApplicationContext(),"注册成功！请记住账号和密码",Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        if(a==1){
                            //Toast.makeText(getApplicationContext(),"买家无重复注册 调用注册函数 此时a="+a,Toast.LENGTH_SHORT).show();
                            registered();//注册
                        }else if(a==2){
                            Toast.makeText(getApplicationContext(),"该账号已经注册过了哦。。。",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 7:
                        if(a==1){
                            Toast.makeText(getApplicationContext(),"该用户未注册",Toast.LENGTH_SHORT).show();
                        }else if(a==2){
                            if(radio == 1) {
                                BuyerLogin();
                                Toast.makeText(getApplicationContext(),"买家登录",Toast.LENGTH_SHORT).show();
                            }else {
                                SellerLogin();
                                Toast.makeText(getApplicationContext(),"卖家登录",Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    case 8:
                        Toast.makeText(getApplicationContext(),"账号或密码不正确",Toast.LENGTH_SHORT).show();
                        break;
                    case 9:
                        Toast.makeText(getApplicationContext(),"买家登录成功",Toast.LENGTH_SHORT).show();
                        text3.setText("已登录");
                        SaveBuyerData();
                        break;
                    case 10:
                        Toast.makeText(getApplicationContext(),"finally 捕获 服务器异常无网络",Toast.LENGTH_LONG).show();
//                        MyApplication myApplication = (MyApplication) getApplication();
//                        myApplication.setWangzhi(wang_zhi2);
//                        wang_zhi = wang_zhi2;
//                        wang_zhi = wang_zhi3;
//                        checkInternet2();

                        break;
                    case 11:
                        Toast.makeText(getApplicationContext(),"IO 捕获 无网络",Toast.LENGTH_LONG).show();
                        break;
                    case 12:
                        Toast.makeText(getApplicationContext(),"ConnectException 捕获",Toast.LENGTH_SHORT).show();
                        break;
                    case 13:
                        Toast.makeText(getApplicationContext(),"finally  捕获 无网",Toast.LENGTH_SHORT).show();
                        break;
                    case 14:
                        Toast.makeText(getApplicationContext(),"if无网络",Toast.LENGTH_SHORT).show();
                        break;
                    case 15:
                        Toast.makeText(getApplicationContext(),"else 有网络",Toast.LENGTH_SHORT).show();
                        break;
                    case 16:
                        Toast.makeText(getApplicationContext(),"finally  2次捕获 无网",Toast.LENGTH_SHORT).show();
                        break;
                    case 17:
                        Toast.makeText(getApplicationContext(),"C",Toast.LENGTH_SHORT).show();
                        break;
                    case 18:
                        Toast.makeText(getApplicationContext(),"M",Toast.LENGTH_SHORT).show();
                        break;
                    case 19:
                        Toast.makeText(getApplicationContext(),"U",Toast.LENGTH_SHORT).show();
                        break;
                    case 22:
                        Toast.makeText(getApplicationContext(),"U2",Toast.LENGTH_SHORT).show();
                        break;
                    case 21:
                        Toast.makeText(getApplicationContext(),"s",Toast.LENGTH_SHORT).show();
                        break;
                    case 23:
                        Toast.makeText(getApplicationContext(),"seller登录成功",Toast.LENGTH_SHORT).show();
                        text3.setText("已登录");
                        SaveSellerData();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"错误",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        checkInternet();

//        if(checkUrl(wang_zhi+"CheckBuyerServlet",1000)){
//            Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
//        }
//        int b = checkUrl(wang_zhi+"CheckBuyerServlet",1);
//        Toast.makeText(getApplicationContext(),b+"",Toast.LENGTH_SHORT).show();
//        if( newCheckInternet()){
//            Toast.makeText(getApplicationContext(),"ok2",Toast.LENGTH_SHORT).show();
//        }else {
//            Toast.makeText(getApplicationContext(),"error2",Toast.LENGTH_SHORT).show();
//        }
//        testUrlWithTimeOut("http://10.7.89.152:8080/canMouZhangSignTest/CheckBuyerServlet?buyerId=1", 2000);


    }

    private Boolean newCheckInternet(){
        Socket socket = null;
        String phone = "1";
        try {
            URL url =new URL(wang_zhi+"CheckBuyerServlet?buyerId"+phone );
            String host = url.getHost();
            int port = url.getPort();
            if (port == -1) {
                port = 80;
            }
            socket = new Socket();
            InetSocketAddress isa = new InetSocketAddress(InetAddress.getByName(host), port);

            socket.connect(isa, 1000);
            if (socket.isConnected()) {
//                Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();
                return true;
            } else {
//                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public int checkUrl(String address, int waitMilliSecond)
    {
        try{
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setConnectTimeout(waitMilliSecond);
            conn.setReadTimeout(waitMilliSecond);

            try {
                conn.connect();
            } catch(Exception e) {
                e.printStackTrace();
                return 1;
            }

            int code = conn.getResponseCode();
            Toast.makeText(getApplicationContext(),code,Toast.LENGTH_SHORT).show();
            if ((code >= 100) && (code < 400)){
                return code;
            }

            return code;
        }catch (IOException e){
            e.printStackTrace();
            return 2;
        }
    }


    private void checkInternet() {
        new Thread() {
            @Override
            public void run() {
                    String phone = "1";
                try {
                    URL url = new URL(wang_zhi+"CheckBuyerServlet?buyerId="+phone );
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info=reader.readLine();
                    String str="";
                    while(info!=null) {
                        str = info;
                        info=reader.readLine();
                    }
                    if(str.equals("")){
                        internet = 1;//无网络
//                        Message msg = Message.obtain();
//                        msg.what=14;//先切换网络 不行的话 最后提示无网络
//                        handler.sendMessage(msg);
                    }else{
                        internet = 2;//有
                        Message msg2 = Message.obtain();
                        msg2.what=15;//else 有网络
                        handler.sendMessage(msg2);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }


                finally{
                    if(internet==1){
                        Message msg = Message.obtain();
                        msg.what=10;//无网络 先切换网络 不行的话 最后提示无网络
                        handler.sendMessage(msg);
                    }
                }
            }
        }.start();

    }
    private void checkInternet2() {
        new Thread() {
            @Override
            public void run() {
                String phone = "1";
                try {
                    URL url = new URL(wang_zhi+"CheckBuyerServlet?buyerId="+phone );
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info=reader.readLine();
                    String str="";
                    while(info!=null) {
                        str = info;
                        info=reader.readLine();
                    }
                    if(str.equals("")){
                        internet = 1;//无网络
//                        Message msg = Message.obtain();
//                        msg.what=14;//先切换网络 不行的话 最后提示无网络
//                        handler.sendMessage(msg);
                    }else{
                        internet = 2;//有
                        Message msg2 = Message.obtain();
                        msg2.what=15;//
                        handler.sendMessage(msg2);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                finally{
                    if(internet==1){
                        Message msg = Message.obtain();
                        msg.what=16;//先切换网络 不行的话 最后提示无网络
                        handler.sendMessage(msg);
                    }
                }
            }
        }.start();

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            if(s.length()>=6){
                text2.setText("ok");
            }else {
                text2.setText("密码不足6位");
            }
        }
    };
    private TextWatcher textWatcher2 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable s) {
            String password = et_password.getText().toString().trim();
            String repassword = et_repassword.getText().toString().trim();
            if(password.equals(repassword)){
                text2.setText("ok");
            }else {
                text2.setText("error");
            }
        }
    };

    private void registListener() {
        listener = new CustomeOnClickListener();
        btn_signin.setOnClickListener(listener);
        btn_rigestered.setOnClickListener(listener);
        btn_getCode.setOnClickListener(listener);
        btn_forget.setOnClickListener(listener);
        buyer_res.setOnClickListener(listener);
        seller_res.setOnClickListener(listener);
        save_data.setOnClickListener(listener);
        restore_data.setOnClickListener(listener);
        clear_data.setOnClickListener(listener);
    }

    private void getViews() {
        et_phone = findViewById(R.id.et_phone);
        et_code = findViewById(R.id.et_code);
        et_password = findViewById(R.id.et_password);
        et_repassword = findViewById(R.id.et_repassword);
        btn_rigestered = findViewById(R.id.btn_registered);
        btn_signin = findViewById(R.id.btn_signin);
        btn_getCode = findViewById(R.id.btn_getCode);
        btn_forget = findViewById(R.id.btn_forget);
        buyer_res = findViewById(R.id.buyer_res);
        seller_res = findViewById(R.id.seller_res);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        save_data = findViewById(R.id.save_data);
        restore_data = findViewById(R.id.restore_data);
        clear_data = findViewById(R.id.clear_data);
    }

    private class CustomeOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_signin://点击登录 还没写
                    if(radio==1){
                        checkBuyerLogin();//检查该买家有无注册
                    }else if(radio == 2){
                        checkSellerLogin();//检查该卖家有无注册
                    }
                    break;
                case R.id.btn_getCode://发送短信
                    getCode();
                    break;
                case R.id.btn_registered://点击注册按钮
                    if(radio==1){
                        checkBuyer();//检查买家有无重复注册
                    }else if(radio == 2){
                        checkSeller();//检查有无重复注册
                    }
                    break;
                case R.id.btn_forget://还没写
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,ForgetPassword.class);
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
                case R.id.save_data:
//                    SaveBuyerData();
//                    SaveSellerData();
                    break;
                case R.id.restore_data:
                    RestoreBuyerData();
                    RestoreSellerData();
                    break;
                case R.id.clear_data:
                    Toast.makeText(getApplicationContext(),"清除",Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = getSharedPreferences("buyerData",MODE_PRIVATE).edit();
                    editor.clear();
                    editor.apply();
                    SharedPreferences.Editor editor2 = getSharedPreferences("sellerData",MODE_PRIVATE).edit();
                    editor2.clear();
                    editor2.apply();
//                    File directory = new File("/data/data/com.example.lenovo.smstest/shared_prefs");
//                    if (directory != null && directory.exists() && directory.isDirectory()) {
//                        for (File item : directory.listFiles()) {
//                            item.delete();
//                        }
//                    }
//
//                    deleteFilesByDirectory(new File("/data/data/"
//                            + getApplicationContext().getPackageName() + "/shared_prefs"));
                    break;
            }
        }
    }
    //保存买家id password
    private void SaveBuyerData() {
        DateFormat df = new SimpleDateFormat("yyyy:mm:dd:HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        String buyerStr = df.format(curDate);
        String phone = et_phone.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        SharedPreferences.Editor editor = getSharedPreferences("buyerData",MODE_PRIVATE).edit();
        editor.putString("buyerId",phone);
        editor.putString("password",password);
//        editor.putInt("time",time);
        editor.putString("time",buyerStr);
        editor.apply();

    }
    private void SaveSellerData() {
        DateFormat df = new SimpleDateFormat("yyyy:mm:dd:HH:mm:ss");
        Date curDate =  new Date(System.currentTimeMillis());
        String sellerStr  = df.format(curDate);
        String phone = et_phone.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        SharedPreferences.Editor editor = getSharedPreferences("sellerData",MODE_PRIVATE).edit();
        editor.putString("sellerId",phone);
        editor.putString("password",password);
//        editor.putInt("time",time);
        editor.putString("time",sellerStr);
        editor.apply();
    }

    private void RestoreBuyerData(){
        SharedPreferences preferences = getSharedPreferences("buyerData",MODE_PRIVATE);
        String buyerId = preferences.getString("buyerId","");
        String password = preferences.getString("password","");
        if(!buyerId.equals("")){
            if (!password.equals("")){
                BuyerLogin2();
            }
        }
        Toast.makeText(getApplication(),buyerId+":"+password,Toast.LENGTH_SHORT).show();
    }
    private void RestoreSellerData(){
        SharedPreferences preferences = getSharedPreferences("sellerData",MODE_PRIVATE);
        String sellerId = preferences.getString("sellerId","");
        String password = preferences.getString("password","");
        if(!sellerId.equals("")){
            if (!password.equals("")){
                BuyerLogin2();
            }
        }
        Toast.makeText(getApplication(),sellerId+":"+password,Toast.LENGTH_SHORT).show();
    }

    private void BuyerLogin() {
        new Thread(){
            public void run(){
                try {
                    String phone = et_phone.getText().toString().trim();
                    String password = et_password.getText().toString().trim();;
                    URL url = new URL(wang_zhi+"BuyerLoginServlet?buyerId=" + phone+"&buyerPassword="+password);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info=reader.readLine();
                    if(info!=null) {
                        Message msg = Message.obtain();
                        msg.what=9;//buyer登录成功
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
    //从SharedPreference中获取id 密码登录
    private void BuyerLogin2(){
        new Thread(){
            public void run(){
                try {
//                    String phone = et_phone.getText().toString().trim();
//                    String password = et_password.getText().toString().trim();
                    SharedPreferences preferences = getSharedPreferences("buyerData",MODE_PRIVATE);
                    String buyerId = preferences.getString("buyerId","");
                    String password = preferences.getString("password","");
                    URL url = new URL(wang_zhi+"BuyerLoginServlet?buyerId=" + buyerId+"&buyerPassword="+password);
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
                        msg.what=23;//seller登录成功
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
    private void SellerLogin2() {
        new Thread(){
            public void run(){
                try {
                    SharedPreferences preferences = getSharedPreferences("sellerData",MODE_PRIVATE);
                    String sellerId = preferences.getString("sellerId","");
                    String password = preferences.getString("password","");

                    URL url = new URL(wang_zhi+"SellerLoginServlet?sellerId=" + sellerId+"&sellerPassword="+password);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info=reader.readLine();
                    if(info!=null) {

                        Message msg = Message.obtain();
                        msg.what=23;//seller登录成功
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
                    msg.what=6;//提示已经注册 否则调用大注册函数 registered();
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
                    msg.what=6;//提示已经注册 否则调用注册函数 registered();
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //注册
    private void registered() {
        String phone = et_phone.getText().toString().trim();
        String code = et_code.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String repassword = et_repassword.getText().toString().trim();
        if(phone.equals("")){
            Toast.makeText(getApplicationContext(),"手机号不能为空", Toast.LENGTH_SHORT).show();
        }else if(phone.length()!=11){
            Toast.makeText(getApplicationContext(),"手机号格式不正确", Toast.LENGTH_SHORT).show();
        } else if(code.equals("")){
            Toast.makeText(getApplicationContext(),"验证码不能为空", Toast.LENGTH_SHORT).show();
        }else if(password.length()<6){
            Toast.makeText(getApplicationContext(),"密码长度不能小于6位", Toast.LENGTH_SHORT).show();
        }else if(password.equals(repassword)){
            submitCode("+86",phone,code);
        }else {
            Toast.makeText(getApplicationContext(),"两次密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
        }
    }
//发送短信验证码
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

    //发送短信的具体方法
    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
    public void sendCode(String country, final String phone) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    Message msg = Message.obtain();//验证码发送成功
                    msg.what=1;//提示验证码发送成功
                    handler.sendMessage(msg);
                } else{
                    // TODO 处理错误的结果
                    Message msg = Message.obtain();
                    msg.what=2;//提示验证码发送失败，请重新
                    handler.sendMessage(msg);
                }

            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(country, phone);
    }


    //验证验证码是否正确
    // 提交验证码，其中的code表示验证码，如“1357”
    public void submitCode(String country, final String phone, String code) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证成功的结果
                    Message msg = Message.obtain();
                    msg.what=3;//若验证码正确 则调用具体注册函数 BuyerSignUp();/SellerSignUp()
                    handler.sendMessage(msg);
                } else{
                    // TODO 处理错误的结果
                    Message msg = Message.obtain();
                    msg.what=4;//提示验证码错误 验证失败
                    handler.sendMessage(msg);
                }
            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code);
    }

    //卖家注册具体方法
    private void SellerSignUp() {
        new Thread() {
            public void run() {
                try {
                    String phone = et_phone.getText().toString().trim();
                    String password = et_password.getText().toString().trim();
                    URL url = new URL(wang_zhi+"AddSellerServlet?sellerId=" + phone + "&sellerPassword=" + password);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //买家注册具体方法
    private void BuyerSignUp() {
        new Thread() {
            public void run() {
                try {
                    String phone = et_phone.getText().toString().trim();
                    String password = et_password.getText().toString().trim();
                    URL url = new URL(wang_zhi+"AddBuyerServlet?buyerId=" + phone + "&buyerPassword=" + password);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    protected void onDestroy() {
        super.onDestroy();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();
    };

    //验证码倒计时
    class MyCountDownTimer implements Runnable {
        @Override
        public void run() {
            //倒计时开始，循环
            while (T > 0) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        btn_getCode.setClickable(false);
                        btn_getCode.setText("重发"+"("+T+")");
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

