/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;


import android.app.SearchManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import org.json.JSONException;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {


    private class EarthQuakeAsyn extends AsyncTask<Void, Void, ArrayList<Earthquake>>{

        @Override
        protected ArrayList<Earthquake> doInBackground(Void... voids) {
            try {
                return QueryUtils.makeRequest();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> arr){
            updateUI(arr);
        }
    }

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    public void updateUI(final ArrayList<Earthquake> arr){
        // Find a reference to the {@link ListView} in the layout
        final ListView earthquakeListView = (ListView) findViewById(R.id.list);

        EarthquakeAdapter adapter = new EarthquakeAdapter(this, arr);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                System.out.println(arr.get(position).getURL());
                intent.putExtra(SearchManager.QUERY, arr.get(position).getURL());
                startActivity(intent);

            }
        });


    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
//        ArrayList<Earthquake> temp = null;
//
//        try {
//            temp = QueryUtils.makeRequest();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        final ArrayList<Earthquake> earthquakes = temp;
//        if(earthquakes == null){
//            return;
//        }

        /**
         * Using Asyntask
         */
//        new EarthQuakeAsyn().execute();


        /**
         * Using AsyncTaskLoader.
         */

        LoaderManager.getInstance(this).initLoader(0,null, this);
//        The below one is deprecated.
//        getSupportLoaderManager().initLoader(0, null,  this);

    }

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int id, Bundle args) {
        return new EarthquakeLoader(this);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> data) {
        updateUI(data);

    }

    @Override
    public void onLoaderReset( Loader<ArrayList<Earthquake>> loader) {


    }


}
