package com.example.promena.voter.Api_Modules.Modules.Login_Module.Synchronize_Module;

import com.google.gson.annotations.SerializedName;

public class Synchronize_Input {

    @SerializedName("ID")
    public int ID;

    @SerializedName("UserID")
    public int UserID;

    @SerializedName("LogDate")
    public String LogDate;

    @SerializedName("Lat")
    public String Lat;

    @SerializedName("Long")
    public String Long;

    @SerializedName("Address")
    public String Address;

}
