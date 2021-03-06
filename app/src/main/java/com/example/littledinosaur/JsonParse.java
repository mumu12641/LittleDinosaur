package com.example.littledinosaur;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.littledinosaur.activity.MessageActivity;
import com.example.littledinosaur.adapter.CommentMessage;
import com.example.littledinosaur.adapter.TreeHoleMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonParse {
    private static String JsonText;
    final int[] iconid = {R.drawable.icon0,R.drawable.icon1,R.drawable.icon2,R.drawable.icon3,R.drawable.icon4,
            R.drawable.icon5,R.drawable.icon6,R.drawable.icon7,R.drawable.icon8,R.drawable.icon9,
            R.drawable.icon10,R.drawable.icon11};

    public JsonParse(String data){
        JsonText = data;
    }

    public Map<String, String[]> jsonParse() throws JSONException, NullPointerException {
/*
        json解析
        JSONObject：Json对象，可以完成Json字符串与Java对象的相互转换
        JSONArray：Json数组，可以完成Json字符串与Java集合或对象的相互转换
*/
        JSONArray jsonArray = new JSONArray(JsonText);
        Map<String, String[]> map = new HashMap<>();
        List<String> listId = new ArrayList<>();
        List<String> listPassword = new ArrayList<>();
        List<String> listName = new ArrayList<>();
        List<String> listextra = new ArrayList<>();
        for (int i=0;i < jsonArray.length();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String email = jsonObject.getString("email");
            String password = jsonObject.getString("password");
            String name = jsonObject.getString("name");
            String extra = jsonObject.getString("extra");
            listId.add(email);
            listPassword.add(password);
            listName.add(name);
            listextra.add(extra);
            Log.d("json",email);
            Log.d("json", password);
        }
        map.put("allEmail",listId.toArray(new String[jsonArray.length()]));
        map.put("allPassword",  listPassword.toArray(new String[jsonArray.length()]));
        map.put("allName",  listName.toArray(new String[jsonArray.length()]));
        map.put("allExtra",  listextra.toArray(new String[jsonArray.length()]));
        return map;
    }

    public Map<String, String[]>jsonParseTreeHoleMessage() throws JSONException, NullPointerException{
        JSONArray jsonArray = new JSONArray(JsonText);
        Map<String, String[]> map = new HashMap<>();
        List<String> listMessageId = new ArrayList<>();
        List<String> listMessageSenderName = new ArrayList<>();
        List<String> listMessageContent = new ArrayList<>();
        List<String> listMessageSendTime = new ArrayList<>();
        List<String> listMessageLikes = new ArrayList<>();
        List<String> listMessageComments = new ArrayList<>();
        List<String> listMessageCollections = new ArrayList<>();
        List<String> listMessageUpdateTime = new ArrayList<>();
        for (int i=0;i < jsonArray.length();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String messageId = jsonObject.getString("messageid");
            String messageSenderName= jsonObject.getString("messagesendername");
            String messageContent = jsonObject.getString("messagecontent");
            String messageSendTime = jsonObject.getString("messagesendtime");
            String messageLikes = jsonObject.getString("messagelikes");
            String messageComments = jsonObject.getString("messagecomments");
            String messageCollections = jsonObject.getString("messagecollections");
            String messageUpdateTime = jsonObject.getString("messageupdatetime");
            listMessageId.add(messageId);
            listMessageSenderName.add(messageSenderName);
            listMessageContent.add(messageContent);
            listMessageSendTime.add(messageSendTime);
            listMessageLikes.add(messageLikes);
            listMessageComments.add(messageComments);
            listMessageCollections.add(messageCollections);
            listMessageUpdateTime.add(messageUpdateTime);
        }
        map.put("allMessageId",listMessageId.toArray(new String[jsonArray.length()]));
        map.put("allMessageSenderName",listMessageSenderName.toArray(new String[jsonArray.length()]));
        map.put("allMessageContent",listMessageContent.toArray(new String[jsonArray.length()]));
        map.put("allMessageSendTime",listMessageSendTime.toArray(new String[jsonArray.length()]));
        map.put("allMessageLikes",listMessageLikes.toArray(new String[jsonArray.length()]));
        map.put("allMessageComments",listMessageComments.toArray(new String[jsonArray.length()]));
        map.put("allMessageCollections",listMessageCollections.toArray(new String[jsonArray.length()]));
        map.put("allMessageUpdateTime",listMessageUpdateTime.toArray(new String[jsonArray.length()]));
        return map;
    }

    public TreeHoleMessage jsonParseTreeHoleMessageContent() throws JSONException {
        JSONObject jsonObject = new JSONObject(JsonText);
        TreeHoleMessage treeHoleMessage = new TreeHoleMessage(jsonObject.getString("messageid"),
                jsonObject.getString("messagecontent"),jsonObject.getString("messagesendername"),
                jsonObject.getString("messagesendtime"),jsonObject.getString("messagelikes"),
                jsonObject.getString("messagecomments"),jsonObject.getString("messagecollections"),
                jsonObject.getString("messageupdatetime"),R.drawable.like,R.drawable.collection);
        return treeHoleMessage;
    }

    public List<CommentMessage> jsonParseMessageComment(String messageid, Context context) throws JSONException {
        JSONObject jsonObject = new JSONObject(JsonText);
        List<CommentMessage> list = new ArrayList<>();
        String array1 = jsonObject.getString("commentsmessageuser");
        String array2 = jsonObject.getString("commentscontent");
        String array3 = jsonObject.getString("commentstime");
        String array4 = jsonObject.getString("commentsmessageusericon");
        JSONArray jsonArray1 = new JSONArray(array1);
        JSONArray jsonArray2 = new JSONArray(array2);
        JSONArray jsonArray3 = new JSONArray(array3);
        JSONArray jsonArray4 = new JSONArray(array4);
        for (int i=0;i<jsonArray1.length();i++){
            JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
            JSONObject jsonObject2 = jsonArray2.getJSONObject(i);
            JSONObject jsonObject3 = jsonArray3.getJSONObject(i);
            JSONObject jsonObject4 = jsonArray4.getJSONObject(i);
            String name = jsonObject1.getString(String.valueOf(i+1));
            String content = jsonObject2.getString(String.valueOf(i+1));
            String time = jsonObject3.getString(String.valueOf(i+1));
            int iconId = iconid[Integer.parseInt((String) jsonObject4.get(String.valueOf(i+1)))];
//            int iconId = R.drawable.icon1;
//            UserDataBase myDatabase = new UserDataBase(context,"User.db",null,2);
//            SQLiteDatabase sqdb = myDatabase.getReadableDatabase();
//            Cursor cursor = sqdb.query("User", null, "UserName=?", new String[]{name}, null, null, null);
//            if (cursor != null) {
//                while (cursor.moveToNext()) {
//                    if (cursor.getString(cursor.getColumnIndex("UserName")).equals(name)){
//                        iconId = iconid[Integer.parseInt(cursor.getString(cursor.getColumnIndex("Extra")))];
//                    }
//                }
//            }
            
            
            CommentMessage commentMessage = new CommentMessage(messageid,content,name,time,iconId);
            list.add(commentMessage);
        }
        return list;
    }
}
