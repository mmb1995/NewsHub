package com.example.android.newshub;

import android.app.Activity;
import android.app.Application;

import com.example.android.newshub.di.component.AppComponent;
import com.example.android.newshub.di.component.DaggerAppComponent;
import com.example.android.newshub.di.module.AppModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class NewsApplication extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .application(this)
                .appModule(new AppModule(this))
                .build()
                .inject(this);
    }

}
