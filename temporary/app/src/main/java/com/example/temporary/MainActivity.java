package com.example.temporary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temp = getIntent().getStringExtra("ArrayOfSubjs");


    }

    public void addsubject(View view) {
        Intent subjectscreen = new Intent(this,AddSubjects.class);
        startActivity(subjectscreen);
        
        
    }

    public void showTimeTable(View view) {

        Intent hello = new Intent(this,TimeTable.class);

        startActivity(hello);

    }
}