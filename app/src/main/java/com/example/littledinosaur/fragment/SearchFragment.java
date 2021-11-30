package com.example.littledinosaur.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.littledinosaur.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchFragment extends Fragment {

    private Context context;
    private TextView calendartext1;
    private TextView calendartext2;
    private TextView calendartext3;
    private TextView mottotext;
    private  TextView mottoauther;

    public SearchFragment(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchfragment,container,false);
        calendartext1 = view.findViewById(R.id.calendartext1);
        calendartext2 = view.findViewById(R.id.calendartext2);
        calendartext3 = view.findViewById(R.id.calendartext3);
        mottoauther = view.findViewById(R.id.mottoauther);
        mottotext = view.findViewById(R.id.mottotext);

        String mottotextString = null;
        String mottoautherString = null;
        final String[] s = new String[1];
        Thread thread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://101.201.50.108:3535/GetMotto").build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (response != null){
                        s[0] = Objects.requireNonNull(Objects.requireNonNull(response).body()).string();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = null;
        try {
             jsonObject = new JSONObject(s[0]);
            mottotextString = jsonObject.getString("text");
            mottoautherString = jsonObject.getString("author");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mottoauther.setText("--"+mottoautherString);
        mottotext.setText(mottotextString);

        Calendar calendar = Calendar.getInstance();
        calendartext1.setText(calendar.get(Calendar.YEAR)+"年"+(calendar.get(Calendar.MONTH) + 1)+"月"+" ");
        calendartext2.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        String[] string3 = {"日","一","二","三","四","五","六"};
        String[] string4 = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
        calendartext3.setText("星期"+string3[calendar.get(Calendar.DAY_OF_WEEK)]+" | "+string4[calendar.get(Calendar.DAY_OF_WEEK)]);



        return view;
    }

}
