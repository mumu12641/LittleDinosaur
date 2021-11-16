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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.littledinosaur.HttpRequest;
import com.example.littledinosaur.JsonParse;
import com.example.littledinosaur.R;
import com.example.littledinosaur.adapter.TreeHoleMessage;
import com.example.littledinosaur.adapter.TreeHoleMessageAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

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
    public HomeFragment(Context context,String username){
        this.context = context;
        this.UserName = username;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.homefragment,container,false);
//        refreshLayout = view.findViewById(R.id.refresh);
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
////                HomeActivity activity = (HomeActivity) getActivity();
////                assert activity != null;
////                activity.setHomeFragment();
//                Toast.makeText(context,"刷新",Toast.LENGTH_SHORT).show();
//                refreshLayout.setRefreshing(false);
//            }
//        });

//        initMessage();
//        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
////
////
//////        floatingActionButton = view.findViewById(R.id.floatingbutton1);
//////        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//////            @Override
//////            public void onClick(View v) {
//////                Intent intent = new Intent(context, WriteTreeHoleMessageActivity.class);
//////                Bundle bundle = new Bundle();
//////                bundle.putString("Username",UserName);
//////                intent.putExtras(bundle);
//////                startActivityForResult(intent,1);
//////            }
//////        });
////
////
////        指定recyclerView的布局方式
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        TreeHoleMessageAdapter treeHoleMessageAdapter = new TreeHoleMessageAdapter(messageslist);
//        recyclerView.setAdapter(treeHoleMessageAdapter);
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

//        RecyclerView recyclerView = frameLayout.findViewById(R.id.recyclerview);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(linearLayoutManager);
//        for(int i = 0; i< Objects.requireNonNull(map[0].get("allMessageId")).length; i++){
//            TreeHoleMessage treeHoleMessage1 = new TreeHoleMessage(
//                    Objects.requireNonNull(map[0].get("allMessageId"))[i],
//                    Objects.requireNonNull(map[0].get("allMessageContent"))[i],
//                    Objects.requireNonNull(map[0].get("allMessageSenderName"))[i],
//                    Objects.requireNonNull(map[0].get("allMessageSendTime"))[i]);
//
//            Toast.makeText(HomeActivity.this, Objects.requireNonNull(map[0].get("allMessageId"))[i],Toast.LENGTH_SHORT).show();
//
//            messageslist.add(treeHoleMessage1);
//        }
//        TreeHoleMessageAdapter treeHoleMessageAdapter = new TreeHoleMessageAdapter(messageslist);
//        recyclerView.setAdapter(treeHoleMessageAdapter);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//          横向分布
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        TreeHoleMessageAdapter treeHoleMessageAdapter = new TreeHoleMessageAdapter(messageslist,UserName,context);
        Log.d("Username",UserName);
        for(int i = 0; i< Objects.requireNonNull(map[0].get("allMessageId")).length; i++){
            TreeHoleMessage treeHoleMessage1 = new TreeHoleMessage(
                    Objects.requireNonNull(map[0].get("allMessageId"))[i],
                    Objects.requireNonNull(map[0].get("allMessageContent"))[i],
                    Objects.requireNonNull(map[0].get("allMessageSenderName"))[i],
                    Objects.requireNonNull(map[0].get("allMessageSendTime"))[i],
                    Objects.requireNonNull(map[0].get("allMessageLikes"))[i],
                    Objects.requireNonNull(map[0].get("allMessageComments"))[i],
                    Objects.requireNonNull(map[0].get("allMessageCollections"))[i],
                    Objects.requireNonNull(map[0].get("allMessageUpdateTime"))[i],
                    R.drawable.like,R.drawable.collection);
//            Toast.makeText(HomeActivity.this, Objects.requireNonNull(map[0].get("allMessageId"))[i],Toast.LENGTH_SHORT).show();
            Log.d("treeholeMEssage", Objects.requireNonNull(map[0].get("allMessageContent"))[i]);
            treeHoleMessageAdapter.addMessage(treeHoleMessage1);
        }
//        LayoutAnimationController controller = new LayoutAnimationController(AnimationUtils.loadAnimation(context,R.anim.anim_treeholemessage));
//        recyclerView.setLayoutAnimation(controller);
        recyclerView.setAdapter(treeHoleMessageAdapter);
        return view;
    }

//    private void initMessage(){
////        TreeHoleMessage treeHoleMessage1 = new TreeHoleMessage("#00001","This is a test message","Administrator","2021/10/24");
////        messageslist.add(treeHoleMessage1);
//    }


}
