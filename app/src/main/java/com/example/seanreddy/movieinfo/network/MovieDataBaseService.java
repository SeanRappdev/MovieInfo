package com.example.seanreddy.movieinfo.network;
import com.example.seanreddy.movieinfo.models.MovieDataBase;

import retrofit2.Call;
import retrofit2.http.GET;

/* Retrofit interface to get calls */
public interface MovieDataBaseService {
    String SERVICE_ENDPOINT = "https://api.themoviedb.org";
    @GET("3/search/movie")
    Call<MovieDataBase> getMovieList();
}
