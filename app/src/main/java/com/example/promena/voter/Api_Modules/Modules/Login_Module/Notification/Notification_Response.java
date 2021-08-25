package com.example.promena.voter.Api_Modules.Modules.Login_Module.Notification;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Notification_Response {

    @SerializedName("Data")
    public List<Notification_ResponseBody> Data;

    @SerializedName("Code")
    public String Code;

    @SerializedName("Message")
    public String Message;
}
