package com.example.littledinosaur.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.littledinosaur.HttpRequest;
import com.example.littledinosaur.ListLikesAndCollects;
import com.example.littledinosaur.R;

import java.util.List;

public class ToDoNoteAdpter extends RecyclerView.Adapter<ToDoNoteAdpter.ViewHolder>{

    private List<String> list;
    private Context context;
    private String Username;

    public ToDoNoteAdpter(List<String> list,Context context,String Username) {
        this.list = list;
        this.Username = Username;
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        LinearLayout linearLayout;

        public ViewHolder(View view){
            super(view);
            textView = view.findViewById(R.id.todonotetext);
            linearLayout = view.findViewById(R.id.note);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.todonote,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.textView.setText(list.get(position));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                删除note
                Thread thread = new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
//                        Log.d("note",list.get(position - 1));
//                        Log.d("note", String.valueOf(position));
//                        Log.d("note",list.get(position));
                        String s = HttpRequest.AddOrDeleteNote(Username,"0",list.get(position));
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                deleteNote(list.get(position));
//                ListLikesAndCollects.removenotes(list.get(position));
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addNote(String s){
        list.add(s);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void deleteNote(String s){
        list.remove(s);
        notifyDataSetChanged();
    }

}
