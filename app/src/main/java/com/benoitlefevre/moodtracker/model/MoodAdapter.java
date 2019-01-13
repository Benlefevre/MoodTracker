package com.benoitlefevre.moodtracker.model;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class MoodAdapter extends ArrayAdapter<Mood> {

    private Context mContext;
    private List<Mood> mMoodList;
    private int mHeightScreen;

    public MoodAdapter(Context context, List<Mood> list, int heightScreen) {
        super(context, 0 , list);
        mContext = context;
        mMoodList = list;
        mHeightScreen = heightScreen;
    }

}
