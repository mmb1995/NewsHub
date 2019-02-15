package com.example.android.newshub.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.android.newshub.di.ViewModelKey;
import com.example.android.newshub.viewmodel.FactoryViewModel;
import com.example.android.newshub.viewmodel.NewsArticleViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(NewsArticleViewModel.class)
    abstract ViewModel bindNewsArticleViewModel(NewsArticleViewModel newsArticleViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);
}
