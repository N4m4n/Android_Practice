package com.example.android.miwok;

import android.media.Image;
import android.widget.ImageView;

public class Word {

    /** Default translation for the word */
    private String mDefaultTranslation;

    /** Miwok translation for the word */
    private String mMiwokTranslation;
    /*ID of the image in the app */
    private int ImageID;
    private int audioID;
    private boolean hasImage;
    private boolean hasAudio;
    /**
     * Create a new Word object.
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param miwokTranslation is the word in the Miwok language
     */
    public Word(String defaultTranslation, String miwokTranslation, int audID) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        audioID = audID;
        hasAudio = true;


    }

    public Word(String defaultTranslation, String miwokTranslation, int Id, int audID) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        ImageID = Id;
        hasImage = true;
        hasAudio = true;
        audioID = audID;
    }


    /**
     * Get the default translation of the word.
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /**
     * Get the Miwok translation of the word.
     */

    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }
    public int getImageID(){ return ImageID;}
    public boolean isHasImage(){
        return hasImage;
    }
    public boolean isHasAudio(){ return  hasAudio;}
    public int getAudioId(){return audioID;}
}