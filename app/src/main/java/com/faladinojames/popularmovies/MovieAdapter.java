package com.faladinojames.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Falade James on 4/13/2017 All Rights Reserved.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Activity context;
    public MovieAdapter(Activity context,JSONObject json)
    {
        this.context=context;
        try {
            this.movies = json.getJSONArray("results");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    JSONArray movies;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(context.getLayoutInflater().inflate(R.layout.movie_grid_item, parent, false), new OnMovieItemClick() {

            @Override
            public void onMovieItemClick(View v, int position) {

                try {
                    context.startActivity(new Intent(context, MovieFullDetails.class).putExtra("movie", movies.getJSONObject(position).toString()));
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        try {
            Movie movie = new Movie(movies.getJSONObject(position));
            Picasso.with(context).load(movie.getPoster()).into(holder.poster);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return movies.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        @InjectView(R.id.movie_poster)
        ImageView poster;
        OnMovieItemClick onMovieItemClick;
        public ViewHolder(View view,OnMovieItemClick onMovieItemClick)
        {
            super(view);
            this.onMovieItemClick=onMovieItemClick;
            view.setOnClickListener(this);
            ButterKnife.inject(this,view);

        }

        @Override
        public void onClick(View v) {
            onMovieItemClick.onMovieItemClick(v,getAdapterPosition());
        }
    }

    interface OnMovieItemClick{
        void onMovieItemClick(View v,int position);
    }
}

