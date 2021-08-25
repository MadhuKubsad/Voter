package com.example.promena.voter.Api_Modules.Modules.Login_Module.Spinner_Datas;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Assembly_Response {

    @SerializedName("Data")
    public List<Assembly_Data> Data;

    @SerializedName("Code")
    public String Code;

    @SerializedName("Message")
    public String Message;
}
