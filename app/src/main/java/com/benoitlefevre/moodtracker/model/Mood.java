package com.benoitlefevre.moodtracker.model;

import java.util.Date;

public class Mood {

    private int mId;
    private int mImage;
    private int mColor;
    private String mCommentary;
    private Date mDay;
    private int mSound;


    public Mood(int id, int image, int color, int sound, String commentary, Date day) {
        mId = id;
        mImage = image;
        mColor = color;
        mSound = sound;
        mCommentary = commentary;
        mDay = day;
    }

    public int getId() {
        return mId;
    }

    public String getCommentary() {
        return mCommentary;
    }

    public void setCommentary(String commentary) {
        mCommentary = commentary;
    }

    public int getImage() {
        return mImage;
    }

    public int getColor() {
        return mColor;
    }

    public Date getDay() {
        return mDay;
    }

    public int getSound() {
        return mSound;
    }

    /**
     *A description of the Mood object with the values of each fields displayed.
     * @return The Mood description in String.
     */
    @Override
    public String toString() {
        return "Mood{" +
                "mId=" + mId +
                ", mImage=" + mImage +
                ", mColor=" + mColor +
                ", mSound=" + mSound +
                ", mCommentary='" + mCommentary + '\'' +
                ", mDay=" + mDay +
                '}';
    }
}

