package com.example.littledinosaur.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.littledinosaur.HttpRequest;
import com.example.littledinosaur.JsonParse;
import com.example.littledinosaur.ListLikesAndCollects;
import com.example.littledinosaur.R;
import com.example.littledinosaur.adapter.TreeHoleMessage;
import com.example.littledinosaur.adapter.TreeHoleMessageAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class HomeFragment extends Fragment {
    private List<TreeHoleMessage> messageslist = new ArrayList<>();
    private Context context;
    private FloatingActionButton floatingActionButton;
    private String UserName;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TreeHoleMessageAdapter treeHoleMessageAdapter;
    private  int allItemNum=0;//所有条目的个数
    private  int itemNum = 0;//现在显示的条目个数
    private  int lastItem = 0;//最后一个条目的位置
    private  boolean loadingFlag = true;//是否可以接着加载更多
    private final Map<String, String[]>[] map = new Map[]{new HashMap<>()};


    public HomeFragment(Context context,String username){
        this.context = context;
        this.UserName = username;
    }

    public List<TreeHoleMessage> getMessageslist() {
        return messageslist;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homefragment,container,false);
        recyclerView = view.findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        treeHoleMessageAdapter = new TreeHoleMessageAdapter(messageslist,UserName,context);

        initArgs();

        Log.d("init", String.valueOf(allItemNum));

        final String[] s = new String[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                s[0] =HttpRequest.GetMessageNum();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        allItemNum = Integer.parseInt(s[0]);


        loadingMore(1,10);
        LayoutAnimationController controller = new LayoutAnimationController(AnimationUtils.loadAnimation(context,R.anim.anim_treeholemessage));
        recyclerView.setLayoutAnimation(controller);
        recyclerView.setAdapter(treeHoleMessageAdapter);
        listenLoadMore();
        return view;
    }

    private void initArgs(){
           allItemNum=0;
           itemNum = 0;
           lastItem = 0;
           loadingFlag = true;
    }

    private void listenLoadMore(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                监听滚动状态
                if(newState == RecyclerView.SCROLL_STATE_IDLE && lastItem + 1 == treeHoleMessageAdapter.getItemCount()){
                    //如果滑动停止 这个时候我们实现刷新
                    if(loadingFlag) {
                        if (itemNum > allItemNum) {
                            Toast.makeText(context, "加载完辣~~~~", Toast.LENGTH_SHORT).show();
                            loadingFlag = false;
                        }
                        if (loadingFlag) {
                            Toast.makeText(context, "正在加载更多~~~", Toast.LENGTH_SHORT).show();
                            loadingMore(itemNum + 1, itemNum + 10);
                        }
                    }
                }

            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                监听滚动过程
                lastItem = linearLayoutManager.findLastVisibleItemPosition();//获取最后一个条目的位置
            }
        });
    }
    private void loadingMore(int start,int end){
//        Log.d("loading","loading");
        Thread thread = new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                JsonParse jsonParse = new JsonParse(HttpRequest.GetSpecificTreeHoleMessage(start,end));
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
        for(int i = 0; i< Objects.requireNonNull(map[0].get("allMessageId")).length; i++){
            int likeid = R.drawable.like;
            int collectid = R.drawable.collection;
            if(ListLikesAndCollects.likes.contains(Objects.requireNonNull(map[0].get("allMessageId"))[i])){
                likeid = R.drawable.likessuccess;
            }
            if(ListLikesAndCollects.collects.contains(Objects.requireNonNull(map[0].get("allMessageId"))[i])){
                collectid = R.drawable.collectsuccess;
            }

            TreeHoleMessage treeHoleMessage1 = new TreeHoleMessage(
                    Objects.requireNonNull(map[0].get("allMessageId"))[i],
                    Objects.requireNonNull(map[0].get("allMessageContent"))[i],
                    Objects.requireNonNull(map[0].get("allMessageSenderName"))[i],
                    Objects.requireNonNull(map[0].get("allMessageSendTime"))[i],
                    Objects.requireNonNull(map[0].get("allMessageLikes"))[i],
                    Objects.requireNonNull(map[0].get("allMessageComments"))[i],
                    Objects.requireNonNull(map[0].get("allMessageCollections"))[i],
                    Objects.requireNonNull(map[0].get("allMessageUpdateTime"))[i],
                    likeid,collectid);
            treeHoleMessageAdapter.addMessage(treeHoleMessage1);
        }
        itemNum = end;
    }
}
