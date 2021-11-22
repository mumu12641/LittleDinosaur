package com.example.littledinosaur.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.littledinosaur.HttpRequest;
import com.example.littledinosaur.service.PostTreeHoleMessageService;
import com.example.littledinosaur.R;
import com.example.littledinosaur.ReturnBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class WriteTreeHoleMessageActivity extends AppCompatActivity {

    private String Username;
    private FloatingActionButton floatingActionButton;
    private String messagecontent;
    private EditText editText;
    private Button returnbtn;
    private int resultcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_tree_hole_message);

        returnbtn = findViewById(R.id.img);
        floatingActionButton = findViewById(R.id.commit_btn);
        editText = findViewById(R.id.editmessagecontent);

        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        assert bundle != null;
        Username = (String) bundle.get("Username");
//        Toast.makeText(WriteTreeHoleMessageActivity.this,Username,Toast.LENGTH_SHORT).show();


        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultcode = 2;
                WriteTreeHoleMessageActivity.this.finish();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messagecontent = editText.getText().toString();
                if (!messagecontent.equals("")) {
//                      输入内容不为空
//                    发送内容到服务器

//                    获取当前时间
                    Calendar calendar = Calendar.getInstance();
                    long unixTime = calendar.getTimeInMillis();//这是时间戳
                    String nowTime = calendar.get(Calendar.YEAR) +
                            "/" + (calendar.get(Calendar.MONTH) + 1) +
                            "/" + calendar.get(Calendar.DAY_OF_MONTH) + " " +
                            calendar.get(Calendar.HOUR_OF_DAY) + ":" +
                            calendar.get(Calendar.MINUTE);
                    String sendtime = calendar.get(Calendar.YEAR) +
                            "/" + (calendar.get(Calendar.MONTH) + 1) +
                            "/" + calendar.get(Calendar.DAY_OF_MONTH);

                    final String[] messageid = new String[1];
                    Thread thread = new Thread() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void run() {
                            messageid[0] = HttpRequest.GetNowMessageId();
                        }
                    };
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("WriteTreeHoleMessage",nowTime);
                    Log.d("WriteTreeHoleMessage",messageid[0]);
                    Intent intent1 = new Intent(WriteTreeHoleMessageActivity.this, PostTreeHoleMessageService.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("messageId", messageid[0]);
                    bundle1.putString("messageSenderName", Username);
                    bundle1.putString("messageContent", messagecontent);
                    bundle1.putString("messageSendTime", sendtime);
                    bundle1.putString("messageLikes","0");
                    bundle1.putString("messageComments","0");
                    bundle1.putString("messageCollections","0");
                    bundle1.putString("messageUpdateTime",nowTime);
                    intent1.putExtras(bundle1);
                    startService(intent1);

                    String result = "ok";
                    Intent newIntent = new Intent(WriteTreeHoleMessageActivity.this, HomeActivity.class);
                    newIntent.putExtra("result", result);//这里可以是任意要传回给A的业务数据
                    resultcode = 1;
                    //Toast.makeText(WriteTreeHoleMessageActivity.this,"发布成功"+messagecontent,Toast.LENGTH_SHORT).show();
                    WriteTreeHoleMessageActivity.this.finish();


//                    Bundle b = new Bundle();
//                    b.putString("Username",Username);
//                    newIntent.putExtras(b);
//                    startActivity(newIntent);
//                    WriteTreeHoleMessageActivity.this.finish();

                }else{
                    Toast.makeText(WriteTreeHoleMessageActivity.this,"请先输入内容哦亲",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            直接退出未保存
            resultcode = 2;
//            Intent newIntent = new Intent(WriteTreeHoleMessageActivity.this, HomeActivity.class);
//            Bundle b = new Bundle();
//            b.putString("Username",Username);
//            newIntent.putExtras(b);
//            startActivity(newIntent);
            WriteTreeHoleMessageActivity.this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        Intent intent1 = new Intent(WriteTreeHoleMessageActivity.this,HomeActivity.class);
//            2表示未发布
        WriteTreeHoleMessageActivity.this.setResult(resultcode,intent1);//使用setResult方法，把resuleCode传给
//            startActivity(intent1);
        super.finish();
    }
}
