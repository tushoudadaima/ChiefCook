package com.example.administrator.shixun.Adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.shixun.Entity.Goods;
import com.example.administrator.shixun.R;

public class ShopCartAdapter extends BaseAdapter {

    private Context context;
    private SparseArray<Goods> list;
    private TextView textView;
    private TextView textView1;
    private Goods goods;

    public ShopCartAdapter(Context context,SparseArray<Goods> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(list.keyAt(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_dialog_goods_item,null);
        goods = list.get(list.keyAt(position));
        textView = view.findViewById(R.id.tv_shopcart);
        textView1 = view.findViewById(R.id.tv_shopcart_two);
        textView.setText(goods.getTitle());
        textView1.setText("价格："+goods.getNum()*Double.parseDouble(goods.getPrice()));
        return view;
    }
}
