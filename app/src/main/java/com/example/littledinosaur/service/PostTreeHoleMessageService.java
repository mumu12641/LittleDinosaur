package com.example.littledinosaur.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.littledinosaur.HttpRequest;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class PostTreeHoleMessageService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.littledinosaur.action.FOO";
    private static final String ACTION_BAZ = "com.example.littledinosaur.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.littledinosaur.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.littledinosaur.extra.PARAM2";

    public PostTreeHoleMessageService() {
        super("PostTreeHoleMessageService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, PostTreeHoleMessageService.class);
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
        Intent intent = new Intent(context, PostTreeHoleMessageService.class);
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
        String messageId;
        String messageSendername;
        String messageContent;
        String messageSendTime;
        String messageLikes;
        String messageComments;
        String messageCollections;
        String messageUpdateTime;
        if (intent != null) {
            bundle = intent.getExtras();
            if (bundle != null) {
                messageId = bundle.getString("messageId");
                messageSendername = bundle.getString("messageSenderName");
                messageContent = bundle.getString("messageContent");
                messageSendTime = bundle.getString("messageSendTime");
                messageLikes = bundle.getString("messageLikes");
                messageComments = bundle.getString("messageComments");
                messageCollections = bundle.getString("messageCollections");
                messageUpdateTime = bundle.getString("messageUpdateTime");
                HttpRequest.PostTreeHoleMessage(messageId,messageSendername,messageContent,messageSendTime,messageLikes,messageComments,messageCollections,messageUpdateTime);
                Log.d("WriteTreeHoleMessage","service");
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
