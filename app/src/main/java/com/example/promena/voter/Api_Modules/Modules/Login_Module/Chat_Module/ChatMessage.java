package com.example.promena.voter.Api_Modules.Modules.Login_Module.Chat_Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatMessage {

    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("SenderUserId")
    @Expose
    private Integer senderUserId;
    @SerializedName("ReceiverUserId")
    @Expose
    private Integer receiverUserId;
    @SerializedName("SentDate")
    @Expose
    private String sentDate;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("SentStatus")
    @Expose
    private Boolean sentStatus;
    @SerializedName("SenderSide")
    @Expose
    private Boolean senderSide;

    public ChatMessage(Integer id, String msg, Integer receiverUserId, Integer senderUserId, String sentDate, Boolean senderSide) {
        this.senderUserId = senderUserId;
        this.receiverUserId = receiverUserId;
        this.sentDate = sentDate;
        this.message = msg;
        this.senderSide = senderSide;
        this.id=id;
    }

    public ChatMessage() {

    }

 /*   public ChatMessage(Integer setId, String setMessage, Integer setReceiverUserId, Integer setSenderUserId, Boolean setSenderSide, String setSentDate) {
        this.senderUserId = senderUserId;
        this.receiverUserId = receiverUserId;
        this.sentDate = sentDate;
        this.message = message;
        this.sentStatus = sentStatus;
        this.senderSide = senderSide;
        this.id=setId;
    }*/


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(Integer senderUserId) {
        this.senderUserId = senderUserId;
    }

    public Integer getReceiverUserId() {
        return receiverUserId;
    }

    public void setReceiverUserId(Integer receiverUserId) {
        this.receiverUserId = receiverUserId;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSentStatus() {
        return sentStatus;
    }

    public void setSentStatus(Boolean sentStatus) {
        this.sentStatus = sentStatus;
    }

    public Boolean getSenderSide() {
        return senderSide;
    }

    public void setSenderSide(Boolean senderSide) {
        this.senderSide = senderSide;
    }
}
