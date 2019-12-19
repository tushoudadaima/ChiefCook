package com.example.administrator.shixun;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.shixun.Adapter.DiscussAdapter;
import com.example.administrator.shixun.Entity.Discuss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DiscussActivity extends AppCompatActivity {
    private ListView pList;
    private SearchView searchView;
    private TextView fabu;
    private EditText tv_title;
    private ImageView img_search;
    private String str;
    private DiscussAdapter discussAdapter;
    private List<Discuss> list = new ArrayList<>();
    private String phone;
    private String buyerId;
    private String nickName;
    private String info;
    private String MUrl;
    private String time;
    private String content;
    private OkHttpClient okHttpClient;
    private MyApplication myApplication;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 100:
                    String dis = (String)msg.obj;
                    String[] a = dis.split(",");
//                    Toast.makeText(getApplicationContext(),a[0]+a[3],Toast.LENGTH_LONG).show();
                    @SuppressLint("SdCardPath") String touxing = "/data/data/com.example.administrator.shixun/files/CanMouZhang/"+a[3]+".jpg";
                    @SuppressLint("SdCardPath") File file = new File("/data/data/com.example.administrator.shixun/files/CanMouZhang/"+a[3]+".jpg");
                    if(file.exists()){
//                        Toast.makeText(getApplicationContext(),a[0]+a[3],Toast.LENGTH_LONG).show();
                        Discuss discuss = new Discuss(touxing,a[0],a[1],a[2]);
                        list.add(discuss);
                    }else {
                        try {
                            downBuyerImg(a[0],a[1],a[2],a[3]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                        Toast.makeText(getApplicationContext(),a[0]+a[3]+"未下载",Toast.LENGTH_LONG).show();
                    }
                    break;
                case 200:
                    discussAdapter = new DiscussAdapter(list,DiscussActivity.this,R.layout.discuss_item);
                    discussAdapter.notifyDataSetChanged();
                    pList.setAdapter(discussAdapter);
//                    Utility.setListViewHeightBasedOnChildren(pList);
                case 300:
                    info = (String)msg.obj;
                    nickName = info;

//                    Toast.makeText(getApplicationContext(),nickName, Toast.LENGTH_LONG).show();
//                    myApplication = (MyApplication) getApplication();
//                    myApplication.setUser(nickName);
//                    Toast.makeText(getApplicationContext(),nickName,Toast.LENGTH_LONG).show();
                    break;
                case 400:
                    Toast.makeText(getApplicationContext(),"发布成功",Toast.LENGTH_LONG).show();
                    showDiscuss();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);
        okHttpClient = new OkHttpClient();
//        MyApplication myApplication = (MyApplication) getApplication();
//        phone = myApplication.getPhone();
        SharedPreferences buyerSP = getSharedPreferences("buyerData",MODE_PRIVATE);
        phone= buyerSP.getString("buyerId","");
        nickName = buyerSP.getString("nickName","");
//        Toast.makeText(getApplicationContext(),phone+buyerId,Toast.LENGTH_SHORT).show();
//        if(phone.equals("null")){
//            Toast.makeText(getApplicationContext(),"phone null",Toast.LENGTH_SHORT).show();
//        }else if(buyerId.equals("")){
//            Toast.makeText(getApplicationContext(),"buyerId null",Toast.LENGTH_SHORT).show();
//        }else if(!phone.equals("")){
//            Toast.makeText(getApplicationContext(),"ph",Toast.LENGTH_SHORT).show();
//        }

        pList = findViewById(R.id.plist);
        searchView = findViewById(R.id.search_gai);
        fabu = findViewById(R.id.fabu);
        tv_title = findViewById(R.id.tv_title);
        img_search = findViewById(R.id.img_search);
        img_search.setImageResource(R.drawable.xiezi);
        tv_title.setHint("我也来说两句");
        Intent intent = getIntent();
        str = intent.getStringExtra("discuss");
        showDiscuss();
        fabu.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                time = formatter.format(date);
                content = tv_title.getText().toString();
//                findName();
//                String url = "/data/data/com.example.administrator.shixun/files/CanMouZhang/"+phone+".jpg";
//                Discuss discuss = new Discuss(url,nickName,time,content);
//                list.add(discuss);
//                discussAdapter.notifyDataSetChanged();
//                pList.setAdapter(discussAdapter);
                list.clear();
                addDiscuss();
                tv_title.setText("");
                discussAdapter.notifyDataSetChanged();

            }
        });

        //简单粗暴 不推荐方式 解决 android.os.NetworkOnMainThreadException
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }

    }

    private void downBuyerImg(String a0,String a1,String a2,String a3) throws IOException {
        String MUrl = getResources().getText(R.string.wang_zhi).toString();
        Request request = new Request.Builder()
                .url(MUrl+"DownBuyerImg?buyerId="+a3)
                .build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        InputStream in = response.body().byteStream();
        OutputStream out = new FileOutputStream(
                getFilesDir().getPath()+"/CanMouZhang/"+a3+".jpg"
        );

        byte[] bytes = new byte[1024];
        int n = -1;
        while((n = in.read(bytes)) != -1){
            out.write(bytes, 0 , n);
            out.flush();
        }
        in.close();
        out.close();
        Discuss discuss = new Discuss(getFilesDir().getPath()+"/CanMouZhang/"+str+".jpg",a0,a1,a2);
        list.add(discuss);
    }


    private void findName() {
        new Thread(){
            @Override
            public void run() {
                try {
                    list.clear();
                    String MUrl = getResources().getText(R.string.wang_zhi).toString();

                    URL url = new URL(MUrl+"FindNameServlet?buyerId="+nickName);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,"GB2312"));
                    String info = "";
                    while((info = reader.readLine()) != null){
                        wrapMessage3(info);
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

    private void addDiscuss() {
        new Thread(){
            @Override
            public void run() {
                try {
                    MUrl = getResources().getText(R.string.wang_zhi).toString();
                    SharedPreferences nickNames = getSharedPreferences("buyerData",MODE_PRIVATE);
                    nickName = nickNames.getString("nickName","");
                    URL url = new URL(MUrl+"AddDiscussServlet?uname="+nickName+"&vname="+str+"&time="+time+"&content="+content+"&buyerId="+phone);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,"GB2312"));
                    String info = "";
//                    while((info = reader.readLine()) != null){
                        wrapMessage4();
//                    }
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

    private void wrapMessage4() {
        Message message = Message.obtain();
        message.what = 400;
        handler.sendMessage(message);
    }


    private void showDiscuss() {
        new Thread(){
            @Override
            public void run() {
                try {
                    MUrl = getResources().getText(R.string.wang_zhi).toString();
                    URL url = new URL(MUrl+"FindDiscussServlet?vname="+str);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in,"GB2312"));
                    String info = "";
                    while((info = reader.readLine()) != null){
                        wrapMessage(info);
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

    private void wrapMessage(String info) {
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
}
