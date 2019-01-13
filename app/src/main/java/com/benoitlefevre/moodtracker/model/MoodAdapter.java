package com.benoitlefevre.moodtracker.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.benoitlefevre.moodtracker.R;

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

    private class MoodViewHolder{
        private TextView date;
        private ImageButton imageButton;
        private LinearLayout linearLayout1;
        private LinearLayout linearLayout2;
        private LinearLayout linearLayout3;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null)
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_historical_item,parent,false);

        MoodViewHolder moodViewHolder = (MoodViewHolder)convertView.getTag();
        if(moodViewHolder == null){
            moodViewHolder = new MoodViewHolder();
            moodViewHolder.date=convertView.findViewById(R.id.HistoricalActivity_textView);
            moodViewHolder.imageButton = convertView.findViewById(R.id.HistoricalActivity_comment_btn);
            moodViewHolder.linearLayout1 = convertView.findViewById(R.id.HistoricalActivity_Mood_layout1);
            moodViewHolder.linearLayout2 = convertView.findViewById(R.id.HistoricalActivity_Mood_layout2);
            moodViewHolder.linearLayout3 = convertView.findViewById(R.id.HistoricalActivity_Mood_layout3);
        }
        final Mood mCurrentMood = mMoodList.get(position);

        moodViewHolder.linearLayout2.setBackgroundResource(mCurrentMood.getColor());

        displayComment(moodViewHolder,mCurrentMood);

        return convertView;
    }

    private void displayComment(final MoodViewHolder moodViewHolder, final Mood mood){
        if(mood.getCommentary()!= null)
            moodViewHolder.imageButton.setVisibility(View.VISIBLE);
        moodViewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mood.getCommentary(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
