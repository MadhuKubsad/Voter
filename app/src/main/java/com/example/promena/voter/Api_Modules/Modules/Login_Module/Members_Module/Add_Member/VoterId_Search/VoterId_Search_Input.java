package com.example.promena.voter.Api_Modules.Modules.Login_Module.Members_Module.Add_Member.VoterId_Search;

import com.google.gson.annotations.SerializedName;

public class VoterId_Search_Input {

    @SerializedName("AccessToken")
    public String AccessToken;

    @SerializedName("voterId")
    public String voterId;

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }
}
