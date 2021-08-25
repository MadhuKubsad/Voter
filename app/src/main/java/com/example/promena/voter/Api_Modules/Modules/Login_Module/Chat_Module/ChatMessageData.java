package com.example.promena.voter.Api_Modules.Modules.Login_Module.Chat_Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatMessageData {

    @SerializedName("pageIndex")
    @Expose
    private Integer pageIndex;
    @SerializedName("totalPages")
    @Expose
    private Integer totalPages;
    @SerializedName("totalRecords")
    @Expose
    private Integer totalRecords;
    @SerializedName("data")
    @Expose
    private List<ChatMessage> data = null;
    @SerializedName("hasPreviousPage")
    @Expose
    private Boolean hasPreviousPage;
    @SerializedName("hasNextPage")
    @Expose
    private Boolean hasNextPage;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<ChatMessage> getData() {
        return data;
    }

    public void setData(List<ChatMessage> data) {
        this.data = data;
    }

    public Boolean getHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(Boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public Boolean getHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(Boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}
