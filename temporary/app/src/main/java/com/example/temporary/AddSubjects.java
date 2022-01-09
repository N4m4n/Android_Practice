package com.example.temporary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Time;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class AddSubjects extends AppCompatActivity {

    public ArrayList<String> TimeTable = new ArrayList<>();
    public String filename = "demofile.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subjects);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Intent i = new Intent(this,TimeTable.class);
        i.putStringArrayListExtra("ArrayOfSubjs",TimeTable);

    }

    public void fetchData(View view) throws IOException {



        EditText newAddedSub= findViewById(R.id.subject_field);
        String subj =newAddedSub.getText().toString();

        EditText newAddedStrt= findViewById(R.id.start_field);
        String strt =newAddedStrt.getText().toString();

        EditText newAddedEnd= findViewById(R.id.end_field);
        String end =newAddedEnd.getText().toString();

        Spinner newAddedDay = (Spinner) findViewById(R.id.dropDown);
        String text = newAddedDay.getSelectedItem().toString();

        String finalData = subj+"_"+strt+"_"+end+"_"+text;
        TimeTable.add(finalData);

        newAddedSub.setText("");
        newAddedStrt.setText("");
        newAddedEnd.setText("");
        newAddedDay.setSelection(0);
        for(int i = 0;i<TimeTable.size();i++){
            System.out.println(TimeTable.get(i));
        }






   }
}