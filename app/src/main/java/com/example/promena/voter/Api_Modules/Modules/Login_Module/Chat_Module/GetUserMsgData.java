package com.example.promena.voter.Api_Modules.Modules.Login_Module.Chat_Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserMsgData {

    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("UserName")
    @Expose
    private String userName;
    @SerializedName("UserImage")
    @Expose
    private String userImage;
    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("SenderUserId")
    @Expose
    private Integer senderUserId;
    @SerializedName("ReceiverUserId")
    @Expose
    private Integer receiverUserId;
    @SerializedName("SentStatus")
    @Expose
    private Boolean sentStatus;
    @SerializedName("Count")
    @Expose
    private Integer count;
    @SerializedName("SentDate")
    @Expose
    private String sentDate;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

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

    public Boolean getSentStatus() {
        return sentStatus;
    }

    public void setSentStatus(Boolean sentStatus) {
        this.sentStatus = sentStatus;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getSentDate() {
        return sentDate;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public GetUserMsgData(String message, Integer userId, String userName, String userImage, Integer senderUserId, Integer receiverUserId, Boolean sentStatus, Integer count, String sentDate) {
        this.message = message;
        this.userId = userId;
        this.userName = userName;
        this.userImage = userImage;
        this.senderUserId = senderUserId;
        this.receiverUserId = receiverUserId;
        this.sentStatus = sentStatus;
        this.count = count;
        this.sentDate = sentDate;
    }
}
