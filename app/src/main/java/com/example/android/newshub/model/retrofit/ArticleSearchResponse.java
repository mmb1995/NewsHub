package com.example.android.newshub.model.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticleSearchResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("response")
    @Expose
    private com.example.android.newshub.model.retrofit.SearchDocs response;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public com.example.android.newshub.model.retrofit.SearchDocs getResponse() {
        return response;
    }

    public void setResponse(com.example.android.newshub.model.retrofit.SearchDocs response) {
        this.response = response;
    }

}
