package com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Member;

import com.google.gson.annotations.SerializedName;

public class Add_Member_Input {

    @SerializedName("ID")
    public int ID;

    @SerializedName("AssemblyID")
    public int AssemblyID;

    @SerializedName("WardID")
    public int WardID;

    @SerializedName("BoothID")
    public String BoothID;

    @SerializedName("BoothNo")
    public String BoothNo;

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

    @SerializedName("UserID")
    public int UserID;

    @SerializedName("Address")
    public String Address;

    @SerializedName("AdultFamilyMembers")
    public int AdultFamilyMembers;

    @SerializedName("VoterID")
    public String VoterID;

    @SerializedName("RegisterDateTime")
    public String RegisterDateTime;
}
