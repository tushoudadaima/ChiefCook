package com.example.administrator.shixun.Send;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.shixun.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MinezActivity extends AppCompatActivity {
    private String ming;
    private String biao;
    private String tupian;
    private TextView biaoa;
    private TextView shijian;
    private ImageView pic;
    private TextView bibibia;
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinxiang);
        ming=this.getIntent().getStringExtra("mingzi");
        biao=this.getIntent().getStringExtra("biaoti");
        tupian=this.getIntent().getStringExtra("tupian");
        biaoa=findViewById(R.id.biaoa);
        shijian=findViewById(R.id.shijian);
        bibibia=findViewById(R.id.bibibi);
        tv=findViewById(R.id.tv_title);
        tv.setText("我的");
        biaoa.setText(biao);
        pic=findViewById(R.id.pic);
        Uri uri=Uri.parse(tupian);
        pic.setImageURI(uri);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        shijian.setText("时间:"+simpleDateFormat.format(date));
        int j = new Random().nextInt(15)%(15-0+1)+0;
        bibibia.setText(ming);
    }
}
