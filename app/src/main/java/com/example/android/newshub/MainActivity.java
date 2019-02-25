package com.example.android.newshub;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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
        SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "MainActivity";

    private static final String WEB_VIEW_TAG = "WebViewFragment";
    private static final String NEWS_RESULTS_LIST_FRAGMENT_TAG = "NewsResultList";
    private static final String PREVIEW_FRAGMENT_TAG ="previewFragment";
    private static final String BUNDLE_SPINNER_POSITION = "spinner_pos";
    private static final String BUNDLE_SELECTED_SECTION = "selection";

    @BindView(R.id.mainSwipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private Spinner mSpinner;
    private int selectedPosition = -1;

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    private boolean isTwoPane;
    private boolean isRestored;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        AndroidInjection.inject(this);

        if (savedInstanceState == null) {
            isRestored = false;
        } else {
            selectedPosition = savedInstanceState.getInt(BUNDLE_SPINNER_POSITION);
            isRestored = true;
        }

        setSupportActionBar(mToolbar);
        mSwipeRefresh.setOnRefreshListener(this);
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

        // check for saved spinner state
        if (selectedPosition != -1) {
            mSpinner.setSelection(selectedPosition);
        } else {
            mSpinner.setSelection(0);
        }

        return true;
    }

    /**
     * Displays a list on news articles based off the option the user chooses from the spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        selectedPosition = position;
        if (isRestored) {
            isRestored = false;
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
        // Make sure observer doesn't force show preview fragment
        if (isRestored) {
            isRestored = false;
        } else if (!(getCurrentFragment() instanceof PreviewFragment)) {
            PreviewFragment previewFragment = new PreviewFragment();

            // display preview and remove NewsResultListFragment
            displayFragment(previewFragment);
        }
    }

    private void displayArticleListFragment(String section) {
        NewsArticleListFragment fragment = NewsArticleListFragment.newInstance(Constants.TOP_STORIES,
                section);
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

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.mainFragmentFrameLayout);
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
                displaySelectedSection(mSpinner.getSelectedItemPosition());
            } else if (currentFragment instanceof PreviewFragment) {
                displayPreview();
            }
        }
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_SPINNER_POSITION, selectedPosition);
        super.onSaveInstanceState(outState);
    }
}
