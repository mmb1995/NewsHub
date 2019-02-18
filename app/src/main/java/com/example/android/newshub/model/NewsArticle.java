package com.example.android.newshub.model;

import java.util.Map;

/**
 * A class representing a single news item (article). Can be parsed from the New York Times API.
 * @author joelross
 */
public class NewsArticle {

    public static final String TAG = "NewsArticle";

    public static final int MAX_IMAGE_WIDTH = 800;

    //instance variables for the NewsArticle data structure
    public String headline = "";
    public String webUrl = "";
    public long publishTime;
    public String snippet = "";
    public Map<String, String> images;
    public String date = "";

    //default empty constructor
    public NewsArticle() {}

    //convenience constructor
    public NewsArticle(String headline, String webUrl, long publishTime, String snippet, Map<String, String> images){
        this.headline = headline;
        this.webUrl = webUrl;
        this.publishTime = publishTime;
        this.snippet = snippet;
        this.images = images;
    }

    public NewsArticle(String headline, long publishTime) {
        this.headline = headline;
        this.publishTime = publishTime;
    }

    public String toString() {
        //can modify this to include more or different details
        return this.headline;
    }

}
