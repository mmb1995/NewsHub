package com.example.android.newshub.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.android.newshub.model.entity.NewsArticle;

import java.util.List;

@Dao
public interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveArticles(List<NewsArticle> articles);

    @Query("SELECT * FROM Articles WHERE section=:section")
    LiveData<List<NewsArticle>> getArticlesBySection(String section);
}
