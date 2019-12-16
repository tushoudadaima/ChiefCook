package com.example.administrator.shixun;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.shixun.Adapter.DiscussAdapter;
import com.example.administrator.shixun.Entity.Discuss;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);
        MyApplication myApplication = (MyApplication) getApplication();
        phone = myApplication.getPhone();

        pList = findViewById(R.id.plist);
        searchView = findViewById(R.id.search_gai);
        fabu = findViewById(R.id.fabu);
        tv_title = findViewById(R.id.tv_title);
        img_search = findViewById(R.id.img_search);
        img_search.setImageResource(R.drawable.xiezi);
        tv_title.setHint("我也来说两句");
        Intent intent = getIntent();
        str = intent.getStringExtra("discuss");
        fabu.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = formatter.format(date);
                String content = tv_title.getText().toString();
                String url = "";
                Discuss discuss = new Discuss(url,phone,time,content);
                list.add(discuss);
                discussAdapter = new DiscussAdapter(list, DiscussActivity.this, R.layout.discuss_item);
                discussAdapter.notifyDataSetChanged();
                pList.setAdapter(discussAdapter);
            }
        });
    }
}
