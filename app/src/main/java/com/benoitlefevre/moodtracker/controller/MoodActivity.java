package com.benoitlefevre.moodtracker.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.benoitlefevre.moodtracker.R;
import com.benoitlefevre.moodtracker.model.Mood;

import java.util.Date;

public class MoodActivity extends AppCompatActivity {

    private ImageView mImageView;
    private ImageButton mCommentaryButton;
    private ImageButton mHistoricalButton;
    private ConstraintLayout mLayout;
    private SharedPreferences mPreferences;
    private Date mDate;
    private Mood[] mMoods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        initActivity();
        mMoods = createMoods();

        mCommentaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    }

    public Mood[] createMoods(){
        Mood mSad = new Mood(0,R.drawable.smiley_sad,R.color.faded_red,null,mDate);
        Mood mDisappointed = new Mood(1, R.drawable.smiley_disappointed,R.color.warm_grey,null,mDate);
        Mood mSimple = new Mood(2,R.drawable.smiley_normal,R.color.cornflower_blue_65,null,mDate);
        Mood mHappy = new Mood(3,R.drawable.smiley_happy,R.color.light_sage,null,mDate);
        Mood mSuperHappy = new Mood(4, R.drawable.smiley_super_happy,R.color.banana_yellow,null,mDate);
        return new Mood[]{mSad,mDisappointed,mSimple,mHappy,mSuperHappy};
    }


}
