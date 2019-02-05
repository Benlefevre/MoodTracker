package com.benoitlefevre.moodtracker.model;

import java.util.Date;

public class Mood {

    private int mId;
    private int mImage;
    private int mColor;
    private String mCommentary;
    private Date mDay;
    private int mSound;

    /**
     * The Mood constructor
     * @param id we need an id to identify easily each mood
     * @param image a link to drawable resource
     * @param color a link to color resource
     * @param sound a link to a raw ressource
     * @param commentary the comment which is attached to the Mood
     * @param day the date of the creation of the Mood
     */
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
     *A description of the Mood object with the values of each fields.
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

