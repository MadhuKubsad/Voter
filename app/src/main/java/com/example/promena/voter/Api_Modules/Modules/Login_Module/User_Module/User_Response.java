package com.example.promena.voter.Api_Modules.Modules.Login_Module.User_Module;

import com.example.promena.voter.Api_Modules.Modules.Login_Module.Response.Login_Response_Body;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User_Response {

    @SerializedName("Data")
    public User_ResponseBody Data;

    @SerializedName("Code")
    public String Code;

    @SerializedName("Message")
    public String Message;
}
