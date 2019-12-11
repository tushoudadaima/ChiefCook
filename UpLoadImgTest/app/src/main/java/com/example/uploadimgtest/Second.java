package com.example.uploadimgtest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class Second extends AppCompatActivity {
    private ImageView imageView2;
    private String phone="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        imageView2=findViewById(R.id.imageView2);

        File file = new File(getFilesDir().getAbsolutePath()+"/"+phone+".jpg");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        String path1 = getFilesDir().getAbsolutePath()+"/"+phone+".jpg";
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeFile(path1);
        if(file.exists()){
            imageView2.setImageBitmap(bitmap);
            Toast.makeText(getApplicationContext(),"123",Toast.LENGTH_SHORT).show();

        }
    }
}
