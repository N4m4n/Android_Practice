package com.example.temporary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class TimeTable extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);
        ArrayList<String> allSubj = getIntent().getStringArrayListExtra("ArrayOfSubjs");
        TextView timetable = (TextView) findViewById(R.id.mainTimeTable);
        timetable.setText(allSubj.get(0));



    }
}