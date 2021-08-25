package com.example.promena.voter.Api_Modules.Modules.Login_Module.DashBoard_Module;

import com.google.gson.annotations.SerializedName;

public class DashBoard_Response {

    @SerializedName("Data")
    public DashBoard_Data Data;

    @SerializedName("Code")
    public String Code;

    @SerializedName("Message")
    public String Message;
}
