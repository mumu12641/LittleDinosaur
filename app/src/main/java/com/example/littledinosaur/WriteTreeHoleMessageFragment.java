package com.example.littledinosaur;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WriteTreeHoleMessageFragment extends Fragment {

    private Context context;
    private String Username;

    public WriteTreeHoleMessageFragment(Context context,String Username){
        this.context = context;
        this.Username = Username;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.writetreeholemessagefragment,container,false);
        ReturnBar returnBar = view.findViewById(R.id.line1);
        returnBar.setText("写树洞");
        return view;
    }
}
