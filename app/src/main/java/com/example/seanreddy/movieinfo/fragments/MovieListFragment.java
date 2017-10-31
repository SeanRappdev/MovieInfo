package com.example.seanreddy.movieinfo.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.seanreddy.movieinfo.R;
import com.example.seanreddy.movieinfo.adapter.MovieRecyclerViewAdapter;
import com.example.seanreddy.movieinfo.models.MovieDataBase;
import com.example.seanreddy.movieinfo.models.Results;
import com.example.seanreddy.movieinfo.network.BaseRetrofitClient;
import com.example.seanreddy.movieinfo.network.MovieDataBaseService;
import com.example.seanreddy.movieinfo.network.MovieSearchInterceptor;
import com.example.seanreddy.movieinfo.util.EndlessRecyclerOnScrollListener;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieListFragment extends Fragment implements MovieRecyclerViewAdapter.OnItemClickHandler {

    private RecyclerView movieRecyclerView;
    MovieRecyclerViewAdapter movieAdapter;
    LinearLayoutManager layoutManager;
    private ArrayList<Results> moviesList;
    private SearchView  searchView;
    int pageNo = 1;

    public MovieListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        searchView =  view.findViewById(R.id.movie_search_view);
        fetchMovieListNetworkMethod("games",true);
        movieRecyclerView = (RecyclerView)view.findViewById(R.id.movie_list_recyclerView);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        movieRecyclerView.setLayoutManager(layoutManager);
        movieRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        movieAdapter =  new MovieRecyclerViewAdapter(new ArrayList<Results>(),this);
        movieRecyclerView.setAdapter(movieAdapter);
        movieRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                Log.d("scrollpostion",movieRecyclerView.getLayoutManager().getItemCount()+
                        " "+layoutManager.findLastVisibleItemPosition()+" " +moviesList.size());
                if(movieRecyclerView.getLayoutManager().getItemCount() == layoutManager.findLastVisibleItemPosition()+6) {
                    if(moviesList!= null){
                        Toast.makeText(getContext(),"Lodaing Data",
                                Toast.LENGTH_SHORT).show();
                        fetchMovieListNetworkMethod("games", false);
                    }
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query != null && !(query.isEmpty())){
                    fetchMovieListNetworkMethod(query,true);
                }else {
                    Snackbar.make(view.findViewById(R.id.main_coordinator_layout),"please enter valid text",
                            Snackbar.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;
    }

    /** Make the calls to fetch movie Results from the Movie db
     * @param query search query for movie list
     * @param newPage search criteria for loading more or data or start new search
     * */
    private void fetchMovieListNetworkMethod(@NonNull String query, @NonNull final boolean newPage) {
        if(moviesList == null){
            moviesList= new ArrayList<Results>();
        }
        if(newPage){
            pageNo =1;
        }else {
            pageNo++;
        }
        MovieSearchInterceptor interceptor = new MovieSearchInterceptor(query, pageNo);
        Retrofit.Builder builder = BaseRetrofitClient.createRetrofitService(MovieDataBaseService.SERVICE_ENDPOINT);
        Retrofit retrofit = builder.client(new OkHttpClient.Builder().addInterceptor(interceptor).build()).build();
        MovieDataBaseService fetchMovieData =retrofit.create(MovieDataBaseService.class);
        Call<MovieDataBase> call = fetchMovieData.getMovieList();

        call.enqueue(new Callback<MovieDataBase>() {
            @Override
            public void onResponse(Call<MovieDataBase> call, Response<MovieDataBase> response) {
                if(response.isSuccessful()){
                    if(newPage){
                        moviesList.clear();
                    }
                    moviesList.addAll(response.body().getResults());
                    movieAdapter.notifyNewDataList(moviesList,newPage);
                }
            }

            @Override
            public void onFailure(Call<MovieDataBase> call, Throwable t) {
                Toast.makeText(getContext(),"coudn't load data please try again",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemClickedOnList(Results result) {
        OnMovieSelectListiner listener = (OnMovieSelectListiner) getActivity();
        listener.OnSelectionChanged(result);
    }

}
