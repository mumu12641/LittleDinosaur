package com.example.littledinosaur.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littledinosaur.ListLikesAndCollects;
import com.example.littledinosaur.UserDataBase;
import com.example.littledinosaur.adapter.CommentMessage;
import com.example.littledinosaur.adapter.CommentMessageAdapter;
import com.example.littledinosaur.HttpRequest;
import com.example.littledinosaur.JsonParse;
import com.example.littledinosaur.R;
import com.example.littledinosaur.adapter.TreeHoleMessage;
import com.example.littledinosaur.adapter.TreeHoleMessageAdapter;

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
    private ScrollView scrollView;
    private String MessageId;
    private String UserName;
    private String IsLike;
    private String IsCollect;
    private ImageView messagesendericon;
    private String MessageContent;
    private String MessageSendTime;
    private String MessageSenderName;
    private String MessageLikes;
    private String MessageComments;
    private String MessageCollections;
    private LinearLayout emptyline;
    private TextView emptytext;
    final int[] iconid = {R.drawable.icon0,R.drawable.icon1,R.drawable.icon2,R.drawable.icon3,R.drawable.icon4,
            R.drawable.icon5,R.drawable.icon6,R.drawable.icon7,R.drawable.icon8,R.drawable.icon9,
            R.drawable.icon10,R.drawable.icon11};

    private String extra;
    private String str;
    private TreeHoleMessage treeHoleMessage;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
