package com.faladinojames.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by Falade James on 4/13/2017 All Rights Reserved.
 */

public class PopularMovieActivity extends AppCompatActivity {
    Manager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager = new Manager(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {finish(); return true;} else
        return super.onOptionsItemSelected(item);
    }
}
