package com.example.administrator.shixun.Send;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.shixun.R;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BlankFragment extends Fragment  {
    private PinAdapter pinAdapter;
    private List<Pin> pinlun=new ArrayList<>();
    private SmartRefreshLayout srl;
    private String[] nmess={"赵","崔","夏","陈","刘","张","毛","周","沛","诗","晓","丹"};
    private String[] namsss={"小","大","豆","浩","文","金","桃","乐","柏","青","蝶","采"};
    private String[] namessss={"兰","烟","航","凡","易","天","珍","竹","平","真","曼","连"};
    private String[] biao={"试试你也可以","这是我第一次做，真的很激动","长这么大，这是我吃过最满意的","什么时候才能吃饱饱","妈呀，我也想吃","快来看看我做的菜","好紧张，不好意思","让营养回归自然","科技造就好营养","人是铁饭是钢","一顿不吃饿得慌","饭饱思淫欲","妈妈我会用功做菜"};
    private int[] imageid={R.drawable.m1,R.drawable.m2,R.drawable.m3,R.drawable.m4,R.drawable.m5,R.drawable.m6,R.drawable.m7,R.drawable.m8,R.drawable.m9,R.drawable.m10,R.drawable.m11,R.drawable.m12,R.drawable.m13,R.drawable.m14,R.drawable.m15,R.drawable.m16,R.drawable.m17,R.drawable.m18};
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container,false);
        srl =view.findViewById(R.id.srl);
        srl.setRefreshHeader(new PhoenixHeader(this.getContext()));
        srl.setRefreshFooter(new ClassicsFooter(this.getContext()).setSpinnerStyle(SpinnerStyle.Scale));
        srl.setReboundDuration(300);
        int i=0;


        while (i<10){
            int j = new Random().nextInt(11)%(11-0+1)+0;
            int jj = new Random().nextInt(11)%(11-0+1)+0;
            int jjj = new Random().nextInt(11)%(11-0+1)+0;
            Pin pin=new Pin();
            String ming=nmess[j]+namsss[jj]+namessss[jjj];
            pin.setText1(biao[j]);
            pin.setImgg(imageid[j]);
            pin.setTextm(ming);
            pin.setTextp(j+"");
            pin.setTextz(jj+"");
            pinlun.add(pin);
            i++;
        }
        ListView listView = view.findViewById(R.id.dingdans);
        pinAdapter = new PinAdapter(this.getActivity(), pinlun, R.layout.items);
        listView.setAdapter(pinAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String biaoti= pinlun.get(position).getText1();
                int tupian=pinlun.get(position).getImgg();
                int i=0;
                while(imageid[i]!=tupian){
                    i++;
                }
                String bbb=i+"";
                String mingzi= pinlun.get(position).getTextm();
                Intent intent3 = new Intent(getActivity(), PinxiangActivity.class);
                intent3.putExtra("biaoti",biaoti);
                intent3.putExtra("tupian",bbb);
                intent3.putExtra("mingzi",mingzi);
                startActivity(intent3);


//                getActivity().overridePendingTransition(
//                        R.anim.in,//进入动画
//                        R.anim.out//出去动画
//                );
            }
        });
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshData();
                srl.finishRefresh();
                Toast.makeText(getContext(),
                        "刷新完成",
                        Toast.LENGTH_SHORT).show();
            }
        });
        srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if(pinlun.size() > 20){
                    srl.finishLoadMoreWithNoMoreData();
                    Toast.makeText(getContext(),
                            "加载数据完毕",
                            Toast.LENGTH_SHORT).show();
                }else {
                    loadMoreData();
                    srl.finishLoadMore();
                    Toast.makeText(getContext(),
                            "加载5条数据",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }


    public void refreshData(){
        pinlun.clear();
        int i=0;


        while (i<10){

            int j = new Random().nextInt(11)%(11-0+1)+0;
            int jj = new Random().nextInt(11)%(11-0+1)+0;
            int jjj = new Random().nextInt(11)%(11-0+1)+0;
            Pin pin=new Pin();
            String ming=nmess[j]+namsss[jj]+namessss[jjj];
            pin.setText1(biao[j]);
            pin.setImgg(imageid[j]);
            pin.setTextm(ming);
            pin.setTextp(j+"");
            pin.setTextz(jj+"");
            pinlun.add(pin);
            i++;
        }
        pinAdapter.notifyDataSetChanged();
    }
    public void loadMoreData(){
        for(int i = 0; i < 5; i++){
            int j = new Random().nextInt(11)%(11-0+1)+0;
            int jj = new Random().nextInt(11)%(11-0+1)+0;
            int jjj = new Random().nextInt(11)%(11-0+1)+0;
            Pin pin=new Pin();
            String ming=nmess[j]+namsss[jj]+namessss[jjj];
            pin.setText1(biao[j]);
            pin.setImgg(imageid[i]);
            pin.setTextm(ming);
            pin.setTextp(j+"");
            pin.setTextz(jj+"");
            pinlun.add(pin);
        }
        pinAdapter.notifyDataSetChanged();
    }

}
