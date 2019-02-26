package com.example.android.newshub.di.module;

import com.example.android.newshub.activity.MainActivity;
import com.example.android.newshub.activity.DetailsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

    @ContributesAndroidInjector
    abstract DetailsActivity contributesDetailsActivity();
}
