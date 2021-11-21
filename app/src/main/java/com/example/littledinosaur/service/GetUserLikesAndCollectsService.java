package com.example.littledinosaur.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.littledinosaur.HttpRequest;
import com.example.littledinosaur.ListLikesAndCollects;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GetUserLikesAndCollectsService extends IntentService {
    private String likesList;
    private String collectList;



    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.littledinosaur.service.action.FOO";
    private static final String ACTION_BAZ = "com.example.littledinosaur.service.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.littledinosaur.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.littledinosaur.service.extra.PARAM2";
    private String UserName;
    public GetUserLikesAndCollectsService() {
        super("GetUserLikesAndCollectsService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, GetUserLikesAndCollectsService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, GetUserLikesAndCollectsService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }
        }

        Bundle bundle = null;
        if (intent != null) {
            bundle = intent.getExtras();
            if (bundle != null) {
                UserName = bundle.getString("Username");
            }
        }
        final String[] s = new String[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                 s[0] = HttpRequest.GetUserLikesAndCollects(UserName);
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        Log.d("pengbin",s[0]);
//        {"likes": [], "collects": ["#00001"]}
        try {
            JSONObject jsonObject = new JSONObject(s[0]);
             likesList = jsonObject.getString("likes");
             collectList =  jsonObject.getString("collects");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(likesList.length()>3) {
            for (int i = 2; i < likesList.length(); i += 9) {
                String s2 = likesList.substring(i, i + 6);
                ListLikesAndCollects.addLike(s2);
//                Log.d("pengbin", s2);
            }
        }
        if(collectList.length()>3) {
            for (int i = 2; i < collectList.length(); i += 9) {
                String s2 = collectList.substring(i, i + 6);
                ListLikesAndCollects.addCollect(s2);
//                Log.d("pengbin", s2);
            }
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
