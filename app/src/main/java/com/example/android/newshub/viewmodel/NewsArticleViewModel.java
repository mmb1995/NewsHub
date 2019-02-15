package com.example.android.newshub.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.android.newshub.model.NewsArticle;
import com.example.android.newshub.repository.NewsRepository;

import java.util.List;

import javax.inject.Inject;

public class NewsArticleViewModel extends ViewModel {
    private static final String TAG = "NewsArticleViewModel";

    // Holds articles returned by the repo
    private LiveData<List<NewsArticle>> mNewsArticles;

    private final NewsRepository mNewsRepo;

    @Inject
    public NewsArticleViewModel(NewsRepository repository) {
        this.mNewsRepo = repository;
    }

    public LiveData<List<NewsArticle>> getTopStories(String searchTerm) {
        Log.i(TAG, "Getting top stories");
        if (mNewsArticles == null) {
            mNewsArticles = mNewsRepo.getTopStories(searchTerm);
        }
        return mNewsArticles;
    }

    public LiveData<List<NewsArticle>> getArticleSearchResults(String searchTerm,
                                                               String beginDate, String endDate) {
        Log.i(TAG, "Getting article search");
        if (mNewsArticles == null) {
            mNewsArticles = mNewsRepo.getArticleSearchResults(searchTerm, beginDate, endDate);
        }
        return mNewsArticles;
    }
}
