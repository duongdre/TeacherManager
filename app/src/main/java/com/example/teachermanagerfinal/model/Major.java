package com.example.teachermanagerfinal.model;

public class Major {
    public int mID;
    public String mCode;
    public String mName;

    public Major() {
    }

    public Major(String mCode, String mName) {
        this.mCode = mCode;
        this.mName = mName;
    }

    public Major(int mID, String mCode, String mName) {
        this.mID = mID;
        this.mCode = mCode;
        this.mName = mName;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmCode() {
        return mCode;
    }

    public void setmCode(String mCode) {
        this.mCode = mCode;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
