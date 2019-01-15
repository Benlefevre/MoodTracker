package com.benoitlefevre.moodtracker.model;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ToolMood {

    //    If we want display more or less that 7 items, we can change the value of NUMBEROFITEMS
    private final static int NUMBEROFITEMS = 7;

    /**
     * Get all SharedPreferences in Map<String,?> and for each Entry convert json String in a Mood object.
     * The MoodList that contains all Moods is sort and we remove the first index because it's corresponding to the today mood.
     * We create a sublist to contain only the number of items that we want.
     * @param sharedPreferences the SharedPreferences where save all Moods
     * @return an ArrayList<Mood>
     */
    public static List<Mood> initMoods(SharedPreferences sharedPreferences){
        Gson gson = new Gson();
        List<Mood> moodList = new ArrayList<>();
        Map<String, ?> prefList = sharedPreferences.getAll();
        for (Map.Entry<String,?>  entry : prefList.entrySet()) {
            String json = entry.getValue().toString();
            Mood mMood = gson.fromJson(json,Mood.class);
            moodList.add(mMood);
        }
        Collections.sort(moodList, new Comparator<Mood>() {
            @Override
            public int compare(Mood o1, Mood o2) {
                return o1.getDay().compareTo(o2.getDay());
            }
        });
//        We can always remove the last index of the moodList because there is always the current mood that is saved is MoodActivity onPause().
        moodList.remove(moodList.size()-1);
        if(moodList.size()>= NUMBEROFITEMS){
            moodList = moodList.subList(moodList.size()-NUMBEROFITEMS,moodList.size());
        }
        return moodList;
    }

    /**
     * Defines if the number of items save in SharedPreferences is greater than the number of items wanted.
     * @param moodList needs a List to define her size and do the comparison.
     * @return int nbItems equals the value of moodList.size() if it's greater than NUMBEROFITEMS else nbItems equals the value of NUMBEROFITEMS
     */
    public static int getNbItemsForStats(List<Mood> moodList){
        int nbItems;
        if(NUMBEROFITEMS > moodList.size())
            nbItems = moodList.size();
        else
            nbItems = NUMBEROFITEMS;
        return nbItems;
    }
}
