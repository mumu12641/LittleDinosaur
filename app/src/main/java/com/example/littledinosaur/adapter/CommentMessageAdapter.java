package com.example.littledinosaur.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.littledinosaur.R;

import java.util.List;

public class CommentMessageAdapter extends RecyclerView.Adapter<CommentMessageAdapter.ViewHolder> {


    private List<CommentMessage> list;
    private Context context;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView commentasendername;
        TextView commentsendtime;
        TextView commentcontent;
        ImageView commentsendericon;
        public ViewHolder(View view){
            super(view);
            commentasendername = view.findViewById(R.id.commentsendername);
            commentcontent = view.findViewById(R.id.commentcontent);
            commentsendtime = view.findViewById(R.id.commentsendtime);
            commentsendericon = view.findViewById(R.id.commentsendericon);
        }
    }

    public CommentMessageAdapter(List<CommentMessage> list,Context context){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commentmessage,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentMessage commentMessage = list.get(position);
        holder.commentsendtime .setText(commentMessage.getCommentSendTime());
        holder.commentcontent .setText(commentMessage.getCommentContent());
        holder.commentasendername .setText(commentMessage.getCommentSenderName());
        holder.commentsendericon.setImageResource(commentMessage.getIconid());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
