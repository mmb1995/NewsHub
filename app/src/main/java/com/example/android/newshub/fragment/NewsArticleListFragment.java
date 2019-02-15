package com.example.android.newshub.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.newshub.R;
import com.example.android.newshub.adapter.NewsArticleAdapter;
import com.example.android.newshub.interfaces.ArticleClickListener;
import com.example.android.newshub.model.NewsArticle;
import com.example.android.newshub.utils.Constants;
import com.example.android.newshub.viewmodel.FactoryViewModel;
import com.example.android.newshub.viewmodel.NewsArticleViewModel;
import com.example.android.newshub.viewmodel.SelectedArticleViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link NewsArticleListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsArticleListFragment extends Fragment implements ArticleClickListener {
    private static final String TAG = "NewsArticleListFragment";

    private static final String ARG_TYPE = "type";
    private static final String ARG_QUERY = "query";

    @BindView(R.id.articleRecyclerView)
    RecyclerView mRecyclerView;

    @Inject
    public FactoryViewModel mFactoryViewModel;

    // ViewModel shared by multiple fragments for keeping track of the currently selected NewsArticle
    private SelectedArticleViewModel mSharedViewModel;
    private String mType;
    private String mQuery;
    private NewsArticleAdapter mAdapter;


    public NewsArticleListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param type mType.
     * @param query mQuery.
     * @return A new instance of fragment NewsArticleListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsArticleListFragment newInstance(String type, String query) {
        NewsArticleListFragment fragment = new NewsArticleListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        args.putString(ARG_QUERY, query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getString(ARG_TYPE);
            mQuery = getArguments().getString(ARG_QUERY);
            mSharedViewModel = ViewModelProviders.of(getActivity()).get(SelectedArticleViewModel.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news_article_list, container, false);
        ButterKnife.bind(this, rootView);
        mRecyclerView.setLayoutManager( new LinearLayoutManager(getContext()));
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AndroidSupportInjection.inject(this);
        getArticles();
    }


    private void getArticles() {
        NewsArticleViewModel model = ViewModelProviders.of(this, mFactoryViewModel)
                .get(NewsArticleViewModel.class);

        Log.i(TAG, "query = " + mQuery);
        // Check for the api to query
        if (this.mType.equals(Constants.TOP_STORIES)) {
            model.getTopStories(mQuery).observe(this, topStories -> {
                if (topStories != null) {
                    Log.i(TAG, "Received top stories");
                    mAdapter = new NewsArticleAdapter(getContext(), this, topStories);
                    mRecyclerView.setAdapter(mAdapter);
                }
            });
        } else {
            // Article Search API
            model.getArticleSearchResults(mQuery, null, null).observe(this,
                    searchResults -> {

                    });
        }

    }

    @Override
    public void onArticleClicked(int position) {
        NewsArticle article = mAdapter.getItemAtPosition(position);
        mSharedViewModel.selectArticle(article);
    }
}
