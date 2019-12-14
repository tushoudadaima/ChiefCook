package com.example.administrator.shixun;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.shixun.Fragment.FirstFragment;
import com.example.administrator.shixun.Fragment.FourFragment;
import com.example.administrator.shixun.Fragment.SecondFragment;
import com.example.administrator.shixun.Fragment.ThreeFragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    private String wang_zhi ;
    private int internet = 1;

    private Map<String, ImageView> imageViewMap = new HashMap<>();
    public List<Map<String, Object>> dataSource;
    private String a;
    private Handler handler = new Handler();
    private String buyerId;
    private String buyerTime;
    private String sellerId;
    private String sellerTime;
    private OkHttpClient okHttpClient;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        wang_zhi = this.getString(R.string.wang_zhi);
        checkInternet();
        okHttpClient = new OkHttpClient();



        SharedPreferences buyerSP = getSharedPreferences("buyerData",MODE_PRIVATE);
        SharedPreferences sellerSP = getSharedPreferences("sellerData",MODE_PRIVATE);
        buyerId = buyerSP.getString("buyerId","");
        sellerId = sellerSP.getString("sellerId","");
        buyerTime = buyerSP.getString("time","");
        sellerTime = sellerSP.getString("time","");


        if (!buyerId.equals("")&&!sellerId.equals("")){
            @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy:mm:dd:HH:mm:ss");
            try {
                Date buyerData = df.parse(buyerTime);
                Date sellerData = df.parse(sellerTime);
                if(buyerData.getTime()>sellerData.getTime()){
                    BuyerLogin2();
                    setBuyerImage();
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
            setBuyerImage();
        }



        handler = new Handler(){
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:
                        Toast.makeText(getApplicationContext(),"服务器异常，请稍后再试",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        MyApplication myApplication = (MyApplication) getApplication();
                        SharedPreferences preferences = getSharedPreferences("buyerData",MODE_PRIVATE);
                        String buyerId = preferences.getString("buyerId","");
                        myApplication.setPhone(buyerId);
//                        Toast.makeText(getApplicationContext(),"buyer登录成功",Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(),"账号密码错误",Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        MyApplication myApplication1 = (MyApplication) getApplication();
                        SharedPreferences preferences1 = getSharedPreferences("sellerData",MODE_PRIVATE);
                        String sellerId = preferences1.getString("sellerId","");
                        myApplication1.setPhone(sellerId);

//                        Toast.makeText(getApplicationContext(),"seller登录成功",Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        a=this.getIntent().getStringExtra("index");
        if(a==null){
            Intent intent = new Intent(this, StartActivity.class);
            intent.putExtra("oo",0);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取FragmentTabHost对象
        FragmentTabHost fragmentTabHost = findViewById(android.R.id.tabhost);

        //初始化FragmentTabHost
        fragmentTabHost.setup(this,
                getSupportFragmentManager(),//FragmentManager对象用来管理多个Fragment
                android.R.id.tabcontent);//真正显示内容页面的容器的id

        //创建tabspec对象
        TabHost.TabSpec tabSpec1 = fragmentTabHost.newTabSpec("tag1")
                .setIndicator(getTabSpecView("tag1",R.drawable.shouye,"首页"));
        //自定义选项卡视图

        fragmentTabHost.addTab(tabSpec1,
                FirstFragment.class,//内容页面对应的Fragment类的Class对象
                null);//传递数据时使用

        TabHost.TabSpec tabSpec2 = fragmentTabHost.newTabSpec("tag2")
                .setIndicator(getTabSpecView("tag2",R.drawable.shangdian,"商店"));

        fragmentTabHost.addTab(tabSpec2,
                SecondFragment.class,
                null);

        TabHost.TabSpec tabSpec3 = fragmentTabHost.newTabSpec("tag3")
                .setIndicator(getTabSpecView("tag3",R.drawable.dingdan,"订单"));

        fragmentTabHost.addTab(tabSpec3,
                ThreeFragment.class,
                null);
        TabHost.TabSpec tabSpec4 = fragmentTabHost.newTabSpec("tag4")
                .setIndicator(getTabSpecView("tag4",R.drawable.wode,"我的"));

        fragmentTabHost.addTab(tabSpec4,
                FourFragment.class,
                null);
        //设置默认选中哪一项
        fragmentTabHost.setCurrentTab(0);
        imageViewMap.get("tag1").setImageResource(R.drawable.shouye1);
        //切换选项卡的事件监听器
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                //当切换选项卡时调用
                switch (tabId){
                    case "tag1":
                        imageViewMap.get("tag1").setImageResource(R.drawable.shouye1);
                        imageViewMap.get("tag2").setImageResource(R.drawable.shangdian);
                        imageViewMap.get("tag3").setImageResource(R.drawable.dingdan);
                        imageViewMap.get("tag4").setImageResource(R.drawable.wode);
                        break;
                    case "tag2":
                        imageViewMap.get("tag1").setImageResource(R.drawable.shouye);
                        imageViewMap.get("tag2").setImageResource(R.drawable.shangdian1);
                        imageViewMap.get("tag3").setImageResource(R.drawable.dingdan);
                        imageViewMap.get("tag4").setImageResource(R.drawable.wode);
                        break;
                    case "tag3":
                        imageViewMap.get("tag1").setImageResource(R.drawable.shouye);
                        imageViewMap.get("tag2").setImageResource(R.drawable.shangdian);
                        imageViewMap.get("tag3").setImageResource(R.drawable.dingdan1);
                        imageViewMap.get("tag4").setImageResource(R.drawable.wode);
                        break;
                    case "tag4":
                        imageViewMap.get("tag1").setImageResource(R.drawable.shouye);
                        imageViewMap.get("tag2").setImageResource(R.drawable.shangdian);
                        imageViewMap.get("tag3").setImageResource(R.drawable.dingdan);
                        imageViewMap.get("tag4").setImageResource(R.drawable.wode1);
                        break;

                }
            }
        });
    }

    private void setBuyerImage() {
        @SuppressLint("SdCardPath") File file = new File(getFilesDir().getPath()+"/CanMouZhang/");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        if(file.exists()){
//            Toast.makeText(getApplication(),"存在"+buyerId+".jpg",Toast.LENGTH_SHORT).show();
            asyncDownBuyerOp();
        }else {
            file.mkdirs();
            asyncDownBuyerOp();
//            Toast.makeText(getApplication(),"不存在"+buyerId+".jpg",Toast.LENGTH_SHORT).show();
        }
    }

    private void asyncDownBuyerOp() {
        new Thread(){
            @Override
            public void run() {
                try {
                    downBuyerImg();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void downBuyerImg() throws IOException {
        Request request = new Request.Builder()
                .url(wang_zhi+"DownBuyerImg?buyerId="+buyerId)
                .build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        InputStream in = response.body().byteStream();
        OutputStream out = new FileOutputStream(
                getFilesDir().getPath()+"/CanMouZhang/"+buyerId+".jpg"
        );

        byte[] bytes = new byte[1024];
        int n = -1;
        while((n = in.read(bytes)) != -1){
            out.write(bytes, 0 , n);
            out.flush();
        }
        in.close();
        out.close();
    }

    //从SharedPreference中获取id 密码登录
    private void BuyerLogin2(){
        new Thread(){
            public void run(){
                try {
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
                        msg.what=2;//登录成功
                        handler.sendMessage(msg);
                    }else{
                        Message msg = Message.obtain();
                        msg.what=3;//账号密码错误
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
                        msg.what=4;//seller登录成功
                        handler.sendMessage(msg);
                    }else{
                        Message msg = Message.obtain();
                        msg.what=3;//账号或密码不正确
                        handler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
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
                    }else{
                        internet = 2;//有
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally{
                    if(internet==1){
                        Message msg = Message.obtain();
                        msg.what=1;//无网络 提示无网络
                        handler.sendMessage(msg);
                    }
                }
            }
        }.start();

    }
    public View getTabSpecView(String tag, int imageResId, String title){
        //加载布局文件
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.activity_base,null);
        //获取控件对象
        ImageView imageView = view.findViewById(R.id.iv_icon);
        imageView.setImageResource(imageResId);
        TextView textView = view.findViewById(R.id.tv_title);
        textView.setText(title);
        imageViewMap.put(tag,imageView);
        return view;
    }

}