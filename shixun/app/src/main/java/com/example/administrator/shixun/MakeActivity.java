package com.example.administrator.shixun;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.shixun.Entity.Methods;

import java.util.List;

import butterknife.ButterKnife;

public class MakeActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{
    private ImageView img1;
    private ImageView img2;
    private TextView txt1;
    private TextView txt2;
    private TextView page;
    private RelativeLayout linear1;
    private RelativeLayout linear2;
    private GestureDetector detector;
    private List<Methods> list;
    private int g=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);
        page = findViewById(R.id.page);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);
        Intent intent = getIntent();
        list = (List<Methods>) intent.getSerializableExtra("make");
        Glide.with(MakeActivity.this).asBitmap().load(list.get(0).getImg()).into(img2);
        txt2.setText(list.get(0).getStep());
        page.setText(1+"/"+list.size()+"");
        ButterKnife.bind(this);
        detector = new GestureDetector(this, this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.detector.onTouchEvent(event);

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {

        return false;
    }


    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        float startX = e1.getX();
        float startY = e1.getY();
        float endX = e2.getX();
        float endY = e2.getY();
        float poorX = endX - startX;
        float poorY = endY - startY;

        if (Math.abs(poorX) > 100) {
            if (poorX < 0 && g>=0 && g<list.size()-1) {
                if(g < list.size()-1 ){
                    Glide.with(this).asBitmap().load(list.get(g+1).getImg()).into(img2);
                    txt2.setText(list.get(g+1).getStep());
                    page.setText(g+1+"/"+list.size());
                }
                if(g >=0 && g<list.size()){
                    Glide.with(this).asBitmap().load(list.get(g).getImg()).into(img1);
                    txt1.setText(list.get(g+1-1).getStep());
                    page.setText(g+2+"/"+list.size());
                }

                ObjectAnimator translated =
                        ObjectAnimator.ofFloat(linear1, "translationX", 0, -2000);
                translated.setDuration(600);
                translated.setRepeatCount(0);
                linear1.setVisibility(View.VISIBLE);
                translated.start();
                ObjectAnimator alphaA =
                        ObjectAnimator.ofFloat(linear1, "alpha", 1, 0);
                alphaA.setDuration(1000);
                alphaA.start();
                ObjectAnimator translatee =
                        ObjectAnimator.ofFloat(linear2, "translationX", 2000, 0);
                translatee.setDuration(600);
                translatee.setRepeatCount(0);
                translatee.start();
                ObjectAnimator alphaB =
                        ObjectAnimator.ofFloat(linear2, "alpha", 0, 1);
                alphaB.setDuration(600);
                alphaB.start();
            } else if(poorX >=0&&g>0&&g<list.size()){
                if(g>=0 && g<list.size()-1){
//                    traslate.setText(abc.get(g));
                    Glide.with(this).asBitmap().load(list.get(g).getImg()).into(img2);
                    txt2.setText(list.get(g).getStep());
                    page.setText(g+1+"/"+list.size());
                }
                if(g>0 && g<list.size()){
//                    traslate1.setText(abc.get(g -1));
                    Glide.with(this).asBitmap().load(list.get(g-1).getImg()).into(img1);
                    txt1.setText(list.get(g-1).getStep());
                    page.setText(g+"/"+list.size());
                }

                ObjectAnimator translateA =
                        ObjectAnimator.ofFloat(linear2, "translationX", 0, 2000);
                translateA.setDuration(600);
                translateA.setRepeatCount(0);
                translateA.start();
                ObjectAnimator alphaC =
                        ObjectAnimator.ofFloat(linear2, "alpha", 1, 0);
                alphaC.setDuration(600);
                alphaC.start();

                ObjectAnimator translatec =
                        ObjectAnimator.ofFloat(linear1, "translationX", -2000, 0);
                translatec.setDuration(600);
                translatec.setRepeatCount(0);
                linear1.setVisibility(View.VISIBLE);
                translatec.start();
                ObjectAnimator alphaD =
                        ObjectAnimator.ofFloat(linear1, "alpha", 0, 1);
                alphaD.setDuration(600);
                alphaD.start();
            }
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float startX = e1.getX();
        float startX2 = e2.getX();
        float end=startX2-startX;
        if(end<0&&g<list.size()-1){
            g=onPlus();
        }
        if(end>0&&g>0){
            g=onMinus();
        }
        return false;
    }

    public int onPlus(){
        g++;
        return g;
    }
    public int onMinus(){
        g--;
        return g;
    }
}
