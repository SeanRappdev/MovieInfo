package com.example.seanreddy.movieinfo.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Class to intercept request for adding query parameters*/
public class MovieSearchInterceptor implements Interceptor {
    String searchQuery;
    int pageNo;
    private final boolean isAdult = false;
    private static final  String APIKEY= "9b26e6efc3cac82c6aafef1bbe04a493";
    private static final String LANGUAGE= "en-US";
    public MovieSearchInterceptor(String searchQuery, int pageNo) {
        this.searchQuery = searchQuery;
        this.pageNo = pageNo;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl url = originalRequest.url();
        HttpUrl modifiedUrl = url.newBuilder().addQueryParameter("api_key",APIKEY)
                .addQueryParameter("language",LANGUAGE)
                .addQueryParameter("query",searchQuery)
                .addQueryParameter("page", String.valueOf(pageNo))
                .addQueryParameter("include_adult", String.valueOf(isAdult)).build();
        Request.Builder reqBuilder = originalRequest.newBuilder().url(modifiedUrl);

        return chain.proceed(reqBuilder.build());
    }
}
