package com.benoitlefevre.moodtracker.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.benoitlefevre.moodtracker.R;
import com.benoitlefevre.moodtracker.model.Mood;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MoodActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImageView;
    private ImageButton mCommentaryButton;
    private ImageButton mHistoricalButton;
    private ConstraintLayout mLayout;
    private SharedPreferences mPreferences;
    private Date mDate;
    private Mood[] mMoods;
    private Mood mCurrentMood;
    private Gson mGson;
    private String today;
    private GestureDetectorCompat mDetector;
    private MediaPlayer mPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        initActivity();
        mMoods = createMoods();
        initMood();

        mCommentaryButton.setTag(0);
        mCommentaryButton.setOnClickListener(this);

        mHistoricalButton.setTag(1);
        mHistoricalButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int view = (int) v.getTag();
        Intent intent;
        switch (view){
            case 0:
                createDialog();
                break;
            case 1:
                intent = new Intent(MoodActivity.this,HistoryActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     *Initializes all the fields of the activity
     */
    public void initActivity(){
        mPreferences = getSharedPreferences("MoodSave",MODE_PRIVATE);
        mLayout = findViewById(R.id.background);
        mImageView = findViewById(R.id.MoodActivity_smiley_img);
        mCommentaryButton = findViewById(R.id.MoodActivity_commentary_btn);
        mHistoricalButton = findViewById(R.id.MoodActivity_history_btn);
        mDate = new Date();
        mGson = new Gson();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E-w",Locale.getDefault());
        today = dateFormat.format(mDate);
        mDetector = new GestureDetectorCompat(this,new MyGestureListener());
        mPlayer = null;

    }

    /**
     * Generate 5 different Mood objects that are displayed when user swipes
     * @return  An array containing the 5 moods
     */
    public Mood[] createMoods(){
        Mood mSad = new Mood(0,R.drawable.smiley_sad,R.color.faded_red,R.raw.e1,null,mDate);
        Mood mDisappointed = new Mood(1, R.drawable.smiley_disappointed,R.color.warm_grey,R.raw.g1,null,mDate);
        Mood mSimple = new Mood(2,R.drawable.smiley_normal,R.color.cornflower_blue_65,R.raw.d1,null,mDate);
        Mood mHappy = new Mood(3,R.drawable.smiley_happy,R.color.light_sage,R.raw.b1,null,mDate);
        Mood mSuperHappy = new Mood(4, R.drawable.smiley_super_happy,R.color.banana_yellow,R.raw.a1,null,mDate);
        return new Mood[]{mSad,mDisappointed,mSimple,mHappy,mSuperHappy};
    }

    /**
     * Modifies the background and the image according to the Mood object that is selected
     */
    public void changeMood(){
        mLayout.setBackgroundResource(mCurrentMood.getColor());
        mImageView.setImageResource(mCurrentMood.getImage());
    }

    /**
     * Initializes the first Mood object that is displayed on screen and verifies if a mood with the same key is already
     * save in SharedPreferences
     */
    public void initMood(){
        String json = mPreferences.getString(today,null);
        if(json != null)
            mCurrentMood = mGson.fromJson(json,Mood.class);
        else
            mCurrentMood = mMoods[2];
        changeMood();
    }

    /**
     * Creates an AlertDialog where the user can save a comment. The comment is put in the field mCommentary of the current Mood that
     * is save in SharedPreferences.
     */
    public void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MoodActivity.this);
        LayoutInflater inflater = MoodActivity.this.getLayoutInflater();
//        We inflate the specific layout to get an EditText in this AlertDialog.
        builder.setView(inflater.inflate(R.layout.alertcommentary,null));
        builder.setTitle("Comment");
//        We recover the user insert in the EditText to save it in the Mood commentary.
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialog diaCom = (Dialog) dialog;
                EditText comDialog = diaCom.findViewById(R.id.alertcommentary_edittext);
                mCurrentMood.setCommentary(comDialog.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        })
                .setCancelable(false)
                .create()
                .show();
    }


//    We override onTouchEvent to pass all the gesture events to mDetector.
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * Inner class that sets in which order the mood object are displayed when
     * user scroll on the screen. We call changeMood() to display changes.
     */
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int delta = 250;
            int idMood = mCurrentMood.getId();
            if(( e1.getY()<e2.getY() ) && (( e2.getY()-e1.getY() ) > delta)){
                if((idMood <= 4) && (idMood > 0)){
                    idMood--;
                    mCurrentMood = mMoods[idMood];
                    playSound(mCurrentMood.getSound());
                }
            }
            if(( e1.getY()>e2.getY() ) && ( (e1.getY()-e2.getY() ) > delta)){
                if((idMood >= 0) && (idMood <4)){
                    idMood++;
                    mCurrentMood = mMoods[idMood];
                    playSound(mCurrentMood.getSound());
                }
            }
            changeMood();
            return true;
        }
    }

    /**
     * Serializes the selected Mood object with Gson and put the created Json string in
     * SharedPreferences with the current date formatted in initActivity() for key.
     * So with a dynamic key, we rewrite on the current date all day and from midnight a new key is used.
     * The save history isn't limited in time.
     */
    public void saveMood(){
        String json = mGson.toJson(mCurrentMood);
        mPreferences.edit().putString(today,json).apply();
    }

    /**
     * Plays a sound after verify if the mPlayer is already created.
     * @param sound is the sound that we want play
     */
    public void playSound(int sound){
        if(mPlayer != null){
            mPlayer.stop();
            mPlayer.release();
        }
        mPlayer = MediaPlayer.create(this,sound);
        mPlayer.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        saveMood();
    }
}
