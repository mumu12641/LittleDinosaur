package com.example.littledinosaur.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littledinosaur.R;
import com.example.littledinosaur.adapter.SchoolAdapter;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SchoolActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView return_btn;
    private RecyclerView recyclerView;
    private ImageView add_school;
    private SchoolAdapter schoolAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_school);

        recyclerView = findViewById(R.id.school_recyclerview);
        return_btn = findViewById(R.id.about_return_btn);
        add_school = findViewById(R.id.add_school);

        return_btn.setOnClickListener(this);
        add_school.setOnClickListener(this);

//        int[] resid = {R.drawable.school,R.drawable.school,R.drawable.school,R.drawable.school,R.drawable.school};
        List<String> names = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SchoolActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        schoolAdapter = new SchoolAdapter(names,SchoolActivity.this);
        recyclerView.setAdapter(schoolAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.about_return_btn:
                SchoolActivity.this.finish();
            case R.id.add_school:
                AlertDialog alertDialog = null;
                AlertDialog.Builder builder = new AlertDialog.Builder(SchoolActivity.this);
                View view = getLayoutInflater().inflate(R.layout.dialog_todonote,null);
                builder.setView(view);
                builder.setCancelable(false);
                alertDialog =  builder.create();
                final EditText editnote = view.findViewById(R.id.editnote);
                TextView textView = view.findViewById(R.id.notecontent);
                textView.setText("添加你的母校");
                final String[] s = new String[1];
                AlertDialog finalAlertDialog = alertDialog;
                view.findViewById(R.id.dialog_confirm_sure).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        s[0] = editnote.getText().toString();
                        Toast.makeText(SchoolActivity.this,"你添加了"+s[0]+"，请为你添加的学校添加封面",Toast.LENGTH_SHORT).show();
                        finalAlertDialog.cancel();
                        schoolAdapter.addSchool(s[0]);
                    }
                });

                view.findViewById(R.id.dialog_confirm_cancle).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finalAlertDialog.cancel();
                    }
                });
                alertDialog.show();
                break;
        }
    }
    private Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }
}