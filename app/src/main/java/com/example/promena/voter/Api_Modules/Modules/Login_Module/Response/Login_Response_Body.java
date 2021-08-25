package com.example.promena.voter.Api_Modules.Modules.Login_Module.Response;

import com.google.gson.annotations.SerializedName;

public class Login_Response_Body {

    @SerializedName("Token")
    public String Token;

    @SerializedName("EmployeeName")
    public String EmployeeName;

    @SerializedName("Image")
    public String Image;

    @SerializedName("Assembly")
    public String Assembly;

    @SerializedName("Ward")
    public String Ward;

}
