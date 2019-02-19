package com.example.android.newshub.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.android.newshub.BuildConfig;
import com.example.android.newshub.model.NewsArticle;
import com.example.android.newshub.model.retrofit.ArticleSearchResponse;
import com.example.android.newshub.model.retrofit.Multimedia;
import com.example.android.newshub.model.retrofit.SearchArticle;
import com.example.android.newshub.model.retrofit.TopStoriesArticle;
import com.example.android.newshub.model.retrofit.TopStoriesResponse;
import com.example.android.newshub.remote.NytApiService;
import com.example.android.newshub.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class NewsRepository {
    private static final String TAG = "NewsRepository";

    private final NytApiService mNewsApiService;

    @Inject
    public NewsRepository(NytApiService service) {
        this.mNewsApiService = service;
    }

    public LiveData<List<NewsArticle>> getTopStories(String searchTerm) {
        final MutableLiveData<List<NewsArticle>> results = new MutableLiveData<>();

        Log.i(TAG, "Api key = " + BuildConfig.NYT_API_KEY);
        // Performs the network request to the Nyt api
        mNewsApiService.getTopStories(searchTerm, BuildConfig.NYT_API_KEY, Constants.SORT_VALUE)
                .enqueue(new Callback<TopStoriesResponse>() {
                    @Override
                    public void onResponse(Call<TopStoriesResponse> call, Response<TopStoriesResponse> response) {
                        Log.i(TAG,response.toString());
                        if (response.isSuccessful()) {
                            List<NewsArticle> articles = parseTopStories(response.body().getTopStories());
                            results.setValue(articles);
                        }
                    }

                    @Override
                    public void onFailure(Call<TopStoriesResponse> call, Throwable t) {
                        Log.i(TAG, "Failed to get data");
                        // TODO replace
                        results.setValue(null);
                    }
                });
        return results;
    }

    /**
     * Parses the query response from the NYT Article Search API
     * http://developer.nytimes.com/article_search_v2.json
     */
    public LiveData<List<NewsArticle>> getArticleSearchResults(String searchTerm, String beginDate, String endDate) {
        final  MutableLiveData<List<NewsArticle>> results = new MutableLiveData<>();
        Log.i(TAG,"Calling New York Times Api ");
        mNewsApiService.getArticlesBySearchTerm(BuildConfig.NYT_API_KEY, searchTerm, Constants.SORT_VALUE,
                beginDate, endDate).enqueue(new Callback<ArticleSearchResponse>() {
            @Override
            public void onResponse(Call<ArticleSearchResponse> call, Response<ArticleSearchResponse> response) {
                Log.i(TAG, response.toString());
                if (response.isSuccessful()) {
                    List<NewsArticle> articles = parseArticleSearch(response.body().getResponse().getArticles());
                    results.setValue(articles);
                }
            }

            @Override
            public void onFailure(Call<ArticleSearchResponse> call, Throwable t) {
                t.printStackTrace();
                // TODO replace
                //results.setValue(null);
            }
        });
        return results;
    }

    /**
     * Parses the query response from the NYT Top Stories API
     * http://developer.nytimes.com/top_stories_v2.json
     */
    private List<NewsArticle> parseTopStories(List<TopStoriesArticle> results) {
        List<NewsArticle> articles = new ArrayList<>();
        for (TopStoriesArticle article: results) {
            String headline = article.getTitle();
            String webUrl = article.getUrl();
            String snippet = article.getAbstract();
            String byline = article.getByline();
            String section = article.getSection();

            //date handling
            String pubDateString = article.getPublishedDate();
            long publishTime;
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
                publishTime = formatter.parse(pubDateString).getTime();
            } catch (ParseException e) {
                publishTime = 0;
            }

            //image extracting
            List<Multimedia> multimediaList = article.getMultimedia();
            Map<String, String> images = mapImageUrls(multimediaList);

            NewsArticle story = new NewsArticle(headline, webUrl, publishTime, snippet, byline, section, images);
            articles.add(story);
        }
        return articles;
    }

    private List<NewsArticle> parseArticleSearch(List<SearchArticle> results) {
        List<NewsArticle> articles = new ArrayList<>();
        for (SearchArticle article: results) {
            String headline = article.getHeadline().getMain();
            String snippet = article.getSnippet();
            String webUrl = article.getWebUrl();
            if (snippet.equals("null")) //abstract is often null for search results
                snippet = "";

            //date handling
            String pubDateString = article.getPubDate();
            long publishTime;
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
                publishTime = formatter.parse(pubDateString).getTime();
            } catch (ParseException e) {
                publishTime = 0;
            }

            //image extracting
            List<Multimedia> multimedia = article.getMultimedia();
            Map<String, String> images = mapImageUrls(multimedia);

            NewsArticle story = new NewsArticle(headline, webUrl, publishTime, snippet, null, null, images);
            articles.add(story);
        }
        return articles;
    }

    /**
     * Returns a map where the images format is the key and the url is the value
     *
     */
    private Map<String, String> mapImageUrls(List<Multimedia> multimediaList) {
        Map<String, String> imagesMap = new HashMap();
        for (Multimedia multimedia: multimediaList) {
            if (multimedia.getFormat() != null && !imagesMap.containsKey(multimedia.getFormat())) {
                imagesMap.put(multimedia.getFormat(), multimedia.getUrl());
            }
        }
        return imagesMap;
    }
}
