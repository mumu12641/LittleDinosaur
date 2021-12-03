package com.example.littledinosaur.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.littledinosaur.HttpRequest;
import com.example.littledinosaur.ListLikesAndCollects;
import com.example.littledinosaur.R;
import com.example.littledinosaur.activity.DownLoadActivity;
import com.example.littledinosaur.activity.MainActivity;
import com.example.littledinosaur.adapter.ToDoNoteAdpter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchFragment extends Fragment {

    private Context context;
    private Activity activity;
    private TextView calendartext1;
    private TextView calendartext2;
    private TextView calendartext3;
    private TextView mottotext;
    private TextView mottoauther;
    private RecyclerView recyclerView;
    private ImageView addnote_btn;
    private String Username;
    private List<String> list = new ArrayList<>();

    public SearchFragment(Context context,Activity activity,String Username){
        this.context = context;
        this.activity = activity;
        this.Username = Username;
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.searchfragment,container,false);
        calendartext1 = view.findViewById(R.id.calendartext1);
        calendartext2 = view.findViewById(R.id.calendartext2);
        calendartext3 = view.findViewById(R.id.calendartext3);
        mottoauther = view.findViewById(R.id.mottoauther);
        mottotext = view.findViewById(R.id.mottotext);
        recyclerView = view.findViewById(R.id.note_recyclerview);
        addnote_btn = view.findViewById(R.id.addnote_btn);

        String mottotextString = null;
        String mottoautherString = null;
        final String[] s = new String[1];
        Thread thread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://101.201.50.108:3535/GetMotto").build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (response != null){
                        s[0] = Objects.requireNonNull(Objects.requireNonNull(response).body()).string();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = null;
        try {
             jsonObject = new JSONObject(s[0]);
            mottotextString = jsonObject.getString("text");
            mottoautherString = jsonObject.getString("author");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mottoauther.setText("--"+mottoautherString);
        mottotext.setText(mottotextString);

        Calendar calendar = Calendar.getInstance();
        calendartext1.setText(calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONTH) + 1)+"月"+" ");
        calendartext2.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        String[] string3 = {"日","一","二","三","四","五","六"};
        String[] string4 = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        calendartext3.setText("星期"+string3[calendar.get(Calendar.DAY_OF_WEEK)-1]+" | "+string4[calendar.get(Calendar.DAY_OF_WEEK)-1]);

//        list.add("这是第1条note");
//        list.add("这是第2条note");
//        list.add("这是第3条note");
//        list.add("这是第4条note");
//        list.add("这是第5条note");
//        list = ListLikesAndCollects.notes;
        final ToDoNoteAdpter toDoNoteAdpter = new ToDoNoteAdpter(ListLikesAndCollects.notes,context,Username);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(toDoNoteAdpter);

        addnote_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = null;
                AlertDialog.Builder builder = null;
                builder = new AlertDialog.Builder(context);
                LayoutInflater inflater1 = activity.getLayoutInflater();
                final View view1 = inflater1.inflate(R.layout.dialog_todonote,null,false);
                builder.setView(view1);
                builder.setCancelable(false);
                dialog = builder.create();
                final EditText editnote = view1.findViewById(R.id.editnote);
                final String[] s = new String[1];
                final AlertDialog finalDialog = dialog;
                view1.findViewById(R.id.dialog_confirm_sure).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        s[0] = editnote.getText().toString();
                        Toast.makeText(context,"你添加了"+s[0],Toast.LENGTH_SHORT).show();
                        toDoNoteAdpter.addNote(s[0]);
                        finalDialog.cancel();
                        Thread thread = new Thread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void run() {
                                String s1 = HttpRequest.AddOrDeleteNote(Username,"1",s[0]);
                            }
                        });
                        thread.start();
//                        ListLikesAndCollects.addnotes(s[0]);
                    }
                });
                view1.findViewById(R.id.dialog_confirm_cancle).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        finalDialog.cancel();
                    }
                });

                dialog.show();
            }
        });


        return view;
    }

}
