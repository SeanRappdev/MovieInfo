package com.example.seanreddy.movieinfo.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seanreddy.movieinfo.R;
import com.example.seanreddy.movieinfo.models.Results;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieRecyclerHolder> {

    List<Results> movieResults;
    private OnItemClickHandler onItemClickHandler;
    private static final String BASEIMAGEURL = "https://image.tmdb.org/t/p/w92";
    public MovieRecyclerViewAdapter(List<Results> movieResults,OnItemClickHandler onItemClickHandler) {
    if(movieResults !=  null){
        this.movieResults = movieResults;
    }else {
        this.movieResults =  new ArrayList<>();
    }
    this.onItemClickHandler = onItemClickHandler;
    }

    @Override
    public MovieRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list,parent,false);
        return new MovieRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieRecyclerHolder holder, int position) {
        final Results result = movieResults.get(position);
        holder.title.setText(result.getTitle());
        holder.language.setText(String.format("lang :%s", result.getOriginalLanguage()));
        holder.releaseDate.setText(String.format("release :%s", result.getReleaseDate()));
        Picasso.with(holder.thumbNail.getContext()).load(BASEIMAGEURL+result.getPoster_path()).
                placeholder(R.drawable.default_image).
                error(R.drawable.default_image).
                into(holder.thumbNail);
        holder.movieListItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickHandler.onItemClickedOnList(result);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieResults.size() ;
    }

    /**Method to handle the movie data and notifying the adapter
     * @param results list of movie Data
     * @param newPage boolean to handle data for new of appending the list
     * */
    public void notifyNewDataList(List<Results> results, boolean newPage) {
        if(newPage){
            movieResults.clear();
        }
        if(results !=  null) {
            int size = movieResults.size()-1;
            movieResults.addAll(results);
            notifyItemRangeChanged(size,movieResults.size()-1);
        }
    }

    public class MovieRecyclerHolder extends RecyclerView.ViewHolder {
        ImageView thumbNail;
        TextView title, language,releaseDate;
        CardView movieListItemView;
        public MovieRecyclerHolder(View itemView) {
            super(itemView);
            movieListItemView = itemView.findViewById(R.id.movie_list_item_view);
            thumbNail = itemView.findViewById(R.id.movie_thumbnail);
            title = itemView.findViewById(R.id.movie_name);
            language = itemView.findViewById(R.id.movie_language);
            releaseDate = itemView.findViewById(R.id.moview_releaseDate);
        }
    }

    /**
     * Interface to listen click actions, implements on calling activity
     * */
    public interface OnItemClickHandler{
        void onItemClickedOnList(Results result);
    }
}
