package com.example.android.miwok;
import android.app.Activity;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import static android.view.View.GONE;

public class WordAdapter extends ArrayAdapter<Word> {
    private String colour;
    public WordAdapter(Activity context, ArrayList<Word> wordsList, String a) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, wordsList);
        this.colour = a;



    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final Word currentWrd = getItem(position);

        View listItemView = convertView;
        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        LinearLayout combined = (LinearLayout) listItemView.findViewById(R.id.combinedTranslation);
        combined.setBackgroundColor(Color.parseColor(colour));

        TextView miwokTranslation = (TextView) listItemView.findViewById(R.id.text1);

        miwokTranslation.setText(currentWrd.getMiwokTranslation());


        final TextView defaultTranslation = (TextView) listItemView.findViewById(R.id.text2);

        defaultTranslation.setText(currentWrd.getDefaultTranslation());

//        MY ALTERNATE APPROACH.

//        miwokTranslation.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
////                Toast.makeText(getContext(), "Translation for "+defaultTranslation.getText(),Toast.LENGTH_SHORT).show();
//                final MediaPlayer pronounciation = MediaPlayer.create(getContext(), currentWrd.getAudioId());
//                pronounciation.start();
//                pronounciation.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                    @Override
//                    public void onCompletion(MediaPlayer mediaPlayer) {
//                        Toast.makeText(getContext(), "Done playing", Toast.LENGTH_SHORT).show();
//                        System.out.println("Hellooooo");
//                        if(pronounciation!=null){
//                            pronounciation.release();
//
//                        }
//                    }
//                });
//
//            }
//        });

        if(currentWrd.isHasImage()) {

            ImageView img = (ImageView) listItemView.findViewById(R.id.recycledImage);
            img.setImageResource(currentWrd.getImageID());
        }else{
            ImageView img = (ImageView) listItemView.findViewById(R.id.recycledImage);
            img.setVisibility(GONE);
        }
        return listItemView;
    }




}
