package com.example.uploadimgtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


//    private static final int REQUEST_TAKE_PHOTO_PERMISSION = 1 ;
    private Button uploadImage;
    private Button selectImage;
    private Button downloadImage;

    private CustomeClickListener listener;
    private ImageView image;
    private String wang_zhi;
    private OkHttpClient okHttpClient;
    private String img_src;
    private String path;
    String phone = "1";
    private Button btn_second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uploadImage = findViewById(R.id.uploadImage);
        selectImage = findViewById(R.id.selectImage);
        downloadImage = findViewById(R.id.downloadImage);
        image = findViewById(R.id.imageView);
        btn_second = findViewById(R.id.btn_second);


        listener = new CustomeClickListener();
        uploadImage.setOnClickListener(listener);
        selectImage.setOnClickListener(listener);
        downloadImage.setOnClickListener(listener);
        btn_second.setOnClickListener(listener);

        okHttpClient = new OkHttpClient();
//        wang_zhi = "10.7.89.152";
        wang_zhi = "192.168.43.176";
//        asyncDownOp();
        File file = new File(getFilesDir().getAbsolutePath()+"/"+phone+".jpg");
//        "/sdcard/DCIM/Camera/CanMouZhang/"+phone+".jpg"
        //@SuppressLint("SdCardPath") File file = new File("/sdcard/DCIM/Camera/CanMouZhang/"+phone+".jpg");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
         String path1 = getFilesDir().getAbsolutePath()+"/"+phone+".jpg";
                //String path1 = "/sdcard/DCIM/Camera/CanMouZhang/"+phone+".jpg";
//        @SuppressLint("SdCardPath") URL uri = new URL("/sdcard/DCIM/Camera/IMG_20191204_035203.jpg");
//        ContentResolver cr = this.getContentResolver();
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeFile(path1);
        if(file.exists()){
            image.setImageBitmap(bitmap);
            Toast.makeText(getApplicationContext(),"123",Toast.LENGTH_SHORT).show();

        }else {
            asyncDownOp();

        }
    }


    private class CustomeClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.selectImage:

                    Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent,1);
                    break;
                case R.id.uploadImage:
                    asyncUpOp();
                    break;
                case R.id.downloadImage:
                    asyncDownOp();
                    break;
                case R.id.btn_second:
                    Intent intent1 = new Intent();
                    intent1.setClass(MainActivity.this,Second.class);
                    startActivity(intent1);

            }

        }
    }


    private void asyncDownOp() {
        new Thread(){
            @Override
            public void run() {
                try {
                    downImg();

                } catch (IOException e) {
                    e.printStackTrace();
                }
//                Message msg = Message.obtain();
//                msg.what = 1;
//                handler.sendMessage(msg);
            }
        }.start();
    }
    private void downImg() throws IOException {
        Request request = new Request.Builder()
                .url("http://10.7.89.152:8080/canMouZhangSignTest/DownBuyerImg?buyerId="+phone)
                .build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        InputStream in = response.body().byteStream();
        OutputStream out = new FileOutputStream(
                getFilesDir().getAbsolutePath()+"/"+phone+".jpg"
        );
//        @SuppressLint("SdCardPath") String file = "/sdcard/DCIM/Camera/CanMouZhang/";
//        File file1 = new File(file);
//        file1.createNewFile();
//        @SuppressLint("SdCardPath") OutputStream out = new FileOutputStream(
//               "/sdcard/DCIM/Camera/CanMouZhang/"+phone+".jpg"
//        );
        byte[] bytes = new byte[1024];
        int n = -1;
        while((n = in.read(bytes)) != -1){
            out.write(bytes, 0 , n);
            out.flush();
        }
        in.close();
        out.close();
    }

    @SuppressLint("SdCardPath")
    private void asyncUpOp() {
        //创建上传文件的异步任务类的对象
//        String filePath = getFilesDir().getAbsolutePath()+"/Tes.png";
//        String filePath = path;
//        path = "/mnt/sdcard/DCIM/Camera/IMG_20191204_141824.jpg";
//        path = "/sdcard/DCIM/Camera/IMG_20191204_035203.jpg";
//        path="/data/data/com.example.uploadimgtest/files/Tes.png";



        Toast.makeText(getApplicationContext(),"正在上传",Toast.LENGTH_SHORT).show();
        UpLoadFileTask task = new UpLoadFileTask(
                this,
                path
        );
        //开始执行异步任务

        task.execute("http://10.7.89.152:8080/canMouZhangSignTest/UpLoadBuyerHeadImg?buyerId="+phone);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //用户操作完成，结果码返回是-1，即RESULT_OK
        if (resultCode == RESULT_OK) {
            //获取选中文件的定位符
            Uri uri = data.getData();

//            img_src = uri.getPath();
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(column_index);


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
