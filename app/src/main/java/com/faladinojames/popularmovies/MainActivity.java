package com.faladinojames.popularmovies;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends PopularMovieActivity {

    String url_popularMovies="https://api.themoviedb.org/3/movie/popular?api_key=ce96e400b466b9caa393be6e7b6b1e2f&language=en-US&page=1";
    String url_highestRated="https://api.themoviedb.org/3/movie/top_rated?api_key=ce96e400b466b9caa393be6e7b6b1e2f&language=en-US&page=1";

    String currentUrl;
    @InjectView(R.id.mainRecycler)
    RecyclerView recycler;
    @InjectView(R.id.refresh)
    SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupWindowAnimations();

        recycler.setLayoutManager(new GridLayoutManager(this,3));
        String movies = manager.getLocalMovies();
        if(movies==null)
        {
            getMovies(url_popularMovies);
        }
        else{
           setUpAdapter();
        }

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(currentUrl==null)
                getMovies(url_popularMovies);
                else getMovies(url_highestRated);
            }
        });

    }

    private void setUpAdapter()
    {
        try {
            MovieAdapter movieAdapter = new MovieAdapter(this, new JSONObject(manager.getLocalMovies()));
            recycler.setAdapter(movieAdapter);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void getMovies(final String url)
    {

        refresh.setRefreshing(true);

        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... params) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                manager.writeToFile(response,MainActivity.this);
                                onPostExecute(response);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
// Add the request to the RequestQueue.
                queue.add(stringRequest);
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                if(s!=null) {
                    refresh.setRefreshing(false);
                    setUpAdapter();
                }
            }
        }.execute();

    }



    private void setupWindowAnimations() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide();
            slide.setDuration(1000);
            getWindow().setExitTransition(slide);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort) {
                sort();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void sort()
    {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setTitle("Sort by");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        arrayAdapter.add("Most popular");
        arrayAdapter.add("Most rated");


        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which)
                {
                    case 0:
                        currentUrl=url_popularMovies;
                        getMovies(url_popularMovies);
                        break;
                    case 1:
                        currentUrl=url_highestRated;
                        getMovies(url_highestRated);
                        break;
                }
            }
        });
        builderSingle.show();
    }
}
