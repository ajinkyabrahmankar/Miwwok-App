package com.example.miwokapp;

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
import java.util.concurrent.TimeUnit;
//public class NumbersClickListner implements View.OnClickListener;

public class NumbersActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        numberlist();

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Open the list of numbers", Toast.LENGTH_SHORT).show();


    }


    //Global creation to check the completion of audio file
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            //releaseMediaPlayer() is a function defined below in order to release the media resources
            releaseMediaPlayer();
        }
    };

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {

                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Pause playback
                        // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                        // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                        // our app is allowed to continue playing sound but at a lower volume. We'll treat
                        // both cases the same way because our app is playing short sound files.

                        // Pause playback and reset player to the start of the file. That way, we can
// play the word from the beginning when we resume playback.

                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    }

                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {

                        // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                        // Stop playback and clean up resources
                        releaseMediaPlayer();
                    }

                    else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Your app has been granted audio focus again
                        // Raise volume to normal, restart playback if necessary
                        mMediaPlayer.start();
                    }
                }
            };




    public void numberlist(){
        final ArrayList<Word> words = new ArrayList<Word>();

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE) ;

        words.add(new Word("one","lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo’e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na’aacha", R.drawable.number_ten, R.raw.number_ten));



        //LinearLayout rootView = (LinearLayout) findViewById((R.id.rootView));

        /*ArrayAdapter<Word> itemsAdapter = new ArrayAdapter<Word>(this ,R.layout.list_item ,words);*/

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(NumbersActivity.this, "List item clicked", Toast.LENGTH_SHORT).show();
                Word word = words.get(position);

                releaseMediaPlayer();



                // Request audio focus for playback
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                    //Start PlayBack


                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getmAudioResourseId());

                    mMediaPlayer.start();

                    //read comments below(at last)

                    mMediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });


        //GridView
        /*ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,words);
        GridView gridView = (GridView) findViewById(R.id.list);
        gridView.setAdapter(itemsAdapter);*/

    }

    @Override
    protected void onStop() {
        super.onStop();

        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {

        if(mMediaPlayer != null){

            mMediaPlayer.release();

            mMediaPlayer = null;


            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }

    }
}


/*HERE WE TRIED TO RELEASE THE MEDIAPLAYER TWO TIMES..ONES WHEN THE AUDIO FILE GETS COMPLETED AND
SECOND WHEN THE USER CLICKS ANOTHER FILE WHILE THE CURRENT IS GOING ON..THE SETONCOMPLETIONLISTENER IS FOR THE CASE WHEN
THE AUDIO FILE GETS COMPLETED ..FOR IT WE HAVE EVEN WRITTEN A OBJECT ONCOMPLETIONLISTERNER AT THE TOP I.E. DECLARED
GLOBALLY..FOR SECOND CASE WE SIMPLY CALL RELEASEMEDIAPLAYER() METHOD BEFORE STARTING NEW FILE SO THAT
THE PREVIOS SOUND STOPS PLAYING
 */



