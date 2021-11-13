package com.example.littledinosaur;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MySettingActivity extends AppCompatActivity {

    private String Username;
    private String Useremail;
    private String Userpassword;
//    private LinearLayout mysettingsline3;
    private EditText editName;
    private TextView save_btn;
    private ImageView return_btn;
    private String newname;

    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_setting);
        ActivityCollector.addAcitivity(this);

        editName = findViewById(R.id.Username);
        return_btn = findViewById(R.id.return_btn);
        TextView email = findViewById(R.id.Useremail);
        save_btn = findViewById(R.id.save_btn);

        final Intent intent = getIntent();
        Bundle b = intent.getExtras();
        assert b != null;
        Username = b.getString("Username");
        newname = Username;
        editName.setText(Username);
        UserDataBase dataBase = new UserDataBase(MySettingActivity.this,"User.db",null,1);
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


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newname = editName.getText().toString();
                if(!newname.equals(Username)){

//                    将新修改的名字写入本地数据库
                    UserDataBase userDataBase = new UserDataBase(MySettingActivity.this,"User.db",null,1);
                    SQLiteDatabase sqLiteDatabase = userDataBase.getWritableDatabase();
//                    values.put("Userid","6285");
//                    sqdb.update("User",values,"password=?",new String[]{"123"})
                    ContentValues values = new ContentValues();
                    values.put("UserName",newname);
                    sqLiteDatabase.update("User",values,"UserEmail=?", new String[]{Useremail});
                    sqLiteDatabase.close();

                    Intent intent = new Intent(MySettingActivity.this,PostChangedNameService.class);
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
                Intent intent1 = new Intent(MySettingActivity.this,HomeActivity.class);
                intent1.putExtra("Username",newname);
                startActivity(intent1);
                MySettingActivity.this.finish();
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
