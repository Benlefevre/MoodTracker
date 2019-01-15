package com.benoitlefevre.moodtracker.model;

import java.util.Date;

public class Mood {

    private int mId;
    private int mImage;
    private int mColor;
    private String mCommentary;
    private Date mDay;


    public Mood(int id, int image, int color, String commentary, Date day) {
        mId = id;
        mImage = image;
        mColor = color;
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

    @Override
    public String toString() {
        return "Mood{" +
                "mId=" + mId +
                ", mImage=" + mImage +
                ", mColor=" + mColor +
                ", mCommentary='" + mCommentary + '\'' +
                ", mDay=" + mDay +
                '}';
    }
}

