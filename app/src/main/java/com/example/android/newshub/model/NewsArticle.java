package com.example.android.newshub.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
    public String imageUrl = "";
    public String date = "";

    //default empty constructor
    public NewsArticle() {}

    //convenience constructor
    public NewsArticle(String headline, String webUrl, long publishTime, String snippet, String imageUrl){
        this.headline = headline;
        this.webUrl = webUrl;
        this.publishTime = publishTime;
        this.snippet = snippet;
        this.imageUrl = imageUrl;
    }

    public NewsArticle(String headline, long publishTime) {
        this.headline = headline;
        this.publishTime = publishTime;
    }

    public String toString() {
        //can modify this to include more or different details
        return this.headline;
    }


    /********************************
     *  API response parsing methods
     ********************************/

    /**
     * Parses the query response from the NYT Top Stories API
     * http://developer.nytimes.com/top_stories_v2.json
     */
    public static List<NewsArticle> parseNYTTopStories(JSONObject response){
        ArrayList<NewsArticle> stories = new ArrayList<NewsArticle>();

        try {
            JSONArray jsonResults = response.getJSONArray("results"); //response.results

            for(int i=0; i<jsonResults.length(); i++){
                JSONObject resultItemObj = jsonResults.getJSONObject(i);

                String headline = resultItemObj.getString("title");
                String webUrl = resultItemObj.getString("url");
                String snippet = resultItemObj.getString("abstract");

                //date handling
                String pubDateString = resultItemObj.getString("published_date");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
                long publishTime = formatter.parse(pubDateString).getTime();

                //image extracting
                JSONArray jsonMultimedia = resultItemObj.getJSONArray("multimedia");
                String imageUrl = extractImageUrl(jsonMultimedia, MAX_IMAGE_WIDTH);

                NewsArticle story = new NewsArticle(headline, webUrl, publishTime, snippet, imageUrl);
                stories.add(story);
            } //end for loop

        } catch (JSONException e) {
            Log.e(TAG, "Error parsing json", e); //Android log the error
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing date", e); //Android log the error
        }

        return stories;
    }

    /**
     * Parses the query response from the NYT Article Search API
     * http://developer.nytimes.com/article_search_v2.json
     */
    public static List<NewsArticle> parseNYTSearch(JSONObject response) {
        ArrayList<NewsArticle> stories = new ArrayList<NewsArticle>();

        try {
            JSONObject jsonResponse = response.getJSONObject("response"); //response.response
            JSONArray jsonResults = jsonResponse.getJSONArray("docs"); //response.response.docs

            for(int i=0; i<jsonResults.length(); i++){
                JSONObject resultItemObj = jsonResults.getJSONObject(i);

                String headline = resultItemObj.getJSONObject("headline").getString("main");
                String snippet = resultItemObj.getString("snippet");
                String webUrl = resultItemObj.getString("web_url");
                if(snippet.equals("null")) //abstract is often null for search results
                    snippet = resultItemObj.getString("snippet");

                //date handling
                String pubDateString = resultItemObj.getString("pub_date");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
                long publishTime = formatter.parse(pubDateString).getTime();

                //image extracting
                JSONArray jsonMultimedia = resultItemObj.getJSONArray("multimedia");
                String imageUrl = "https://static01.nyt.com/"+ extractImageUrl(jsonMultimedia, MAX_IMAGE_WIDTH); //add domain

                NewsArticle story = new NewsArticle(headline, webUrl, publishTime, snippet, imageUrl);
                stories.add(story);
            } //end for loop

        } catch (JSONException e) {
            Log.e(TAG, "Error parsing json", e); //Android log the error
        } catch (ParseException e) {
            Log.e(TAG, "Error parsing date", e); //Android log the error
        }

        return stories;
    }

    /**
     * Returns the url of the image in the NYT multimedia JSON Array.
     * Chooses the largest image smaller than `maxWidth`.
     */
    public static String extractImageUrl(JSONArray jsonMultimedia, int maxWidth) {
        String biggestImageUrl = "";
        int biggestImageSize = 0;

        try {
            //simple maximum search
            for(int j=0; j<jsonMultimedia.length(); j++) {
                JSONObject multimediaItem = jsonMultimedia.getJSONObject(j);
                int imageWidth = multimediaItem.getInt("width");
                if (imageWidth <= maxWidth && imageWidth > biggestImageSize) {
                    biggestImageUrl = multimediaItem.getString("url");
                    biggestImageSize = multimediaItem.getInt("width");
                }
            }
        } catch (JSONException e){
            Log.e(TAG, "Error parsing json", e); //Android log the error
        }

        return biggestImageUrl;
    }
}
