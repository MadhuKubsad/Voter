package com.example.promena.voter.Api_Modules.Modules.Login_Module.Response;

import com.google.gson.annotations.SerializedName;

public class Login_Response {

    @SerializedName("Data")
    public Login_Response_Body Data;

    @SerializedName("Code")
    public String Code;

    @SerializedName("Message")
    public String Message;

}
