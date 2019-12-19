package com.example.administrator.shixun.Send;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.shixun.R;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import java.util.ArrayList;
import java.util.List;

public class Mine2Activity extends AppCompatActivity {
    private aaApapter pinAdapter;
    private List<pinn> pinlun=new ArrayList<>();
    private SmartRefreshLayout srl;
    private String biaoaaa;
    private String bibb;
    private String res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_blank);
        srl =findViewById(R.id.srl);
        srl.setRefreshHeader(new PhoenixHeader(this.getApplicationContext()));
        srl.setRefreshFooter(new ClassicsFooter(this.getApplicationContext()).setSpinnerStyle(SpinnerStyle.Scale));
        srl.setReboundDuration(300);
        res=this.getIntent().getStringExtra("pict");
        Uri uri=Uri.parse(res);
        biaoaaa=this.getIntent().getStringExtra("xinxian");
        bibb=this.getIntent().getStringExtra("bib");
        pinn pin=new pinn();
        MyaddActivity mainActivity=new MyaddActivity();
        pin.setText1(bibb);

        pin.setImgg(uri);
        pin.setTextm("我的");
        pin.setTextp(0+"");
        pin.setTextz(0+"");
        pinlun.add(pin);
        ListView listView = findViewById(R.id.dingdans);
        pinAdapter = new aaApapter(this, pinlun, R.layout.items);
        listView.setAdapter(pinAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String biaoti= pinlun.get(position).getText1();

                String mingzi= pinlun.get(position).getTextm();
                Intent intent3 = new Intent(Mine2Activity.this, MinezActivity.class);
                intent3.putExtra("biaoti",bibb);
                intent3.putExtra("tupian",res);
                intent3.putExtra("mingzi",biaoaaa);
                startActivity(intent3);
                overridePendingTransition(
                        R.anim.in,//进入动画
                        R.anim.out//出去动画
                );
            }
        });
    }
    public static Bitmap getPicFromBytes(byte[] bytes, BitmapFactory.Options opts) {

        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,  opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;

    }


}
