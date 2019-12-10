package com.example.administrator.shixun;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.shixun.LoginAndSignup.ForgetPassword;

import java.io.FileNotFoundException;

public class CountActivity extends AppCompatActivity {
    private LinearLayout firstchange;
    private CustomeClickListener listener;
    private ImageView image;
    private LinearLayout secondchange;
    private LinearLayout thirdchange;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        firstchange=findViewById(R.id.firstchange);
        image=findViewById(R.id.imageid);
        secondchange=findViewById(R.id.secondchange);
        thirdchange=findViewById(R.id.thirdchange);
        registerListeners();

    }
    private void registerListeners() {
        listener = new CustomeClickListener();
        firstchange.setOnClickListener(listener);
        secondchange.setOnClickListener(listener);
        thirdchange.setOnClickListener(listener);
    }
    class CustomeClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.firstchange:
                    Intent intent = new Intent(Intent.ACTION_PICK,

                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    intent.setType("image/*");
                    startActivityForResult(intent,1);

                    break;
                case R.id.secondchange:
                    Intent intent1=new Intent(CountActivity.this, ForgetPassword.class);
                    startActivity(intent1);
                    break;

                case R.id.thirdchange:
                    Toast.makeText(getApplicationContext(),"已退出",Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = getSharedPreferences("buyerData",MODE_PRIVATE).edit();
                    editor.clear();
                    editor.apply();
                    SharedPreferences.Editor editor2 = getSharedPreferences("sellerData",MODE_PRIVATE).edit();
                    editor2.clear();
                    editor2.apply();
                    MyApplication myApplication = (MyApplication) getApplication();
                    String phone ="点击登录";
                    myApplication.setPhone(phone);

                    Intent intent2 = new Intent(CountActivity.this, MainActivity.class);
                    intent2.putExtra("index","fs");
                    startActivity(intent2);
                    break;
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //用户操作完成，结果码返回是-1，即RESULT_OK
        if (resultCode == RESULT_OK) {
            //获取选中文件的定位符
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            //使用content的接口
            ContentResolver cr = this.getContentResolver();
            try {
                //获取图片
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                image.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        } else {
            //操作错误或没有选择图片
            Log.i("MainActivtiy", "operation error");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
