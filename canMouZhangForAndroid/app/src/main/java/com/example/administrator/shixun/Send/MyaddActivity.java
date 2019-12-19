package com.example.administrator.shixun.Send;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.shixun.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class MyaddActivity extends AppCompatActivity implements View.OnClickListener{
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA };

    private Button Button01;
    private Button Button02;
    private ImageView ImageView01;
    public Uri bitm;
    public String bidehua;
    private EditText editText;
    private EditText bib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myadd);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        editText=findViewById(R.id.suggestaaa);
        bib=findViewById(R.id.bib);
        initView();
    }
    public int verifyPermissions(Activity activity, String permission) {
        int Permission = ActivityCompat.checkSelfPermission(activity,permission);
        if (Permission == PackageManager.PERMISSION_GRANTED) {

            return 1;
        }else{
            return 0;
        }
    }

    private void initView() {

        Button02 = findViewById(R.id.Button02);
        ImageView01 = findViewById(R.id.ImageView);

        Button02.setOnClickListener(this);
        ImageView01.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.Button02:
               bidehua=editText.getText().toString();
                Intent intent = new Intent(MyaddActivity.this, Mine2Activity.class);
                ByteArrayOutputStream output = new ByteArrayOutputStream();//初始化一个流对象
//                bitm.compress(Bitmap.CompressFormat.PNG,1 ,output);//把bitmap100%高质量压缩 到 output对象里
////                byte[]result = output.toByteArray();
                String result=bitm.toString();
                String bibb=bib.getText().toString();
                intent.putExtra("pict",result);
                intent.putExtra("xinxian",bidehua);
                intent.putExtra("bib",bibb);
                startActivity(intent);


                Toast.makeText(
                        this,
                        "发布成功！",
                        Toast.LENGTH_SHORT
                ).show();
                break;
            case R.id.ImageView:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //    设置Title的图标
                //    设置Title的内容
                //    设置Content来显示一个信息
                //    设置一个PositiveButton
                builder.setPositiveButton("拍照", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(verifyPermissions(MyaddActivity.this,PERMISSIONS_STORAGE[2]) == 0){
                            ActivityCompat.requestPermissions(MyaddActivity.this, PERMISSIONS_STORAGE, 3);
                        }else{
                            //已经有权限
                            toCamera();  //打开相机
                        }

                    }
                });
                //    设置一个NegativeButton
                builder.setNegativeButton("相册", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        toPicture();
                        Toast.makeText(MyaddActivity.this,"正在加载", Toast.LENGTH_SHORT).show();
                    }
                });
                //    设置一个NeutralButton
                builder.setNeutralButton("取消", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
                //    显示出该对话框
                builder.show();

                break;
        }
    }


    private File tempFile = null;   //新建一个 File 文件（用于相机拿数据）

    //获取 相机 或者 图库 返回的图片
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //判断返回码不等于0
        if (requestCode != RESULT_CANCELED){    //RESULT_CANCELED = 0(也可以直接写“if (requestCode != 0 )”)
            //读取返回码
            switch (requestCode){
                case 100:   //相册返回的数据（相册的返回码）

                    Uri uri01 = data.getData();
                    bitm=uri01;
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri01));
                        ImageView01.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                case 101:  //相机返回的数据（相机的返回码）
                    try {
                        tempFile = new File(Environment.getExternalStorageDirectory(),"fileImg.jpg");  //相机取图片数据文件
                        Uri uri02 = Uri.fromFile(tempFile);   //图片文件
                        bitm=uri02;
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri02));

                        ImageView01.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    //跳转相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);  //跳转到 ACTION_IMAGE_CAPTURE
        intent.setType("image/*");
        startActivityForResult(intent,100);
    }

    //跳转相机
    private void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  //跳转到 ACTION_IMAGE_CAPTURE
        //判断内存卡是否可用，可用的话就进行存储
        //putExtra：取值，Uri.fromFile：传一个拍照所得到的文件，fileImg.jpg：文件名
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"fileImg.jpg")));
        startActivityForResult(intent,101); // 101: 相机的返回码参数（随便一个值就行，只要不冲突就好）
    }


}
