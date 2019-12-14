package com.example.administrator.shixun;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.shixun.Adapter.FirstAdapter;
import com.example.administrator.shixun.Entity.Show;
import com.example.administrator.shixun.Util.Utility;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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

public class ListActivity extends AppCompatActivity {
    private String name;
    private String str;
    private Button img_to;
    private TextView text;
    private ListView listView;
    private SmartRefreshLayout srl2;
    private Handler handler;
    private FirstAdapter firstAdapter;
    private LoadingDialog loadingDialog;
    private List<Show> list = new ArrayList<>();
    private String MUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        img_to = findViewById(R.id.img_to);
        text = findViewById(R.id.text2);
        listView = findViewById(R.id.listAll);
        srl2 = findViewById(R.id.srl2);
        srl2.setReboundDuration(1000);
        srl2.setFooterHeight(50);
        srl2.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                listView.setAdapter(null);
                loadingDialog = new LoadingDialog(ListActivity.this);
                loadingDialog.show();
                list.clear();
                showName();
                srl2.finishRefresh();
            }
        });

        srl2.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (list.size()<30){
                    showName();
                    srl2.finishLoadMore();
                }else {
                    srl2.finishLoadMoreWithNoMoreData();
                }
            }
        });
        Intent intent = getIntent();
        name = intent.getStringExtra("cai");
        text.setText(name);
        img_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListActivity.this.finish();
            }
        });
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        showName();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 100:
                        str = (String) msg.obj;
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
                        loadingDialog.dismiss();
                        firstAdapter = new FirstAdapter(list,ListActivity.this,R.layout.item_layout);
                        firstAdapter.notifyDataSetChanged();
                        listView.setAdapter(firstAdapter);
                        Utility.setListViewHeightBasedOnChildren(listView);
                        break;
                }
            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListActivity.this,SimpleActivity.class);
                intent.putExtra("name",list.get(position).getName());
                startActivity(intent);
            }
        });
    }

    private void showName(){
        new Thread(){
            @Override
            public void run() {
                try {
                    MUrl = getResources().getText(R.string.num).toString();
                    URL url = new URL(MUrl+"/name?food="+name);
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

    private void wrapMessage1(String info) {
        Message message = Message.obtain();
        message.obj = info;
        message.what = 100;
        handler.sendMessage(message);
    }

    private void wrapMessage2() {
        Message message = Message.obtain();
        message.what = 200;
        handler.sendMessage(message);
    }
}
