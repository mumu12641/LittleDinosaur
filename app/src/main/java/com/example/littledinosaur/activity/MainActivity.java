package com.example.littledinosaur.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;

import com.example.littledinosaur.ActivityCollector;
import com.example.littledinosaur.service.GetUserDataIntentService;
import com.example.littledinosaur.R;
import com.example.littledinosaur.UserDataBase;

public class MainActivity extends AppCompatActivity {


    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCollector.addAcitivity(this);

        UserDataBase myDatabase = new UserDataBase(this,"User.db",null,1);
        SQLiteDatabase sqdb = myDatabase.getWritableDatabase();


        Intent intent = new Intent(MainActivity.this, GetUserDataIntentService.class);
        startService(intent);

        ImageView imageView = findViewById((R.id.lancnh));
        ImageView imageView1 = findViewById(R.id.animation);
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

             @Override
             public void onAnimationEnd(Animation animation) {
                 Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                 startActivity(intent);
                 MainActivity.this.finish();
             }

             @Override
             public void onAnimationRepeat(Animation animation) {

             }
         });

//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
