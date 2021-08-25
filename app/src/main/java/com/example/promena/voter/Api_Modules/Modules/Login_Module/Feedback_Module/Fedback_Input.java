package com.example.promena.voter.Api_Modules.Modules.Login_Module.Feedback_Module;

import com.google.gson.annotations.SerializedName;

public class Fedback_Input {

    @SerializedName("ID")
    public int ID;

    @SerializedName("UserID")
    public int UserID;

    @SerializedName("VoterID")
    public String VoterID;

    @SerializedName("Name")
    public String Name;

    @SerializedName("Mobile")
    public String Mobile;

    @SerializedName("Subject")
    public String Subject;

    @SerializedName("FeedBack1")
    public String FeedBack1;

    @SerializedName("FeedBackDateTime")
    public String FeedBackDateTime;


}


