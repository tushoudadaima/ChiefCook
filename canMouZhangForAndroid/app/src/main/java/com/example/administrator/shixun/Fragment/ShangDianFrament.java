package com.example.administrator.shixun.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.shixun.Adapter.CatograyAdapter;
import com.example.administrator.shixun.Adapter.GoodsAdapter;
import com.example.administrator.shixun.Adapter.ShopCartAdapter;
import com.example.administrator.shixun.Entity.Catogray;
import com.example.administrator.shixun.Entity.Goods;
import com.example.administrator.shixun.PayWorkActivity;
import com.example.administrator.shixun.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShangDianFrament extends Fragment {

    //控件
    private ListView lv_catogary, lv_good,lv_list_bottom;
    private ImageView iv_logo;
    private TextView tv_car,tv_delete_cart;
    private  TextView tv_count,tv_totle_money;
    Double totleMoney = 0.00;
    private TextView bv_unm;
    private RelativeLayout rl_bottom;
//    private MyApp myApp;
    //分类和商品
    private List<Catogray> list = new ArrayList<Catogray>();
    private List<Goods> list2 = new ArrayList<Goods>();
    private CatograyAdapter catograyAdapter;//分类的adapter
    private GoodsAdapter goodsAdapter;//分类下商品adapter
    private ShopCartAdapter shopCartAdapter;
//    ProductAdapter productAdapter;//底部购物车的adapter
//    GoodsDetailAdapter goodsDetailAdapter;//套餐详情的adapter
    private static DecimalFormat df;
    private RelativeLayout relativeLayout_left;
    //底部数据
    private SparseArray<Goods> selectedList;
    //套餐
    private List<Goods> list3 = new ArrayList<Goods>();
    private List<Goods> list4 = new ArrayList<Goods>();
    private List<Goods> list5 = new ArrayList<Goods>();
    private List<Goods> list6 = new ArrayList<Goods>();
    private List<Goods> list7 = new ArrayList<Goods>();
    private boolean isFirst = true;
    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    public static final String LOCAL_BROADCAST = "com.shixun.cast.LOCAL_BROADCAST";
    BottomSheetDialog bottomSheetDialog;
    private Goods goodsBean;

    private Handler mHanlder;
    private ViewGroup anim_mask_layout;//动画层
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shangdianframent, container,false);
//        myApp = (MyApp) getApplicationContext();
        mHanlder = new Handler(getContext().getMainLooper());
        lv_catogary = view.findViewById(R.id.lv_catogary);
        lv_good = view.findViewById(R.id.lv_good);
        tv_car = view.findViewById(R.id.tv_car);
        //底部控件
        rl_bottom = view.findViewById(R.id.rl_bottom);
        tv_count = view.findViewById(R.id.tv_count);
        bv_unm = view.findViewById(R.id.bv_unm);
        tv_totle_money= view.findViewById(R.id.tv_totle_money);
        relativeLayout_left= view.findViewById(R.id.rl_bottom);
        selectedList = new SparseArray<>();
        df = new DecimalFormat("0.00");

        initData();
        addListener();

        //发送广播
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localReceiver = new LocalReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction(LOCAL_BROADCAST);
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);

        relativeLayout_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initDialog();
            }
        });

        tv_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PayWorkActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    //填充数据
    private void initData() {
        //商品
        if(isFirst){
            //蔬菜
            goodsBean = new Goods();
            goodsBean.setTitle("白萝卜");
            goodsBean.setProduct_id(16);
            goodsBean.setCategory_id(16);
            goodsBean.setIcon(R.mipmap.bailuobo);
            goodsBean.setOriginal_price("2");
            goodsBean.setPrice("1");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("大葱");
            goodsBean.setProduct_id(17);
            goodsBean.setCategory_id(17);
            goodsBean.setIcon(R.mipmap.dacong);
            goodsBean.setOriginal_price("3");
            goodsBean.setPrice("2");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("大豆");
            goodsBean.setProduct_id(18);
            goodsBean.setCategory_id(18);
            goodsBean.setIcon(R.mipmap.dadou);
            goodsBean.setOriginal_price("7");
            goodsBean.setPrice("5");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("豆角");
            goodsBean.setProduct_id(19);
            goodsBean.setCategory_id(19);
            goodsBean.setIcon(R.mipmap.doujiao);
            goodsBean.setOriginal_price("7");
            goodsBean.setPrice("4");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("红萝卜");
            goodsBean.setProduct_id(20);
            goodsBean.setCategory_id(20);
            goodsBean.setIcon(R.mipmap.hongluobo);
            goodsBean.setOriginal_price("10");
            goodsBean.setPrice("7");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("黄瓜");
            goodsBean.setProduct_id(21);
            goodsBean.setCategory_id(21);
            goodsBean.setIcon(R.mipmap.huanggua);
            goodsBean.setOriginal_price("7");
            goodsBean.setPrice("6");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("尖椒");
            goodsBean.setProduct_id(22);
            goodsBean.setCategory_id(22);
            goodsBean.setIcon(R.mipmap.jianjiao);
            goodsBean.setOriginal_price("2");
            goodsBean.setPrice("6.5");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("韭菜");
            goodsBean.setProduct_id(23);
            goodsBean.setCategory_id(23);
            goodsBean.setIcon(R.mipmap.jiucai);
            goodsBean.setOriginal_price("4");
            goodsBean.setPrice("2.5");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("卷心菜");
            goodsBean.setProduct_id(24);
            goodsBean.setCategory_id(24);
            goodsBean.setIcon(R.mipmap.juanxincai);
            goodsBean.setOriginal_price("8.6");
            goodsBean.setPrice("5.7");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("苦瓜");
            goodsBean.setProduct_id(25);
            goodsBean.setCategory_id(25);
            goodsBean.setIcon(R.mipmap.kugua);
            goodsBean.setOriginal_price("2");
            goodsBean.setPrice("3.4");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("莲藕");
            goodsBean.setProduct_id(26);
            goodsBean.setCategory_id(26);
            goodsBean.setIcon(R.mipmap.lianou);
            goodsBean.setOriginal_price("9");
            goodsBean.setPrice("6.8");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("南瓜");
            goodsBean.setProduct_id(27);
            goodsBean.setCategory_id(27);
            goodsBean.setIcon(R.mipmap.nangua);
            goodsBean.setOriginal_price("8");
            goodsBean.setPrice("4");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("茄子");
            goodsBean.setProduct_id(28);
            goodsBean.setCategory_id(28);
            goodsBean.setIcon(R.mipmap.qiezi);
            goodsBean.setOriginal_price("8.6");
            goodsBean.setPrice("4.7");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("芹菜");
            goodsBean.setProduct_id(29);
            goodsBean.setCategory_id(29);
            goodsBean.setIcon(R.mipmap.qincai);
            goodsBean.setOriginal_price("8");
            goodsBean.setPrice("4");
            list3.add(goodsBean);goodsBean = new Goods();
            goodsBean.setTitle("小油菜");
            goodsBean.setProduct_id(30);
            goodsBean.setCategory_id(30);
            goodsBean.setIcon(R.mipmap.youcai);
            goodsBean.setOriginal_price("5");
            goodsBean.setPrice("2.7");
            list3.add(goodsBean);goodsBean = new Goods();
            goodsBean.setTitle("青椒");
            goodsBean.setProduct_id(31);
            goodsBean.setCategory_id(31);
            goodsBean.setIcon(R.mipmap.qingjiao);
            goodsBean.setOriginal_price("7.5");
            goodsBean.setPrice("4.7");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("秋葵");
            goodsBean.setProduct_id(32);
            goodsBean.setCategory_id(32);
            goodsBean.setIcon(R.mipmap.qiukui);
            goodsBean.setOriginal_price("10.6");
            goodsBean.setPrice("6.7");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("生菜");
            goodsBean.setProduct_id(33);
            goodsBean.setCategory_id(33);
            goodsBean.setIcon(R.mipmap.shengcai);
            goodsBean.setOriginal_price("2.6");
            goodsBean.setPrice("1.4");
            list3.add(goodsBean);goodsBean = new Goods();
            goodsBean.setTitle("生姜");
            goodsBean.setProduct_id(34);
            goodsBean.setCategory_id(34);
            goodsBean.setIcon(R.mipmap.shengjiang);
            goodsBean.setOriginal_price("10.6");
            goodsBean.setPrice("9.4");
            list3.add(goodsBean);goodsBean = new Goods();
            goodsBean.setTitle("丝瓜");
            goodsBean.setProduct_id(35);
            goodsBean.setCategory_id(35);
            goodsBean.setIcon(R.mipmap.sigua);
            goodsBean.setOriginal_price("5.4");
            goodsBean.setPrice("4.1");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("土豆");
            goodsBean.setProduct_id(36);
            goodsBean.setCategory_id(36);
            goodsBean.setIcon(R.mipmap.tudou);
            goodsBean.setOriginal_price("5");
            goodsBean.setPrice("3.2");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("豌豆");
            goodsBean.setProduct_id(37);
            goodsBean.setCategory_id(37);
            goodsBean.setIcon(R.mipmap.wandou);
            goodsBean.setOriginal_price("5.6");
            goodsBean.setPrice("4.6");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("香菜");
            goodsBean.setProduct_id(38);
            goodsBean.setCategory_id(38);
            goodsBean.setIcon(R.mipmap.xiangcai);
            goodsBean.setOriginal_price("5.4");
            goodsBean.setPrice("2.4");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("西红柿");
            goodsBean.setProduct_id(39);
            goodsBean.setCategory_id(39);
            goodsBean.setIcon(R.mipmap.xihongshi);
            goodsBean.setOriginal_price("7.5");
            goodsBean.setPrice("4.7");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("西蓝花");
            goodsBean.setProduct_id(40);
            goodsBean.setCategory_id(40);
            goodsBean.setIcon(R.mipmap.xilanhua);
            goodsBean.setOriginal_price("7.8");
            goodsBean.setPrice("4.6");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("洋葱");
            goodsBean.setProduct_id(41);
            goodsBean.setCategory_id(41);
            goodsBean.setIcon(R.mipmap.yangcong);
            goodsBean.setOriginal_price("3.6");
            goodsBean.setPrice("2.5");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("玉米");
            goodsBean.setProduct_id(42);
            goodsBean.setCategory_id(42);
            goodsBean.setIcon(R.mipmap.yumi);
            goodsBean.setOriginal_price("5.6");
            goodsBean.setPrice("3.5");
            list3.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("小瓜");
            goodsBean.setProduct_id(43);
            goodsBean.setCategory_id(43);
            goodsBean.setIcon(R.mipmap.xiaogua);
            goodsBean.setOriginal_price("200");
            goodsBean.setPrice("100");
            list3.add(goodsBean);

            //水果
            goodsBean = new Goods();
            goodsBean.setTitle("菠萝");
            goodsBean.setProduct_id(4);
            goodsBean.setCategory_id(4);
            goodsBean.setIcon(R.mipmap.boluo);
            goodsBean.setOriginal_price("80");
            goodsBean.setPrice("60");
            list4.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("草莓");
            goodsBean.setProduct_id(5);
            goodsBean.setCategory_id(5);
            goodsBean.setIcon(R.mipmap.caomei);
            goodsBean.setOriginal_price("80");
            goodsBean.setPrice("60");
            list4.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("哈密瓜");
            goodsBean.setProduct_id(6);
            goodsBean.setCategory_id(6);
            goodsBean.setIcon(R.mipmap.hamigua);
            goodsBean.setOriginal_price("80");
            goodsBean.setPrice("60");
            list4.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("梨");
            goodsBean.setProduct_id(7);
            goodsBean.setCategory_id(7);
            goodsBean.setIcon(R.mipmap.li);
            goodsBean.setOriginal_price("80");
            goodsBean.setPrice("60");
            list4.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("芒果");
            goodsBean.setProduct_id(8);
            goodsBean.setCategory_id(8);
            goodsBean.setIcon(R.mipmap.mangguo);
            goodsBean.setOriginal_price("80");
            goodsBean.setPrice("60");
            list4.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("牛油果");
            goodsBean.setProduct_id(9);
            goodsBean.setCategory_id(9);
            goodsBean.setIcon(R.mipmap.niuyouguo);
            goodsBean.setOriginal_price("80");
            goodsBean.setPrice("60");
            list4.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("苹果");
            goodsBean.setProduct_id(10);
            goodsBean.setCategory_id(10);
            goodsBean.setIcon(R.mipmap.pingguo);
            goodsBean.setOriginal_price("80");
            goodsBean.setPrice("60");
            list4.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("桑葚");
            goodsBean.setProduct_id(11);
            goodsBean.setCategory_id(11);
            goodsBean.setIcon(R.mipmap.sangshen);
            goodsBean.setOriginal_price("80");
            goodsBean.setPrice("60");
            list4.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("山竹");
            goodsBean.setProduct_id(12);
            goodsBean.setCategory_id(12);
            goodsBean.setIcon(R.mipmap.shanzhu);
            goodsBean.setOriginal_price("80");
            goodsBean.setPrice("60");
            list4.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("桃");
            goodsBean.setProduct_id(13);
            goodsBean.setCategory_id(13);
            goodsBean.setIcon(R.mipmap.tao);
            goodsBean.setOriginal_price("80");
            goodsBean.setPrice("60");
            list4.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("西瓜");
            goodsBean.setProduct_id(14);
            goodsBean.setCategory_id(14);
            goodsBean.setIcon(R.mipmap.xigua);
            goodsBean.setOriginal_price("80");
            goodsBean.setPrice("60");
            list4.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("柚子");
            goodsBean.setProduct_id(15);
            goodsBean.setCategory_id(15);
            goodsBean.setIcon(R.mipmap.youzi);
            goodsBean.setOriginal_price("80");
            goodsBean.setPrice("60");
            list4.add(goodsBean);

            //水产
            goodsBean = new Goods();
            goodsBean.setTitle("草鱼");
            goodsBean.setProduct_id(1);
            goodsBean.setCategory_id(1);
            goodsBean.setIcon(R.mipmap.caoyu);
            goodsBean.setOriginal_price("40");
            goodsBean.setPrice("20");
            list5.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("鲫鱼");
            goodsBean.setProduct_id(2);
            goodsBean.setCategory_id(2);
            goodsBean.setIcon(R.mipmap.jiyu);
            goodsBean.setOriginal_price("40");
            goodsBean.setPrice("20");
            list5.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("鲤鱼");
            goodsBean.setProduct_id(3);
            goodsBean.setCategory_id(3);
            goodsBean.setIcon(R.mipmap.liyu);
            goodsBean.setOriginal_price("40");
            goodsBean.setPrice("20");
            list5.add(goodsBean);

            //肉
            goodsBean = new Goods();
            goodsBean.setTitle("鸡蛋");
            goodsBean.setProduct_id(44);
            goodsBean.setCategory_id(44);
            goodsBean.setIcon(R.mipmap.jidan);
            goodsBean.setOriginal_price("40");
            goodsBean.setPrice("20");
            list6.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("牛肉");
            goodsBean.setProduct_id(45);
            goodsBean.setCategory_id(45);
            goodsBean.setIcon(R.mipmap.niurou);
            goodsBean.setOriginal_price("40");
            goodsBean.setPrice("20");
            list6.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("排骨");
            goodsBean.setProduct_id(46);
            goodsBean.setCategory_id(46);
            goodsBean.setIcon(R.mipmap.paigu);
            goodsBean.setOriginal_price("40");
            goodsBean.setPrice("20");
            list6.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("五花肉");
            goodsBean.setProduct_id(47);
            goodsBean.setCategory_id(47);
            goodsBean.setIcon(R.mipmap.wuhuarou);
            goodsBean.setOriginal_price("40");
            goodsBean.setPrice("20");
            list6.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("羊肉");
            goodsBean.setProduct_id(48);
            goodsBean.setCategory_id(48);
            goodsBean.setIcon(R.mipmap.yangrou);
            goodsBean.setOriginal_price("40");
            goodsBean.setPrice("20");
            list6.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("猪瘦肉");
            goodsBean.setProduct_id(49);
            goodsBean.setCategory_id(49);
            goodsBean.setIcon(R.mipmap.zhushourou);
            goodsBean.setOriginal_price("40");
            goodsBean.setPrice("20");
            list6.add(goodsBean);
            //菌
            goodsBean = new Goods();
            goodsBean.setTitle("香菇");
            goodsBean.setProduct_id(50);
            goodsBean.setCategory_id(50);
            goodsBean.setIcon(R.mipmap.xianggu);
            goodsBean.setOriginal_price("40");
            goodsBean.setPrice("20");
            list7.add(goodsBean);
            goodsBean = new Goods();
            goodsBean.setTitle("杏鲍菇");
            goodsBean.setProduct_id(51);
            goodsBean.setCategory_id(51);
            goodsBean.setIcon(R.mipmap.xingbaogu);
            goodsBean.setOriginal_price("40");
            goodsBean.setPrice("20");
            list7.add(goodsBean);


            Catogray catograyBean3 = new Catogray();
            catograyBean3.setCount(3);
            catograyBean3.setKind("新鲜蔬菜");
            catograyBean3.setList(list3);
            list.add(catograyBean3);

            Catogray catograyBean4 = new Catogray();
            catograyBean4.setCount(4);
            catograyBean4.setKind("时令水果");
            catograyBean4.setList(list4);
            list.add(catograyBean4);

            Catogray catograyBean5 = new Catogray();
            catograyBean5.setCount(5);
            catograyBean5.setKind("精致水产");
            catograyBean5.setList(list5);
            list.add(catograyBean5);

            Catogray catograyBean6 = new Catogray();
            catograyBean6.setCount(6);
            catograyBean6.setKind("禽肉蛋奶");
            catograyBean6.setList(list6);
            list.add(catograyBean6);

            Catogray catograyBean7 = new Catogray();
            catograyBean7.setCount(7);
            catograyBean7.setKind("美味菌类");
            catograyBean7.setList(list7);
            list.add(catograyBean7);

            isFirst = false;
        }


        //默认值
        list2.clear();
        list2.addAll(list.get(0).getList());

        //分类
        catograyAdapter = new CatograyAdapter(getContext(), list);
        lv_catogary.setAdapter(catograyAdapter);
        catograyAdapter.notifyDataSetChanged();
        //商品
        goodsAdapter = new GoodsAdapter(getContext(), list2, catograyAdapter,selectedList);
        lv_good.setAdapter(goodsAdapter);
        goodsAdapter.notifyDataSetChanged();

    }


    //添加监听
    private void addListener() {
        lv_catogary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("fyg","list.get(position).getList():"+list.get(position).getList());
                list2.clear();
                list2.addAll(list.get(position).getList());
                catograyAdapter.setSelection(position);
                catograyAdapter.notifyDataSetChanged();
                goodsAdapter.notifyDataSetChanged();
            }
        });
    }





    //创建套餐详情view
