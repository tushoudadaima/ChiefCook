package com.example.administrator.shixun.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.shixun.Adapter.FirstAdapter;
import com.example.administrator.shixun.Entity.Show;
import com.example.administrator.shixun.ListActivity;
import com.example.administrator.shixun.LoadingDialog;
import com.example.administrator.shixun.MyApplication;
import com.example.administrator.shixun.R;
import com.example.administrator.shixun.SearchActivity;
import com.example.administrator.shixun.SimpleActivity;
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

public class FirstFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private com.example.administrator.shixun.SearchView searchView;
    private SmartRefreshLayout srl;
    private RelativeLayout click1;
    private RelativeLayout click2;
    private RelativeLayout click3;
    private RelativeLayout click4;
    private TextView jia;
    private TextView gao;
    private TextView hai;
    private TextView duo;
    private CustomOnClickListener customOnClickListener;
    private EditText editText;
    private View view;
    private ListView listView;
    private FirstAdapter firstAdapter;
    private Handler handler;
    private String str;
    private LoadingDialog loadingDialog;
    private List<Show> list = new ArrayList<>();

    private ViewPager viewPager;
    private int[] imageResIds;
    private ArrayList<ImageView> imageViewList;
    private LinearLayout ll_point_container;
    private String[] contentDescs;
    private TextView tv_desc;
    private int previousSelectedPosition = 0;
    boolean isRunning = false;
    private Activity activity;
    private String MUrl;
    @SuppressLint("HandlerLeak")
    @Override
    public Context getContext() {
        if(activity == null){
            return MyApplication.getmInstance();
        }
        return activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Model数据
        initData();
        // Controller 控制器
        initAdapter();
        // 开启轮询
        new Thread() {
            public void run() {
                isRunning = true;
                while (isRunning) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 往下跳一位
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        }
                    });
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    private void initAdapter() {
        ll_point_container.getChildAt(0).setEnabled(true);
        tv_desc.setText(contentDescs[0]);
        previousSelectedPosition = 0;

        // 设置适配器
        viewPager.setAdapter(new MyAdapter());

        // 默认设置到中间的某个位置
        int pos = Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % imageViewList.size());
        // 2147483647 / 2 = 1073741823 - (1073741823 % 5)
        viewPager.setCurrentItem(5000000); // 设置到某个位置
    }

    private void initData() {
        // 图片资源id数组
        imageResIds = new int[]{R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};

        // 文本描述
        contentDescs = new String[]{
                "红烧肉",
                "鱼香肉丝",
                "西红柿炒鸡蛋",
                "可乐鸡翅",

        };

        // 初始化要展示的5个ImageView
        imageViewList = new ArrayList<ImageView>();

        ImageView imageView;
        View pointView;
        LinearLayout.LayoutParams layoutParams;
        for (int i = 0; i < imageResIds.length; i++) {
            // 初始化要显示的图片对象
            imageView = new ImageView(this.getContext());
            imageView.setBackgroundResource(imageResIds[i]);
            imageViewList.add(imageView);
            // 加小白点, 指示器
            pointView = new View(this.getContext());
            pointView.setBackgroundResource(R.drawable.dian);
            layoutParams = new LinearLayout.LayoutParams(5, 5);
            if (i != 0)
                layoutParams.leftMargin = 10;
            // 设置默认所有都不可用
            pointView.setEnabled(false);
            ll_point_container.addView(pointView, layoutParams);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.firstfragment, container,false);
            viewPager = (ViewPager)view.findViewById(R.id.viewpager);
            viewPager.setOnPageChangeListener(this);// 设置页面更新监听
            ll_point_container = (LinearLayout)view. findViewById(R.id.ll_point_container);
            tv_desc = view.findViewById(R.id.tv_desc);
            srl = view.findViewById(R.id.srl);
            listView = view.findViewById(R.id.list);
            searchView = view.findViewById(R.id.search);
            editText = view.findViewById(R.id.tv_title);
            jia = view.findViewById(R.id.jia);
            gao = view.findViewById(R.id.gao);
            hai = view.findViewById(R.id.hai);
            duo = view.findViewById(R.id.duo);
            click1 = view.findViewById(R.id.click1);
            click2 = view.findViewById(R.id.click2);
            click3 = view.findViewById(R.id.click3);
            click4 = view.findViewById(R.id.click4);
            customOnClickListener  = new CustomOnClickListener();
            click1.setOnClickListener(customOnClickListener);
            click2.setOnClickListener(customOnClickListener);
            click3.setOnClickListener(customOnClickListener);
            click4.setOnClickListener(customOnClickListener);
            editText.setFocusable(false);
            editText.setCursorVisible(false);
            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    startActivity(intent);
                }
            });
            list.clear();
            loadingDialog = new LoadingDialog(getContext());
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
                            int zan = random.nextInt(20);
                            String[] a = str.split("&");
                            String[] b = a[0].split("=");
                            String[] c = a[1].split("=");
                            String[] d = a[2].split("=");
                            Show show = new Show(b[1],c[1],d[1],eye+"",zan+"");
                            list.add(show);
                            break;
                        case 200:
                            loadingDialog.dismiss();
                            firstAdapter = new FirstAdapter(list,getActivity(),R.layout.item_layout);
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
                    Intent intent = new Intent(getActivity(), SimpleActivity.class);
                    intent.putExtra("name",list.get(position).getName());
                    startActivity(intent);
                }
            });

            srl.setFooterHeight(50);
            srl.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    listView.setAdapter(null);
                    list.clear();
                    loadingDialog = new LoadingDialog(getContext());
                    loadingDialog.show();
                    showName();
                    srl.finishRefresh(4000);
                }
            });

            srl.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    int a = listView.getCount();
                    if (a < 30){
                        showName();
                        srl.finishLoadMore(4000);
                    }else {
                        srl.finishLoadMoreWithNoMoreData();
                    }

                }
            });
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            editText.clearFocus();
            editText.setCursorVisible(false);
            parent.removeView(view);
        }
        return view;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        // 新的条目被选中时调用
        int newPosition = position % imageViewList.size();

        //设置文本
        tv_desc.setText(contentDescs[newPosition]);
        // 把之前的禁用, 把最新的启用, 更新指示器
        ll_point_container.getChildAt(previousSelectedPosition).setEnabled(false);
        ll_point_container.getChildAt(newPosition).setEnabled(true);
        // 记录之前的位置
        previousSelectedPosition = newPosition;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        // 3. 指定复用的判断逻辑, 固定写法
        @Override
        public boolean isViewFromObject(View view, Object object) {
//			System.out.println("isViewFromObject: "+(view == object));
            // 当划到新的条目, 又返回来, view是否可以被复用.
            // 返回判断规则
            return view == object;
        }

        // 1. 返回要显示的条目内容, 创建条目
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // container: 容器: ViewPager
            // position: 当前要显示条目的位置 0 -> 4

