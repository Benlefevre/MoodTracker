package com.benoitlefevre.moodtracker.controller;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.benoitlefevre.moodtracker.R;
import com.benoitlefevre.moodtracker.model.Mood;
import com.benoitlefevre.moodtracker.model.ToolMood;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
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
        initPie();
    }

    /**
     * Initializes all fields of the activity
     */
    public void initActivity(){
        mPreferences = getSharedPreferences("MoodSave",MODE_PRIVATE);
        mMoodList = new ArrayList<>();
        mPercent = new ArrayList<>();
        mPieChart = findViewById(R.id.pieChart);
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

    /**
     * Initializes the form, the description and the legend of pie chart and sets witch values are displayed
     */
    public void initPie(){
//        We set the good description with the number of items displayed
        mPieChart.getDescription().setText("Mood division for last " + ToolMood.getNbItemsForStats(mMoodList) + " days");
        mPieChart.getDescription().setTextSize(20.0f);
//        We set an entire PieChart without center hole.
        mPieChart.setDrawHoleEnabled(false);
        mPieChart.setEntryLabelColor(Color.BLACK);
//        We convert float data values to percent.
        mPieChart.setUsePercentValues(true);
//        We animate the open of StatActivity
        mPieChart.animateY(800, Easing.EaseInCirc);

        Legend legend = mPieChart.getLegend();
        legend.setEnabled(false);

//        We set the data that displayed in PieChart
        PieDataSet set = new PieDataSet(mPercent,null);
        set.setColors(new int[]{R.color.faded_red,R.color.warm_grey,R.color.cornflower_blue_65,R.color.light_sage,R.color.banana_yellow},this);

        PieData data = new PieData(set);
        data.setValueTextColor(Color.BLACK);
        mPieChart.setData(data);
        mPieChart.invalidate();
    }
}
