package com.example.administrator.shixun;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HelpActivity extends AppCompatActivity {
    private TextView help;
    private Button commit;
    private CustomeClickListener listener;
    private EditText suggest;
    private EditText call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        help=findViewById(R.id.helpa);
        commit=findViewById(R.id.commit);
        suggest=findViewById(R.id.suggest);
        call=findViewById(R.id.call);
        listener = new CustomeClickListener();
        help.setOnClickListener(listener);
        commit.setOnClickListener(listener);
    }
    class CustomeClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.helpa:
                    Intent intent2 = new Intent(Intent.ACTION_DIAL);
                    Uri data = Uri.parse("tel:" + "15231180689");
                    intent2.setData(data);
                    startActivity(intent2);

                    break;
                case R.id.commit:
                    final String suggests=suggest.getText().toString();
                    final String calls=call.getText().toString();
                    new Thread() {
                        @Override
                        public void run() {

                            URL url = null;
                            try {
                                url = new URL("http://192.168.137.1:8080/homework27/service?choose=20" + "&suggests=" + suggests+"&calls="+calls);
                                URLConnection conn = url.openConnection();
                                InputStream in = conn.getInputStream();
                                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                                String dui = "";
                                String v = "";


                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();

                    break;




            }
        }
    }
}
