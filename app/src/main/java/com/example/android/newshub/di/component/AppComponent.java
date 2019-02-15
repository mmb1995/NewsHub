package com.example.android.newshub.di.component;

import com.example.android.newshub.NewsApplication;
import com.example.android.newshub.di.module.ActivityModule;
import com.example.android.newshub.di.module.AppModule;
import com.example.android.newshub.di.module.FragmentModule;
import com.example.android.newshub.di.module.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AppModule.class,
        AndroidSupportInjectionModule.class,
        ActivityModule.class,
        FragmentModule.class,
        ViewModelModule.class
})

public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(NewsApplication application);
        Builder appModule(AppModule appModule);
        AppComponent build();
    }

    void inject(NewsApplication application);
}
