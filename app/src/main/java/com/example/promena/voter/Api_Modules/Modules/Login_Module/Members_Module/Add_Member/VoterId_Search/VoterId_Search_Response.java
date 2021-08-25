package com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Member.VoterId_Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VoterId_Search_Response {

    @SerializedName("Data")
    @Expose
    private VoterId_Search_Response_Data data;
    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;

    public VoterId_Search_Response_Data getData() {
        return data;
    }

    public void setData(VoterId_Search_Response_Data data) {
        this.data = data;
    }

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
}
