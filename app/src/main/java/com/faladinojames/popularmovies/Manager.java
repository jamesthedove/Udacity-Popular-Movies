package com.faladinojames.popularmovies;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Falade James on 4/13/2017 All Rights Reserved.
 */

public class Manager {


    Context context;
    public Manager(Context context)
    {
        this.context=context;
    }

    void writeToFile(String data,Context context) {
        try {
            JSONObject jsonObject= new JSONObject(data);
            //new Manager(context).storeNextPageToken(jsonObject.getString("nextPageToken"));

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("movies.json", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException |JSONException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    public String getLocalMovies()
    {


        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("movies.json");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();

                return ret;


            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;



    }


}
