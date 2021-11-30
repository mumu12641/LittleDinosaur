package com.example.littledinosaur.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.littledinosaur.HttpRequest;
import com.example.littledinosaur.R;
import com.example.littledinosaur.adapter.AboutMessageAdapter;

import org.json.JSONException;
import org.json.JSONObject;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView return_btn;
    private TextView nowversion;
    private String[][] content;
    @SuppressLint({"WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        return_btn = findViewById(R.id.about_return_btn);
        nowversion = findViewById(R.id.nowversion);
        return_btn.setOnClickListener(this);

        String verName = null;
        try {
            verName = AboutActivity.this.getPackageManager().getPackageInfo(AboutActivity.this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        nowversion.setText("version " + verName);


        final String[] string = {null};
        Thread thread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                string[0] = HttpRequest.GetNowApkVersion();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//                解析版本信息
        final JSONObject[] jsonObject= {null};
        try {
            jsonObject[0] = new JSONObject(string[0]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int newversion= 0;
        String apkDescribe = null;
        try {
//            newversion = Integer.parseInt(jsonObject[0].getString("apkVersion"));
            assert jsonObject[0] != null;
            apkDescribe = jsonObject[0].getString("apkDescribe");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        content = new String[][]{
                {apkDescribe},
                {"LittleDinosaur是一款面向当代大学生的树洞社区产品，目前已经基本实现了树洞社区的基本内容，包括但不仅限于发帖、点赞、评论、收藏，我们希望每一位洞友都能在这里找到自己的栖身之处，我们的故事就从这里开始♥♥♥"},
                {"Infinity Studio成立于2021/11，由一群来自各个城市各个大学的志同道合的朋友联合创建，我们的宗旨是以无限的idea致力于最好的大学生产品。现在业务包括Android端APP开发、前端页面开发、Python服务器开发等👏👏👏"},
                {"如果你对于我们的Infinity Studio有着浓厚的兴趣的话，欢迎联系我们\uD83C\uDF1F\uD83C\uDF1F\uD83C\uDF1F" +
                        "联系邮箱：pbbbbb@hust.edu.cn"}
        };
        AboutMessageAdapter aboutMessageAdapter = new AboutMessageAdapter(content,AboutActivity.this);
        final ExpandableListView expandableListView = findViewById(R.id.about_message);
        expandableListView.setDivider(null);
        expandableListView.setGroupIndicator(null);
        expandableListView.setAdapter(aboutMessageAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.about_return_btn:
                AboutActivity.this.finish();
        }
    }
}
