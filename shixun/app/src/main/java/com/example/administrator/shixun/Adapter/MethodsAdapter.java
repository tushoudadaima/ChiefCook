package com.example.administrator.shixun.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.shixun.Entity.Methods;
import com.example.administrator.shixun.R;

import java.util.List;

public class MethodsAdapter extends BaseAdapter {
    private List<Methods> content;
    private Context context;
    private int item_layout_id;

    public MethodsAdapter(List<Methods> content, Context context, int item_layout_id) {
        this.content = content;
        this.context = context;
        this.item_layout_id = item_layout_id;
    }

    @Override
    public int getCount() {
        return content.size();
    }

    @Override
    public Object getItem(int position) {
        return content.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.methods_layout,null);
            viewHolder.txt_method = convertView.findViewById(R.id.txt_method);
            viewHolder.img_method = convertView.findViewById(R.id.img_method);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Methods methods = (Methods) getItem(position);
        viewHolder.txt_method.setText(methods.getStep());
        Glide.with(context).asBitmap().load(methods.getImg()).into(viewHolder.img_method);
        return convertView;
    }

    static class ViewHolder{
        TextView txt_method;
        ImageView img_method;
    }
}
