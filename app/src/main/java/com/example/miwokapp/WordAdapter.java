package com.example.miwokapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    private static final String LOG_TAG = WordAdapter.class.getSimpleName();

    private int mColorResourseId;

    public WordAdapter(Activity context, ArrayList<Word> words, int colorResourseId) {

        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        //Here the ArrayAdapter is the superclass of WordAdapter subclass.
        super(context, 0, words);
        mColorResourseId = colorResourseId;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        Word currentWord = getItem(position);

        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        TextView defaulttext = (TextView) listItemView.findViewById(R.id.default_text_view);

        defaulttext.setText(currentWord.getDefaultTranslation());

        TextView miwoktext = (TextView) listItemView.findViewById(R.id.second_text_view);

        miwoktext.setText(currentWord.getMiwokTranslation());

        ImageView image = (ImageView) listItemView.findViewById(R.id.image_view);

        if(currentWord.hasImage()) {

            image.setImageResource(currentWord.getImageResourseId());
            image.setVisibility(View.VISIBLE);
        }
        else{
            image.setVisibility(View.GONE);
        }


        //Set the theme color for list item
        View textContainer = listItemView.findViewById(R.id.text_container);

        //Find the color thet the resourse ID maps to :
        int color = ContextCompat.getColor(getContext(), mColorResourseId);

        //Set the background color of the text container View
        textContainer.setBackgroundColor(color);

        //return super.getView(position, convertView, parent);
        return listItemView;
    }
}
