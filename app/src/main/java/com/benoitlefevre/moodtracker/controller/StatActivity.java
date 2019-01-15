package com.benoitlefevre.moodtracker.controller;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.benoitlefevre.moodtracker.R;
import com.benoitlefevre.moodtracker.model.Mood;
import com.benoitlefevre.moodtracker.model.ToolMood;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class StatActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;
    private List<Mood> mMoodList;
    private float[] mMoodDivision;
    private List<PieEntry> mPercent;
    private PieChart mPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        initActivity();

//        We use ToolMood.initMoods() to initialize mMoodList because this tool sets the number of items displayed
//        in this activity and in HistoryActivity. We can change easily the number of items displayed by changing just one constant variable.
        mMoodList = ToolMood.initMoods(mPreferences);

        getPercents();
        initPieEntry();

    }

    /**
     * Initializes all fields of the activity
     */
    public void initActivity(){
        mPreferences = getSharedPreferences("Moodsave",MODE_PRIVATE);
        mMoodList = new ArrayList<>();
    }

    /**
     * For each Mood in mMoodList, gets the Mood Id and counts each value of Id in an array.
     */
    public void getPercents(){
        mMoodDivision = new float[]{0,0,0,0,0};
        for (Mood mood : mMoodList){
            switch (mood.getId()){
                case 0:
                    mMoodDivision[0]++;
                    break;
                case 1:
                    mMoodDivision[1]++;
                    break;
                case 2:
                    mMoodDivision[2]++;
                    break;
                case 3:
                    mMoodDivision[3]++;
                    break;
                case 4:
                    mMoodDivision[4]++;
                    break;
            }
        }
    }

    /**
     * Calculates for each value in mMoodDivision the ratio of this value compared to the List<Mood>.size().
     * Each calculated value is put in a List<PieEntry> that is the values displayed in the pie chart
     */
    public void initPieEntry(){
        int size = mMoodList.size();
        mPercent.add(new PieEntry(mMoodDivision[0] /(float)size,""));
        if(mPercent.get(0).getValue() >0)
            mPercent.get(0).setLabel("Sad Mood");
        mPercent.add(new PieEntry(mMoodDivision[1] /(float)size,""));
        if(mPercent.get(1).getValue() >0)
            mPercent.get(1).setLabel("Disappointed Mood");
        mPercent.add(new PieEntry(mMoodDivision[2] / (float)size,""));
        if(mPercent.get(2).getValue() >0)
            mPercent.get(2).setLabel("Simple Mood");
        mPercent.add(new PieEntry(mMoodDivision[3] / (float)size,""));
        if(mPercent.get(3).getValue() >0)
            mPercent.get(3).setLabel("Happy Mood");
        mPercent.add(new PieEntry(mMoodDivision[4] / (float)size,""));
        if(mPercent.get(4).getValue() >0)
            mPercent.get(4).setLabel("Very Happy Mood");
    }
    
}
