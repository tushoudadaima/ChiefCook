package com.example.administrator.shixun.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.shixun.Entity.Meterial;
import com.example.administrator.shixun.R;

import java.util.List;

public class MeterialAdapter extends BaseAdapter {
    private List<Meterial> content;
    private Context context;
    private int item_layout_id;

    public MeterialAdapter(List<Meterial> content, Context context, int item_layout_id) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.meterial_layout,null);
            viewholder = new ViewHolder();
            viewholder.vname = convertView.findViewById(R.id.vname);
            viewholder.count = convertView.findViewById(R.id.count);
            convertView.setTag(viewholder);
        }else {
            viewholder = (ViewHolder) convertView.getTag();
        }
        Meterial meterial = (Meterial) getItem(position);
        viewholder.vname.setText(meterial.getVname());
        viewholder.count.setText(meterial.getCount());
        return convertView;
    }

    static class ViewHolder{
        TextView vname;
        TextView count;
    }
}