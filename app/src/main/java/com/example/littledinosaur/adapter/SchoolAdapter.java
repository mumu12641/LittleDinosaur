package com.example.littledinosaur.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.littledinosaur.R;
import com.example.littledinosaur.activity.CertainSchoolActivity;
import com.example.littledinosaur.activity.SchoolActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.ViewHolder>{

    private List<String> array;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView schoolName;
        public ViewHolder(View itemView,Context context) {
            super(itemView);
            schoolName = itemView.findViewById(R.id.school_name);
//            imageView = itemView.findViewById(R.id.school_image);
            schoolName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CertainSchoolActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    public SchoolAdapter(List<String> array,Context context){
        this.array = array;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_adapter,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,this.context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolAdapter.ViewHolder holder, int position) {
//        holder.imageView.setImageResource(array[position]);
        holder.schoolName.setText(array.get(position));
//        holder.imageView.setImageBitmap(getURLimage("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.zhimg.com%2Fv2-a41b31193908812d578bc9ca869ff646_1440w.jpg%3Fsource%3D172ae18b&refer=http%3A%2F%2Fpic1.zhimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1642939593&t=b78616a1036afd31d2501c9149ee1c7f"));
    }

    @Override
    public int getItemCount() {
        return array.size();
    }
    @SuppressLint("NotifyDataSetChanged")
    public void addSchool(String s){
        array.add(s);
        notifyDataSetChanged();
    }
}
