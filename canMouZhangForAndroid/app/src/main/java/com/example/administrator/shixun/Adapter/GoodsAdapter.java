package com.example.administrator.shixun.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v4.content.LocalBroadcastManager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.shixun.Entity.Goods;
import com.example.administrator.shixun.Fragment.ShangDianFrament;
import com.example.administrator.shixun.R;

import java.util.List;

public class GoodsAdapter extends BaseAdapter {

    private List<Goods> list;
    private Context context;
    private CatograyAdapter catograyAdapter;
    private SparseArray<Goods> selectedList;
    private LocalBroadcastManager localBroadcastManager;

    public GoodsAdapter(Context context, List<Goods> list2, CatograyAdapter catograyAdapter,SparseArray<Goods> selectedList) {
        this.context=context;
        this.list=list2;
        this.catograyAdapter=catograyAdapter;
        this.selectedList = selectedList;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Viewholder viewholder;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.shopcart_right_listview,null);
            viewholder=new Viewholder();
            viewholder.tv_name= convertView.findViewById(R.id.tv_name);
            viewholder.tv_original_price= convertView.findViewById(R.id.tv_original_price);
            viewholder.tv_price=  convertView.findViewById(R.id.tv_price);
            viewholder.iv_add= convertView.findViewById(R.id.iv_add);
            viewholder.iv_remove=  convertView.findViewById(R.id.iv_remove);
            viewholder.tv_acount=  convertView.findViewById(R.id.tv_acount);
            viewholder.iv_pic=  convertView.findViewById(R.id.iv_pic);
            viewholder.rl_item=  convertView.findViewById(R.id.rl_item);
            convertView.setTag(viewholder);
        }else {
            viewholder = (Viewholder) convertView.getTag();

        }
        viewholder.tv_name.setText(list.get(position).getTitle());
        viewholder.tv_original_price.setText("￥"+list.get(position).getOriginal_price());
        viewholder.tv_original_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
        viewholder.tv_price.setText("￥"+list.get(position).getPrice());
        viewholder.iv_pic.setBackgroundResource(list.get(position).getIcon());


        if(list.get(position)!=null){
            //默认进来数量
            if (list.get(position).getNum()<1){
                viewholder.tv_acount.setVisibility(View.INVISIBLE);
                viewholder.iv_remove.setVisibility(View.INVISIBLE);
                catograyAdapter.notifyDataSetChanged();
            }else{
                viewholder.tv_acount.setVisibility(View.VISIBLE);
                viewholder.iv_remove.setVisibility(View.VISIBLE);
                viewholder.tv_acount.setText(String.valueOf(list.get(position).getNum()));
                catograyAdapter.notifyDataSetChanged();
            }
        }else{
            viewholder.tv_acount.setVisibility(View.INVISIBLE);
            viewholder.iv_remove.setVisibility(View.INVISIBLE);
        }

        //商品图片
//        if(list.get(position).getIcon()!=null){
//            ImageLoader.getInstance().displayImage(
//                    list.get(position).getIcon(), viewholder.iv_pic);
//        }


        viewholder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = getSelectedItemCountById(list.get(position).getProduct_id());
                if (count < 1) {
                    viewholder.iv_remove.setAnimation(getShowAnimation());
                    viewholder.iv_remove.setVisibility(View.VISIBLE);
                    viewholder.tv_acount.setVisibility(View.VISIBLE);
                }

                handlerCarNum(1,list.get(position),true);
                catograyAdapter.notifyDataSetChanged();

                localBroadcastManager = LocalBroadcastManager.getInstance(context);
                Intent intent = new Intent(ShangDianFrament.LOCAL_BROADCAST);

                localBroadcastManager.sendBroadcast(intent);

//                int[] loc = new int[2];
//                viewholder.iv_add.getLocationInWindow(loc);
//                int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
//                v.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
//                ImageView ball = new ImageView(context);
//                ball.setImageResource(R.drawable.number);
//                fragment.setAnim(ball, startLocation);// 开始执行动画

            }
        });

        viewholder.iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = getSelectedItemCountById(list.get(position).getProduct_id());
                if (count < 2) {
                    viewholder.iv_remove.setAnimation(getHiddenAnimation());
                    viewholder.iv_remove.setVisibility(View.GONE);
                    viewholder.tv_acount.setVisibility(View.GONE);
                }
                handlerCarNum(0,list.get(position),true);
                catograyAdapter.notifyDataSetChanged();
                localBroadcastManager = LocalBroadcastManager.getInstance(context);
                Intent intent = new Intent(ShangDianFrament.LOCAL_BROADCAST);
//                intent.putExtra("objectList",(Serializable)selectedList);
                localBroadcastManager.sendBroadcast(intent);
            }
        });

        viewholder.rl_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(list.get(position).getPackage_product_info()!=null&&list.get(position).getTitle()!=null){
//                    catograyAdapter.notifyDataSetChanged();
//                  ((MainActivity)context).showDetailSheet(list.get(position).getPackage_product_info(),list.get(position).getTitle());
//                }else{
////                    Toast.makeText(context, "没有详情!", Toast.LENGTH_SHORT).show();
//                }
            }
        });
        return convertView;
    }
    class Viewholder{
        TextView tv_name,tv_original_price,tv_price;
        ImageView iv_add,iv_remove,iv_pic;
        TextView tv_acount;
        RelativeLayout rl_item;
    }



    //显示减号的动画
    private Animation getShowAnimation(){
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0,720,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF,2f
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(0,1);
        set.addAnimation(alpha);
        set.setDuration(200);
        return set;
    }
    //隐藏减号的动画
    private Animation getHiddenAnimation(){
        AnimationSet set = new AnimationSet(true);
        RotateAnimation rotate = new RotateAnimation(0,720,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        set.addAnimation(rotate);
        TranslateAnimation translate = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,2f
                ,TranslateAnimation.RELATIVE_TO_SELF,0
                ,TranslateAnimation.RELATIVE_TO_SELF,0);
        set.addAnimation(translate);
        AlphaAnimation alpha = new AlphaAnimation(1,0);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }

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
//        update(refreshGoodList);
    }

}
