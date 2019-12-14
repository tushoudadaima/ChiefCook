package com.example.administrator.shixun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class SearchActivity extends AppCompatActivity {
    private Button img_go;
    private EditText edt_search;
    private TextView txt_search;
    private String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        img_go = findViewById(R.id.img_go);
        edt_search = findViewById(R.id.tv_title);
        txt_search = findViewById(R.id.txt_search);

        img_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.this.finish();
            }
        });
        txt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str = edt_search.getText().toString();
                Intent intent = new Intent(SearchActivity.this,SimpleActivity.class);
                intent.putExtra("name",str);
                startActivity(intent);
                finish();
            }
        });
    }
}