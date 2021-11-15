package com.example.littledinosaur.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.littledinosaur.ActivityCollector;
import com.example.littledinosaur.HttpRequest;
import com.example.littledinosaur.R;
import com.example.littledinosaur.service.RegisterIntentService;
import com.example.littledinosaur.UserDataBase;

public class RegisterActivity extends AppCompatActivity {

    private EditText textemail;
    private EditText textpassword;
    private EditText textkey;
    private Button confirm;
    private Button getkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActivityCollector.addAcitivity(this);
        final String[] KeyCode = new String[1];
        final boolean[] flag = {true};
        textemail = findViewById(R.id.textemail);
        textpassword = findViewById(R.id.textpassword);
        textkey = findViewById(R.id.textconfirm);
        confirm = findViewById(R.id.confirm_btn);
        getkey = findViewById(R.id.getkey);
        getkey.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
//                判断是否重复注册
                String emailstr = textemail.getText().toString();
                UserDataBase myDatabase = new UserDataBase(RegisterActivity.this,"User.db",null,1);
                SQLiteDatabase sqdb = myDatabase.getReadableDatabase();
                Cursor cursor = sqdb.query("User",null,null, null,null,null,null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        if (cursor.getString(cursor.getColumnIndex("UserEmail")).equals(emailstr)) {
                            Toast.makeText(RegisterActivity.this, "该邮箱已被注册，注册失败", Toast.LENGTH_SHORT).show();
                            textemail.setText(null);
                            textpassword.setText(null);
                            textkey.setText(null);
                            flag[0] = true;
                            cursor.close();
                            return;
                        }
                    }
                    cursor.close();
                }
                cursor.close();
                if (flag[0] == true){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
//                        获取验证码
                            String emailstr = textemail.getText().toString();
                            KeyCode[0] = HttpRequest.GetKeyCode(emailstr);
                        }
                    }).start();
//                    只获取一次验证码
                    flag[0] = false;
                }

            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String emailstr = textemail.getText().toString();
                String passwordstr = textpassword.getText().toString();
                String keystr = textkey.getText().toString();
                if (keystr.equals(KeyCode[0])){
                    Intent intent = new Intent(RegisterActivity.this, RegisterIntentService.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("emailstr",emailstr);
                    bundle.putString("passwordstr",passwordstr);
                    intent.putExtras(bundle);
                    startService(intent);
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
                    RegisterActivity.this.finish();
                    startActivity(intent1);
                } else{
                    Toast.makeText(RegisterActivity.this,"验证码输入错误，注册失败",Toast.LENGTH_SHORT).show();
                    textemail.setText(null);
                    textpassword.setText(null);
                    textkey.setText(null);
                    flag[0] = true;
                }
            }
        });


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
