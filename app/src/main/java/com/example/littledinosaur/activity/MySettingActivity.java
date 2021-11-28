package com.example.littledinosaur.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littledinosaur.ActivityCollector;
import com.example.littledinosaur.HttpRequest;
import com.example.littledinosaur.ListLikesAndCollects;
import com.example.littledinosaur.service.PostChangedNameService;
import com.example.littledinosaur.R;
import com.example.littledinosaur.UserDataBase;

import org.json.JSONException;
import org.json.JSONObject;

public class MySettingActivity extends AppCompatActivity {

    private String Username;
    private String Useremail;
    private String Userpassword;
    private LinearLayout mysettingsline7;
    private LinearLayout mysettingsline1;
    private EditText editName;
    private TextView save_btn;
    private ImageView return_btn;
    private String newname;
    private Button exit_btn;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1){

            }else if(msg.what ==2){

            }
        }
    };
    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting);
        ActivityCollector.addAcitivity(this);

        editName = findViewById(R.id.Username);
        return_btn = findViewById(R.id.return_btn);
        exit_btn = findViewById(R.id.exitbtn);
        TextView email = findViewById(R.id.Useremail);
        save_btn = findViewById(R.id.save_btn);
        mysettingsline7 = findViewById(R.id.mysettingsline7);
        mysettingsline1 = findViewById(R.id.mysettingsline1);

        final Intent intent = getIntent();
        Bundle b = intent.getExtras();
        assert b != null;
        Username = b.getString("Username");
        newname = Username;
        editName.setText(Username);
        UserDataBase dataBase = new UserDataBase(MySettingActivity.this,"User.db",null,2);
        SQLiteDatabase sq = dataBase.getReadableDatabase();
        Cursor cursor = sq.query("User", null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex("UserName")).equals(Username)){
                    Useremail = cursor.getString(cursor.getColumnIndex("UserEmail"));
                    Userpassword = cursor.getString(cursor.getColumnIndex("UserPassword"));
                }
            }
        }
        email.setText(Useremail);


        mysettingsline7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MySettingActivity.this,"emm...这个功能我们正在努力开发呢",Toast.LENGTH_SHORT).show();
            }
        });
        mysettingsline1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MySettingActivity.this,"emm...这个功能我们正在努力开发呢",Toast.LENGTH_SHORT).show();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newname = editName.getText().toString();
                if(!newname.equals(Username)){

//                    将新修改的名字写入本地数据库
                    UserDataBase userDataBase = new UserDataBase(MySettingActivity.this,"User.db",null,2);
                    SQLiteDatabase sqLiteDatabase = userDataBase.getWritableDatabase();
//                    values.put("Userid","6285");
//                    sqdb.update("User",values,"password=?",new String[]{"123"})
                    ContentValues values = new ContentValues();
                    values.put("UserName",newname);
                    sqLiteDatabase.update("User",values,"UserEmail=?", new String[]{Useremail});
                    sqLiteDatabase.close();

                    Intent intent = new Intent(MySettingActivity.this, PostChangedNameService.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Username",newname);
                    bundle.putString("Useremail",Useremail);
                    intent.putExtras(bundle);
                    startService(intent);

                    Toast.makeText(MySettingActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editName.clearFocus();
                Intent intent1 = new Intent(MySettingActivity.this, HomeActivity.class);
                intent1.putExtra("Username",newname);
                startActivity(intent1);
                MySettingActivity.this.finish();
            }
        });

        exit_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ListLikesAndCollects.clearList();
                SharedPreferences sp=MySettingActivity.this.getSharedPreferences("login",MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.clear();
                edit.apply();
                Intent intent = new Intent(MySettingActivity.this, LoginActivity.class);
                MySettingActivity.this.finish();
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent1 = new Intent(MySettingActivity.this,HomeActivity.class);
            intent1.putExtra("Username",newname);
            startActivity(intent1);
            MySettingActivity.this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
