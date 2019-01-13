package com.benoitlefevre.moodtracker.controller;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ListView;

import com.benoitlefevre.moodtracker.R;
import com.benoitlefevre.moodtracker.model.Mood;
import com.benoitlefevre.moodtracker.model.MoodAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class HistoricalActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private ListView mListView;
    private List<Mood> mMoodList;
    private int mHeightScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historical);

        initActivity();
        initMoods();
        getHeightScreen();
        MoodAdapter adapter = new MoodAdapter(this,mMoodList,mHeightScreen);
        mListView.setAdapter(adapter);
    }

    public void initActivity(){
        mListView = findViewById(R.id.HistoricalActivity_listView);
        mPreferences = getSharedPreferences("MoodSave",MODE_PRIVATE);
        mMoodList = new ArrayList<>();
    }

    public void initMoods(){
        Gson gson = new Gson();
        Map<String, ?> prefList = mPreferences.getAll();
        for (Map.Entry<String,?>  entry : prefList.entrySet()) {
            String json = entry.getValue().toString();
            Mood mMood = gson.fromJson(json,Mood.class);
            mMoodList.add(mMood);
        }
        Collections.sort(mMoodList, new Comparator<Mood>() {
            @Override
            public int compare(Mood o1, Mood o2) {
                return o1.getDay().compareTo(o2.getDay());
            }
        });
        mMoodList.remove(mMoodList.size()-1);
        if(mMoodList.size()>= 7) {
            mMoodList = mMoodList.subList(mMoodList.size()-7,mMoodList.size());
        }
    }

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
