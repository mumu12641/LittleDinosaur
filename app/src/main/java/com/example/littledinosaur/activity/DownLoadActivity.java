package com.example.littledinosaur.activity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.littledinosaur.R;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class DownLoadActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView down;
    private TextView progress;
    private TextView file_name;
    private TextView nowversion;
    private ProgressBar pb_update;
    private DownloadManager downloadManager;
    private DownloadManager.Request request;
    private TextView tishi;
    private TextView updatecontent;
    private String update;
    public static String downloadUrl = "http://101.201.50.108:3535/APKDownload";
    Timer timer;
    long id;
    TimerTask task;
    @SuppressLint("HandlerLeak")
    Handler handler =new Handler(){
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            int pro = bundle.getInt("pro");
            String name  = bundle.getString("name");
            pb_update.setProgress(pro);
            Log.d("pro", String.valueOf(pro));
            progress.setText(String.valueOf(pro)+"%");
            file_name.setText(name);
        }
    };

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        down = (TextView) findViewById(R.id.down);
        progress = (TextView) findViewById(R.id.progress);
        file_name = (TextView) findViewById(R.id.file_name);
        nowversion = findViewById(R.id.nowversion);
        updatecontent = findViewById(R.id.updatecontent);
        pb_update = findViewById(R.id.pb_update);
        tishi = findViewById(R.id.tishi);
        tishi.setVisibility(View.INVISIBLE);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        update = bundle.getString("updatecontent");
        updatecontent.setText(update);


        String verName = null;
        try {
            verName = DownLoadActivity.this.getPackageManager().getPackageInfo(DownLoadActivity.this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        nowversion.setText("v " + verName);

        down.setOnClickListener(this);
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        request = new DownloadManager.Request(Uri.parse(downloadUrl));

        request.setTitle("LittleDinosaur");
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(true);
        request.setMimeType("application/vnd.android.package-archive");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //创建目录
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir() ;

        //设置文件存放路径
//        request.setDestinationInExternalFilesDir( this , Environment.DIRECTORY_DOWNLOADS ,  "LittleDinosaur.apk" );
        request.setDestinationInExternalPublicDir(  Environment.DIRECTORY_DOWNLOADS  , "LittleDinosaur.apk" ) ;
        pb_update.setMax(100);
        final  DownloadManager.Query query = new DownloadManager.Query();
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                Cursor cursor = downloadManager.query(query.setFilterById(id));
                if (cursor != null && cursor.moveToFirst()) {
                    if (cursor.getInt(
                            cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        pb_update.setProgress(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tishi.setVisibility(View.VISIBLE);
                                down.setText("下载完成");
                                Toast.makeText(DownLoadActivity.this,"下载完成，请下滑状态栏进行安装哟~~~",Toast.LENGTH_SHORT).show();
                            }
                        });

                        //install(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/LittleDinosaur.apk" );
                        task.cancel();
                    }
                    String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
                    String address = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    int bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    int pro =  (bytes_downloaded * 100) / bytes_total;
                    Message msg =Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putInt("pro",pro);
                    bundle.putString("name",title);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
                cursor.close();
            }
        };
        timer.schedule(task, 0,100);
    }

    @Override
    public void onClick(View v) {
        id = downloadManager.enqueue(request);
        task.run();
        down.setClickable(false);
        down.setText("正在下载");
//        down.setBackgroundResource(R.drawable.btn_disable_shape);

    }
    private void install(String path) {
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(Uri.parse("file://" + path), "application/vnd.android.package-archive");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//4.0以上系统弹出安装成功打开界面
//        startActivity(intent);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File apkFile = new File(path);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(DownLoadActivity.this, "com.example.littledinosaur.fileprovider", apkFile);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }
}
