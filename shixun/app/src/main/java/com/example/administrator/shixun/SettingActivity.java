package com.example.administrator.shixun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.shixun.LoginAndSignup.LoginActivity;

public class SettingActivity extends AppCompatActivity {
    private LinearLayout exit;
    private CustomeClickListener listener;
    private LinearLayout first;
    private LinearLayout second;
    private LinearLayout jiehuan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        exit=findViewById(R.id.exit);
        first=findViewById(R.id.first);
        second=findViewById(R.id.second);
        jiehuan=findViewById(R.id.jiehuan);
        registerListeners();
    }
    private void registerListeners() {
        listener = new CustomeClickListener();
        exit.setOnClickListener(listener);
        first.setOnClickListener(listener);
        second.setOnClickListener(listener);
        jiehuan.setOnClickListener(listener);

    }
    class CustomeClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.exit:
                    Toast.makeText(
                            getApplicationContext(),
                            "已退出当前账号",
                            Toast.LENGTH_SHORT
                    ).show();
                    System.exit(0);


                    break;
                case R.id.first:
                    Intent intent = new Intent(SettingActivity.this,CountActivity.class);
                    startActivity(intent);
                    break;
                case R.id.second:
                    Intent intent1 = new Intent(SettingActivity.this,HelpActivity.class);
                    startActivity(intent1);
//                    Intent intent2 = new Intent(Intent.ACTION_DIAL);
//                    Uri data = Uri.parse("tel:" + "15231180689");
//                    intent2.setData(data);
//                    startActivity(intent2);

                    break;
                case R.id.jiehuan:
                    Intent intent2 = new Intent(SettingActivity.this, LoginActivity.class);
                    startActivity(intent2);



            }
        }
    }

}