//    public void showDetailSheet(List<ItemBean> listItem, String mealName){
//        bottomDetailSheet = createMealDetailView(listItem,mealName);
//        if(bottomSheetLayout.isSheetShowing()){
//            bottomSheetLayout.dismissSheet();
//        }else {
//            if(listItem.size()!=0){
//                bottomSheetLayout.showWithSheetView(bottomDetailSheet);
//            }
//        }
//    }

    //查看套餐详情
//    private View createMealDetailView(List<ItemBean> listItem, String mealName){
//        View view = LayoutInflater.from(this).inflate(R.layout.activity_goods_detail,(ViewGroup) getWindow().getDecorView(),false);
//        ListView lv_product = (MyListView) view.findViewById(R.id.lv_product);
//        TextView tv_meal = (TextView) view.findViewById(R.id.tv_meal);
//        TextView tv_num = (TextView) view.findViewById(R.id.tv_num);
//        int count=0;
//        for(int i=0;i<listItem.size();i++){
//            count = count+Integer.parseInt(listItem.get(i).getNote2());
//        }
//        tv_meal.setText(mealName);
//        tv_num.setText("(共"+count+"件)");
//        goodsDetailAdapter = new GoodsDetailAdapter(MainActivity.this,listItem);
//        lv_product.setAdapter(goodsDetailAdapter);
//        goodsDetailAdapter.notifyDataSetChanged();
//        return view;
//    }





    //创建购物车view
