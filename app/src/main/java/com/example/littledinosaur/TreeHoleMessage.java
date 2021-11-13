package com.example.littledinosaur;

import java.io.Serializable;

public class TreeHoleMessage implements Serializable {
    private String MessageId;
    private String MessageContent;
    private String MessageSenderName;
    private String MessageSendTime;
    private String MessageLikes;
    private String MessageComments;
    private String MessageUpdateTime;
    private String MessageCollections;
    private int ImgId;
    private int collectimgid;

    public TreeHoleMessage(String MessageId,String MessageContent,String MessageSenderName,String MessageSendTime,
                           String MessageLikes,String MessageComments,String MessageCollections,String MessageUpdateTime,
                           int imgId, int collectimgid){
        this.MessageContent = MessageContent;
        this.MessageId = MessageId;
        this.MessageSenderName = MessageSenderName;
        this.MessageSendTime = MessageSendTime;
        this.MessageLikes = MessageLikes;
        this.MessageComments = MessageComments;
        this.MessageCollections = MessageCollections;
        this.MessageUpdateTime = MessageUpdateTime;
        this.ImgId = imgId;
        this.collectimgid = collectimgid;
    }

    public String  getMessageId() {
        return MessageId;
    }

    public String getMessageContent() {
        return MessageContent;
    }

    public String getMessageSenderName() {
        return MessageSenderName;
    }

    public String getMessageSendTime() {
        return MessageSendTime;
    }

    public String getMessageComments() {
        return MessageComments;
    }

    public String getMessageLikes() {
        return MessageLikes;
    }

    public String getMessageUpdateTime() {
        return MessageUpdateTime;
    }

    public int getImgId() {
        return ImgId;
    }

    public void setImgId(int imgId) {
        ImgId = imgId;
    }

    public int getCollectimgid() {
        return collectimgid;
    }

    public void setCollectimgid(int collectimgid) {
        this.collectimgid = collectimgid;
    }

    public String getMessageCollections() {
        return MessageCollections;
    }

    public void setMessageCollections(String messageCollections) {
        MessageCollections = messageCollections;
    }

    public void setMessageContent(String messageContent) {
        MessageContent = messageContent;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    public void setMessageSenderName(String messageSenderName) {
        MessageSenderName = messageSenderName;
    }

    public void setMessageSendTime(String messageSendTime) {
        MessageSendTime = messageSendTime;
    }

    public void setMessageComments(String messageComments) {
        MessageComments = messageComments;
    }

    public void setMessageLikes(String messageLikes) {
        MessageLikes = messageLikes;
    }

    public void setMessageUpdateTime(String messageUpdataTime) {
        MessageUpdateTime = messageUpdataTime;
    }
}
