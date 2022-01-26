package com.example.android.quakereport;

import android.net.Uri;



public class Earthquake {
    private String place;
    private String date;
    private double magnitude;
    private String URL;
    Earthquake(String place, String date, double magnitude, String url){
        this.date = date;
        this.magnitude = magnitude;
        this.place = place;
        this.URL = url;
    }

    public double getMagnitude(){
        return this.magnitude;
    }
    public String getPlace(){
        return this.place;
    }
    public String getDate(){
        return this.date;
    }
    public String getURL(){return this.URL;}


}
