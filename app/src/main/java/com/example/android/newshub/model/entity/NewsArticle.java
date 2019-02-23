package com.example.android.newshub.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Articles")
public class NewsArticle {

    @PrimaryKey
    @NonNull
    public String url;

    public String headline;
    public String snippet;
    public String imageUrl;
    public String date;
    public String byline;
    public String section;

    public NewsArticle() {};
}
