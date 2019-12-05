package com.example.administrator.shixun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.administrator.shixun.Fragment.FirstFragment;
import com.example.administrator.shixun.Fragment.FourFragment;
import com.example.administrator.shixun.Fragment.SecondFragment;
import com.example.administrator.shixun.Fragment.ThreeFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

;


public class MainActivity extends AppCompatActivity {

    private Map<String, ImageView> imageViewMap = new HashMap<>();
    public List<Map<String, Object>> dataSource;
    private String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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