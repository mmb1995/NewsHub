package com.example.android.newshub.utils;

public class Constants {
    public static final String BASE_URL = "https://api.nytimes.com/svc/";

    private static final String TOP_STORIES_PARAM = "topstories/v2/%s.json?";

    private static final String ARTICLE_SEARCH_PARAM = "search/v2/articlesearch.json?";

    public static final String QUERY_PARAM = "q";

    public static final String ARG_BEGIN_DATE = "begin_date";

    public static final String ARG_END_DATE = "end_date";

    public static final String SORT_PARAM = "sort";

    public static final String SORT_VALUE = "newest";

    public static final String API_KEY_PARAM = "api-key";

    public static final String TOP_STORIES = "Top Stories";

    public static final String TOP_STORIES_HOME = "home";

    public static final String TOP_STORIES_SPORTS = "sports";

    public static final String TOP_STORIES_WORLD = "world";

    public static final String SEARCH_RESULTS = "Search Results";

    public static final String MEDIUM_IMAGE_KEY = "mediumThreeByTwo210";

    public static final String JUMBO_IMAGE_KEY = "superJumbo";

    public static final String THUMBNAIL_IMAGE_KEY = "thumbLarge";

    public static final int MAX_IMAGE_WIDTH = 3000;
}
