package com.example.android.newshub;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import com.example.android.newshub.fragment.NewsArticleListFragment;
import com.example.android.newshub.fragment.PreviewFragment;
import com.example.android.newshub.fragment.WebViewModalFragment;
import com.example.android.newshub.model.entity.NewsArticle;
import com.example.android.newshub.utils.Constants;
import com.example.android.newshub.viewmodel.SelectedArticleViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector,
    PreviewFragment.OnFullArticleButtonListener, WebViewModalFragment.OnWebViewClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MainActivity";

    private static final String WEB_VIEW_TAG = "WebViewFragment";
    private static final String NEWS_RESULTS_LIST_FRAGMENT_TAG = "NewsResultList";
    private static final String PREVIEW_FRAGMENT_TAG ="previewFragment";

    @BindView(R.id.mainSwipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        AndroidInjection.inject(this);

        mSwipeRefresh.setOnRefreshListener(this);

        // Set up observer to listen for when the user selects an article
        SelectedArticleViewModel model = ViewModelProviders.of(this).get(SelectedArticleViewModel.class);
        model.getSelectedArticle().observe(this, selectedArticle -> {
            displayPreview();
        });

        if (savedInstanceState == null) {
            displayArticleListFragment();
        }
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    private void displayFragment(Fragment fragment) {
        if (isVisible()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.mainFragmentFrameLayout, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            // No fragment currently visible
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.mainFragmentFrameLayout, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    private void displayPreview() {
        // Create preview fragment
        PreviewFragment previewFragment = new PreviewFragment();

        // display preview and remove NewsResultListFragment
        displayFragment(previewFragment);
    }

    private void displayArticleListFragment() {
        NewsArticleListFragment fragment = NewsArticleListFragment.newInstance(Constants.TOP_STORIES,
                Constants.TOP_STORIES_HOME);
        displayFragment(fragment);
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
        webFragment.show(getSupportFragmentManager(), WEB_VIEW_TAG);
    }

    /**
     * Helper method to determine if a fragment is currently visible on the screen
     * @return
     */
    private boolean isVisible() {
        return getSupportFragmentManager().findFragmentById(R.id.mainFragmentFrameLayout) != null;
    }

    @Override
    public void onWebViewButtonClick() {

    }

    @Override
    public void onRefresh() {
        // Check what fragment is currently being displayed and refresh it
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.mainFragmentFrameLayout);
        if (currentFragment != null) {
            if (currentFragment instanceof NewsArticleListFragment) {
                displayArticleListFragment();
            } else if (currentFragment instanceof PreviewFragment) {
                displayPreview();
            }
        }
        mSwipeRefresh.setRefreshing(false);
    }
}
