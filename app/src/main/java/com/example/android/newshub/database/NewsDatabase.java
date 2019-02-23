package com.example.android.newshub.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.android.newshub.model.entity.NewsArticle;

@Database(entities = {NewsArticle.class}, version = 1, exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
}
