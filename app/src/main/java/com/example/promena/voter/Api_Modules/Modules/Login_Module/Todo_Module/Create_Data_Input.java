package com.example.promena.voter.Api_Modules.Modules.Login_Module.Todo_Module;

import com.google.gson.annotations.SerializedName;

public class Create_Data_Input {

    @SerializedName("ID")
    public int ID;

    @SerializedName("TaskTitle")
    public String TaskTitle;

    @SerializedName("Task")
    public String Task;

    @SerializedName("TaskDateTime")
    public String TaskDateTime;

    @SerializedName("Priority")
    public int Priority;

    @SerializedName("UserID")
    public int UserID;

    @SerializedName("Status")
    public boolean Status;
}
