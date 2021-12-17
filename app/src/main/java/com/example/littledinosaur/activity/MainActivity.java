/*
  ___        __ _       _ _           ____  _             _ _
 |_ _|_ __  / _(_)_ __ (_) |_ _   _  / ___|| |_ _   _  __| (_) ___
  | || '_ \| |_| | '_ \| | __| | | | \___ \| __| | | |/ _` | |/ _ \
  | || | | |  _| | | | | | |_| |_| |  ___) | |_| |_| | (_| | | (_) |
 |___|_| |_|_| |_|_| |_|_|\__|\__, | |____/ \__|\__,_|\__,_|_|\___/
                              |___/
 */



package com.example.littledinosaur.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.littledinosaur.HttpRequest;
import com.example.littledinosaur.service.GetUserDataIntentService;
import com.example.littledinosaur.R;
import com.example.littledinosaur.UserDataBase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
    private ImageView imageView;
    private ImageView imageView3;
    private ImageView imageView4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // checkPermission();
        checkNotifySetting();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.lancnh);
        if (!isOpenNetwork()){
            Toast.makeText(MainActivity.this,"网络未连接，即将退出应用",Toast.LENGTH_SHORT).show();
            StartAnimation();
        }

        UserDataBase myDatabase = new UserDataBase(this,"User.db",null,2);
        SQLiteDatabase sqdb = myDatabase.getWritableDatabase();

        int verCode = -1;
        try {
            verCode = MainActivity.this.getPackageManager().getPackageInfo("com.example.littledinosaur", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("version","版本号获取失败");
        }
//                APKVersionBean apkVersionBean = new APKVersionBean()
        Log.d("version",String.valueOf(verCode));

//                    获取版本信息
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
            newversion = Integer.parseInt(jsonObject[0].getString("apkVersion"));
            apkDescribe = jsonObject[0].getString("apkDescribe");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//                如果不是最新版本
        if(verCode<newversion) {

             AlertDialog alert = null;
             AlertDialog.Builder builder = null;
            //初始化Builder
            builder = new AlertDialog.Builder(MainActivity.this);
//加载自定义的那个View,同时设置下
            final LayoutInflater inflater = MainActivity.this.getLayoutInflater();
            View view_dialog = inflater.inflate(R.layout.dialog_update, null,false);
            builder.setView(view_dialog);
            builder.setCancelable(false);
            alert = builder.create();

            String finalApkDescribe = apkDescribe;
            view_dialog.findViewById(R.id.update_btn).setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(MainActivity.this, DownLoadActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("updatecontent", finalApkDescribe);
                    intent1.putExtras(bundle);
                    startActivity(intent1);
                }
            });
            alert.show();
        }
        else {
            if (isOpenNetwork()) {
                Intent intent = new Intent(MainActivity.this, GetUserDataIntentService.class);
                startService(intent);
            }
            StartAnimation();
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    /**  * 对网络连接状态进行判断  * @return  true, 可用； false， 不可用  */
    private boolean isOpenNetwork() {
        ConnectivityManager connManager = (ConnectivityManager)getSystemService(MainActivity.CONNECTIVITY_SERVICE);
        if(connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }  return false;
    }
    private void StartAnimation(){

        final ImageView imageView1 = findViewById(R.id.animation);
        AnimationSet animationSet = new AnimationSet(true);//创建一个AlphaAnimation对象，参数从完全的透明度，到完全的不透明
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        //设置动画执行的时间
        alphaAnimation.setDuration(1000);
        // 将alphaAnimation对象添加到AnimationSet当中
        animationSet.addAnimation(alphaAnimation);
        // 使用ImageView的startAnimation方法执行动画
        imageView.startAnimation(animationSet);
        imageView1.startAnimation(animationSet);

        animationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
//                 }
                MainActivity.this.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


//    String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
//    List<String> mPermissionList = new ArrayList<>();
//
//    // private ImageView welcomeImg = null;
//    private static final int PERMISSION_REQUEST = 1;
//// 检查权限
//
//    private void checkPermission() {
//        mPermissionList.clear();
//        //判断哪些权限未授予
//        for (int i = 0; i < permissions.length; i++) {
//            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
//                mPermissionList.add(permissions[i]);
//            }
//        }
//        /**
//         * 判断是否为空
//         */
//        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
//        } else {//请求权限方法
//            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
//            ActivityCompat.requestPermissions(MainActivity.this, permissions, PERMISSION_REQUEST);
//        }
//    }
//    /**
//     * 响应授权
//     * 这里不管用户是否拒绝，都进入首页，不再重复申请权限
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case PERMISSION_REQUEST:
//                break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//                break;
//        }
//    }
    private void checkNotifySetting() {
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        // areNotificationsEnabled方法的有效性官方只最低支持到API 19，低于19的仍可调用此方法不过只会返回true，即默认为用户已经开启了通知。
        boolean isOpened = manager.areNotificationsEnabled();
        Log.d("notification", String.valueOf(isOpened));
        if (!isOpened) {
            Toast.makeText(MainActivity.this,"你还没有开启该应用的通知权限哦，请前往设置打开~",Toast.LENGTH_SHORT).show();
//            try {
//                Intent intent = new Intent();
//                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
//                //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
////                intent.putExtra(EXTRA_APP_PACKAGE, getPackageName());
////                intent.putExtra(EXTRA_CHANNEL_ID, getApplicationInfo().uid);
//
//                //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
//                intent.putExtra("app_package", getPackageName());
//                intent.putExtra("app_uid", getApplicationInfo().uid);
//
//                // 小米6 -MIUI9.6-8.0.0系统，是个特例，通知设置界面只能控制"允许使用通知圆点"——然而这个玩意并没有卵用，我想对雷布斯说：I'm not ok!!!
//                //  if ("MI 6".equals(Build.MODEL)) {
//                //      intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                //      Uri uri = Uri.fromParts("package", getPackageName(), null);
//                //      intent.setData(uri);
//                //      // intent.setAction("com.android.settings/.SubSettings");
//                //  }
//                startActivity(intent);
//            } catch (Exception e) {
//                e.printStackTrace();
//                Intent intent = new Intent();
//
//                //下面这种方案是直接跳转到当前应用的设置界面。
//                //https://blog.csdn.net/ysy950803/article/details/71910806
//                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                Uri uri = Uri.fromParts("package", getPackageName(), null);
//                intent.setData(uri);
//                startActivity(intent);
//            }
        }else{
            Log.d("permission","yes");
        }
    }

}
