package com.example.administrator.shixun.Send;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.shixun.R;

import java.util.ArrayList;
import java.util.List;

public class PinAdapter extends BaseAdapter{
    private List<Pin> dingdans = new ArrayList<>();//数据源
    private int itemLayoutId;
    private Context context;

    //构造方法
    public PinAdapter(Context context, List<Pin> dingdans, int itemLayoutId){

        this.context = context;
        this.dingdans = dingdans;
        this.itemLayoutId = itemLayoutId;
    }

    @Override
    //数据条数
    public int getCount() {
        if (null != dingdans) {
            return dingdans.size();
        }else {
            return 0;
        }
    }

    @Override
    //每一项要显示的数据
    public Object getItem(int position) {
        if (null != dingdans) {
            return dingdans.get(position);
        }else {
            return null;
        }
    }

    @Override
    //获得id参数值
    public long getItemId(int position) {
        return position;
    }

    @Override
    //加载item布局文件，生成每项item对应的视图对象
    public View getView(final int position, View convertView, ViewGroup parent) {
        //加载item对应的布局文件
        if (null == convertView) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(itemLayoutId, null);
        }

        //获取每一个item中各种视图控件的对象
        TextView text1 = convertView.findViewById(R.id.text11);
        TextView textm = convertView.findViewById(R.id.pinming);
        TextView textp = convertView.findViewById(R.id.pinpin);
        TextView textz = convertView.findViewById(R.id.pinzan);
        ImageView img = convertView.findViewById(R.id.imgg);
        text1.setText(dingdans.get(position).getText1());
        textm.setText(dingdans.get(position).getTextm());
        textp.setText(dingdans.get(position).getTextp());
        textz.setText(dingdans.get(position).getTextz());
        img.setImageResource(dingdans.get(position).getImgg());





////        获取删除按钮对象
//        Button delete = convertView.findViewById(R.id.idelete);
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //删除一条记录
//                dingdans.remove(position);
//                notifyDataSetChanged();
//            }
//        });

        return convertView;
    }
}
