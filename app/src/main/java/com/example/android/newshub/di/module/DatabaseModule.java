package com.example.android.newshub.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.android.newshub.database.NewsDao;
import com.example.android.newshub.database.NewsDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    @Singleton
    NewsDatabase providesDatabase(Application application) {
        return Room.databaseBuilder(application, NewsDatabase.class, "newsDatabase.db")
                .build();
    }

    @Provides
    @Singleton
    NewsDao providesNewsDao(NewsDatabase database) {
        return database.newsDao();
    }

    @Provides
    Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }
}
