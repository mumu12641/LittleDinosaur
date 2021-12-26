package com.example.littledinosaur.activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.littledinosaur.R;

public class CertainSchoolActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView return_btn;
    private ImageView upload;

    private ImageView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certain_school);
        return_btn = findViewById(R.id.about_return_btn);
        upload = findViewById(R.id.upload);

        test  = findViewById(R.id.test);

        return_btn.setOnClickListener(this);
        upload.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.return_btn:
                CertainSchoolActivity.this.finish();
                break;
            case R.id.upload:
                if (ContextCompat.checkSelfPermission(CertainSchoolActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(CertainSchoolActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else {
                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                    intent.setType("image/*");
                    startActivityForResult(intent, 1);
                }
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String imagePath = null;
        if (requestCode == 1){
            Uri uri = data.getData();
            Log.d("uri",String.valueOf(uri));
            if (DocumentsContract.isDocumentUri(this,uri)){
                String docId = DocumentsContract.getDocumentId(uri);
                assert uri != null;
                if("com.android.providers.media.documents".equals(uri.getAuthority())){
                    String id = docId.split(":")[1];
                    Log.d("docid", docId);
                    Log.d("id", id);
                    String selection = MediaStore.Images.Media._ID + "=" +id;
                    Log.d("selection", selection);
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
                }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                    Uri uri1 = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(docId));
                    imagePath = getImagePath(uri1,null);
                }
            }else if("content".equalsIgnoreCase(uri.getScheme())) {
                imagePath = getImagePath(uri, null);
            }else if ("file".equalsIgnoreCase(uri.getScheme())){
                imagePath = uri.getPath();
            }
        }
        if (imagePath != null) {
            Log.d("test", imagePath);
            displayImage(imagePath);
        }else{
            Log.d("test","null");
        }
    }

    public String getImagePath(Uri uri,String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);   //内容提供器
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));   //获取路径
            }
        }
        cursor.close();
        return path;
    }
    private void displayImage(String path){
        if (path!=null){
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            test.setImageBitmap(bitmap);
        }
    }


}