package com.example.administrator.shixun.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.shixun.Entity.Discuss;
import com.example.administrator.shixun.R;

import java.util.List;

public class DiscussAdapter extends BaseAdapter{
    private List<Discuss> content;
    private Context context;
    private int item_layout_id;

    public DiscussAdapter(List<Discuss> content, Context context, int item_layout_id) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.discuss_item,null);
            viewHolder.img_user = convertView.findViewById(R.id.img_user);
            viewHolder.user = convertView.findViewById(R.id.user);
            viewHolder.time = convertView.findViewById(R.id.time);
            viewHolder.contents = convertView.findViewById(R.id.contents);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Discuss discuss = (Discuss) getItem(position);
        Glide.with(context).asBitmap().load(discuss.getImg_user()).into(viewHolder.img_user);
        viewHolder.user.setText(discuss.getUser());
        viewHolder.time.setText(discuss.getDate());
        viewHolder.contents.setText(discuss.getContent());
        return convertView;
    }

    static class ViewHolder{
        ImageView img_user;
        TextView user;
        TextView time;
        TextView contents;
    }
}