//    private void showBottomSheet(){
//        bottomSheet = createBottomSheetView();
//        if(bottomSheetLayout.isSheetShowing()){
//            bottomSheetLayout.dismissSheet();
//        }else {
//            if(selectedList.size()!=0){
//                bottomSheetLayout.showWithSheetView(bottomSheet);
//            }
//        }
//    }




    //查看购物车布局
//    private View createBottomSheetView(){
//        View view = LayoutInflater.from(this).inflate(R.layout.layout_bottom_sheet,(ViewGroup) getWindow().getDecorView(),false);
//        MyListView lv_product = (MyListView) view.findViewById(R.id.lv_product);
//        TextView clear = (TextView) view.findViewById(R.id.clear);
//        clear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                clearCart();
//            }
//        });
//        productAdapter = new ProductAdapter(MainActivity.this,goodsAdapter, selectedList);
//        lv_product.setAdapter(productAdapter);
//        return view;
//    }

    //清空购物车
    public void clearCart(){
        selectedList.clear();
        list2.clear();
        if (list.size() > 0) {
            for (int j=0;j<list.size();j++){
                list.get(j).setCount(0);
                for(int i=0;i<list.get(j).getList().size();i++){
                    list.get(j).getList().get(i).setNum(0);
                }
            }
            list2.addAll(list.get(0).getList());
            catograyAdapter.setSelection(0);
            //刷新不能删
            catograyAdapter.notifyDataSetChanged();
            goodsAdapter.notifyDataSetChanged();
        }
        update(true);
        bottomSheetDialog.cancel();
    }


    //根据商品id获取当前商品的采购数量
    public int getSelectedItemCountById(int id){
        Goods temp = selectedList.get(id);
        if(temp==null){
            return 0;
        }
        return temp.getNum();
    }


    public void handlerCarNum(int type, Goods goodsBean, boolean refreshGoodList){
        if (type == 0) {
            Goods temp = selectedList.get(goodsBean.getProduct_id());
            if(temp!=null){
                if(temp.getNum()<2){
                    goodsBean.setNum(0);
                    selectedList.remove(goodsBean.getProduct_id());

                }else{
                    int i =  goodsBean.getNum();
                    goodsBean.setNum(--i);
                }
            }
        } else if (type == 1) {
            Goods temp = selectedList.get(goodsBean.getProduct_id());
            if(temp==null){
                goodsBean.setNum(1);
                selectedList.append(goodsBean.getProduct_id(), goodsBean);
            }else{
                int i= goodsBean.getNum();
                goodsBean.setNum(++i);
            }
        }
        update(refreshGoodList);
    }



    //刷新布局 总价、购买数量等
    private void update(boolean refreshGoodList){
        int size = selectedList.size();
        int count =0;
        for(int i=0;i<size;i++){
            Goods item = selectedList.valueAt(i);
            count += item.getNum();
            totleMoney += item.getNum()*Double.parseDouble(item.getPrice());
        }
        tv_totle_money.setText("￥"+String.valueOf(df.format(totleMoney)));
        totleMoney = 0.00;
        if(count<1){
            bv_unm.setVisibility(View.GONE);
        }else{
            bv_unm.setVisibility(View.VISIBLE);
        }

        bv_unm.setText(String.valueOf(count));

//        if(productAdapter!=null){
//            productAdapter.notifyDataSetChanged();
//        }

        if(goodsAdapter!=null){
            goodsAdapter.notifyDataSetChanged();
        }

        if(catograyAdapter!=null){
            catograyAdapter.notifyDataSetChanged();
        }

//        if(bottomSheetLayout.isSheetShowing() && selectedList.size()<1){
//            bottomSheetLayout.dismissSheet();
//        }
    }


    /**
     * @Description: 创建动画层
     * @param
     * @return void
     * @throws
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup)getActivity().getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE-1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(final ViewGroup parent, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    public void setAnim(final View v, int[] startLocation) {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, v, startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        tv_car.getLocationInWindow(endLocation);
        // 计算位移
        int endX = 0 - startLocation[0] + 40;// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标

        TranslateAnimation translateAnimationX = new TranslateAnimation(0,endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationY.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(800);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }
        });

    }

    private class LocalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
//            selectedList = (SparseArray<Goods>)intent.getSerializableExtra("objectList");
            if(action.equals(LOCAL_BROADCAST)){
                goodsAdapter.notifyDataSetChanged();
                update(true);
            }
        }
    }

    public void onDestroy(){
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    private void initDialog(){
//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
//        bottomSheetDialog.setContentView(R.layout.activity_dialog_goods);
//        lv_list_bottom.setAdapter(shopCartAdapter);
//        bottomSheetDialog.show();

        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_dialog_goods,null);
        lv_list_bottom = view.findViewById(R.id.lv_list_bottom);
        tv_delete_cart = view.findViewById(R.id.tv_delete_cart);
        shopCartAdapter = new ShopCartAdapter(getContext(),selectedList);
        lv_list_bottom.setAdapter(shopCartAdapter);
        tv_delete_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCart();
            }
        });
        bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

//        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
//        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_dialog_goods,null);
//        bottomSheetDialog.setContentView(view);
//        bottomSheetDialog.setCancelable(true);
//        bottomSheetDialog.setCanceledOnTouchOutside(true);
//        bottomSheetDialog.show();
    }
}