//        ??????????????????????????????????????????????????????
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1){
                likeimg.setBackgroundResource(R.drawable.likessuccess);
            }else if(msg.what ==2){
                collectimg.setBackgroundResource(R.drawable.collectsuccess);
            }
        }
    };
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        MessageId = bundle.getString("MessageId");
        UserName = bundle.getString("UserName");

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
        scrollView = findViewById(R.id.scollerview);
        likes = findViewById(R.id.likesline);
        likeimg = findViewById(R.id.likes);
        collections = findViewById(R.id.collectionsline);
        collectimg = findViewById(R.id.collectionsimg);
        return_btn = findViewById(R.id.return_btn);
        recyclerView = findViewById(R.id.commentss);
        messagesendericon = findViewById(R.id.messagesendericon);
        emptyline = findViewById(R.id.emptyline);
        emptytext = findViewById(R.id.emptytext);

        collections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] s1 = new String[1];
                Thread thread1 = new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        s1[0] = HttpRequest.IsLikeOrCollectTheMessage(MessageId, UserName,"1");
                    }
                });
                thread1.start();
                try {
                    thread1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (s1[0].equals("1")){
                    //                    ???????????? ???????????????????????????
                    collectimg.setImageResource(R.drawable.collection);
                    final String[] s5 = new String[1];
                    Thread thread = new Thread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void run() {
                            s5[0] = HttpRequest.PostLikesOrCollectionsMessage(MessageId, UserName,"2");
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String collectnum = messagecollections.getText().toString();
                    int a = Integer.valueOf(collectnum);
                    collectnum = new String(String.valueOf(a-1));
                    messagecollections.setText(collectnum);
                    Toast.makeText(v.getContext(),"????????????",Toast.LENGTH_SHORT).show();
                    ListLikesAndCollects.removeCollect(MessageId);
                }else if(s1[0].equals("0")){
                    //                    ????????????
                    final String[] s6 = new String[1];
                    Thread thread = new Thread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void run() {
                            s6[0] = HttpRequest.PostLikesOrCollectionsMessage(MessageId, UserName,"3");
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String collectnum = messagecollections.getText().toString();
                    int a = Integer.valueOf(collectnum);
                    collectnum = new String(String.valueOf(a+1));
                    messagecollections.setText(collectnum);
                    collectimg.setImageResource(R.drawable.collectsuccess);
                    Toast.makeText(v.getContext(),"????????????",Toast.LENGTH_SHORT).show();
                    ListLikesAndCollects.addCollect(MessageId);
                }
            }
        });
        likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] s1 = new String[1];
                final String[] s = new String[1];
                Thread thread1 = new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        s1[0] = HttpRequest.IsLikeOrCollectTheMessage(MessageId, UserName,"0");
                    }
                });
                thread1.start();
                try {
                    thread1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (s1[0].equals("1")){
                    likeimg.setImageResource(R.drawable.like);
                    //????????????
                    final String[] s3 = new String[1];
                    Thread thread = new Thread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void run() {
//                                "fruits":["apple","pear","grape"]
                            s3[0] = HttpRequest.PostLikesOrCollectionsMessage(MessageId,UserName,"0");
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String likenum = messagelikes.getText().toString();
                    int a = Integer.valueOf(likenum);
                    likenum = new String(String.valueOf(a-1));
                    messagelikes.setText(likenum);
                    Toast.makeText(v.getContext(),"????????????",Toast.LENGTH_SHORT).show();
                    ListLikesAndCollects.removeLike(MessageId);
                }
                else{
//                    ????????????
                    Thread thread = new Thread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void run() {
                            s[0] = HttpRequest.PostLikesOrCollectionsMessage(MessageId,UserName,"1");
                        }
                    });
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String likenum = messagelikes.getText().toString();
                    int a = Integer.valueOf(likenum);
                    likenum = new String(String.valueOf(a+1));
                    messagelikes.setText(likenum);
                    Toast.makeText(v.getContext(),"????????????",Toast.LENGTH_SHORT).show();
                    //???????????????????????????????????????
                    likeimg.setImageResource(R.drawable.likessuccess);
                    ListLikesAndCollects.addLike(MessageId);
                }
            }
        });

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


        Thread thread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                str = HttpRequest.GetTreeHoleMessageContent(MessageId);
                IsLike = HttpRequest.IsLikeOrCollectTheMessage(MessageId,UserName,"0");
                IsCollect = HttpRequest.IsLikeOrCollectTheMessage(MessageId,UserName,"1");
                if (IsLike.equals("1")){
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
                if (IsCollect.equals("1")){
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                }
            }
        });
        thread.start();
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        Thread thread1 = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                str = HttpRequest.GetTreeHoleMessageContent(MessageId);
            }
        });
        thread1.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
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


        UserDataBase myDatabase = new UserDataBase(MessageActivity.this,"User.db",null,2);
        SQLiteDatabase sqdb = myDatabase.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = sqdb.query("User", null, "UserName=?", new String[]{treeHoleMessage.getMessageSenderName()}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex("UserName")).equals(treeHoleMessage.getMessageSenderName())){
                    messagesendericon.setImageResource(iconid[Integer.parseInt(cursor.getString(cursor.getColumnIndex("Extra")))]);
                }
            }
        }
        @SuppressLint("Recycle") Cursor cursor1 = sqdb.query("User", null, "UserName=?", new String[]{UserName}, null, null, null);
        if (cursor1 != null) {
            while (cursor1.moveToNext()) {
                if (cursor1.getString(cursor1.getColumnIndex("UserName")).equals(UserName)){
                    extra = cursor1.getString(cursor1.getColumnIndex("Extra"));
                }
            }
        }

        sendcommentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String CommentStr = editcomment.getText().toString();
                if (CommentStr.length() != 0){
                    Calendar calendar = Calendar.getInstance();
                    final String nowTime;
                    if(calendar.get(Calendar.MINUTE)<10){
                        nowTime = calendar.get(Calendar.YEAR) +
                                "/" + (calendar.get(Calendar.MONTH) + 1) +
                                "/" + calendar.get(Calendar.DAY_OF_MONTH) + " " +
                                calendar.get(Calendar.HOUR_OF_DAY) + ":0" +
                                calendar.get(Calendar.MINUTE);
                    }else {
                        nowTime = calendar.get(Calendar.YEAR) +
                                "/" + (calendar.get(Calendar.MONTH) + 1) +
                                "/" + calendar.get(Calendar.DAY_OF_MONTH) + " " +
                                calendar.get(Calendar.HOUR_OF_DAY) + ":" +
                                calendar.get(Calendar.MINUTE);
                    }
                    Thread thread1 = new Thread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void run() {
                            HttpRequest.PostMessageComment(MessageId,UserName,nowTime,CommentStr,extra);
                        }
                    });
                    thread1.start();
                    editcomment.setText("");
                    editcomment.clearFocus();
                    refreshLayout.setRefreshing(true);
                    MessageActivity.this.onRefresh();
                    Toast.makeText(MessageActivity.this,"????????????",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MessageActivity.this,"???????????????????????????",Toast.LENGTH_SHORT).show();
                }
            }
        });

        list = new ArrayList<>();
        final String[] getcommentstr = new String[1];
        Thread thread2 = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                getcommentstr[0] = HttpRequest.GetMessageComment(MessageId);
                JsonParse jsonParse1 = new JsonParse(getcommentstr[0]);
                try {
                    list = jsonParse1.jsonParseMessageComment(MessageId,MessageActivity.this);
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
        if (commentMessageAdapter.getItemCount()==0){
            emptyline.setVisibility(View.VISIBLE);
            emptytext.setText("??????~?????????????????????????????? ????????????????????????????????????????????????~");
        }else{
            emptyline.setVisibility(View.INVISIBLE);
        }
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
                            list = jsonParse1.jsonParseMessageComment(MessageId,MessageActivity.this);
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
                if (commentMessageAdapter.getItemCount()==0){
                    emptyline.setVisibility(View.VISIBLE);
                }else{
                    emptyline.setVisibility(View.INVISIBLE);
                }
                refreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}
