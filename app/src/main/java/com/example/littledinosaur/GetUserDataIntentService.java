package com.example.littledinosaur;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class GetUserDataIntentService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.example.littledinosaur.action.FOO";
    private static final String ACTION_BAZ = "com.example.littledinosaur.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.example.littledinosaur.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.littledinosaur.extra.PARAM2";

    public GetUserDataIntentService() {
        super("GetUserDataIntentService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, GetUserDataIntentService.class);
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
        Intent intent = new Intent(context, GetUserDataIntentService.class);
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

        String string = HttpRequest.RequestHandler();
        Map<String, String[]> dic = new HashMap<>();
        JsonParse jsonParseHandler = new JsonParse(string);
        try {
//            解析获得的json文本
            Log.d("Service","连接服务器服务，将网络用户数据写入本机数据库");
            dic = jsonParseHandler.jsonParse();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        UserDataBase myDatabase = new UserDataBase(this, "User.db", null, 1);
        SQLiteDatabase sqdb = myDatabase.getWritableDatabase();
        sqdb.execSQL("delete from User");
        String[] ArrayEmail = dic.get("allEmail");
        String[] ArrayPassword = dic.get("allPassword");
        String[] ArrayName = dic.get("allName");
        String[] ArrayExtra = dic.get("allExtra");
        ContentValues contentValues = new ContentValues();
        try{
            for (int i = 0; i< Objects.requireNonNull(ArrayEmail).length; i++){
                contentValues.put("UserEmail",ArrayEmail[i]);
                contentValues.put("UserPassword", Objects.requireNonNull(ArrayPassword)[i]);
                contentValues.put("UserName", Objects.requireNonNull(ArrayName)[i]);
                contentValues.put("Extra", Objects.requireNonNull(ArrayExtra)[i]);
                sqdb.insert("User",null,contentValues);
                contentValues.clear();
            }
        } catch (NullPointerException e){
            e.printStackTrace();
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
