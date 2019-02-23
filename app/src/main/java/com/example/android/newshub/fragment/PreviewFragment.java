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
import com.example.android.newshub.viewmodel.SelectedArticleViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class PreviewFragment extends Fragment {
    private static final String TAG = "PreviewFragment";

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

    private OnFullArticleButtonListener mCallback;
    private NewsArticle mNewsArticle;

    /**
     * Must be implemented by parent activity
     */
    public interface OnFullArticleButtonListener {
         void onFullArticleButtonClicked(NewsArticle article);
    }


    public PreviewFragment() {
        // Required empty public constructor
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
        SelectedArticleViewModel model = ViewModelProviders.of(getActivity()).get(SelectedArticleViewModel.class);
        model.getSelectedArticle().observe(this, article -> {
            mNewsArticle = article;
            updatePreview();
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
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
