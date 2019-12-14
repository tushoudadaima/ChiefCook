package com.example.administrator.shixun;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.shixun.LoginAndSignup.ForgetPassword;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;

public class CountActivity extends AppCompatActivity {
    private LinearLayout firstchange;
    private CustomeClickListener listener;
    private ImageView image;
    private LinearLayout secondchange;
    private LinearLayout thirdchange;
    private String img_src;
    private String path;
    private String path2;
    private String path3;
    private String buyerId;
    private String sellerId;
    private String buyerOrSeller;
    private String wang_zhi ;
    private OkHttpClient okHttpClient;


    @SuppressLint("SdCardPath")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        okHttpClient = new OkHttpClient();

        wang_zhi = this.getString(R.string.wang_zhi);

        //为了区分buyer seller
        SharedPreferences buyerSP = getSharedPreferences("buyerData",MODE_PRIVATE);
        SharedPreferences sellerSP = getSharedPreferences("sellerData",MODE_PRIVATE);
        buyerId = buyerSP.getString("buyerId","");
        sellerId = sellerSP.getString("sellerId","");
        String buyerTime = buyerSP.getString("time","");
        String sellerTime = sellerSP.getString("time","");

        if (!buyerId.equals("")&&!sellerId.equals("")){
            @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("yyyy:mm:dd:HH:mm:ss");
            try {
                Date buyerData = df.parse(buyerTime);
                Date sellerData = df.parse(sellerTime);
                if(buyerData.getTime()>sellerData.getTime()){
                    buyerOrSeller = "buyer";
                }else {
                    buyerOrSeller = "seller";
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if(buyerId.equals("")&&!sellerId.equals("")){

            buyerOrSeller = "seller";
        }else if(!buyerId.equals("")){
            buyerOrSeller = "buyer";
        }


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
                    Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
    @SuppressLint("SdCardPath")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //用户操作完成，结果码返回是-1，即RESULT_OK
        if (resultCode == RESULT_OK) {
            //获取选中文件的定位符
            Uri uri = data.getData();

            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);//图片真实路径

            path2=getFilesDir().getPath()+"/CanMouZhang/";//data下CanMouZhang 文件夹
            path3 = path2+buyerId+".jpg";
            File file2 = new File(path2);
            File file3 = new File(path3);
            if(file2.exists()){
//                Toast.makeText(getApplicationContext(),"存在文件夹 正在压缩",Toast.LENGTH_SHORT).show();
                compressBitmap(path,file3);//压缩函数
            }else {
//                Toast.makeText(getApplicationContext(),"不存在文件夹 创建 正在压缩",Toast.LENGTH_SHORT).show();
                file2.mkdirs();
                compressBitmap(path,file3);//压缩函数
            }
            Log.e("uri", uri.toString());
            //使用content的接口
            ContentResolver cr = this.getContentResolver();

            asyncUpOp();//上传函数

            //获取图片
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            image.setImageBitmap(bitmap);
        } else {
            //操作错误或没有选择图片
            Log.i("MainActivtiy", "operation error");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("SdCardPath")
    private void asyncUpOp() {
        //创建上传文件的异步任务类的对象
//        Toast.makeText(getApplicationContext(),path3+"  正在上传",Toast.LENGTH_SHORT).show();
        UpLoadFileTask task = new UpLoadFileTask(
                this,
                path3
        );
        //开始执行异步任务

        //如果一个手机号即可以是买家又可以是卖家 就得分开写下面的
        if(buyerOrSeller.equals("buyer")){
            task.execute(wang_zhi+"UpLoadBuyerHeadImg?buyerId="+buyerId);
        }else {
            Toast.makeText(getApplicationContext(),"卖家暂不支持更改头像",Toast.LENGTH_SHORT).show();
        }
    }

    public void compressBitmap(String filePath, File file){
        // 数值越高，图片像素越低
        int inSampleSize = 8;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //采样率
        options.inSampleSize = inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 ,baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
