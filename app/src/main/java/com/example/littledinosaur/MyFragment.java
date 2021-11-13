package com.example.littledinosaur;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment {
    private String Username;
    private Context context;
    public MyFragment(Context context,String Username){
        this.context = context;
        this.Username = Username;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myfragment,container,false);
        LinearLayout line1 = view.findViewById(R.id.line1);
        TextView name = view.findViewById(R.id.username);
        name.setText(Username);
        line1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MySettingActivity.class);
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
