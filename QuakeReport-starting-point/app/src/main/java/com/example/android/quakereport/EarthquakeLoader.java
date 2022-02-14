package com.example.android.quakereport;

import android.content.Context;
import android.util.Log;

import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONException;

import java.net.MalformedURLException;
import java.util.ArrayList;


public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {

    public EarthquakeLoader(Context context) {
        super(context);
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {
        Log.e("loader", "Getting the data");
        try {
            return QueryUtils.makeRequest();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
