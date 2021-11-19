package com.example.littledinosaur.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.littledinosaur.HttpRequest;
import com.example.littledinosaur.R;
import com.example.littledinosaur.activity.MessageActivity;

import java.util.List;

public class TreeHoleMessageAdapter extends RecyclerView.Adapter<TreeHoleMessageAdapter.ViewHolder> {

    private List<TreeHoleMessage> list;
    private String UserName;
    private Context context;

    private Handler handler;
    class ViewHolder extends RecyclerView.ViewHolder{

        public static final  int MSG =1;
        TextView messageid;
        TextView messagecontent;
        TextView messagetime;
        TextView messagelikes;
        TextView messagecomments;
        TextView messagecollections;
        LinearLayout likes;
        LinearLayout collections;
        ImageView likeimg;
        ImageView collectimg;
        RelativeLayout messageactivity;



        ViewHolder(View view){
            super(view);
            messagecontent = view.findViewById(R.id.messagecontent);
            messageid = view.findViewById(R.id.messageid);
            messagetime = view.findViewById(R.id.messagetime);
            messagelikes = view.findViewById(R.id.messagelikes);
            messagecomments = view.findViewById(R.id.messagecomments);
            messagecollections = view.findViewById(R.id.messagecollections);
            likes = view.findViewById(R.id.likesline);
            likeimg = view.findViewById(R.id.likes);
            messageactivity = view.findViewById(R.id.messageactivity);
            collections = view.findViewById(R.id.collectionsline);
            collectimg = view.findViewById(R.id.collectionsimg);

            collections.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int possition = (int) v.getTag();
                    final String[] s1 = new String[1];
                    final String MessageId = list.get(possition).getMessageId();
                    Thread thread1 = new Thread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void run() {
                            s1[0] = HttpRequest.IsLikeOrCollectTheMessage(MessageId, TreeHoleMessageAdapter.this.UserName,"1");
                        }
                    });
                    thread1.start();
                    try {
                        thread1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (s1[0].equals("1")){
    //                    取消收藏 把内容发送到服务器
                        ViewHolder.this.collectimg.setImageResource(R.drawable.collection);
                        final String[] s5 = new String[1];
                        Thread thread = new Thread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void run() {
                                s5[0] = HttpRequest.PostLikesOrCollectionsMessage(MessageId, TreeHoleMessageAdapter.this.UserName,"2");
                            }
                        });
                        thread.start();
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String collectnum = ViewHolder.this.messagecollections.getText().toString();
                        int a = Integer.valueOf(collectnum);
                        collectnum = new String(String.valueOf(a-1));
                        ViewHolder.this.messagecollections.setText(collectnum);
                        Toast.makeText(v.getContext(),"取消收藏",Toast.LENGTH_SHORT).show();
                    }else if(s1[0].equals("0")){
    //                    收藏成功
                        final String[] s6 = new String[1];
                        Thread thread = new Thread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void run() {
                                s6[0] = HttpRequest.PostLikesOrCollectionsMessage(MessageId, TreeHoleMessageAdapter.this.UserName,"3");
                            }
                        });
                        thread.start();
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String collectnum = ViewHolder.this.messagecollections.getText().toString();
                        int a = Integer.valueOf(collectnum);
                        collectnum = new String(String.valueOf(a+1));
                        ViewHolder.this.messagecollections.setText(collectnum);
                        ViewHolder.this.collectimg.setImageResource(R.drawable.collectsuccess);
                        Toast.makeText(v.getContext(),"收藏成功",Toast.LENGTH_SHORT).show();
                    }
                    }
                });
            likes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String[] s1 = new String[1];
                    int possition = (int) v.getTag();
                    final String MessageId = list.get(possition).getMessageId();
                    final String[] s = new String[1];
                    Thread thread1 = new Thread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void run() {
                            s1[0] = HttpRequest.IsLikeOrCollectTheMessage(MessageId, TreeHoleMessageAdapter.this.UserName,"0");
                        }
                    });
                    thread1.start();
                    try {
                        thread1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (s1[0].equals("1")){
                        ViewHolder.this.likeimg.setImageResource(R.drawable.like);
                        //取消点赞
                        final String[] s3 = new String[1];
                        Thread thread = new Thread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void run() {
//                                "fruits":["apple","pear","grape"]
                                s3[0] = HttpRequest.PostLikesOrCollectionsMessage(MessageId, TreeHoleMessageAdapter.this.UserName,"0");
                            }
                        });
                        thread.start();
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String likenum = ViewHolder.this.messagelikes.getText().toString();
                        int a = Integer.valueOf(likenum);
                        likenum = new String(String.valueOf(a-1));
                        ViewHolder.this.messagelikes.setText(likenum);
                        Toast.makeText(v.getContext(),"取消点赞",Toast.LENGTH_SHORT).show();
                    }
                    else{
//                    点赞成功
                        Thread thread = new Thread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void run() {
                                s[0] = HttpRequest.PostLikesOrCollectionsMessage(MessageId, TreeHoleMessageAdapter.this.UserName,"1");
                            }
                        });
                        thread.start();
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        String likenum = ViewHolder.this.messagelikes.getText().toString();
                        int a = Integer.valueOf(likenum);
                        likenum = new String(String.valueOf(a+1));
                        ViewHolder.this.messagelikes.setText(likenum);
                        Toast.makeText(v.getContext(),"点赞成功",Toast.LENGTH_SHORT).show();
                        //点赞成功后发送信息到服务器
                        ViewHolder.this.likeimg.setImageResource(R.drawable.likessuccess);
                    }
                }
            });
        }
    }

    public TreeHoleMessageAdapter(List<TreeHoleMessage> list,String UserName,Context context){
        this.list = list;
        this.UserName = UserName;
        this.context = context;
    }

    @SuppressLint("HandlerLeak")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //        创建一个加载了布局的ViewHolder
