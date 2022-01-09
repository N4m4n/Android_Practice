package com.example.courtcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int scoreA = 0;
    int scoreB = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addthreeA(View view) {
        scoreA+=3;
        displayA(scoreA);
    }

    public void addtwoA(View view) {
        scoreA +=2;
        displayA(scoreA);

    }

    public void addoneA(View view) {
        scoreA+=1;
        displayA(scoreA);
    }

    public void resetA(View view) {
        scoreA=0;
        displayA(scoreA);
    }
    public void displayA(int a){
        TextView teamAscore = (TextView) findViewById(R.id.teamAscore);
        teamAscore.setText(String.valueOf(a));
    }
    public void addthreeB(View view) {
        scoreB+=3;
        displayB(scoreB);
    }

    public void addtwoB(View view) {
        scoreB +=2;
        displayB(scoreB);

    }

    public void addoneB(View view) {
        scoreB+=1;
        displayB(scoreB);
    }

    public void resetB(View view) {
        scoreB=0;
        displayB(scoreB);
    }
    public void displayB(int b){
        TextView teamBscore = (TextView) findViewById(R.id.teamBscore);
        teamBscore.setText(String.valueOf(b));
    }
}