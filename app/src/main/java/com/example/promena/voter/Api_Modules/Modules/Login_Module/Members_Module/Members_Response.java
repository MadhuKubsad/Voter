package com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Members_Response {

    @SerializedName("Data")
    public List<Members_List> Data;

    @SerializedName("Code")
    public String Code;

    @SerializedName("Message")
    public String Message;
}
