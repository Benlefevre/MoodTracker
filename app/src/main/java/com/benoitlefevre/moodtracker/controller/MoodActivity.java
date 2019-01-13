package com.benoitlefevre.moodtracker.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
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

public class MoodActivity extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        initActivity();
        mMoods = createMoods();
        initMood();

        mCommentaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });

        mHistoricalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoodActivity.this,HistoricalActivity.class);
                startActivity(intent);
            }
        });

    }

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

    }

    public Mood[] createMoods(){
        Mood mSad = new Mood(0,R.drawable.smiley_sad,R.color.faded_red,null,mDate);
        Mood mDisappointed = new Mood(1, R.drawable.smiley_disappointed,R.color.warm_grey,null,mDate);
        Mood mSimple = new Mood(2,R.drawable.smiley_normal,R.color.cornflower_blue_65,null,mDate);
        Mood mHappy = new Mood(3,R.drawable.smiley_happy,R.color.light_sage,null,mDate);
        Mood mSuperHappy = new Mood(4, R.drawable.smiley_super_happy,R.color.banana_yellow,null,mDate);
        return new Mood[]{mSad,mDisappointed,mSimple,mHappy,mSuperHappy};
    }

    public void initMood(){
        String json = mPreferences.getString(today,null);
        if(json != null)
            mCurrentMood = mGson.fromJson(json,Mood.class);
        else
            mCurrentMood = mMoods[2];
        changeMood();
    }

    public void changeMood(){
        mLayout.setBackgroundResource(mCurrentMood.getColor());
        mImageView.setImageResource(mCurrentMood.getImage());
    }

    public void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MoodActivity.this);
        LayoutInflater inflater = MoodActivity.this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.alertcommentary,null));
        builder.setTitle("Comment");
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

    public void saveMood(){
        String json = mGson.toJson(mCurrentMood);
        mPreferences.edit().putString(today,json).apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveMood();
    }
}
