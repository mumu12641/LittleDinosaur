package com.example.littledinosaur.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.littledinosaur.R;
import com.example.littledinosaur.activity.LoginActivity;
import com.example.littledinosaur.activity.MySettingActivity;

import static android.content.Context.MODE_PRIVATE;

public class MyFragment extends Fragment {
    private String Username;
    private Context context;
    private Activity activity;
    private LinearLayout line3;
    public MyFragment(Context context,String Username,Activity activity){
        this.context = context;
        this.Username = Username;
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myfragment,container,false);
        LinearLayout line1 = view.findViewById(R.id.line1);
        TextView name = view.findViewById(R.id.username);
        line3 = view.findViewById(R.id.line3);
        name.setText(Username);
        line3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MySettingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Username",Username);
                intent.putExtras(bundle);
                Activity finish = (Activity) context;
                finish.finish();
                startActivity(intent);
            }
        });
        return view;
    }
}
