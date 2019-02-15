package com.example.android.newshub.di.module;

import com.example.android.newshub.fragment.NewsArticleListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract NewsArticleListFragment contributeNewsArticleListFragment();
}
