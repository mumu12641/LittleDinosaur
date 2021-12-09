package com.example.littledinosaur.adapter;

public class CommentMessage {
    private String MessageId;
    private String CommentContent;
    private String CommentSenderName;
    private String CommentSendTime;
    private int iconid;

    public CommentMessage(String MessageId, String CommentContent,String CommentSenderName,String CommentSendTime, int iconid){
        this.CommentContent = CommentContent;
        this.CommentSenderName = CommentSenderName;
        this.MessageId = MessageId;
        this.CommentSendTime = CommentSendTime;
        this.iconid = iconid;
    }

    public int getIconid() {
        return iconid;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    public void setCommentContent(String commentContent) {
        CommentContent = commentContent;
    }

    public void setCommentenderName(String commentenderName) {
        CommentSendTime = commentenderName;
    }

    public void setCommentSendTime(String commentSendTime) {
        CommentSendTime = commentSendTime;
    }

    public String getCommentSendTime() {
        return CommentSendTime;
    }

    public String getCommentContent() {
        return CommentContent;
    }

    public String getCommentSenderName() {
        return CommentSenderName;
    }

    public String getMessageId() {
        return MessageId;
    }

}
