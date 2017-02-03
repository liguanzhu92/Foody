package com.example.guanzhuli.foody.model;
// Lily: Designed and implemented Food class for future using.
// Xiao: Added setImage function for storing image after ImageRequest finished.

import android.graphics.Bitmap;



/**
 * Created by Guanzhu Li on 1/13/2017.
 */
public class Food {
    private int mId;
    private String mName;
    private String mRecepiee;
    private double mPrice;
    private String mCategory;
    private String mCity;
    private String mImageUrl;
    private Bitmap mImage;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getRecepiee() {
        return mRecepiee;
    }

    public void setRecepiee(String recepiee) {
        mRecepiee = recepiee;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public Bitmap getImage(){
        return mImage;
    }

    public void setImage(Bitmap image) {
        this.mImage = image;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
}
