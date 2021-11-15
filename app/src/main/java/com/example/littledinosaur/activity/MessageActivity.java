package com.example.littledinosaur.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littledinosaur.adapter.CommentMessage;
import com.example.littledinosaur.adapter.CommentMessageAdapter;
import com.example.littledinosaur.HttpRequest;
import com.example.littledinosaur.JsonParse;
import com.example.littledinosaur.R;
import com.example.littledinosaur.adapter.TreeHoleMessage;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MessageActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private TextView messageidtext;
    private TextView messagesendername;
    private TextView messagesendtime;
    private TextView messagecontent;
    private TextView messagelikes;
    private TextView messagecomments;
    private TextView messagecollections;
    private EditText editcomment;
    private LinearLayout likes;
    private LinearLayout collections;
    private ImageView likeimg;
    private ImageView collectimg;
    private ImageView return_btn;
    private Button sendcommentbtn;
    private RecyclerView recyclerView;
    private List<CommentMessage> list;
    private SwipeRefreshLayout refreshLayout;
    private String MessageId;
    private String UserName;
    private String IsLike;
    private String IsCollect;
    private String MessageContent;
    private String MessageSendTime;
    private String MessageSenderName;
    private String MessageLikes;
    private String MessageComments;
    private String MessageCollections;


    private String str;
    private TreeHoleMessage treeHoleMessage;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        messageidtext = findViewById(R.id.messageidtext);
        messagecontent = findViewById(R.id.messageContent);
        messagesendername = findViewById(R.id.messagesendername);
        messagesendtime = findViewById(R.id.messagesendtime);
        messagelikes = findViewById(R.id.messagelikes);
        messagecomments = findViewById(R.id.messagecomments);
        messagecollections = findViewById(R.id.messagecollections);
        sendcommentbtn = findViewById(R.id.sendcommentbtn);
        editcomment = findViewById(R.id.editcomment);
        refreshLayout = findViewById(R.id.refreshcomments);
        likes = findViewById(R.id.likesline);
        likeimg = findViewById(R.id.likes);
        collections = findViewById(R.id.collectionsline);
        collectimg = findViewById(R.id.collectionsimg);
        return_btn = findViewById(R.id.return_btn);
        recyclerView = findViewById(R.id.commentss);

        refreshLayout.setOnRefreshListener(this);
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread () {
                    public void run () {
                        try {
                            Instrumentation inst= new Instrumentation();
                            inst.sendKeyDownUpSync(KeyEvent. KEYCODE_BACK);
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        MessageId = bundle.getString("MessageId");
        UserName = bundle.getString("UserName");
        Thread thread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                str = HttpRequest.GetTreeHoleMessageContent(MessageId);
                IsLike = HttpRequest.IsLikeOrCollectTheMessage(MessageId,UserName,"0");
                IsCollect = HttpRequest.IsLikeOrCollectTheMessage(MessageId,UserName,"1");
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (IsLike.equals("1")){
            likeimg.setBackgroundResource(R.drawable.likessuccess);
        }
        if (IsCollect.equals("1")){
            collectimg.setBackgroundResource(R.drawable.collectsuccess);
        }
        JsonParse jsonParse = new JsonParse(str);
        try {
            treeHoleMessage = jsonParse.jsonParseTreeHoleMessageContent();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        messagesendtime.setText(treeHoleMessage.getMessageSendTime());
        messagesendername.setText(treeHoleMessage.getMessageSenderName());
        messagecontent.setText(treeHoleMessage.getMessageContent());
        messageidtext.setText(treeHoleMessage.getMessageId());
        messagelikes.setText(treeHoleMessage.getMessageLikes());
        messagecomments.setText(treeHoleMessage.getMessageComments());
        messagecollections.setText(treeHoleMessage.getMessageCollections());

        sendcommentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String CommentStr = editcomment.getText().toString();
                if (CommentStr.length() != 0){
                    Calendar calendar = Calendar.getInstance();
                    final String nowTime = calendar.get(Calendar.YEAR) +
                                "/" + (calendar.get(Calendar.MONTH) + 1) +
                                "/" + calendar.get(Calendar.DAY_OF_MONTH) + " " +
                                calendar.get(Calendar.HOUR_OF_DAY) + ":" +
                                calendar.get(Calendar.MINUTE);
                    Thread thread1 = new Thread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void run() {
                            HttpRequest.PostMessageComment(MessageId,UserName,nowTime,CommentStr);
                        }
                    });
                    thread1.start();
                    editcomment.setText("");
                    editcomment.clearFocus();
                    refreshLayout.setRefreshing(true);
                    MessageActivity.this.onRefresh();
                    Toast.makeText(MessageActivity.this,"发送成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MessageActivity.this,"请先输入你的评论哦",Toast.LENGTH_SHORT).show();
                }
            }
        });

        list = new ArrayList<>();
//        CommentMessage commentMessage = new CommentMessage("#1231",
//                "这是一条新评论","pbpbpbpb",
//                "2021/11/12 23:08");
//        list.add(commentMessage);
        final String[] getcommentstr = new String[1];
        Thread thread2 = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                getcommentstr[0] = HttpRequest.GetMessageComment(MessageId);
                JsonParse jsonParse1 = new JsonParse(getcommentstr[0]);
                try {
                    list = jsonParse1.jsonParseMessageComment(MessageId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread2.start();
        try {
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MessageActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        CommentMessageAdapter commentMessageAdapter = new CommentMessageAdapter(list,MessageActivity.this);
        recyclerView.setAdapter(commentMessageAdapter);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final String[] getcommentstr = new String[1];
                Thread thread2 = new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        getcommentstr[0] = HttpRequest.GetMessageComment(MessageId);
                        JsonParse jsonParse1 = new JsonParse(getcommentstr[0]);
                        try {
                            list = jsonParse1.jsonParseMessageComment(MessageId);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread2.start();
                try {
                    thread2.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CommentMessageAdapter commentMessageAdapter = new CommentMessageAdapter(list,MessageActivity.this);
                recyclerView.setAdapter(commentMessageAdapter);
                refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}
