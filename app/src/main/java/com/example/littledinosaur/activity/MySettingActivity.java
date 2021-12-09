package com.example.littledinosaur.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littledinosaur.ListLikesAndCollects;
import com.example.littledinosaur.service.PostChangedNameService;
import com.example.littledinosaur.R;
import com.example.littledinosaur.UserDataBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private ImageView userportrait;
    private List<String> nameList = new ArrayList<>();



    final int[] iconnum = new int[1];
    final int[] iconid = {R.drawable.icon0,R.drawable.icon1,R.drawable.icon2,R.drawable.icon3,R.drawable.icon4,
            R.drawable.icon5,R.drawable.icon6,R.drawable.icon7,R.drawable.icon8,R.drawable.icon9,
            R.drawable.icon10,R.drawable.icon11};
    private int iconsrc;
    private int oldicon;

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

        editName = findViewById(R.id.Username);
        return_btn = findViewById(R.id.return_btn);
        exit_btn = findViewById(R.id.exitbtn);
        TextView email = findViewById(R.id.Useremail);
        save_btn = findViewById(R.id.save_btn);
        mysettingsline7 = findViewById(R.id.mysettingsline7);
        mysettingsline1 = findViewById(R.id.mysettingsline1);
        userportrait = findViewById(R.id.userportrait);

        final Intent intent = getIntent();
        Bundle b = intent.getExtras();
        assert b != null;
        Username = b.getString("Username");

//        nameList.add(Username);

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
                    iconsrc = iconid[Integer.parseInt(cursor.getString(cursor.getColumnIndex("Extra")))];
                    iconnum[0] = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Extra")));
                    oldicon = iconnum[0];
                }else{
                    nameList.add(cursor.getString(cursor.getColumnIndex("UserName")));
                }
            }
        }
        email.setText(Useremail);
        userportrait.setImageResource(iconsrc);

        mysettingsline7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MySettingActivity.this,"emm...这个功能我们正在努力开发呢",Toast.LENGTH_SHORT).show();
            }
        });
        mysettingsline1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog dialog = null;
                AlertDialog.Builder builder = null;
                builder = new AlertDialog.Builder(MySettingActivity.this);
                LayoutInflater inflater1 = MySettingActivity.this.getLayoutInflater();
                final View view1 = inflater1.inflate(R.layout.dialog_icon,null,false);
                builder.setView(view1);
                builder.setCancelable(false);
                dialog = builder.create();

                final ImageView icon = view1.findViewById(R.id.icon);
//                final int[] iconnum = new int[1];
//                final int[] iconid = {R.drawable.icon0,R.drawable.icon1,R.drawable.icon2,R.drawable.icon3,R.drawable.icon4,
//                        R.drawable.icon5,R.drawable.icon6,R.drawable.icon7,R.drawable.icon8,R.drawable.icon9,
//                        R.drawable.icon10,R.drawable.icon11};
                iconnum[0]=1;
                view1.findViewById(R.id.icon_change).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Random rand = new Random();
                        iconnum[0] = rand.nextInt(12);
                        icon.setImageResource(iconid[iconnum[0]]);
                    }
                });
                final AlertDialog finalDialog = dialog;
                view1.findViewById(R.id.icon_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finalDialog.cancel();
                        userportrait.setImageResource(iconid[iconnum[0]]);
                        iconsrc = iconid[iconnum[0]];
                        Log.d("icon", String.valueOf(iconnum[0]));
                    }
                });

                dialog.show();
//                Toast.makeText(MySettingActivity.this,"emm...这个功能我们正在努力开发呢",Toast.LENGTH_SHORT).show();
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newname = editName.getText().toString();
                if(nameList.contains(newname)){
                    Toast.makeText(MySettingActivity.this,"改名字已经被使用了嗷，请换一个试试叭~",Toast.LENGTH_SHORT).show();
                    newname = Username;
                    editName.setText(Username);
                    return;
                }
                if(!newname.equals(Username)||oldicon != iconnum[0]){

//                    将新修改的名字写入本地数据库
                    UserDataBase userDataBase = new UserDataBase(MySettingActivity.this,"User.db",null,2);
                    SQLiteDatabase sqLiteDatabase = userDataBase.getWritableDatabase();
//                    values.put("Userid","6285");
//                    sqdb.update("User",values,"password=?",new String[]{"123"})
                    ContentValues values = new ContentValues();
                    values.put("UserName",newname);
                    values.put("Extra",String.valueOf(iconnum[0]));
                    sqLiteDatabase.update("User",values,"UserEmail=?", new String[]{Useremail});
                    sqLiteDatabase.close();

                    Intent intent = new Intent(MySettingActivity.this, PostChangedNameService.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Username",newname);
                    bundle.putString("Useremail",Useremail);
                    bundle.putString("Extra",String.valueOf(iconnum[0]));
                    intent.putExtras(bundle);
                    startService(intent);

                    if (!newname.equals(Username)){
//                        如果更改了名字就重新写文件
                        SharedPreferences sp = MySettingActivity.this.getSharedPreferences("login",LoginActivity.MODE_PRIVATE);
                        SharedPreferences.Editor edit = sp.edit();
                        edit.clear();
                        edit.putString("UserEmail", Useremail);
                        edit.putString("UserPassword",Userpassword);
                        edit.putString("UserName",newname);
                        edit.apply();
                    }

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
    }
}
