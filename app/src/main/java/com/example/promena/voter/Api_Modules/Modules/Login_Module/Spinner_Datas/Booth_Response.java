package com.example.promena.voter.Api_Modules.Modules.Login_Module.Spinner_Datas;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Booth_Response {

    @SerializedName("Code")
    public String Code;

    @SerializedName("Message")
    public String Message;

    @SerializedName("Data")
    public List<Booth_Datas> Data;
}
