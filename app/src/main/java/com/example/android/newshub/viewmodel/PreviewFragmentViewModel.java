package com.example.android.newshub.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.newshub.model.entity.NewsArticle;
import com.example.android.newshub.repository.NewsRepository;

import javax.inject.Inject;

public class PreviewFragmentViewModel extends ViewModel {
    private LiveData<NewsArticle> mNewsArticle;
    private NewsRepository mRepo;

    @Inject
    public PreviewFragmentViewModel(NewsRepository repo) {
        this.mRepo = repo;
    }

    public void init(String articleUrl) {
        if (mNewsArticle != null) {
            return;
        }
        mNewsArticle = mRepo.getArticle(articleUrl);
    }

    public LiveData<NewsArticle> getArticle() {
        return mNewsArticle;
    }
}
