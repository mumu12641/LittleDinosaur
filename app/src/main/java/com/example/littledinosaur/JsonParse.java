package com.example.littledinosaur;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonParse {
    private static String JsonText;

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

    public List<CommentMessage> jsonParseMessageComment(String messageid) throws JSONException {
//        private String MessageId;
//        private String CommentContent;
//        private String CommentSenderName;
//        private String CommentSendTime;
        JSONObject jsonObject = new JSONObject(JsonText);
        List<CommentMessage> list = new ArrayList<>();
//        {"likesmessageuser": [{"1": "Infinity Studio Administrator"}],
//        "commentsmessageuser": [{"1": "Infinity Studio Administrator"}],
//        "commentscontent": [{"1": "\u4f60\u597d"}],
//        "commentstime": [{"1": "2021/11/13 13:57"}],
//        "collectmessageuser": [{"1": "Infinity Studio Administrator"}]}
        String array1 = jsonObject.getString("commentsmessageuser");
        String array2 = jsonObject.getString("commentscontent");
        String array3 = jsonObject.getString("commentstime");
        JSONArray jsonArray1 = new JSONArray(array1);
        JSONArray jsonArray2 = new JSONArray(array2);
        JSONArray jsonArray3 = new JSONArray(array3);
        for (int i=0;i<jsonArray1.length();i++){
            JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
            JSONObject jsonObject2 = jsonArray2.getJSONObject(i);
            JSONObject jsonObject3 = jsonArray3.getJSONObject(i);
            String name = jsonObject1.getString(String.valueOf(i+1));
            String content = jsonObject2.getString(String.valueOf(i+1));
            String time = jsonObject3.getString(String.valueOf(i+1));
            CommentMessage commentMessage = new CommentMessage(messageid,content,name,time);
            list.add(commentMessage);
        }
        return list;
    }
}
