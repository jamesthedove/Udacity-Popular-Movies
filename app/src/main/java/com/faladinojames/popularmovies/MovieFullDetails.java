package com.faladinojames.popularmovies;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MovieFullDetails extends PopularMovieActivity {

    Movie movie;
    @InjectView(R.id.releaseDate)
    TextView releaseDate;
    @InjectView(R.id.ratingBar)
    RatingBar ratingBar;
    @InjectView(R.id.tvSynopsis)
    TextView synopsis;
    @InjectView(R.id.ivPoster)
    ImageView poster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_full_details);
        ButterKnife.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setupWindowAnimations();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try {
            movie = new Movie(new JSONObject(getIntent().getStringExtra("movie")));
            setTitle(movie.getTitle());
            ratingBar.setRating(movie.getRating());
            synopsis.setText(movie.getSynopsis());
            releaseDate.setText(movie.getReleaseDate());
            Picasso.with(this).load(movie.getPoster()).into(poster);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }


    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setDuration(1000);
            getWindow().setEnterTransition(fade);
        }
    }
}
