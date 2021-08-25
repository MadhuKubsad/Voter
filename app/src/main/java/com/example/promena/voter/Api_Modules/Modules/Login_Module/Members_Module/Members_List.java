package com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module;

import com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Sub_Member.Sub_Members_List;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Members_List {

    @SerializedName("ID")
    public String ID;

    @SerializedName("AssemblyID")
    public String AssemblyID;

    @SerializedName("WardID")
    public String WardID;

    @SerializedName("BoothID")
    public String BoothID;

    @SerializedName("BoothNo")
    public String BoothNo;

    @SerializedName("SubEnrollments")
    public List<Sub_Members_List> SubEnrollments;

    @SerializedName("Name")
    public String Name;

    @SerializedName("Gender")
    public String Gender;

    @SerializedName("EmailID")
    public String EmailID;

    @SerializedName("Mobile")
    public String Mobile;

    @SerializedName("Photo")
    public String Photo;

    @SerializedName("Address")
    public String Address;

    @SerializedName("VoterID")
    public String VoterID;

    @SerializedName("UserID")
    public String UserID;

    @SerializedName("RegisterDateTime")
    public String RegisterDateTime;
}
