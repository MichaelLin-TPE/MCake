package com.cake.mcakeapp.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CommentData implements Serializable {

    @SerializedName("comment")
    private String comment;
    @SerializedName("uuid")
    private String uuid;
    @SerializedName("star_amount")
    private int starAmount;
    @SerializedName("photo_url_array")
    private ArrayList<String> photoUrlArray;
    @SerializedName("time")
    private long timeMillis;

    public long getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getStarAmount() {
        return starAmount;
    }

    public void setStarAmount(int starAmount) {
        this.starAmount = starAmount;
    }

    public ArrayList<String> getPhotoUrlArray() {
        return photoUrlArray;
    }

    public void setPhotoUrlArray(ArrayList<String> photoUrlArray) {
        this.photoUrlArray = photoUrlArray;
    }
}
