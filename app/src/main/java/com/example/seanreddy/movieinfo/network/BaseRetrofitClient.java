package com.example.seanreddy.movieinfo.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRetrofitClient {

    /**
     * Creates a retrofit client from an arbitrary class
     * @param endPoint REST endpoint url
     * @return retrofit service with defined endpoint
     */
    public static Retrofit.Builder createRetrofitService(final String endPoint) {
        final Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(endPoint)
                        .addConverterFactory(GsonConverterFactory.create());

        return builder;
    }


}
