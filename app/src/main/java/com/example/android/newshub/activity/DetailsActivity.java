package com.example.android.newshub.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.android.newshub.R;
import com.example.android.newshub.fragment.PreviewFragment;
import com.example.android.newshub.fragment.WebViewModalFragment;
import com.example.android.newshub.model.entity.NewsArticle;
import com.example.android.newshub.utils.Constants;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class DetailsActivity extends AppCompatActivity implements HasSupportFragmentInjector,
        WebViewModalFragment.OnWebViewClickListener, PreviewFragment.OnFullArticleButtonListener {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        AndroidInjection.inject(this);

        if (getIntent() != null && savedInstanceState == null) {
            String articleUrl = getIntent().getStringExtra(Constants.DETAILS_ACTIVITY_INTENT_EXTRA);
            displayPreview(articleUrl);
        }
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    private void displayPreview(String articleUrl) {
        PreviewFragment previewFragment = PreviewFragment.newInstance(articleUrl);
        if (getCurrentFragment() == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.details_frame_layout, previewFragment)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.details_frame_layout, previewFragment)
                    .commit();
        }
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.details_frame_layout);
    }

    @Override
    public void onWebViewButtonClick() {

    }

    @Override
    public void onFullArticleButtonClicked(NewsArticle article) {
        displayFullArticle(article);
    }

    /**
     * Displays a full news article in a WebView popup
     * @param article article to be displayed
     */
    private void displayFullArticle(NewsArticle article) {
        WebViewModalFragment webFragment = WebViewModalFragment.newInstance(article.url);
        webFragment.show(getSupportFragmentManager(), null);
    }

}
