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
            Toast.makeText(MainActivity.this,"????????????????????????????????????",Toast.LENGTH_SHORT).show();
            StartAnimation();
        }

        UserDataBase myDatabase = new UserDataBase(this,"User.db",null,2);
        SQLiteDatabase sqdb = myDatabase.getWritableDatabase();

        int verCode = -1;
        try {
            verCode = MainActivity.this.getPackageManager().getPackageInfo("com.example.littledinosaur", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("version","?????????????????????");
        }
//                APKVersionBean apkVersionBean = new APKVersionBean()
        Log.d("version",String.valueOf(verCode));

//                    ??????????????????
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
//                ??????????????????
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
//                ????????????????????????
        if(verCode<newversion) {

             AlertDialog alert = null;
             AlertDialog.Builder builder = null;
            //?????????Builder
            builder = new AlertDialog.Builder(MainActivity.this);
//????????????????????????View,???????????????
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
    /**  * ?????????????????????????????????  * @return  true, ????????? false??? ?????????  */
    private boolean isOpenNetwork() {
        ConnectivityManager connManager = (ConnectivityManager)getSystemService(MainActivity.CONNECTIVITY_SERVICE);
        if(connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }  return false;
    }
    private void StartAnimation(){

        final ImageView imageView1 = findViewById(R.id.animation);
        AnimationSet animationSet = new AnimationSet(true);//????????????AlphaAnimation????????????????????????????????????????????????????????????
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        //???????????????????????????
        alphaAnimation.setDuration(1000);
        // ???alphaAnimation???????????????AnimationSet??????
        animationSet.addAnimation(alphaAnimation);
        // ??????ImageView???startAnimation??????????????????
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
//// ????????????
//
//    private void checkPermission() {
//        mPermissionList.clear();
//        //???????????????????????????
//        for (int i = 0; i < permissions.length; i++) {
//            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
//                mPermissionList.add(permissions[i]);
//            }
//        }
//        /**
//         * ??????????????????
//         */
//        if (mPermissionList.isEmpty()) {//?????????????????????????????????????????????
//        } else {//??????????????????
//            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//???List????????????
//            ActivityCompat.requestPermissions(MainActivity.this, permissions, PERMISSION_REQUEST);
//        }
//    }
//    /**
//     * ????????????
//     * ???????????????????????????????????????????????????????????????????????????
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
        // areNotificationsEnabled??????????????????????????????????????????API 19?????????19??????????????????????????????????????????true?????????????????????????????????????????????
        boolean isOpened = manager.areNotificationsEnabled();
        Log.d("notification", String.valueOf(isOpened));
        if (!isOpened) {
            Toast.makeText(MainActivity.this,"?????????????????????????????????????????????????????????????????????~",Toast.LENGTH_SHORT).show();
//            try {
//                Intent intent = new Intent();
//                intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
//                //????????????????????? API 26, ???8.0??????8.0??????????????????
////                intent.putExtra(EXTRA_APP_PACKAGE, getPackageName());
////                intent.putExtra(EXTRA_CHANNEL_ID, getApplicationInfo().uid);
//
//                //????????????????????? API21??????25?????? 5.0??????7.1 ???????????????????????????
//                intent.putExtra("app_package", getPackageName());
//                intent.putExtra("app_uid", getApplicationInfo().uid);
//
//                // ??????6 -MIUI9.6-8.0.0??????????????????????????????????????????????????????"????????????????????????"??????????????????????????????????????????????????????????????????I'm not ok!!!
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
//                //??????????????????????????????????????????????????????????????????
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
