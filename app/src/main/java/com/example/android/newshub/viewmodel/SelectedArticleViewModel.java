package com.example.android.newshub.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.android.newshub.model.entity.NewsArticle;

public class SelectedArticleViewModel extends ViewModel {
    private final MutableLiveData<NewsArticle> selectedArticle = new MutableLiveData<>();

    public SelectedArticleViewModel() {

    }

    /**
     * Updates the selected TopStoriesArticle when the user chooses a new option from the list
     * @param article the new NewArticle selected by the user
     */
    public void selectArticle(NewsArticle article) {
        selectedArticle.setValue(article);
    }

    /**
     * Returns the currently selected TopStoriesArticle
     * @return
     */
    public LiveData<NewsArticle> getSelectedArticle() {
        return selectedArticle;
    }
}
