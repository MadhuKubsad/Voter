package com.example.promena.voter.Api_Modules.Modules.Login_Module.User_Module;

import com.google.gson.annotations.SerializedName;

public class User_ResponseBody {

    @SerializedName("ID")
    public int ID;

    @SerializedName("AssemblyID")
    public String AssemblyID;

    @SerializedName("WardID")
    public String WardID;

    @SerializedName("BoothID")
    public String BoothID;

    @SerializedName("BoothNo")
    public String BoothNo;

    @SerializedName("UserName")
    public String UserName;

    @SerializedName("Gender")
    public String Gender;

    @SerializedName("EmailID")
    public String EmailID;

    @SerializedName("Mobile")
    public String Mobile;

    @SerializedName("Password")
    public String Password;

    @SerializedName("Photo")
    public String Photo;

    @SerializedName("RegisterDateTime")
    public String RegisterDateTime;

    @SerializedName("Approval")
    public boolean Approval;

    @SerializedName("ApprovedBy")
    public int ApprovedBy;

    @SerializedName("AccessToken")
    public String AccessToken;

}