//        下面开始加载布局
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.treeholemessage,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
//        final String[] s2 = new String[1];
//        final String[] s4 = new String[1];
//        Thread thread1 = new Thread(new Runnable() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void run() {
//                for (int position=0;position<list.size();position++) {
//                    String MessageId = list.get(position).getMessageId();
//                    s2[0] = HttpRequest.IsLikeOrCollectTheMessage(MessageId, TreeHoleMessageAdapter.this.UserName,"0");
//                    if (s2[0].equals("0")){
//                        list.get(position).setImgId(R.drawable.like);
//                        Message msg = new Message();
//                        msg.what =3;
//                        handler.sendMessage(msg);
//                    }else if (s2[0].equals("1")) {
//                        list.get(position).setImgId(R.drawable.likessuccess);
//                        Message msg = new Message();
//                        msg.what =1;
//                        handler.sendMessage(msg);
//                    }
//                }
//            }
//        });
//        Thread thread2 = new Thread(new Runnable() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void run() {
//                for (int position = 0; position < list.size(); position++) {
//                    String MessageId = list.get(position).getMessageId();
//                    s4[0] = HttpRequest.IsLikeOrCollectTheMessage(MessageId, TreeHoleMessageAdapter.this.UserName,"1");
//                    if (s4[0].equals("0")) {
//                        list.get(position).setCollectimgid(R.drawable.collection);
//                        Message msg = new Message();
//                        msg.what =4;
//                        handler.sendMessage(msg);
//                    } else if (s4[0].equals("1")) {
//                        list.get(position).setCollectimgid(R.drawable.collectsuccess);
//                        Message msg = new Message();
//                        msg.what =2;
//                        handler.sendMessage(msg);
//                    }
//                }
//            }
//        });
//        thread2.start();
//        thread1.start();
        viewHolder.messageactivity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int possition = viewHolder.getAdapterPosition();
                 String MessageId = list.get(possition).getMessageId();
                Intent intent = new Intent(v.getContext(), MessageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("MessageId",MessageId);
                bundle.putString("UserName",UserName);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
//        handler = new Handler(){
//            public void handleMessage(Message msg){
//                if (msg.what == 1){
//                    int position = viewHolder.getAdapterPosition();
//                    TreeHoleMessage message = list.get(position);
//                    viewHolder.likeimg.setImageResource(R.drawable.likessuccess);
//                    Log.d("hanlder","likesucces");
//                }else if(msg.what == 2){
//                    viewHolder.collectimg.setImageResource(R.drawable.collectsuccess);
//                }else if(msg.what == 3){
//                    viewHolder.likeimg.setImageResource(R.drawable.like);
//                }else if(msg.what == 4){
//                    viewHolder.collectimg.setImageResource(R.drawable.collection);
//                }
//            }
//        };
        return viewHolder;
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        //        这里加载布局中的资源
//        Fruit fruit = mlist.get(position);
////        holder.textView.setText(fruit.getName());
////        holder.imageView.setImageResource(fruit.getImageId());
        TreeHoleMessage message = list.get(position);
        holder.messagecontent.setText(message.getMessageContent());
        holder.messageid.setText(message.getMessageId());
        holder.messagetime.setText(message.getMessageSendTime());
        holder.messagelikes.setText(message.getMessageLikes());
        holder.messagecomments.setText(message.getMessageComments());
        holder.messagecollections.setText(message.getMessageCollections());

        holder.collections.setTag(position);
        holder.likes.setTag(position);

        holder.likeimg.setImageResource(message.getImgId());
        holder.collectimg.setImageResource(message.getCollectimgid());
                handler = new Handler(){
            public void handleMessage(Message msg){
                if (msg.what == 1){
                    holder.likeimg.setImageResource(R.drawable.likessuccess);
                    Log.d("hanlder","likesucces");
                }else if(msg.what == 2){
                    holder.collectimg.setImageResource(R.drawable.collectsuccess);
                }else if(msg.what == 3){
                    holder.likeimg.setImageResource(R.drawable.like);
                }else if(msg.what == 4){
                    holder.collectimg.setImageResource(R.drawable.collection);
                }
            }
        };
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addMessage(TreeHoleMessage treeHoleMessage){
        list.add(treeHoleMessage);
        notifyDataSetChanged();
    }
}
