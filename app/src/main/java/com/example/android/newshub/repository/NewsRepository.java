package com.example.android.newshub.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.newshub.BuildConfig;
import com.example.android.newshub.database.NewsDao;
import com.example.android.newshub.model.Resource;
import com.example.android.newshub.model.entity.NewsArticle;
import com.example.android.newshub.model.retrofit.Multimedia;
import com.example.android.newshub.model.retrofit.TopStoriesArticle;
import com.example.android.newshub.model.retrofit.TopStoriesResponse;
import com.example.android.newshub.remote.NytApiService;
import com.example.android.newshub.utils.Constants;
import com.example.android.newshub.utils.RateLimiter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;

@Singleton
public class NewsRepository {
    private static final String TAG = "NewsRepository";

    private final NytApiService mNewsApiService;
    private final NewsDao mNewsDao;

    private RateLimiter newsRateLimit = new RateLimiter<String>(10, TimeUnit.MINUTES);

    @Inject
    public NewsRepository(NytApiService service, NewsDao dao) {
        this.mNewsApiService = service;
        this.mNewsDao = dao;
    }

    /**
     * Returns the top stories related to the given section. This method checks if fresh local date is
     * available and if not it fetches new data from the relevant api
     * @param section section of the nyt top stories api to query
     * @return
     */
    public LiveData<Resource<List<NewsArticle>>> getTopStories(final String section) {
        return new NetworkBoundResource<List<NewsArticle>, TopStoriesResponse>() {

            @Override
            protected boolean shouldFetch(@Nullable List<NewsArticle> data) {
                return data == null || data.size() == 0 || newsRateLimit.shouldFetch(section);
            }

            @Override
            protected void saveCallResult(@NonNull TopStoriesResponse response) {
                Log.i(TAG, "response = " + response.getTopStories());
                mNewsDao.deleteOldArticles(section);
                List<NewsArticle> articles = processResponse(response.getTopStories());
                mNewsDao.saveArticles(articles);
            }

            @NonNull
            @Override
            protected LiveData<List<NewsArticle>> loadFromDb() {
                Log.i(TAG, "loading from database");
                return mNewsDao.getArticlesBySection(section);
            }

            @NonNull
            @Override
            protected Call<TopStoriesResponse> createCall() {
                return mNewsApiService.getTopStories(section, BuildConfig.NYT_API_KEY, Constants.SORT_VALUE);
            }

            @Override
            protected void onFetchFailed() {
                newsRateLimit.reset(section);
            }

            /**
             * Parses the retrofit response and converts the items to their database counterpart
             *
             * @param topStories
             * @return
             */
            protected List<NewsArticle> processResponse(List<TopStoriesArticle> topStories) {
                List<NewsArticle> articles = new ArrayList<NewsArticle>();

                for (TopStoriesArticle topStory : topStories) {
                    if (topStory.getUrl() != null) {
                        NewsArticle article = new NewsArticle();
                        article.url = topStory.getUrl();
                        article.headline = topStory.getTitle();
                        article.snippet = topStory.get_abstract();
                        article.byline = topStory.getByline();
                        article.section = section;
                        article.date = topStory.getPublishedDate();

                        //date handling
                        String pubDateString = topStory.getPublishedDate();
                        long publishTime;
                        try {
                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                            article.date = formatter.format(formatter.parse(topStory.getPublishedDate()));
                        } catch (ParseException e) {
                            article.date = "";
                        }
                        article.imageUrl = extractImageUrl(topStory);
                        articles.add(article);
                    }
                }
                return articles;
            }

            /**
             * Attempts to return the largest imageUrl associated with the article
             *
             * @param topStory
             * @return
             */
            protected String extractImageUrl(TopStoriesArticle topStory) {
                String imageUrl = "";
                for (Multimedia media : topStory.getMultimedia()) {
                    if (media.format.equals(Constants.JUMBO_IMAGE_KEY)) {
                        return media.url;
                    } else if (media.format.equals(Constants.MEDIUM_IMAGE_KEY)) {
                        imageUrl = media.url;
                    }
                }
                return imageUrl;
            }
        }.getAsLiveData();
    }

    public LiveData<NewsArticle> getArticle(String url) {
        return mNewsDao.getArticle(url);
    }

}