//			newPosition = position % 5
            int newPosition = position % imageViewList.size();

            ImageView imageView = imageViewList.get(newPosition);
            // a. 把View对象添加到container中
            container.addView(imageView);
            // b. 把View对象返回给框架, 适配器
            return imageView; // 必须重写, 否则报异常
        }

        // 2. 销毁条目
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // object 要销毁的对象
            container.removeView((View) object);
        }
    }

    private void showName(){
        new Thread(){
            @Override
            public void run() {
                try {
                    String[] food = {"家常菜","减肥餐","下饭菜","快手菜","宵夜"};
                    Random random = new Random();
                    String foods = food[random.nextInt(4)];
                    MUrl = getResources().getText(R.string.num).toString();
                    URL url = new URL(MUrl+"/name?food="+foods);
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

    class CustomOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.click1:
                    Intent intent1 = new Intent(getActivity(), ListActivity.class);
                    intent1.putExtra("cai",jia.getText().toString());
                    startActivity(intent1);
                    break;
                case R.id.click2:
                    Intent intent2 = new Intent(getActivity(), ListActivity.class);
                    intent2.putExtra("cai",gao.getText().toString());
                    startActivity(intent2);
                    break;
                case R.id.click3:
                    Intent intent3 = new Intent(getActivity(), ListActivity.class);
                    intent3.putExtra("cai",hai.getText().toString());
                    startActivity(intent3);
                    break;
                case R.id.click4:
                    Intent intent4 = new Intent(getActivity(), ListActivity.class);
                    intent4.putExtra("cai",duo.getText().toString());
                    startActivity(intent4);
                    break;
            }
        }
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
