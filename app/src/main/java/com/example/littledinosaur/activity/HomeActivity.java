package com.example.littledinosaur.activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.example.littledinosaur.ActivityCollector;
import com.example.littledinosaur.fragment.HomeFragment;
import com.example.littledinosaur.fragment.MyFragment;
import com.example.littledinosaur.R;
import com.example.littledinosaur.fragment.SearchFragment;
import com.example.littledinosaur.TitleBar;
import com.example.littledinosaur.adapter.TreeHoleMessage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener {

    private FragmentManager fragmentManager;
    private ImageView home;
    private ImageView search;
    private ImageView my;
    private TextView hometext;
    private TextView searchtext;
    private TextView mytext;
    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private MyFragment myFragment;
    private TitleBar titleBar;
    private String UserName;
    private FloatingActionButton floatingActionButton;
    private FrameLayout frameLayout;
    private List<TreeHoleMessage> messageslist = new ArrayList<>();
    private SwipeRefreshLayout refreshLayout;
    private FragmentTransaction transaction;
    private Scroller scroller;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ActivityCollector.addAcitivity(this);

        scroller = new Scroller(HomeActivity.this);

        fragmentManager = getSupportFragmentManager();
        home = findViewById(R.id.home);
        search = findViewById(R.id.search);
        floatingActionButton = findViewById(R.id.floatingbutton1);
        my = findViewById(R.id.my);
        hometext  = findViewById(R.id.hometext);
        searchtext = findViewById(R.id.searchtext);
        mytext = findViewById(R.id.mytext);
        titleBar = findViewById(R.id.titlebar);
        frameLayout = findViewById(R.id.content);


        home.setOnClickListener(this);
        search.setOnClickListener(this);
        my.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);

        titleBar = new TitleBar(HomeActivity.this,null);
        refreshLayout = findViewById(R.id.Refresh);
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        refreshLayout.setRefreshing(false);
//                    }
//                }, 2000);
//            }
//        });


        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        assert b != null;
        UserName = b.getString("Username");

        home.performClick();//模拟点击
        refreshLayout.setOnRefreshListener(this);



    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {

        /* 逻辑：每次点击的时候先将所有的碎片设置为未选择
        再将点击的碎片设置为选择状态（便于点击的图片变化） 将所有的碎片变为不可见
         * 将点击的碎片显示出来 最后commit */

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideAllFragment(transaction);
        switch (v.getId()){
            case R.id.home:
                hideAllFragment(transaction);
                setSelected();
                home.setSelected(true);
                titleBar.setVisibility(View.VISIBLE);
                refreshLayout.setVisibility(View.VISIBLE);
                floatingActionButton.setVisibility(View.VISIBLE);
                titleBar.setText("首页");
                if (home.isSelected()){
                    home.setImageResource(R.drawable.homeclicked1);
//                    hometext.setTextColor(Color.rgb(6,194,95));
                }
                if (homeFragment == null){
//                    这里其实可以实现将所有的碎片都初始化好 并且添加进去
//                    Log.d("Username",UserName);
                    homeFragment = new HomeFragment(HomeActivity.this,UserName);
                    transaction.add(R.id.content,homeFragment);
                }else{
                    transaction.show(homeFragment);
                }
                break;
            case R.id.search:
                setSelected();
                search.setSelected(true);
                titleBar.setVisibility(View.VISIBLE);
               // refreshLayout.setVisibility(INVISIBLE);
                titleBar.setText("发现");
                floatingActionButton.setVisibility(INVISIBLE);
                if (search.isSelected()){
                    search.setImageResource(R.drawable.searchclicked1);
//                    searchtext.setTextColor(Color.rgb(6,194,95));
                }
                hideAllFragment(transaction);
                if (searchFragment == null){
                    searchFragment = new SearchFragment(HomeActivity.this);
                    transaction.add(R.id.content,searchFragment);
                }else{
                    transaction.show(searchFragment);
                }
                break;
            case R.id.my:
                setSelected();
                my.setSelected(true);
                titleBar.setText("我的");
                floatingActionButton.setVisibility(INVISIBLE);
              //  refreshLayout.setVisibility(INVISIBLE);
                if (my.isSelected()){
                    my.setImageResource(R.drawable.myclicked1);
//                    mytext.setTextColor(Color.rgb(6,194,95));
                }
                hideAllFragment(transaction);
                if (myFragment == null){
                    myFragment = new MyFragment(HomeActivity.this,UserName);
                    transaction.add(R.id.content,myFragment);
                }else{
                    transaction.show(myFragment);
                }
                break;
            case R.id.floatingbutton1:
                if (floatingActionButton.getVisibility()==View.VISIBLE){
//                    如果悬浮按钮可见 点击有效
                    Intent intent = new Intent(HomeActivity.this, WriteTreeHoleMessageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("Username",UserName);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,1);
//                    startActivity(intent);
//                    HomeActivity.this.finish();
                }
        }
        transaction.commitAllowingStateLoss();
    }

    private void hideAllFragment(FragmentTransaction transaction){
        if (searchFragment != null){
            transaction.hide(searchFragment);
        }if (homeFragment != null){
            transaction.hide(homeFragment);
        }if (myFragment != null){
            transaction.hide(myFragment);
        }
    }

    private void setSelected(){
        home.setSelected(false);
        search.setSelected(false);
        my.setSelected(false);
        home.setImageResource(R.drawable.home);
        search.setImageResource(R.drawable.search);
        my.setImageResource(R.drawable.my);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==1){
            assert data != null;
            String result = data.getStringExtra("result");
            Toast.makeText(HomeActivity.this,"发布成功",Toast.LENGTH_SHORT).show();
            homeFragment = null;
            home.performClick();//模拟点击
        }else if(resultCode==2){
            homeFragment = null;
            home.performClick();//模拟点击
            Toast.makeText(HomeActivity.this,"未发布",Toast.LENGTH_SHORT).show();
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    private void RefreshTreeHoleMessage() throws JSONException, InterruptedException {
//        final Map<String, String[]>[] map = new Map[]{new HashMap<>()};
//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                JsonParse jsonParse = new JsonParse(HttpRequest.GetTreeHoleMessage());
//                try {
//                    map[0] = jsonParse.jsonParseTreeHoleMessage();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        thread.start();
//        thread.join();
//
////        RecyclerView recyclerView = frameLayout.findViewById(R.id.recyclerview);
////        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this);
////        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
////        recyclerView.setLayoutManager(linearLayoutManager);
////        for(int i = 0; i< Objects.requireNonNull(map[0].get("allMessageId")).length; i++){
////            TreeHoleMessage treeHoleMessage1 = new TreeHoleMessage(
////                    Objects.requireNonNull(map[0].get("allMessageId"))[i],
////                    Objects.requireNonNull(map[0].get("allMessageContent"))[i],
////                    Objects.requireNonNull(map[0].get("allMessageSenderName"))[i],
////                    Objects.requireNonNull(map[0].get("allMessageSendTime"))[i]);
////
////            Toast.makeText(HomeActivity.this, Objects.requireNonNull(map[0].get("allMessageId"))[i],Toast.LENGTH_SHORT).show();
////
////            messageslist.add(treeHoleMessage1);
////        }
////        TreeHoleMessageAdapter treeHoleMessageAdapter = new TreeHoleMessageAdapter(messageslist);
////        recyclerView.setAdapter(treeHoleMessageAdapter);
//
//            RecyclerView recyclerView = frameLayout.findViewById(R.id.recyclerview);
//
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this);
////          横向分布
////        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//            recyclerView.setLayoutManager(linearLayoutManager);
//            TreeHoleMessageAdapter treeHoleMessageAdapter = new TreeHoleMessageAdapter(messageslist,UserName);
//            for(int i = 0; i< Objects.requireNonNull(map[0].get("allMessageId")).length; i++){
//            TreeHoleMessage treeHoleMessage1 = new TreeHoleMessage(
//                    Objects.requireNonNull(map[0].get("allMessageId"))[i],
//                    Objects.requireNonNull(map[0].get("allMessageContent"))[i],
//                    Objects.requireNonNull(map[0].get("allMessageSenderName"))[i],
//                    Objects.requireNonNull(map[0].get("allMessageSendTime"))[i],
//                    Objects.requireNonNull(map[0].get("allMessageLikes"))[i],
//                    Objects.requireNonNull(map[0].get("allMessageComments"))[i],
//                    Objects.requireNonNull(map[0].get("allMessageCollections"))[i],
//                    Objects.requireNonNull(map[0].get("allMessageUpdateTime"))[i]);
////            Toast.makeText(HomeActivity.this, Objects.requireNonNull(map[0].get("allMessageId"))[i],Toast.LENGTH_SHORT).show();
//                Log.d("treeholeMEssage", Objects.requireNonNull(map[0].get("allMessageContent"))[i]);
//                treeHoleMessageAdapter.addMessage(treeHoleMessage1);
//            }
//            recyclerView.setAdapter(treeHoleMessageAdapter);
//    }

//    public void setHomeFragment(){
//        homeFragment = null;
//        home.performClick();
//    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
//                FragmentTransaction transaction = fragmentManager.beginTransaction();
//                transaction.remove(homeFragment);
//                homeFragment = null;
//                transaction.commitAllowingStateLoss();
//                home.performClick();
            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                refreshLayout.setRefreshing(false);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.remove(homeFragment);
                homeFragment = null;
                transaction.commitAllowingStateLoss();
                home.performClick();
            }
        }, 10);
    }

}
