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


import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import org.json.JSONException;
import org.w3c.dom.Text;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

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
        TextView emptyText = (TextView) findViewById(R.id.empty_view);
        emptyText.setText("No earthquakes found");
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
        ProgressBar loadingBr = (ProgressBar) findViewById(R.id.loadingBar);
        loadingBr.setVisibility(View.GONE);


    }

    /**Creating the menus bar on top of screen (three dots)
     *
     * @param menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**Action to perform on clicking the bar on top of screen (three dots)
     *
     * @param item
     * @return boolean
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        Log.e(LOG_TAG, "Hello");
        TextView emptyText = (TextView) findViewById(R.id.empty_view);
        ListView list = (ListView) findViewById(R.id.list);
        list.setEmptyView(emptyText);








        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint({"NewApi", "LocalSuppress"}) Network netwrk = connMgr.getActiveNetwork();
        NetworkCapabilities caps = connMgr.getNetworkCapabilities(netwrk);
        if(caps!=null && (caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))){
            LoaderManager.getInstance(this).initLoader(0,null, this);
        }else{
            emptyText.setText("No internet connection!");
            ProgressBar lod = (ProgressBar) findViewById(R.id.loadingBar);
            lod.setVisibility(View.GONE);
        }



        // Get details on the currently active default data network
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
//        if (networkInfo != null && networkInfo.isConnected()) {
//
//            LoaderManager.getInstance(this).initLoader(0,null, this);
//        }else{
//            emptyText.setText("No internet connection!");
//            ProgressBar lod = (ProgressBar) findViewById(R.id.loadingBar);
//            lod.setVisibility(View.GONE);
//        }

//        The below one is deprecated.
//        getSupportLoaderManager().initLoader(0, null,  this);















    }

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int id, Bundle args) {
        Log.e(LOG_TAG, "Created a manager");
        return new EarthquakeLoader(this);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> data) {
        Log.e(LOG_TAG, "Finished getting the data");

        /**To clear the lis tto test the empty state**/

//        data.clear();

        updateUI(data);

    }

    @Override
    public void onLoaderReset( Loader<ArrayList<Earthquake>> loader) {
        Log.e(LOG_TAG, "Reset the manager.");

    }


}
