package com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Sub_Member;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Sub_Enrollment_Response {

    @SerializedName("Data")
    public List<Sub_Members_List> Data;

    @SerializedName("Code")
    public String Code;

    @SerializedName("Message")
    public String Message;
}
