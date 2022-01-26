package com.example.android.quakereport;

import android.net.Uri;
import android.renderscript.ScriptGroup;
import android.util.EventLogTags;
import android.util.Log;
import android.view.LayoutInflater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {


    private static String LOG_TAG = "QueryUtils";
    /** Sample JSON response for a USGS query */
    private static final String QUERY_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=5&limit=20";

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Returns a string from the input stream passed as parameter.
     */
    public static String makeStringResponse(InputStream inputStream) throws IOException{
        StringBuilder out = new StringBuilder();
        if(inputStream != null){
            InputStreamReader isReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isReader);
            String line = br.readLine();
            while (line != null){
                out.append(line);
                line = br.readLine();
            }

        }
        return out.toString();
    }


    /**
     * Makes the HTTP request to the website to get the JsonObject.
     */
    public static ArrayList<Earthquake> makeRequest() throws MalformedURLException, JSONException {
        String response = "";
        URL url = new URL(QUERY_URL);
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try{
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(10000);
            connection.setRequestMethod("GET");
            connection.connect();
            if(connection.getResponseCode() == 200){
                inputStream = connection.getInputStream();
                response = makeStringResponse(inputStream);

            }else{
                Log.e(LOG_TAG, "Error code: " +connection.getResponseCode());
                return null;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        JSONObject infoRecd = new JSONObject(response);

        return extractEarthquakes(infoRecd);
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Earthquake> extractEarthquakes(JSONObject SAMPLE_JSON_RESPONSE) {
        if(SAMPLE_JSON_RESPONSE == null){
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject allData = SAMPLE_JSON_RESPONSE;
            JSONArray feature = allData.getJSONArray("features");
            for(int i = 0;i<feature.length();i++){
                JSONObject currEarthquake = feature.getJSONObject(i).getJSONObject("properties");
                int mag = currEarthquake.getInt("mag");
                long timeinMilli = currEarthquake.getInt("time");
                Date dateObject = new Date(timeinMilli);
                String time = dateFormatter.format(dateObject);

                String place = currEarthquake.getString("place");
                String url = currEarthquake.getString("url");
                earthquakes.add(new Earthquake(place,time, mag,url));

            }




        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            System.out.println("something wrong");
        }

        // Return the list of earthquakes
        return earthquakes;
    }

}