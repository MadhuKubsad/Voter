package com.example.promena.voter.Api_Modules.Modules.Login_Module.Chat_Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetChatMessage {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ChatMessageData data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChatMessageData getData() {
        return data;
    }

    public void setData(ChatMessageData data) {
        this.data = data;
    }

}
