package com.example.littledinosaur.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.littledinosaur.ListLikesAndCollects;
import com.example.littledinosaur.R;
import com.example.littledinosaur.UserDataBase;
import com.example.littledinosaur.service.GetUserLikesAndCollectsService;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {


    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isOpenNetwork()){
            LoginActivity.this.finish();
        }
            SharedPreferences sp=this.getSharedPreferences("login", Context.MODE_PRIVATE);
            String email=sp.getString("UserEmail", "error");
            String password=sp.getString("UserPassword", "error");
            String name = sp.getString("UserName","error");
        if (!email.equals("error")&&!password.equals("error")&&!name.equals("error")) {


            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            @SuppressLint("WrongConstant") Notification.Builder notification;
            String channelId = String.valueOf(new Random().nextInt()); //自己生成的用于通知栏的channelId，高版本必备
            NotificationChannel mChannel = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                mChannel = new NotificationChannel(channelId, "name", NotificationManager.IMPORTANCE_HIGH);
                mChannel.enableVibration(true);
                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                manager.createNotificationChannel(mChannel);
                notification = new Notification.Builder(LoginActivity.this,channelId);
            }else{
                notification = new Notification.Builder(LoginActivity.this);
            }
            notification.setContentTitle("登录成功").
                    setContentText("欢迎！！！")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.dragon1)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.dragon1))
                    .setPriority(NotificationCompat.PRIORITY_MAX);
            Notification notification1 = notification.build();
            manager.notify(1, notification1);
                Bundle bundle = new Bundle();
                bundle.putString("Username", name);


                ListLikesAndCollects.clearList();
                Intent intent1 = new Intent(LoginActivity.this, GetUserLikesAndCollectsService.class);
                intent1.putExtras(bundle);
                startService(intent1);
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

                intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(LoginActivity.this, "欢迎！！！", Toast.LENGTH_SHORT).show();
                LoginActivity.this.finish();
            }
        setContentView(R.layout.activity_login);
        Button register = findViewById(R.id.btn3);
        Button login = findViewById(R.id.btn2);
        final EditText EmailText = findViewById(R.id.EmailText);
        final EditText PasswordText = findViewById(R.id.PasswordText);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                boolean flag = false;
                String Emailstr = EmailText.getText().toString();
                String Passwordstr = PasswordText.getText().toString();
                UserDataBase myDatabase = new UserDataBase(LoginActivity.this,"User.db",null,2);
                SQLiteDatabase sqdb = myDatabase.getReadableDatabase();
                Cursor cursor = sqdb.query("User", null, "UserEmail=?", new String[]{Emailstr}, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
//                        Toast.makeText(LoginActivity.this,cursor.getColumnIndex("UserName"),Toast.LENGTH_SHORT).show();
                        if (Passwordstr.equals(cursor.getString(cursor.getColumnIndex("UserPassword")))
                                &&
                                Emailstr.equals(cursor.getString((cursor.getColumnIndex("UserEmail"))))) {
                            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            @SuppressLint("WrongConstant") Notification.Builder notification;
                            String channelId = String.valueOf(new Random().nextInt()); //自己生成的用于通知栏的channelId，高版本必备
                            NotificationChannel mChannel = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                mChannel = new NotificationChannel(channelId, "name", NotificationManager.IMPORTANCE_HIGH);
                                mChannel.enableVibration(true);
                                mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                                manager.createNotificationChannel(mChannel);
                                notification = new Notification.Builder(LoginActivity.this,channelId);
                            }else{
                                notification = new Notification.Builder(LoginActivity.this);
                            }
                            notification.setContentTitle("登录成功").
                                    setContentText("欢迎！！！")
                                    .setWhen(System.currentTimeMillis())
                                    .setSmallIcon(R.drawable.dragon1)
                                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.dragon1))
                                    .setPriority(NotificationCompat.PRIORITY_MAX)
                                    .setAutoCancel(true);
                            Notification notification1 = notification.build();
                            manager.notify(1, notification1);


                            flag = true;

                            SharedPreferences sp = LoginActivity.this.getSharedPreferences("login",LoginActivity.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putString("UserEmail", Emailstr);
                            edit.putString("UserPassword",Passwordstr);
                            edit.putString("UserName",cursor.getString(cursor.getColumnIndex("UserName")));
                            edit.apply();

                            //进入主页面
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            Bundle bundle = new Bundle();

                            bundle.putString("Username",cursor.getString(cursor.getColumnIndex("UserName")));
                            ListLikesAndCollects.clearList();
                            Intent intent1 = new Intent(LoginActivity.this, GetUserLikesAndCollectsService.class);
                            intent1.putExtras(bundle);
                            startService(intent1);

                            intent.putExtras(bundle);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this,"欢迎！！！",Toast.LENGTH_SHORT).show();
                            LoginActivity.this.finish();
//                            登录成功通知
//                            设置点击
//                            Intent intent1 = new Intent(LoginActivity.this,HomeActivity.class);
//                            PendingIntent pendingIntent = PendingIntent.getActivity(LoginActivity.this,0,intent,0);

                        }
                    }
                }
                if (flag == false){
                    Toast.makeText(LoginActivity.this, "邮箱或密码输入错误，登录失败", Toast.LENGTH_SHORT).show();
                    EmailText.setText(null);
                    PasswordText.setText(null);
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    /**  * 对网络连接状态进行判断  * @return  true, 可用； false， 不可用  */
    private boolean isOpenNetwork() {
        ConnectivityManager connManager = (ConnectivityManager)getSystemService(LoginActivity.CONNECTIVITY_SERVICE);
        if(connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }  return false;
    }
}
