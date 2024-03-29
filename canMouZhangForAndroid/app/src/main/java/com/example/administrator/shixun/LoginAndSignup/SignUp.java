package com.example.administrator.shixun.LoginAndSignup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.shixun.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class SignUp extends AppCompatActivity {
    private EditText et_phone;
    private EditText et_code;
    private EditText et_password;
    private EditText et_repassword;
    private EditText et_nicheng;
    private Button btn_rigestered;
    private TextView btn_getCode;
    private TextView tx_pswStatus;
    private TextView tx_repswStatus;
    private RadioButton buyer_res;
    private RadioButton seller_res;
    private CustomeOnClickListener listener;
    public int T = 60; //倒计时时长
    private Handler mHandler = new Handler();
    private Handler handler = new Handler();
    private int radio =1;
    private String wang_zhi ;
    private int a =0;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        wang_zhi=this.getString(R.string.wang_zhi);

        getViews();
        registListener();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            radio = bundle.getInt("radioStatus");
        }

        if(radio == 1){
            buyer_res.setChecked(true);
        }else if(radio == 2){
            seller_res.setChecked(true);
        }

        et_password.addTextChangedListener(textWatcher);
        et_repassword.addTextChangedListener(textWatcher2);

        handler = new Handler(){
            public void handleMessage(Message msg) {
                switch (msg.what){
//                    case 1:
//                        Toast.makeText(getApplicationContext(),"验证码发送成功",Toast.LENGTH_SHORT).show();
//                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(),"验证码发送失败，请重新尝试",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        //Toast.makeText(getApplicationContext(),"验证码正确 验证成功 开始调用注册函数",Toast.LENGTH_SHORT).show();
                        if(radio == 1) {
                            BuyerSignUp();
//                            asyncUpOp();
                        }else {
                            SellerSignUp();
                        }
                        Toast.makeText(getApplicationContext(),"注册成功！请记住账号和密码",Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent();
                        intent1.setClass(SignUp.this, LoginActivity.class);
                        startActivity(intent1);
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
                        Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(),"错误",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
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
                tx_pswStatus.setText("√");
            }else {
                tx_pswStatus.setText("密码不足6位");
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
                tx_repswStatus.setText("√");
            }else {
                tx_repswStatus.setText("两次密码不同");
            }

        }
    };

    private void registListener() {
        listener = new CustomeOnClickListener();
        btn_rigestered.setOnClickListener(listener);
        btn_getCode.setOnClickListener(listener);
        buyer_res.setOnClickListener(listener);
        seller_res.setOnClickListener(listener);
    }

    private void getViews() {
        et_phone = findViewById(R.id.et_phone);
        et_code = findViewById(R.id.et_code);
        et_password = findViewById(R.id.et_password);
        et_repassword = findViewById(R.id.et_repassword);
        btn_rigestered = findViewById(R.id.btn_registered);
        btn_getCode = findViewById(R.id.btn_getCode);
//        btn_forget = findViewById(R.id.btn_forget);
        buyer_res = findViewById(R.id.buyer_res);
        seller_res = findViewById(R.id.seller_res);
        tx_pswStatus = findViewById(R.id.tx_pswStatus);
        tx_repswStatus = findViewById(R.id.tx_repswStatus);
        et_nicheng = findViewById(R.id.et_nicheng);
    }

    private class CustomeOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
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
                case R.id.btn_forget:
                    Intent intent = new Intent(SignUp.this,ForgetPassword.class);
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
                    String password = et_password.getText().toString().trim();;
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
        String nicheng = et_nicheng.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String repassword = et_repassword.getText().toString().trim();
        if(phone.equals("")){
            Toast.makeText(getApplicationContext(),"手机号不能为空", Toast.LENGTH_SHORT).show();
        }else if(phone.length()!=11){
            Toast.makeText(getApplicationContext(),"手机号格式不正确", Toast.LENGTH_SHORT).show();
        } else if(code.equals("")){
            Toast.makeText(getApplicationContext(),"验证码不能为空", Toast.LENGTH_SHORT).show();
        }else if(nicheng.equals("")){
            Toast.makeText(getApplicationContext(),"昵称不能为空",Toast.LENGTH_LONG).show();
        } else if(password.length()<6){
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
//            btn_getCode.setBackgroundColor(Color.parseColor("#FAFFF0"));
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
//                    Message msg = Message.obtain();//验证码发送成功
//                    msg.what=1;//提示验证码发送成功
//                    handler.sendMessage(msg);
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
                    String nicheng = et_nicheng.getText().toString().trim();
                    URL url = new URL(wang_zhi+"AddBuyerServlet?buyerId=" + phone + "&buyerPassword=" + password+"&uname="+nicheng);
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
//                    btn_getCode.setBackgroundColor(Color.parseColor("#DCDCDC"));
                }
            });
            T = 60; //最后再恢复倒计时时长
        }
    }
}

