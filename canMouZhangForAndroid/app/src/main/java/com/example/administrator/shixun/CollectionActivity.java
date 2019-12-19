package com.example.administrator.shixun;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.shixun.Adapter.FirstAdapter;
import com.example.administrator.shixun.Entity.Show;
import com.example.administrator.shixun.Util.Utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CollectionActivity extends AppCompatActivity {
    private ListView collection;
    private List<Show> list = new ArrayList<>();
    private String MUrl;
    private FirstAdapter firstAdapter;
    private String buyerId;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 100:
                    String str = (String)msg.obj;
                    Random random = new Random();
                    int eye = random.nextInt(100);
                    int zan = random.nextInt(10);
                    String[] a = str.split("&");
                    String[] b = a[0].split("=");
                    String[] c = a[1].split("=");
                    String[] d = a[2].split("=");
                    Show show = new Show(b[1],c[1],d[1],eye+"",zan+"");
                    list.add(show);
                    break;
                case 200:
                    firstAdapter = new FirstAdapter(list,CollectionActivity.this,R.layout.item_layout);
                    firstAdapter.notifyDataSetChanged();
                    collection.setAdapter(firstAdapter);
                    Utility.setListViewHeightBasedOnChildren(collection);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);

        SharedPreferences buyerSP = getSharedPreferences("buyerData",MODE_PRIVATE);
//        SharedPreferences sellerSP = getSharedPreferences("sellerData",MODE_PRIVATE);
        buyerId = buyerSP.getString("buyerId","");
//        sellerId = sellerSP.getString("sellerId","");
        collection = findViewById(R.id.collection);
        showCollection();
        collection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CollectionActivity.this,SimpleActivity.class);
                intent.putExtra("name",list.get(position).getName());
                startActivity(intent);
            }
        });
    }

    private void showCollection() {
        new Thread(){
            @Override
            public void run() {
                try {
                    MUrl = getResources().getText(R.string.wang_zhi).toString();
                    URL url = new URL(MUrl+"FindCollectionServlet?uname="+buyerId);
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
}
