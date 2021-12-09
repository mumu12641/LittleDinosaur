package com.example.littledinosaur.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.littledinosaur.HttpRequest;
import com.example.littledinosaur.JsonParse;
import com.example.littledinosaur.ListLikesAndCollects;
import com.example.littledinosaur.R;
import com.example.littledinosaur.adapter.TreeHoleMessage;
import com.example.littledinosaur.adapter.TreeHoleMessageAdapter;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MyMessageAcitivity extends AppCompatActivity implements View.OnClickListener{

    private TextView title;
    private ImageView mymessage_return_btn;
    private RecyclerView recyclerView;
    private List<TreeHoleMessage> messageslist = new ArrayList<>();
    private RelativeLayout emptyline;
    private TextView emptytext;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message_acitivity);

        final Map<String, String[]>[] map = new Map[]{new HashMap<>()};
        Thread thread = new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                JsonParse jsonParse = new JsonParse(HttpRequest.GetTreeHoleMessage());
                try {
                    map[0] = jsonParse.jsonParseTreeHoleMessage();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        String UserName = bundle.getString("Username");
        String titlestr = bundle.getString("flag");


        title = findViewById(R.id.mymemessage_title);
        recyclerView = findViewById(R.id.mymessage_recyclerview);
        emptyline = findViewById(R.id.emptyline);
        emptytext = findViewById(R.id.emptytext1);
        title.setText(titlestr);
        mymessage_return_btn = findViewById(R.id.mymessage_return_btn);
        mymessage_return_btn.setOnClickListener(this);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyMessageAcitivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        TreeHoleMessageAdapter treeHoleMessageAdapter = new TreeHoleMessageAdapter(messageslist,
                UserName,
                MyMessageAcitivity.this);
        assert titlestr != null;

        for(int i = 0; i< Objects.requireNonNull(map[0].get("allMessageId")).length; i++){
            int likeid = R.drawable.like;
            int collectid = R.drawable.collection;
            if(ListLikesAndCollects.likes.contains(Objects.requireNonNull(map[0].get("allMessageId"))[i])){
                likeid = R.drawable.likessuccess;
            }
            if(ListLikesAndCollects.collects.contains(Objects.requireNonNull(map[0].get("allMessageId"))[i])){
                collectid = R.drawable.collectsuccess;
            }
            if(titlestr.equals("我的点赞")&&ListLikesAndCollects.likes.contains(Objects.requireNonNull(map[0].get("allMessageId"))[i])) {
                TreeHoleMessage treeHoleMessage1 = new TreeHoleMessage(
                        Objects.requireNonNull(map[0].get("allMessageId"))[i],
                        Objects.requireNonNull(map[0].get("allMessageContent"))[i],
                        Objects.requireNonNull(map[0].get("allMessageSenderName"))[i],
                        Objects.requireNonNull(map[0].get("allMessageSendTime"))[i],
                        Objects.requireNonNull(map[0].get("allMessageLikes"))[i],
                        Objects.requireNonNull(map[0].get("allMessageComments"))[i],
                        Objects.requireNonNull(map[0].get("allMessageCollections"))[i],
                        Objects.requireNonNull(map[0].get("allMessageUpdateTime"))[i],
                        likeid, collectid);
                treeHoleMessageAdapter.addMessage(treeHoleMessage1);
            }
            else if(titlestr.equals("我的收藏")&&ListLikesAndCollects.collects.contains(Objects.requireNonNull(map[0].get("allMessageId"))[i])){
                TreeHoleMessage treeHoleMessage1 = new TreeHoleMessage(
                        Objects.requireNonNull(map[0].get("allMessageId"))[i],
                        Objects.requireNonNull(map[0].get("allMessageContent"))[i],
                        Objects.requireNonNull(map[0].get("allMessageSenderName"))[i],
                        Objects.requireNonNull(map[0].get("allMessageSendTime"))[i],
                        Objects.requireNonNull(map[0].get("allMessageLikes"))[i],
                        Objects.requireNonNull(map[0].get("allMessageComments"))[i],
                        Objects.requireNonNull(map[0].get("allMessageCollections"))[i],
                        Objects.requireNonNull(map[0].get("allMessageUpdateTime"))[i],
                        likeid, collectid);
                treeHoleMessageAdapter.addMessage(treeHoleMessage1);
            }
            else if (titlestr.equals("我的树洞")&&Objects.requireNonNull(map[0].get("allMessageSenderName"))[i].equals(UserName)){
                TreeHoleMessage treeHoleMessage1 = new TreeHoleMessage(
                        Objects.requireNonNull(map[0].get("allMessageId"))[i],
                        Objects.requireNonNull(map[0].get("allMessageContent"))[i],
                        Objects.requireNonNull(map[0].get("allMessageSenderName"))[i],
                        Objects.requireNonNull(map[0].get("allMessageSendTime"))[i],
                        Objects.requireNonNull(map[0].get("allMessageLikes"))[i],
                        Objects.requireNonNull(map[0].get("allMessageComments"))[i],
                        Objects.requireNonNull(map[0].get("allMessageCollections"))[i],
                        Objects.requireNonNull(map[0].get("allMessageUpdateTime"))[i],
                        likeid, collectid);
                treeHoleMessageAdapter.addMessage(treeHoleMessage1);
            }
        }
        if (treeHoleMessageAdapter.getItemCount()==0){
            Log.d("test","wuneirong");
            emptyline.setVisibility(View.VISIBLE);
            if (titlestr.equals("我的点赞")){
                emptytext.setText("你还没有给任何帖子点赞哦，要不你去点个赞👍试试");
            } else if(titlestr.equals("我的树洞")){
                emptytext.setText("你还没有发过帖子哦，去首页发个帖子试试吧，说不定能碰到和你有共同话题的人呢♥♥♥");
            }else if(titlestr.equals("我的收藏")){
                emptytext.setText("你还没有收藏过任何帖子哦，去收藏一个试试叭");
            }
        }else{
            emptyline.setVisibility(View.INVISIBLE);
        }
        LayoutAnimationController controller = new LayoutAnimationController(AnimationUtils.loadAnimation(MyMessageAcitivity.this,
                R.anim.anim_treeholemessage));
        recyclerView.setLayoutAnimation(controller);
        recyclerView.setAdapter(treeHoleMessageAdapter);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.mymessage_return_btn:
                MyMessageAcitivity.this.finish();
        }
    }
}
