package com.example.administrator.shixun.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.shixun.Entity.Show;
import com.example.administrator.shixun.R;

import java.util.List;

public class FirstAdapter extends BaseAdapter {
    private List<Show> content;
    private Context context;
    private int item_layout_id;

    public FirstAdapter(List<Show> content, Context context, int item_layout_id) {
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
        ViewHolder viewholder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_layout,null);
            viewholder = new ViewHolder();
            viewholder.txt_name = convertView.findViewById(R.id.txt_name);
            viewholder.txt_description = convertView.findViewById(R.id.txt_description);
            viewholder.img = convertView.findViewById(R.id.img_list);
            convertView.setTag(viewholder);
        }else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        Show show = (Show) getItem(position);
        viewholder.txt_name.setText(show.getName());
        viewholder.txt_description.setText(show.getDescription());
        Glide.with(context).asBitmap().load(show.getImgUrl()).into(viewholder.img);
        return convertView;
    }

    static class ViewHolder{
        TextView txt_name;
        TextView txt_description;
        ImageView img;
    }


}
