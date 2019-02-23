package com.example.android.newshub.remote;

import com.example.android.newshub.model.retrofit.ArticleSearchResponse;
import com.example.android.newshub.model.retrofit.TopStoriesResponse;
import com.example.android.newshub.utils.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NytApiService {
    @GET("search/v2/articlesearch.json")
    Call<ArticleSearchResponse> getArticlesBySearchTerm(@Query(Constants.API_KEY_PARAM) String apiKey,
                                                        @Query(Constants.QUERY_PARAM) String searchTerm,
                                                        @Query(Constants.SORT_PARAM) String sortOrder,
                                                        @Query(Constants.ARG_BEGIN_DATE) String beginDate,
                                                        @Query(Constants.ARG_END_DATE) String endDate);
    @GET("topstories/v2/{search_query}.json")
    Call<TopStoriesResponse> getTopStories(@Path("search_query") String searchTerm,
                                           @Query(Constants.API_KEY_PARAM) String apiKey,
                                           @Query(Constants.SORT_PARAM) String sortOrder);
}
