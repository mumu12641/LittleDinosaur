package com.example.littledinosaur.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.littledinosaur.R;

import org.w3c.dom.Text;

public class AboutMessageAdapter extends BaseExpandableListAdapter {

    private String[] GroupStrings = {"\uD83C\uDF08更新日志","\uD83D\uDC40产品介绍","\uD83D\uDC42团队介绍","✉️联系我们"};
    private String[][] content;
    private Context context;

    public AboutMessageAdapter(String[][] content,Context context) {
        this.content = content;
        this.context = context;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.aboutmessage,parent,false);
        TextView grouptext = view.findViewById(R.id.grouptext);
        grouptext.setText(GroupStrings[groupPosition]);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view;
        if (groupPosition == 3){
            view = LayoutInflater.from(context).inflate(R.layout.aboutcontactus,parent,false);
        }else {
            view = LayoutInflater.from(context).inflate(R.layout.aboutmessagecontent, parent, false);
        }
        TextView textView = view.findViewById(R.id.grouptextcontent);
        textView.setText(content[groupPosition][childPosition]);
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public int getGroupCount() {
        return GroupStrings.length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return GroupStrings[groupPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return content[groupPosition].length;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return content[groupPosition][childPosition];
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }
}
