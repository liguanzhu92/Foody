package com.example.guanzhuli.foody.model;
// Lily: Designed and implemented Order class for future using
// Xiao: Added getAddress and setAddress functions after web service been changed.

/**
 * Created by Guanzhu Li on 1/13/2017.
 */
public class Order {
    private int mId;
    private String mName;
    private int mQuantity;
    private double mTotal;
    private String mCategory;
    private String mDate;
    private String mAddress;
    private String mStatus;

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

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

    public double getTotal() {
        return mTotal;
    }

    public void setTotal(double total) {
        mTotal = total;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
