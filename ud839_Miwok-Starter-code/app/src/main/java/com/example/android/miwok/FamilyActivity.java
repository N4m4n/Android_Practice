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

public class FamilyActivity extends AppCompatActivity {
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
        maudioManger = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> listOfColours = new ArrayList<>();
        listOfColours.add(new Word("father", "әpә", R.drawable.family_father, R.raw.family_father));
        listOfColours.add(new Word("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        listOfColours.add(new Word("son", "angsi", R.drawable.family_son, R.raw.family_son));
        listOfColours.add(new Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        listOfColours.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        listOfColours.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        listOfColours.add(new Word("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        listOfColours.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        listOfColours.add(new Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        listOfColours.add(new Word("grandfather.", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        WordAdapter toShow = new WordAdapter(this, listOfColours, "#379237");
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
                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getAudioId());

                    // Start the audio file
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            Toast.makeText(FamilyActivity.this, "Done playing", Toast.LENGTH_SHORT).show();
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