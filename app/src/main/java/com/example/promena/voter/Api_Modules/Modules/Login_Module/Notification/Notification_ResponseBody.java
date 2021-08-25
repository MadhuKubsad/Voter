package com.example.promena.voter.Api_Modules.Modules.Login_Module.Notification;

import com.google.gson.annotations.SerializedName;

public class Notification_ResponseBody {

    @SerializedName("ID")
    public String ID;

    @SerializedName("Link")
    public String Link;

    @SerializedName("Text")
    public String Text;

    @SerializedName("NotificationDate")
    public String NotificationDate;
}
