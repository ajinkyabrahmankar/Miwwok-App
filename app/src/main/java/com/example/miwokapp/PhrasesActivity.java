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
//public class NumbersClickListner implements View.OnClickListener;

public class PhrasesActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        numberlist();

    }

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


    //Global creation to check the completion of audio file
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            //releaseMediaPlayer() is a function defined below in order to release the media resources
            releaseMediaPlayer();
        }
    };

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Open the list of numbers", Toast.LENGTH_SHORT).show();


    }

    public void numberlist(){
        final ArrayList<Word> words = new ArrayList<Word>();

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE) ;


        words.add(new Word("Where are you going","minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "oyaaset", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I'm feeling good", "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I am coming", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        words.add(new Word("I am coming", "әәnәm", R.raw.phrase_im_coming));
        words.add(new Word("Lets go", "yoowutis", R.raw.phrase_lets_go));
        words.add(new Word("Come here", "әnni'nem", R.raw.phrase_come_here));



        WordAdapter adapter = new WordAdapter(this, words, R.color.category_phrases);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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


                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getmAudioResourseId());

                    mMediaPlayer.start();

                    //read comments below(at last)

                    mMediaPlayer.setOnCompletionListener(onCompletionListener);
                }

            }
        });

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


