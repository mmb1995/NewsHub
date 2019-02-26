package com.example.android.newshub.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.android.newshub.R;
import com.example.android.newshub.fragment.NewsArticleListFragment;
import com.example.android.newshub.fragment.PreviewFragment;
import com.example.android.newshub.fragment.WebViewModalFragment;
import com.example.android.newshub.model.entity.NewsArticle;
import com.example.android.newshub.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector,
    PreviewFragment.OnFullArticleButtonListener, WebViewModalFragment.OnWebViewClickListener,
        SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemSelectedListener, NewsArticleListFragment.OnArticleSelectedListner {

    private static final String TAG = "MainActivity";

    private static final String WEB_VIEW_TAG = "WebViewFragment";
    private static final String NEWS_RESULTS_LIST_FRAGMENT_TAG = "NewsResultList";
    private static final String PREVIEW_FRAGMENT_TAG ="previewFragment";
    private static final String BUNDLE_SPINNER_POSITION = "spinner_pos";
    private static final String BUNDLE_SELECTED_SECTION = "selection";

    @Nullable
    @BindView(R.id.mainSwipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private Spinner mSpinner;
    private int selectedPosition;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    private boolean isTwoPane;
    private boolean hasRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        AndroidInjection.inject(this);

        // check configuration
        if (isTablet()) {
            isTwoPane = true;
            if (savedInstanceState == null) {
                displayArticleListFragment(Constants.TOP_STORIES_HOME);
            } else {
                selectedPosition = savedInstanceState.getInt(BUNDLE_SPINNER_POSITION);
            }
        } else {
            // Not in two pane mode
            isTwoPane = false;
            mSwipeRefresh.setOnRefreshListener(this);
            if (savedInstanceState == null) {
                displayArticleListFragment(Constants.TOP_STORIES_HOME);
            } else {
                selectedPosition = savedInstanceState.getInt(BUNDLE_SPINNER_POSITION);
            }
        }

        setSupportActionBar(mToolbar);
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_main, menu);

        // Create the spinner
        MenuItem item = menu.findItem(R.id.action_dropdown);
        mSpinner = (Spinner) item.getActionView();

        // Set listener
        mSpinner.setOnItemSelectedListener(this);

        // Create the adapter
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sections_dropdown_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        // set spinner position
        mSpinner.setSelection(selectedPosition);

        return true;
    }

    /**
     * Displays a list on news articles based off the option the user chooses from the spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        selectedPosition = position;
        if (!hasRun) {
            hasRun = true;
            Log.i(TAG, "first run");
        } else {
            displaySelectedSection(position);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Required override method
    }

    /**
     * Displays articles related to the current spinner selection also called by swipe refresh listener
     * @param position
     */
    private void displaySelectedSection(int position) {
        switch (position) {
            case 0:
                displayArticleListFragment(Constants.TOP_STORIES_HOME);
                break;
            case 1:
                displayArticleListFragment(Constants.TOP_STORIES_WORLD);
                break;
            case 2:
                displayArticleListFragment(Constants.TOP_STORIES_SPORTS);
                break;
            default:
                break;
        }
    }

    /**
     * Displays the given fragment in the container with the given id
     * @param fragment
     * @param layoutId
     */
    private void displayFragment(Fragment fragment, int layoutId) {
        if (isVisible(layoutId)) {
            getSupportFragmentManager().beginTransaction()
                    .replace(layoutId, fragment)
                    .addToBackStack(null)
                    .commit();
        } else {
            // No fragment currently visible
            getSupportFragmentManager().beginTransaction()
                    .add(layoutId, fragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    /**
     * Displays preview if the device is a tablet
     */
    private void displayPreview(String articleUrl) {
        PreviewFragment previewFragment = PreviewFragment.newInstance(articleUrl);

        // display preview and remove NewsResultListFragment
        displayFragment(previewFragment, R.id.preview_frame_layout);
    }

    private void displayArticleListFragment(String section) {
        NewsArticleListFragment fragment = NewsArticleListFragment.newInstance(Constants.TOP_STORIES,
                section);
        displayFragment(fragment, R.id.news_article_list_frameLayout);
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

    private boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }

    /**
     * Helper method to determine if a fragment is currently visible in the given container
     * @return
     */
    private boolean isVisible(int layoutId) {
        return getSupportFragmentManager().findFragmentById(layoutId) != null;
    }

    private Fragment getCurrentFragment(int layoutId) {
        return getSupportFragmentManager().findFragmentById(layoutId);
    }

    @Override
    public void onArticleSelected(NewsArticle article) {
        if (isTwoPane) {
            displayPreview(article.url);
        } else {
            // Launch details activity
            Intent startDetailsActivityIntent = new Intent(MainActivity.this, DetailsActivity.class);
            startDetailsActivityIntent.putExtra(Constants.DETAILS_ACTIVITY_INTENT_EXTRA, article.url);
            startActivity(startDetailsActivityIntent);
        }
    }

    @Override
    public void onWebViewButtonClick() {

    }

    @Override
    public void onRefresh() {
        // Check configuration
        if (isTwoPane) {
            // TODO
        } else if (mSpinner != null) {
            displaySelectedSection(mSpinner.getSelectedItemPosition());
        }
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_SPINNER_POSITION, selectedPosition);
        super.onSaveInstanceState(outState);
    }

    /**
     *  Sets up default tablet ui
     */
    private void setUpTabletUi(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            displayArticleListFragment(Constants.TOP_STORIES_HOME);
        } else {
            selectedPosition = savedInstanceState.getInt(BUNDLE_SPINNER_POSITION);
        }
    }

}
