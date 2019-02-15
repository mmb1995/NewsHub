package com.example.android.newshub.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.newshub.R;
import com.example.android.newshub.interfaces.ArticleClickListener;
import com.example.android.newshub.model.NewsArticle;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsArticleAdapter extends  RecyclerView.Adapter<NewsArticleAdapter.NewsArticleViewHolder> {
    private static final String TAG = "NewsArticleAdapter";

    private Context mContext;
    private List<NewsArticle> mNewsArticles;
    private final ArticleClickListener mClickListener;

    public NewsArticleAdapter(Context context, ArticleClickListener clickListener,
                              List<NewsArticle> articles) {
        mContext = context;
        mClickListener = clickListener;
        mNewsArticles = articles;
    }

    @NonNull
    @Override
    public NewsArticleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View rootView = inflater.inflate(R.layout.item_article_card, viewGroup, false);
        return new NewsArticleViewHolder(rootView, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsArticleViewHolder holder, int position) {
        NewsArticle currentArticle = mNewsArticles.get(position);
        holder.mTitleTextView.setText(currentArticle.headline);
        holder.mAbstractTextView.setText(currentArticle.snippet);
        holder.mSectionTextView.setText("Politics");

        // load image
        Picasso.get()
                .load(currentArticle.imageUrl)
                .into(holder.mArticleImageView);

    }

    @Override
    public int getItemCount() {
        return (mNewsArticles != null ? mNewsArticles.size(): 0);
    }

    public NewsArticle getItemAtPosition(int position) {
        return (mNewsArticles != null ? mNewsArticles.get(position): null);
    }

    public class NewsArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.titleTextView)
        TextView mTitleTextView;

        @BindView(R.id.articleImageView)
        ImageView mArticleImageView;

        @BindView(R.id.sectionTextView)
        TextView mSectionTextView;

        @BindView(R.id.abstractTextView)
        TextView mAbstractTextView;

        private final ArticleClickListener mListner;

        public NewsArticleViewHolder(@NonNull View itemView, ArticleClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mListner = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListner.onArticleClicked(getLayoutPosition());
        }
    }
}
