package com.example.littledinosaur.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
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
import com.example.littledinosaur.activity.SchoolActivity;
import com.example.littledinosaur.adapter.ToDoNoteAdpter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchFragment extends Fragment implements View.OnClickListener{

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
    private ImageView musicimage;
    private ImageView musicplay;
    private ImageView nextsong;
    private SeekBar musicbar;
    private MediaPlayer mediaPlayer;
    private ImageView research_song;
    private RelativeLayout school_reline;

    private int songLength;//歌曲时长
    private boolean isPlaying = false;//是否在播放
    private musicThread thread;
    private int aleadyPlaySongNum=0;//播放了的歌曲的数目
    private String[] strings = {"http://m10.music.126.net/20211215190055/9c966fb7d78281811eba87138be0f2a7/ymusic/7bd9/a7d9/c4e5/fd6ae6210e54056cdca0bd6c8624123c.mp3",
    "http://m10.music.126.net/20211215204019/ef672147d41bb2774c01ef2ac8ecba54/ymusic/535e/015a/0352/d48c7c9a4c60c7b74c9448aa4c6817df.mp3"};

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
        musicbar = view.findViewById(R.id.musicbar);
        nextsong = view.findViewById(R.id.nextsong);
        musicplay = view.findViewById(R.id.playsong);
        musicimage = view.findViewById(R.id.musicimage);
        research_song = view.findViewById(R.id.research_song);
        school_reline = view.findViewById(R.id.school_reline);

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

        school_reline.setOnClickListener(this);
        musicplay.setOnClickListener(this);
        nextsong.setOnClickListener(this);
        research_song.setOnClickListener(this);
        musicbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress=seekBar.getProgress();
                if (mediaPlayer!=null) {
                    mediaPlayer.seekTo(progress);
                    mediaPlayer.start();
                    musicplay.setBackgroundResource(R.drawable.pause);
                }
            }
        });


        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.playsong:
                if (!isPlaying){
                    isPlaying = true;
                    musicplay.setBackgroundResource(R.drawable.pause);
                    if (mediaPlayer != null&&!mediaPlayer.isPlaying()){
                        musicbar.setProgress(mediaPlayer.getCurrentPosition());
                        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                        Log.d("musicnow",String.valueOf(mediaPlayer.getCurrentPosition()));
                        mediaPlayer.start();
                    }
                }else{
                    isPlaying = false;
                    if(mediaPlayer.isPlaying() && mediaPlayer!=null){
                        mediaPlayer.pause();
                    }
                    musicplay.setBackgroundResource(R.drawable.play);
                }
                if (isPlaying && mediaPlayer == null) {
//                    第一次点击就开始
                    mediaPlayer = new MediaPlayer();
                    try {
                        Toast.makeText(context,"准备歌曲中~~~",Toast.LENGTH_SHORT).show();
//                        mediaPlayer.setDataSource(strings[aleadyPlaySongNum]);
                        mediaPlayer.setDataSource("http://101.201.50.108:3535/GetMusic");
                        mediaPlayer.prepareAsync();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
//                        准备完成
                            Toast.makeText(context,"开始播放~~~",Toast.LENGTH_SHORT).show();
                            mediaPlayer.start();
                            songLength = mediaPlayer.getDuration();
                            musicbar.setMax(songLength);
                            thread = new musicThread();
                            thread.start();
                        }
                    });
                    mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                        @Override
                        public void onBufferingUpdate(MediaPlayer mp, int percent) {
                            //得到缓冲值
                            int secendProssed= mediaPlayer.getDuration()/100*percent;
                            //设置第二进度
                            musicbar.setSecondaryProgress(secendProssed);
                        }
                    });
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
//                        播放完成
                            thread = null;
                            isPlaying = false;
                            mediaPlayer.stop();
                            mediaPlayer.reset();
                            mediaPlayer = null;
                            musicplay.setBackgroundResource(R.drawable.play);
//                            进入下一首
                            aleadyPlaySongNum++;
                            if (aleadyPlaySongNum==2){
                                aleadyPlaySongNum=0;
                            }
                            musicplay.performClick();
                            Log.d("perform","performonclick ok");

                        }
                    });
                }
                break;
            case R.id.nextsong:
                Toast.makeText(context,"下一首",Toast.LENGTH_SHORT).show();
                if (mediaPlayer != null){
                    aleadyPlaySongNum++;
                    if (aleadyPlaySongNum==2){
                        aleadyPlaySongNum=0;
                    }
                    thread = null;
                    isPlaying = false;
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer = null;
                    musicplay.setBackgroundResource(R.drawable.play);
                    musicplay.performClick();
                    Log.d("perform","performonclick ok");
                }
                break;
            case R.id.research_song:
                AlertDialog dialog = null;
                AlertDialog.Builder builder = null;
                builder = new AlertDialog.Builder(context);
                LayoutInflater inflater1 = activity.getLayoutInflater();
                final View view1 = inflater1.inflate(R.layout.dialog_todonote,null,false);
                TextView notecontent = view1.findViewById(R.id.notecontent);
                notecontent.setText("请输入你想要添加的歌曲\n歌曲数据来自于网易云，出现歌曲错误请联系开发人员");
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
                        finalDialog.cancel();
                        Thread thread = new Thread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void run() {
                                String s1 = HttpRequest.PostMusic(s[0]);
                            }
                        });
                        thread.start();
                    }
                });
                view1.findViewById(R.id.dialog_confirm_cancle).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        finalDialog.cancel();
                    }
                });

                dialog.show();
                break;
            case R.id.school_reline:
                Intent intent = new Intent(context, SchoolActivity.class);
                startActivity(intent);
                break;
        }
    }
    private class musicThread extends Thread{
        @Override
        public void run() {
            super.run();
            while(musicbar.getProgress()<musicbar.getMax()) {
                int nowpos = mediaPlayer.getCurrentPosition();
                musicbar.setProgress(nowpos);
                SystemClock.sleep(1000);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("meidadestroy","destroymediaplayer");
        thread = null;
        musicbar.setProgress(musicbar.getMax());
        if (mediaPlayer!=null) {
            mediaPlayer.release();
        }
    }
}
