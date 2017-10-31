package com.example.seanreddy.movieinfo.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seanreddy.movieinfo.R;
import com.example.seanreddy.movieinfo.models.Results;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment {

    public static final String RESULTOBJ ="ResultObject" ;
    ImageView imageViewMovieFocusImage;
    TextView textViewTitle, textViewLanguage, textViewReleaseDate, textViewRaters, textViewRating, textViewOverview;
    Button buttonBack;
    private static final String BASEIMAGEURL = "https://image.tmdb.org/t/p/w500";
    private Results results;

    public MovieDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        // Inflate the layout for this fragment
        imageViewMovieFocusImage = (ImageView)view.findViewById(R.id.imageViewMovieFocusImage);
        textViewTitle = (TextView) view.findViewById(R.id.textViewMovieFocusTitle);
        textViewLanguage = (TextView) view.findViewById(R.id.textViewMovieFocusLanguage);
        textViewReleaseDate = (TextView) view.findViewById(R.id.textViewMovieFocusReleaseDate);
        textViewRaters = (TextView) view.findViewById(R.id.textViewMovieFocusRaters);
        textViewRating = (TextView) view.findViewById(R.id.textViewMovieFocusRating);
        textViewOverview = (TextView) view.findViewById(R.id.textViewMovieFocusOverview);
        buttonBack = (Button) view.findViewById(R.id.buttonBack);

        // setting back button listener
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // getting parcel object from the previous Activity
        Results movie =  getArguments().getParcelable(RESULTOBJ);
        setDescription(movie);

    }

    public void setDescription(Results movie) {
        // setting values for all the views
        if(movie != null){
            Picasso.with(imageViewMovieFocusImage.getContext()).load(BASEIMAGEURL+movie.getPoster_path()).
                    placeholder(R.drawable.default_movie).
                    error(R.drawable.default_movie).
                    into(imageViewMovieFocusImage);
            textViewTitle.setText(movie.getTitle());
            textViewLanguage.setText(String.format("Language: %s", movie.getOriginalLanguage().isEmpty() ? "Unknown" : movie.getOriginalLanguage()));
            textViewReleaseDate.setText(String.format("Release Date: %s", movie.getReleaseDate().isEmpty() ? "Unknown" : movie.getReleaseDate()));
            textViewRaters.setText(String.format("#Voters: %s", String.valueOf(movie.getVoteCount())));
            textViewRating.setText(String.format("Rating: %s", String.valueOf(movie.getPopularity())));
            textViewOverview.setText(movie.getOverview().isEmpty()?"Unknown" : movie.getOverview());
        }
    }

    public static MovieDetailFragment newInstance(Results result) {
        MovieDetailFragment fragment = new MovieDetailFragment ();
        Bundle args = new Bundle();
        args.putParcelable(MovieDetailFragment.RESULTOBJ,result);
        fragment.setArguments(args);
        return fragment;
    }
}
