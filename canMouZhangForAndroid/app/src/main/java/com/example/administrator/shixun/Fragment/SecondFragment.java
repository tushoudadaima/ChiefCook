package com.example.administrator.shixun.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.shixun.Adapter.CustomAdapter;
import com.example.administrator.shixun.ListActivity;
import com.example.administrator.shixun.R;
import com.example.administrator.shixun.SearchActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecondFragment  extends Fragment {

    private EditText etSearch;
    private TextView btSearch;
    private String Gname;
    private String Sname;
    private TextView gv_name;
    private EditText editText;
    private CustomAdapter customAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.secondfragment, container,false);
        editText = view.findViewById(R.id.tv_title);
        editText.setFocusable(false);
        editText.setCursorVisible(false);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //创建listView
        final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>() {
        };
        ListView listView = getActivity().findViewById(R.id.lv_list);
        final Map<String, Object> item1 = new HashMap<>();
        item1.put("name", "菜式");
        list.add(item1);
        Map<String, Object> item2 = new HashMap<>();
        item2.put("name", "菜系");
        list.add(item2);
        Map<String, Object> item3 = new HashMap<>();
        item3.put("name", "食材");
        list.add(item3);
        Map<String, Object> item4 = new HashMap<>();
        item4.put("name", "烹饪方法");
        list.add(item4);
        Map<String, Object> item5 = new HashMap<>();
        item5.put("name", "口味");
        list.add(item5);
        customAdapter = new CustomAdapter(getContext(), list, R.layout.list2_item);
        listView.setAdapter(customAdapter);
        //创建GridView
        final GridView gridView = getActivity().findViewById(R.id.gv_list);
        //gridview点击事件
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gv_name = view.findViewById(R.id.gv_name);
                Intent intent = new Intent(getActivity(), ListActivity.class);
                intent.putExtra("cai",gv_name.getText().toString());
                startActivity(intent);
            }
        });
        //菜式
        final List<Map<String, Object>> gvlistcaixi = new ArrayList<>();
        Map<String, Object> gvitem11 = new HashMap<>();
        gvitem11.put("name", "家常菜");
        gvitem11.put("image", R.drawable.jiachangcai);
        gvlistcaixi.add(gvitem11);
        Map<String, Object> gvitem12 = new HashMap<>();
        gvitem12.put("name", "私房菜");
        gvitem12.put("image", R.drawable.sifangcai);
        gvlistcaixi.add(gvitem12);
        Map<String, Object> gvitem13 = new HashMap<>();
        gvitem13.put("name", "凉菜");
        gvitem13.put("image", R.drawable.liangcai);
        gvlistcaixi.add(gvitem13);
        Map<String, Object> gvitem14 = new HashMap<>();
        gvitem14.put("name", "素菜");
        gvitem14.put("image", R.drawable.sucai);
        gvlistcaixi.add(gvitem14);
        Map<String, Object> gvitem15 = new HashMap<>();
        gvitem15.put("name", "下酒菜");
        gvitem15.put("image", R.drawable.xiajiucai);
        gvlistcaixi.add(gvitem15);
        Map<String, Object> gvitem16 = new HashMap<>();
        gvitem16.put("name", "海鲜");
        gvitem16.put("image", R.drawable.haixian);
        gvlistcaixi.add(gvitem16);
        Map<String, Object> gvitem17 = new HashMap<>();
        gvitem17.put("name", "汤");
        gvitem17.put("image", R.drawable.tang);
        gvlistcaixi.add(gvitem17);
        Map<String, Object> gvitem18 = new HashMap<>();
        gvitem18.put("name", "小吃");
        gvitem18.put("image", R.drawable.xiaochi);
        gvlistcaixi.add(gvitem18);
        //菜系
        final List<Map<String, Object>> gvlistshicai = new ArrayList<>();
        Map<String, Object> gvitem21 = new HashMap<>();
        gvitem21.put("name", "川菜");
        gvitem21.put("image", R.drawable.chuancai);
        gvlistshicai.add(gvitem21);
        Map<String, Object> gvitem22 = new HashMap<>();
        gvitem22.put("name", "湘菜");
        gvitem22.put("image", R.drawable.xiajiucai);
        gvlistshicai.add(gvitem22);
        Map<String, Object> gvitem23 = new HashMap<>();
        gvitem23.put("name", "粤菜");
        gvitem23.put("image", R.drawable.yuecai);
        gvlistshicai.add(gvitem23);
        Map<String, Object> gvitem24 = new HashMap<>();
        gvitem24.put("name", "闽菜");
        gvitem24.put("image", R.drawable.mincai);
        gvlistshicai.add(gvitem24);
        Map<String, Object> gvitem25 = new HashMap<>();
        gvitem25.put("name", "浙菜");
        gvitem25.put("image", R.drawable.zhecai);
        gvlistshicai.add(gvitem25);
        Map<String, Object> gvitem26 = new HashMap<>();
        gvitem26.put("name", "鲁菜");
        gvitem26.put("image", R.drawable.lucai);
        gvlistshicai.add(gvitem26);
        Map<String, Object> gvitem27 = new HashMap<>();
        gvitem27.put("name", "徽菜");
        gvitem27.put("image", R.drawable.huicai);
        gvlistshicai.add(gvitem27);
        Map<String, Object> gvitem28 = new HashMap<>();
        gvitem28.put("name", "豫菜");
        gvitem28.put("image", R.drawable.yucai);
        gvlistshicai.add(gvitem28);
        //食材
        final List<Map<String, Object>> gvlistweidao = new ArrayList<>();
        Map<String, Object> gvitem31 = new HashMap<>();
        gvitem31.put("name", "蓄肉类");
        gvitem31.put("image", R.drawable.rou);
        gvlistweidao.add(gvitem31);
        Map<String, Object> gvitem32 = new HashMap<>();
        gvitem32.put("name", "禽蛋类");
        gvitem32.put("image", R.drawable.dan);
        gvlistweidao.add(gvitem32);
        Map<String, Object> gvitem33 = new HashMap<>();
        gvitem33.put("name", "水产类");
        gvitem33.put("image", R.drawable.shuichan);
        gvlistweidao.add(gvitem33);
        Map<String, Object> gvitem34 = new HashMap<>();
        gvitem34.put("name", "蔬菜类");
        gvitem34.put("image", R.drawable.shucai);
        gvlistweidao.add(gvitem34);
        Map<String, Object> gvitem35 = new HashMap<>();
        gvitem35.put("name", "水果类");
        gvitem35.put("image", R.drawable.shuiguo);
        gvlistweidao.add(gvitem35);
        //烹饪方法
        final List<Map<String, Object>> gvlistpengren = new ArrayList<>();
        Map<String, Object> gvitem41 = new HashMap<>();
        gvitem41.put("name", "炒");
        gvitem41.put("image", R.drawable.chao);
        gvlistpengren.add(gvitem41);
        Map<String, Object> gvitem42 = new HashMap<>();
        gvitem42.put("name", "炸");
        gvitem42.put("image", R.drawable.zha);
        gvlistpengren.add(gvitem42);
        Map<String, Object> gvitem43 = new HashMap<>();
        gvitem43.put("name", "煎");
        gvitem43.put("image", R.drawable.jianbao);
        gvlistpengren.add(gvitem43);
        Map<String, Object> gvitem44 = new HashMap<>();
        gvitem44.put("name", "烤");
        gvitem44.put("image", R.drawable.kao);
        gvlistpengren.add(gvitem44);
        Map<String, Object> gvitem45 = new HashMap<>();
        gvitem45.put("name", "蒸");
        gvitem45.put("image", R.drawable.zheng);
        gvlistpengren.add(gvitem45);
        Map<String, Object> gvitem46 = new HashMap<>();
        gvitem46.put("name", "炖");
        gvitem46.put("image", R.drawable.dun);
        gvlistpengren.add(gvitem46);
        Map<String, Object> gvitem47 = new HashMap<>();
        gvitem47.put("name", "煮");
        gvitem47.put("image", R.drawable.zhu);
        gvlistpengren.add(gvitem47);
        Map<String, Object> gvitem48 = new HashMap<>();
        gvitem48.put("name", "涮");
        gvitem48.put("image", R.drawable.shuan);
        gvlistpengren.add(gvitem48);
        //口味
        final List<Map<String, Object>> gvlistrenqun = new ArrayList<>();
        Map<String, Object> gvitem51 = new HashMap<>();
        gvitem51.put("name", "酸");
        gvitem51.put("image", R.drawable.suan);
        gvlistrenqun.add(gvitem51);
        Map<String, Object> gvitem52 = new HashMap<>();
        gvitem52.put("name", "甜");
        gvitem52.put("image", R.drawable.tian);
        gvlistrenqun.add(gvitem52);
        Map<String, Object> gvitem53 = new HashMap<>();
        gvitem53.put("name", "苦");
        gvitem53.put("image", R.drawable.ku);
        gvlistrenqun.add(gvitem53);
        Map<String, Object> gvitem54 = new HashMap<>();
        gvitem54.put("name", "辣");
        gvitem54.put("image", R.drawable.la);
        gvlistrenqun.add(gvitem54);
        Map<String, Object> gvitem55 = new HashMap<>();
        gvitem55.put("name", "咸");
        gvitem55.put("image", R.drawable.xian);
        gvlistrenqun.add(gvitem55);
        CustomAdapter customAdaptercaixi = new CustomAdapter(getContext(), gvlistcaixi, R.layout.gridview_item);
        gridView.setAdapter(customAdaptercaixi);

        //list点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    //点击菜式
                    case 0:
                        customAdapter = new CustomAdapter(getContext(), gvlistcaixi, R.layout.gridview_item);
                        gridView.setAdapter(customAdapter);
                        break;
                    //点击菜系
                    case 1:
                        customAdapter = new CustomAdapter(getContext(), gvlistshicai, R.layout.gridview_item);
                        gridView.setAdapter(customAdapter);
                        break;
                    //点击食材
                    case 2:
                        customAdapter = new CustomAdapter(getContext(), gvlistweidao, R.layout.gridview_item);
                        gridView.setAdapter(customAdapter);
                        break;
                    //点击烹饪方法
                    case 3:
                        customAdapter = new CustomAdapter(getContext(), gvlistpengren, R.layout.gridview_item);
                        gridView.setAdapter(customAdapter);
                        break;
                    //点击口味
                    case 4:
                        customAdapter = new CustomAdapter(getContext(), gvlistrenqun, R.layout.gridview_item);
                        gridView.setAdapter(customAdapter);
                        break;

                }
            }
        });
    }
}
