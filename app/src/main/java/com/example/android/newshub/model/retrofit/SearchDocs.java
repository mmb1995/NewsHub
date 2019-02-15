package com.example.android.newshub.model.retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchDocs {

    @SerializedName("docs")
    @Expose
    private List<SearchArticle> docs = null;

    public List<SearchArticle> getArticles() {
        return docs;
    }

    public void setArticles(List<SearchArticle> docs) {
        this.docs = docs;
    }

}
