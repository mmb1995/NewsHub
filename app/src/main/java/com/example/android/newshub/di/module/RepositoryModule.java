package com.example.android.newshub.di.module;

import com.example.android.newshub.remote.NytApiService;
import com.example.android.newshub.repository.NewsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Provides
    @Singleton
    NewsRepository providesNewsRepository(NytApiService service) {
        return new NewsRepository(service);
    }
}
