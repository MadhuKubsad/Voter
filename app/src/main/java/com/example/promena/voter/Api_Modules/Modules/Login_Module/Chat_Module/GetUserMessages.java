package com.example.promena.voter.Api_Modules.Modules.Login_Module.Chat_Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUserMessages {

    @SerializedName("code")
    @Expose
    public Integer code;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("data")
    @Expose
    public List<GetUserMsgData> data = null;

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

    public List<GetUserMsgData> getData() {
        return data;
    }

    public void setData(List<GetUserMsgData> data) {
        this.data = data;
    }

}
