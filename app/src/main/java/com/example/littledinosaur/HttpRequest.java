package com.example.littledinosaur;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpRequest {

//    http://101.201.50.108:3535/
    public static String IP = "101.201.50.108";
//    public static String IP =  "10.19.152.213";
    public static String Port = "3535";
//  public static String Port = "6363";

    //    获取用户信息json
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String RequestHandler(){
        OkHttpClient client = new OkHttpClient();
//                        Request request = new Request.Builder().url("http://10.19.159.208/test.json").build();
//        Request request = new Request.Builder().url("http://"+IP+":6363/GetUser").build();
        Request request = new Request.Builder().url("http://"+IP+":"+ Port +"/GetUser").build();
//        Request request = new Request.Builder().url("http://10.12.29.213:6363/GetUser").build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String string = null;
        try {
            if (response != null){
                string  = Objects.requireNonNull(Objects.requireNonNull(response).body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        返回json字符串
        return string;
    }


    //    提交注册信息
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void PostHandler(String UserEmail, String UserPassword, String UserName,String Extra){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("UserEmail",UserEmail)
                .add("UserPassword",UserPassword)
                .add("UserName", UserName)
                .add("Extra",Extra)
                .build();
//        Request request = new Request.Builder().url("http://"+IP+":6363/PostUser").post(requestBody).build();
        Request request = new Request.Builder().url("http://"+IP+":"+Port+"/PostUser").post(requestBody).build();
//        Request request = new Request.Builder().url("http://10.12.29.213:6363/PostUser").post(requestBody).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String string = "";
        try {
            string  = Objects.requireNonNull(Objects.requireNonNull(response).body()).string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("Server", Objects.requireNonNull(string));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String GetKeyCode(String UserEmail){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("UserEmail",UserEmail).build();
//        Request request = new Request.Builder().url("http://"+IP+":6363/GetKey").post(requestBody).build();
        Request request = new Request.Builder().url("http://"+IP+":"+Port+"/GetKey").post(requestBody).build();
//        Request request = new Request.Builder().url("http://10.12.29.213:6363/GetKey").post(requestBody).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String string = "";
        try {
            string  = Objects.requireNonNull(Objects.requireNonNull(response).body()).string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void changeName(String newname, String Useremail){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("UserEmail",Useremail).add("NewName",newname).build();
        Request request = new Request.Builder().url("http://"+IP+":"+Port+"/ChangeName").post(requestBody).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String string = "";
        try {
            string  = Objects.requireNonNull(Objects.requireNonNull(response).body()).string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("ChangeName",string);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void PostTreeHoleMessage(String messageId, String messageSenderName,
                                           String messageContent, String messageSendTime,
                                           String messageLikes, String messageComments,
                                           String messageCollections,String messageUpdateTime){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("MessageId",messageId)
                .add("MessageSenderName",messageSenderName)
                .add("MessageContent",messageContent)
                .add("MessageSendTime",messageSendTime)
                .add("MessageLikes",messageLikes)
                .add("MessageComments",messageComments)
                .add("MessageCollections",messageCollections)
                .add("MessageUpdateTime",messageUpdateTime)
                .build();
        Request request = new Request.Builder().url("http://"+IP+":"+Port+"/PostTreeHoleMessage").post(requestBody).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String string = "";
        try {
            string  = Objects.requireNonNull(Objects.requireNonNull(response).body()).string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("PostTreeHoleMessage",string);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String GetTreeHoleMessage(){
        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url("http://"+IP+":6363/GetTreeHoleMessage").build();
        Request request = new Request.Builder().url("http://"+IP+":"+Port+"/GetTreeHoleMessage").build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String string = null;
        try {
            if (response != null){
                string  = Objects.requireNonNull(Objects.requireNonNull(response).body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String GetNowMessageId(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://"+IP+":"+Port+"/GetNowMessageId").build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String string = null;
        try {
            if (response != null){
                string  = Objects.requireNonNull(Objects.requireNonNull(response).body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String PostLikesOrCollectionsMessage(String MessageId, String UserName,String flag){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("MessageId",MessageId).add("UserName",UserName)
                .add("Flag",flag).build();
//        Request request = new Request.Builder().url("http://"+IP+":6363/LikeOrCollectTreeHoleMessage").post(requestBody).build();
        Request request = new Request.Builder().url("http://"+IP+":"+Port+"/LikeOrCollectTreeHoleMessage").post(requestBody).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String string = null;
        try {
            if (response != null){
                string  = Objects.requireNonNull(Objects.requireNonNull(response).body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String IsLikeOrCollectTheMessage(String MessageId, String UserName,String flag){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("MessageId",MessageId)
                .add("UserName",UserName)
                .add("Flag",flag)
                .build();
//        Request request = new Request.Builder().url("http://"+IP+":6363/IsLikeOrCollectTheMessage").post(requestBody).build();
        Request request = new Request.Builder().url("http://"+IP+":"+Port+"/IsLikeOrCollectTheMessage").post(requestBody).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String string = null;
        try {
            if (response != null){
                string  = Objects.requireNonNull(Objects.requireNonNull(response).body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String GetTreeHoleMessageContent(String MessageId){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("MessageId",MessageId)
                .build();
//        Request request = new Request.Builder().url("http://"+IP+":6363/IsLikeOrCollectTheMessage").post(requestBody).build();
        Request request = new Request.Builder().url("http://"+IP+":"+Port+"/GetTreeHoleMessageContent").post(requestBody).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String string = null;
        try {
            if (response != null){
                string  = Objects.requireNonNull(Objects.requireNonNull(response).body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void PostMessageComment(String MessageId, String CommentSenderName,String CommentSendTime,String CommentCotent){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("MessageId",MessageId)
                .add("CommentSenderName",CommentSenderName)
                .add("CommentSendTime",CommentSendTime)
                .add("CommentContent",CommentCotent)
                .build();
        Request request = new Request.Builder().url("http://"+IP+":"+Port+"/PostMessageComment").post(requestBody).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String string = null;
        try {
            if (response != null){
                string  = Objects.requireNonNull(Objects.requireNonNull(response).body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String GetMessageComment(String MessageId){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("MessageId",MessageId)
                .build();
        Request request = new Request.Builder().url("http://"+IP+":"+Port+"/GetMessageComment").post(requestBody).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String string = null;
        try {
            if (response != null){
                string  = Objects.requireNonNull(Objects.requireNonNull(response).body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String GetUserLikesAndCollects(String UserName){
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("UserName",UserName)
                .build();
        Request request = new Request.Builder().url("http://"+IP+":"+Port+"/GetUserLikesAndCollects").post(requestBody).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String string = null;
        try {
            if (response != null){
                string  = Objects.requireNonNull(Objects.requireNonNull(response).body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String GetNowApkVersion(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://"+IP+":"+Port+"/GetApkVersion").build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String string = null;
        try {
            if (response != null){
                string  = Objects.requireNonNull(Objects.requireNonNull(response).body()).string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }
}
