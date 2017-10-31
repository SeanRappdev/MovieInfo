package com.example.seanreddy.movieinfo.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.seanreddy.movieinfo.R;
import com.example.seanreddy.movieinfo.adapter.MovieRecyclerViewAdapter;
import com.example.seanreddy.movieinfo.fragments.MovieDetailFragment;
import com.example.seanreddy.movieinfo.fragments.MovieListFragment;
import com.example.seanreddy.movieinfo.fragments.OnMovieSelectListiner;
import com.example.seanreddy.movieinfo.models.Results;

public class MainActivity extends AppCompatActivity implements OnMovieSelectListiner {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Check whether the Activity is using the layout verison with the fragment_container
        // FrameLayout and if so we must add the first fragment
        if (findViewById(R.id.fragment_container) != null){

            // However if we are being restored from a previous state, then we don't
            // need to do anything and should return or we could end up with overlapping Fragments
            if (savedInstanceState != null){
                return;
            }

            // Create an Instance of Fragment
            Fragment movieListFragment = new MovieListFragment();
            movieListFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,movieListFragment)
                    .commit();
        }

    }

    @Override
    public void OnSelectionChanged(Results result) {
        if(!getSupportActionBar().isShowing()){
            getSupportActionBar().show();
        }
        MovieDetailFragment detailFragment = (MovieDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.description_fragment);

        if (detailFragment != null){
            // If description is available, we are in two pane layout
            // so we call the method in DescriptionFragment to update its content
            detailFragment.setDescription(result);
        } else {
            MovieDetailFragment movieDetailFragment = MovieDetailFragment.newInstance(result);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the backStack so the User can navigate back
            fragmentTransaction.replace(R.id.fragment_container,movieDetailFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
