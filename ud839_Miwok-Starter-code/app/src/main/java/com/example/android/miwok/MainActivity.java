
package com.example.android.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);


        TextView number = (TextView) findViewById(R.id.numbers);

// Set a click listener on that View
        number.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent numbersIntent = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(numbersIntent);
            }

        });




        TextView family = (TextView) findViewById(R.id.family);

// Set a click listener on that View
        family.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent familyList = new Intent(MainActivity.this, FamilyActivity.class);
                startActivity(familyList);
            }
        });





        TextView phrases = (TextView) findViewById(R.id.phrases);

// Set a click listener on that View
        phrases.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent phraseIntent = new Intent(MainActivity.this, PhrasesActivity.class);
                startActivity(phraseIntent);
            }
        });






        TextView colour = (TextView) findViewById(R.id.colors);

// Set a click listener on that View
        colour.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent coloursIntent = new Intent(MainActivity.this, ColoursActivity.class);
                startActivity(coloursIntent);
            }
        });










    }


//    public void openNumberList(View view) {
//        Intent numberList = new Intent(this, NumbersActivity.class);
//        startActivity(numberList);
//    }

//    public void openFamilyList(View view) {
//        Intent familyList = new Intent(this, FamilyActivity.class);
//        startActivity(familyList);
//    }

//    public void openColoursList(View view) {
//        Intent colorList = new Intent(this, ColoursActivity.class);
//        startActivity(colorList);
//    }

//    public void openPhrasesList(View view) {
//        Intent phrasesList = new Intent(this, PhrasesActivity.class);
//        startActivity(phrasesList);
//    }
}
