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

public class PhrasesActivity extends AppCompatActivity {
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
        maudioManger = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        setContentView(R.layout.words_list);
        final ArrayList<Word> listOfColours = new ArrayList<>();
        listOfColours.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        listOfColours.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        listOfColours.add(new Word("My name is..", "oyaaset...", R.raw.phrase_my_name_is));
        listOfColours.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        listOfColours.add(new Word("I am feeling good", "kuchi achit", R.raw.phrase_im_feeling_good));
        listOfColours.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        listOfColours.add(new Word("I am coming", "әәnәm", R.raw.phrase_im_coming));
        listOfColours.add(new Word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        listOfColours.add(new Word("Let's go", "yoowutis", R.raw.phrase_lets_go));
        listOfColours.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));

        WordAdapter toShow = new WordAdapter(PhrasesActivity.this, listOfColours, "#16AFCA");
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
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getAudioId());

                    // Start the audio file
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            Toast.makeText(PhrasesActivity.this, "Done playing", Toast.LENGTH_SHORT).show();
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