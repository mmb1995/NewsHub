package com.example.android.newshub.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "Articles")
public class NewsArticle implements Parcelable {

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

    protected NewsArticle(Parcel in) {
        url = in.readString();
        headline = in.readString();
        snippet = in.readString();
        imageUrl = in.readString();
        date = in.readString();
        byline = in.readString();
        section = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(headline);
        dest.writeString(snippet);
        dest.writeString(imageUrl);
        dest.writeString(date);
        dest.writeString(byline);
        dest.writeString(section);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<NewsArticle> CREATOR = new Parcelable.Creator<NewsArticle>() {
        @Override
        public NewsArticle createFromParcel(Parcel in) {
            return new NewsArticle(in);
        }

        @Override
        public NewsArticle[] newArray(int size) {
            return new NewsArticle[size];
        }
    };
}

