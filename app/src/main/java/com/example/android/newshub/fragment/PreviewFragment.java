package com.example.android.newshub.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.newshub.R;
import com.example.android.newshub.model.entity.NewsArticle;
import com.example.android.newshub.viewmodel.FactoryViewModel;
import com.example.android.newshub.viewmodel.PreviewFragmentViewModel;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 *
 */
public class PreviewFragment extends Fragment {
    private static final String TAG = "PreviewFragment";

    private static final String ARG_ARTICLE_URL = "article_url";

    @BindView(R.id.articleTitleTextView)
    TextView mTitleTextView;
    @BindView(R.id.authorTextView)
    TextView mAuthorTextView;
    @BindView(R.id.previewImageView)
    ImageView mImageView;
    @BindView(R.id.abstractTextView)
    TextView mAbstractTextView;
    @BindView(R.id.fullArticleButton)
    Button mFullArticleBtn;

    @Inject
    public FactoryViewModel mFactoryViewModel;

    private OnFullArticleButtonListener mCallback;
    private NewsArticle mNewsArticle;
    private String mArticleUrl;

    /**
     * Must be implemented by parent activity
     */
    public interface OnFullArticleButtonListener {
         void onFullArticleButtonClicked(NewsArticle article);
    }


    public PreviewFragment() {
        // Required empty public constructor
    }

    public static PreviewFragment newInstance(String url) {
        PreviewFragment fragment = new PreviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ARTICLE_URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArticleUrl = getArguments().getString(ARG_ARTICLE_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_preview, container, false);
        ButterKnife.bind(this, rootView);
        mFullArticleBtn.setOnClickListener( view -> {
            mCallback.onFullArticleButtonClicked(mNewsArticle);
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Ensures the parent activity has implemented the necessary interfaces
        try {
            mCallback = (OnFullArticleButtonListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                + "must implement onFullArticleButtonListener");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AndroidSupportInjection.inject(this);
        getArticle();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    private void getArticle() {
        PreviewFragmentViewModel model = ViewModelProviders.of(this, mFactoryViewModel)
                .get(PreviewFragmentViewModel.class);
        model.init(mArticleUrl);
        model.getArticle().observe(this, article -> {
            if (article != null) {
                mNewsArticle = article;
                updatePreview();
            }
        });
    }

    private void updatePreview() {
        // display ui
        mTitleTextView.setText(mNewsArticle.headline);
        mAuthorTextView.setText(mNewsArticle.byline);
        mAbstractTextView.setText(mNewsArticle.snippet);
        mFullArticleBtn.setVisibility(View.VISIBLE);

        // Attempt to get imageUrl
        String imageUrl = mNewsArticle.imageUrl;

        // load image
        if (imageUrl != null && !imageUrl.equals("")) {
            Log.i(TAG, "imageUrl = " + imageUrl);
            Picasso.get()
                    .load(imageUrl)
                    .fit()
                    .centerCrop()
                    .error(R.drawable.nyt_error_image)
                    .into(mImageView);
        } else {
            // No valid images available
            Picasso.get()
                    .load(R.drawable.nyt_error_image)
                    .fit()
                    .centerCrop()
                    .into(mImageView);
        }

    }

}
