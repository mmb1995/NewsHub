package com.example.android.newshub.di.module;

import com.example.android.newshub.fragment.NewsArticleListFragment;
import com.example.android.newshub.fragment.PreviewFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract NewsArticleListFragment contributeNewsArticleListFragment();

    @ContributesAndroidInjector
    abstract PreviewFragment contributesPreviewFragment();
}
