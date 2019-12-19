package com.example.administrator.shixun;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.shixun.Adapter.MeterialAdapter;
import com.example.administrator.shixun.Adapter.MethodsAdapter;
import com.example.administrator.shixun.Entity.Meterial;
import com.example.administrator.shixun.Entity.Methods;
import com.example.administrator.shixun.LoginAndSignup.LoginActivity;
import com.example.administrator.shixun.Util.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class SimpleActivity extends AppCompatActivity {
    private ScrollView scrollView;

    private TextView change;
    private TextView txt_word;
    private LinearLayout add;
    private Button img_in;
    private ImageView imgp;
    private TextView names;
    private ListView mlist;
    private ListViewForScrollView melist;
    private ImageView pinglun;
    private ImageView shoucang;
    private String shoucangstatus;
    private MeterialAdapter meterialAdapter;
    private MethodsAdapter methodsAdapter;

    private String astr;
    private Handler handler;
    private String[] b;
    private String str;
    private String str2;
    private String str3;
    private String str4;

    private List<Methods> list = new ArrayList<>();
    private List<Meterial> list2 = new ArrayList<>();
    private String MUrl;

    private String phone;
    private String buyerId;
    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        shoucangstatus = "1";
        scrollView = findViewById(R.id.scroll);
        add = findViewById(R.id.add);
        txt_word = findViewById(R.id.txt_word);
        img_in = findViewById(R.id.img_in);
        imgp = findViewById(R.id.imgP);
        names = findViewById(R.id.names);
        change = findViewById(R.id.change);
        mlist = findViewById(R.id.mlist);
        melist = findViewById(R.id.methods);
        pinglun = findViewById(R.id.pinglun);
        shoucang = findViewById(R.id.shoucang);
        Intent intent = getIntent();
        astr = intent.getStringExtra("name");
        names.setText(astr);
        img_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleActivity.this.finish();
            }
        });
        SharedPreferences buyerSP = getSharedPreferences("buyerData",MODE_PRIVATE);
        buyerId = buyerSP.getString("buyerId","");
        showBigImg();
        showWord();
        showMeterial();
        showAllMsg();
        findCollection();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 100:
                        str = (String) msg.obj;
                        String[] a = str.split("@");
                        Methods methods = new Methods(a[0],a[1]);
                        list.add(methods);
                        break;
                    case 200:
                        methodsAdapter = new MethodsAdapter(list, SimpleActivity.this, R.layout.methods_layout);
                        methodsAdapter.notifyDataSetChanged();
                        melist.setAdapter(methodsAdapter);
                        int count = list.size();
                        add.removeAllViews();
                        for (int i = 0; i < count; i++  ) {
                            View v = methodsAdapter.getView(i, null, null);
                            add.addView(v);
                        }
                        break;
                    case 300:
                        str2 = (String)msg.obj;
                        b = str2.split(":");
                        Meterial meterial = new Meterial(b[0],b[1]);
                        list2.add(meterial);
                        break;
                    case 400:
                        meterialAdapter = new MeterialAdapter(list2, SimpleActivity.this, R.layout.meterial_layout);
                        meterialAdapter.notifyDataSetChanged();
                        mlist.setAdapter(meterialAdapter);
                        Utility.setListViewHeightBasedOnChildren(mlist);
                        break;
                    case 500:
                        str3 = (String)msg.obj;
                        Glide.with(SimpleActivity.this).asBitmap().load(str3).into(imgp);
                        break;
                    case 600:
                        str4 = (String)msg.obj;
                        txt_word.setText(str4);
                        break;
                    case 800:
                        String ad = (String)msg.obj;
                        if (ad.equals("有")){
                            shoucang.setImageResource(R.drawable.shoucang2);
                            shoucangstatus="2";
                        }else{
                            shoucang.setImageResource(R.drawable.shoucang);
                            shoucangstatus="1";
                        }

                        break;
                }
            }
        };
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SimpleActivity.this, MakeActivity.class);
                intent.putExtra("make",(Serializable) list);
                startActivity(intent);
            }
        });
        //评论
        pinglun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication myApplication = (MyApplication) getApplication();
                phone = myApplication.getPhone();
                if(phone.equals("null")||buyerId.equals("")){
                    Intent intent = new Intent(SimpleActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(SimpleActivity.this, DiscussActivity.class);
                    intent.putExtra("discuss",astr);
                    startActivity(intent);
                }

            }
        });
        //收藏
        shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shoucangstatus.equals("1")){
                    shoucang.setImageResource(R.drawable.shoucang2);
                    Toast.makeText(getApplicationContext(),"收藏成功",Toast.LENGTH_LONG).show();
                    shoucangstatus="2";
                    addCollection();
                }else if(shoucangstatus.equals("2")){
                    shoucang.setImageResource(R.drawable.shoucang);
                    Toast.makeText(getApplicationContext(),"取消收藏",Toast.LENGTH_LONG).show();
                    shoucangstatus="1";
                    deleteCollection();
                }
            }
        });
        scrollView.smoothScrollTo(0,0);
    }

    private void addCollection() {
        new Thread(){
            @Override
            public void run() {
                try {
                    MUrl = getResources().getText(R.string.wang_zhi).toString();
//                    SharedPreferences sharedPreferences = getSharedPreferences("buyerData",MODE_PRIVATE);
//                    phones = sharedPreferences.getString("phone","");
                    URL url = new URL(MUrl+"AddCollectionServlet?vname="+astr+"&uname="+buyerId);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,"GB2312"));
                    String info = reader.readLine();
                    while((info = reader.readLine()) != null){
                        wrapMessage7(info);
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

    private void deleteCollection() {
        new Thread(){
            @Override
            public void run() {
                try {
                    MUrl = getResources().getText(R.string.wang_zhi).toString();
                    URL url = new URL(MUrl+"DeleteCollectionServlet?vname="+astr+"&uname="+buyerId);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,"GB2312"));
                    String info = "";
                    while((info = reader.readLine()) != null){
                        wrapMessage7(info);
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

    private void findCollection(){
        new Thread(){
            @Override
            public void run() {
                try {
                    MUrl = getResources().getText(R.string.num).toString();
                    URL url = new URL(MUrl+"/CheckServlet?uname="+buyerId+"&vname="+astr);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,"GB2312"));
                    String info = "";
                    while((info = reader.readLine()) != null){
                        wrapMessage8(info);
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

    private void wrapMessage8(String info) {
        Message message = Message.obtain();
        message.obj = info;
        message.what = 800;
        handler.sendMessage(message);
    }

    private void wrapMessage7(String info) {
        Message message = Message.obtain();
        message.obj = info;
        message.what = 700;
        handler.sendMessage(message);
    }


    private void showBigImg() {
        new Thread(){
            @Override
            public void run() {
                try {
                    MUrl = getResources().getText(R.string.wang_zhi).toString();
                    URL url = new URL(MUrl+"image?food="+astr);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,"GB2312"));
                    String info = "";
                    while((info = reader.readLine()) != null){
                        wrapMessage5(info);
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
    private void showMeterial(){
        new Thread(){
            @Override
            public void run() {
                try {
                    MUrl = getResources().getText(R.string.wang_zhi).toString();
                    URL url = new URL(MUrl+"meterial?food="+astr);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,"GB2312"));
                    String info = "";
                    while((info = reader.readLine()) != null){
                        wrapMessage3(info);
                    }
                    wrapMessage4();
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

    private void showWord(){
        new Thread(){
            @Override
            public void run() {
                try {
                    MUrl = getResources().getText(R.string.wang_zhi).toString();
                    URL url = new URL(MUrl+"word?food="+astr);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,"GB2312"));
                    String info = "";
                    while((info = reader.readLine()) != null){
                        wrapMessage6(info);
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
    private void showAllMsg() {
        new Thread(){
            @Override
            public void run() {
                try {
                    MUrl = getResources().getText(R.string.wang_zhi).toString();
                    URL url = new URL(MUrl+"methods?food="+astr);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,"GB2312"));
                    String info1 = "";
                    while((info1 = reader.readLine()) != null){
                        wrapMessage1(info1);
                    }
                    wrapMessage2();
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



    private void wrapMessage2() {
        Message message = Message.obtain();
        message.what = 200;
        handler.sendMessage(message);
    }

    private void wrapMessage1(String info) {
        Message message = Message.obtain();
        message.obj = info;
        message.what = 100;
        handler.sendMessage(message);
    }

    private void wrapMessage3(String info) {
        Message message = Message.obtain();
        message.obj = info;
        message.what = 300;
        handler.sendMessage(message);
    }

    private void wrapMessage4() {
        Message message = Message.obtain();
        message.what = 400;
        handler.sendMessage(message);
    }

    private void wrapMessage5(String info) {
        Message message = Message.obtain();
        message.obj = info;
        message.what = 500;
        handler.sendMessage(message);
    }

    private void wrapMessage6(String info) {
        Message message = Message.obtain();
        message.obj = info;
        message.what = 600;
        handler.sendMessage(message);
    }

}