package com.faladinojames.popularmovies;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Falade James on 4/13/2017 All Rights Reserved.
 */

public class Movie {

    JSONObject json;
    public Movie(JSONObject jsonObject)
    {
        json=jsonObject;
    }


    public String getPoster()
    {
        String url=null;
        try {
            url = Constants.IMAGE_BASE_URL + Constants.IMAGE_SMALL_SIZE + json.getString("poster_path");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return url;
    }
    public String getTitle()
    {
        try{
            return json.getString("original_title");
        }
        catch (JSONException e)
        {
            return null;
        }
    }
    public String getSynopsis()
    {
        try{
            return json.getString("overview");
        }
        catch (JSONException e)
        {
            return null;
        }
    }
    public String getReleaseDate()
    {
        try{
            return json.getString("release_date");
        }
        catch (JSONException e)
        {
            return null;
        }
    }

    public float getRating()
    {
        try{
            return Float.valueOf(String.valueOf(json.getDouble("vote_average")));
        }
        catch (JSONException e)
        {
            return 0;
        }
    }


}
