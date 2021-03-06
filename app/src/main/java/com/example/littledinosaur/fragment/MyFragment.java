package com.example.littledinosaur.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.LinkAddress;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.littledinosaur.R;
import com.example.littledinosaur.UserDataBase;
import com.example.littledinosaur.activity.AboutActivity;
import com.example.littledinosaur.activity.LoginActivity;
import com.example.littledinosaur.activity.MyMessageAcitivity;
import com.example.littledinosaur.activity.MySettingActivity;

import static android.content.Context.MODE_PRIVATE;

public class MyFragment extends Fragment implements View.OnClickListener{
    private String Username;
    private Context context;
    private Activity activity;
    private LinearLayout line3;
    private LinearLayout line6;
    private LinearLayout line8;
    private LinearLayout line9;
    private LinearLayout line5;
    private ImageView userimage;
    final int[] iconid = {R.drawable.icon0,R.drawable.icon1,R.drawable.icon2,R.drawable.icon3,R.drawable.icon4,
            R.drawable.icon5,R.drawable.icon6,R.drawable.icon7,R.drawable.icon8,R.drawable.icon9,
            R.drawable.icon10,R.drawable.icon11};

    public MyFragment(Context context,String Username,Activity activity){
        this.context = context;
        this.Username = Username;
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.myfragment,container,false);
        LinearLayout line1 = view.findViewById(R.id.line1);
        TextView name = view.findViewById(R.id.username);
        name.setText(Username);
        line3 = view.findViewById(R.id.line3);
        line6 = view.findViewById(R.id.line6);
        line8 = view.findViewById(R.id.line8);
        line9 = view.findViewById(R.id.line9);
        line5 = view.findViewById(R.id.line5);
        userimage = view.findViewById(R.id.userimage);

        UserDataBase myDatabase = new UserDataBase(context,"User.db",null,2);
        SQLiteDatabase sqdb = myDatabase.getReadableDatabase();
        Cursor cursor = sqdb.query("User", null, "UserName=?", new String[]{Username}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (cursor.getString(cursor.getColumnIndex("UserName")).equals(Username)){
                    userimage.setImageResource(iconid[Integer.parseInt(cursor.getString(cursor.getColumnIndex("Extra")))]);
                }
            }
        }

        line3.setOnClickListener(this);
        line6.setOnClickListener(this);
        line8.setOnClickListener(this);
        line9.setOnClickListener(this);
        line5.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.line3:
                Intent intent = new Intent(context, MySettingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Username",Username);
                intent.putExtras(bundle);
                Activity finish = (Activity) context;
                finish.finish();
                startActivity(intent);
                break;
            case R.id.line6:
                Intent intent1 = new Intent(context, MyMessageAcitivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString("Username",Username);
                bundle1.putString("flag","????????????");
                intent1.putExtras(bundle1);
                startActivity(intent1);
                break;
            case R.id.line8:
                Intent intent2 = new Intent(context, MyMessageAcitivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putString("Username",Username);
                bundle2.putString("flag","????????????");
                intent2.putExtras(bundle2);
                startActivity(intent2);
                break;
            case R.id.line9:
                Intent intent3 = new Intent(context, MyMessageAcitivity.class);
                Bundle bundle3 = new Bundle();
                bundle3.putString("Username",Username);
                bundle3.putString("flag","????????????");
                intent3.putExtras(bundle3);
                startActivity(intent3);
                break;
            case R.id.line5:
//                Toast.makeText(context,"emm...???????????????????????????????????????",Toast.LENGTH_SHORT).show();
                Intent intent4 = new Intent(context, AboutActivity.class);
                startActivity(intent4);
                break;
        }
    }
}
