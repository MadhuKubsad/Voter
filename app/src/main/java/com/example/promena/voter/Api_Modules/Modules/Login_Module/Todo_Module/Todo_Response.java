package com.example.promena.voter.Api_Modules.Modules.Login_Module.Todo_Module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Todo_Response {

    @SerializedName("Data")
    public List<Todo_List> Data;

    @SerializedName("Code")
    public String Code;

    @SerializedName("Message")
    public String Message;
}
