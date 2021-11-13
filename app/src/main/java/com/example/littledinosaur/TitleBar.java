package com.example.littledinosaur;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

public class TitleBar extends LinearLayout {
//    自定义标题框控件
    public TitleBar(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.titlebar,this);
    }
    public void setText(String string){
        TextView textView = findViewById(R.id.titletext);
        textView.setText(string);
    }
}
