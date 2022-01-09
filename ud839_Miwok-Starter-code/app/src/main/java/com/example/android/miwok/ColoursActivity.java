package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;

public class ColoursActivity extends AppCompatActivity {
    private MediaPlayer mMediaPlayer;
    private AudioManager maudioManger;
    private AudioManager.OnAudioFocusChangeListener afListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }else if( focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
        }
    };

    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
            maudioManger.abandonAudioFocus(afListener);


        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        releaseMediaPlayer();
        super.onStop();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);
        final ArrayList<Word> listOfColours = new ArrayList<>();
        maudioManger = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        listOfColours.add(new Word("red", "wetetti", R.drawable.color_red, R.raw.color_red));
        listOfColours.add(new Word("green", "chkokki", R.drawable.color_green, R.raw.color_green));
        listOfColours.add(new Word("brown", "takakki", R.drawable.color_brown, R.raw.color_brown));
        listOfColours.add(new Word("gray", "tapoppi", R.drawable.color_gray, R.raw.color_gray));
        listOfColours.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        listOfColours.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        listOfColours.add(new Word("dusty yellow", "toppise", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        listOfColours.add(new Word("mustard yellow", "chiwitta", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        WordAdapter toShow = new WordAdapter(this, listOfColours, "#8800A0");
        ListView listv = (ListView) findViewById(R.id.list);
        listv.setAdapter(toShow);

        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                // Get the {@link Word} object at the given position the user clicked on
                Word word = listOfColours.get(position);

                int result = maudioManger.requestAudioFocus(afListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

                if(result ==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                    // We have audio focus now.

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mMediaPlayer = MediaPlayer.create(ColoursActivity.this, word.getAudioId());

                    // Start the audio file
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            Toast.makeText(ColoursActivity.this, "Done playing", Toast.LENGTH_SHORT).show();
                            System.out.println("Hellooooo");
                            if (mMediaPlayer != null) {
                                mMediaPlayer.release();


                            }
                        }
                    });
                }

            }
        });

    }
}