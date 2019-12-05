package com.example.administrator.shixun;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class SearchView extends LinearLayout implements View.OnClickListener{
    private Context context;

    public SearchView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public SearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public SearchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        //加载布局文件
        LayoutInflater.from(context).inflate(R.layout.search_layout, this);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
