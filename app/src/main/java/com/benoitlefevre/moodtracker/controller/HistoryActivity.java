package com.benoitlefevre.moodtracker.controller;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ListView;

import com.benoitlefevre.moodtracker.R;
import com.benoitlefevre.moodtracker.model.Mood;
import com.benoitlefevre.moodtracker.model.MoodAdapter;
import com.benoitlefevre.moodtracker.model.ToolMood;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private ListView mListView;
    private List<Mood> mMoodList;
    private int mHeightScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        initActivity();

//        We use ToolMood.initMoods() to initialize mMoodList because this tool sets the number of items displayed
//        in this activity and in StatActivity. We can change easily the number of items displayed by changing just one constant variable.
        mMoodList = ToolMood.initMoods(mPreferences);

        getHeightScreen();
        MoodAdapter adapter = new MoodAdapter(this,mMoodList,mHeightScreen);
        mListView.setAdapter(adapter);
    }

    /**
     * Initializes all the fields of the activity
     */
    public void initActivity(){
        mListView = findViewById(R.id.HistoricalActivity_listView);
        mPreferences = getSharedPreferences("MoodSave",MODE_PRIVATE);
        mMoodList = new ArrayList<>();
    }

    /**
     * Gets the screen size and does the difference between the RealMetrics and the Metrics.
     * Sets mHeightScreen to the Metrics less the difference.
     */
    public void getHeightScreen(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        int heightScreen = displayMetrics.heightPixels;
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int heightScreen2 = displayMetrics.heightPixels;
        int diff = (heightScreen - heightScreen2);
        mHeightScreen = heightScreen2-diff;
    }
}
