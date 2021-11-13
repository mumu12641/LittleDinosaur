package com.example.littledinosaur;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReturnBar extends LinearLayout {
    private TextView textView;
    private Button button;
    public ReturnBar(final Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.returnbar,this);
        textView = findViewById(R.id.titletext);
        button = findViewById(R.id.img);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
    }

    public void setText(String text){
        textView.setText(text);
    }
}
