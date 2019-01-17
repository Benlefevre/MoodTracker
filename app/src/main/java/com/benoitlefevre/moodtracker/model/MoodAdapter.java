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

import java.util.Calendar;
import java.util.Date;
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

    /**
     * Inner class that list all fields for each items of the  HistoryActivity ListView
     */
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
//        If convertView is null, we inflate the specific layout to ListView items.
        if(convertView == null)
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_history_item,parent,false);
//        If MoodHolderView is null, we call all views.
        MoodViewHolder moodViewHolder = (MoodViewHolder)convertView.getTag();
        if(moodViewHolder == null){
            moodViewHolder = new MoodViewHolder();
            moodViewHolder.date = convertView.findViewById(R.id.HistoricalActivity_textView);
            moodViewHolder.imageButton = convertView.findViewById(R.id.HistoricalActivity_comment_btn);
            moodViewHolder.linearLayout1 = convertView.findViewById(R.id.HistoricalActivity_Mood_layout1);
            moodViewHolder.linearLayout2 = convertView.findViewById(R.id.HistoricalActivity_Mood_layout2);
            moodViewHolder.linearLayout3 = convertView.findViewById(R.id.HistoricalActivity_Mood_layout3);
        }
//        We recover the Mood to the ListView with .get(position) in mCurrentMood to get attributes that we need for customize
//        the ViewHolder.
        final Mood mCurrentMood = mMoodList.get(position);

//        We make all changes that we need to have a correct display
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) moodViewHolder.linearLayout1.getLayoutParams();
        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) moodViewHolder.linearLayout2.getLayoutParams();
        LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams) moodViewHolder.linearLayout3.getLayoutParams();

        changeHeight(moodViewHolder.linearLayout1,params1);

        moodViewHolder.linearLayout2.setBackgroundResource(mCurrentMood.getColor());

        changeWeight(mCurrentMood,moodViewHolder.linearLayout2,moodViewHolder.linearLayout3,params2,params3);

        displayComment(moodViewHolder.imageButton,mCurrentMood);

        changeText(mCurrentMood,moodViewHolder.date);

        return convertView;
    }

    /**
     * Defines if the ImageButton in parameter must be Visible or not according to the mood has or not a comment. If it's Visible, it sets the OnCLickListener and
     * displays a Toast with the Mood commentary.
     * @param imageButton The ViewHolder ImageButton that you want make visible or not
     * @param mood The mood on which we want get the commentary
     */
    private void displayComment(ImageButton imageButton, final Mood mood){
        if(mood.getCommentary()!= null)
            imageButton.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mood.getCommentary(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Changes the height of items on ListView
     * @param layout The ViewHolder layout that includes all the content of the MoodViewHolder
     * @param params The parameters of the previous layout to be able to change his height
     */
    private void changeHeight(View layout,LinearLayout.LayoutParams params){
        params.height = mHeightScreen/7;
        layout.setLayoutParams(params);
    }

    /**
     *Changes the weight of layouts to represent the Mood graphically in the ListView item.
     * @param mood The selected mood in the item
     * @param layout The first ViewHolder layout that represent the color associated to the mood and contains the TextView and ImageButton
     * @param layout2 An other ViewHolder layout that changes to see the first change according to the weight of the principal layout
     * @param params The LinearLayout.LayoutParams to change dynamically the weight of the principal ViewHolder layout
     * @param params2 The LinearLayout.LayoutParams to change dynamically the weight of the second ViewHolder layout
     */
    private void changeWeight(Mood mood, View layout, View layout2, LinearLayout.LayoutParams params, LinearLayout.LayoutParams params2){
        switch (mood.getId()){
            case 0:
                params.weight = 0.2f;
                params2.weight = 0.8f;
                break;
            case 1:
                params.weight = 0.4f;
                params2.weight = 0.6f;
                break;
            case 2:
                params.weight = 0.6f;
                params2.weight = 0.4f;
                break;
            case 3:
                params.weight = 0.8f;
                params2.weight = 0.2f;
                break;
            case 4:
                params.weight = 1.0f;
                params2.weight = 0.0f;
                break;
        }
        layout.setLayoutParams(params);
        layout2.setLayoutParams(params2);
    }

    /**
     * Renames the Mood date into a string that is more easily reading. The text displayes can be "Today","Yesterday",Last week" or "X days ago" according to the size of the historical.
     * @param mood The displayed Mood in the ListView Item
     * @param view The ViewHolder TextView that changes after the conversion of the Mood Date
     */
    private void changeText(Mood mood, TextView view){
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(new Date());
        currentDate.set(Calendar.HOUR_OF_DAY,1);
        Calendar moodDate = Calendar.getInstance();
        moodDate.setTime(mood.getDay());
        moodDate.set(Calendar.HOUR_OF_DAY,23);
        String[] legends = {"Today","Yesterday","Last week"};
        int i = 0;
        while(currentDate.getTime().after(moodDate.getTime())) {
            moodDate.add(Calendar.DATE, 1);
            i++;
        }
        String legend = i + " days ago";
        if(i >= 0 && i<=1)
            view.setText(legends[i]);
        else if(i == 7)
            view.setText(legends[2]);
        else
            view.setText(legend);
    }
}
