package com.example.android.newshub.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.android.newshub.model.Resource;
import com.example.android.newshub.model.entity.NewsArticle;
import com.example.android.newshub.repository.NewsRepository;

import java.util.List;

import javax.inject.Inject;

public class NewsArticleViewModel extends ViewModel {
    private static final String TAG = "NewsArticleViewModel";

    // Holds articles returned by the repo
    private LiveData<Resource<List<NewsArticle>>> mNewsArticles;

    private final NewsRepository mNewsRepo;

    @Inject
    public NewsArticleViewModel(NewsRepository repository) {
        this.mNewsRepo = repository;
    }

    public LiveData<Resource<List<NewsArticle>>> getTopStories(String searchTerm) {
        if (mNewsArticles == null) {
            Log.i(TAG, "Getting top stories");
            mNewsArticles = mNewsRepo.getTopStories(searchTerm);
        }
        Log.i(TAG, "Returning top stories");
        return mNewsArticles;
    }

}
