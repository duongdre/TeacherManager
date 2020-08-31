package com.example.teachermanagerfinal.model;

public class Book {
    public int mID;
    public String mCode;
    public String mName;
    public String mMajor;

    public Book() {
    }

    public Book(String mCode, String mName, String mMajor) {
        this.mCode = mCode;
        this.mName = mName;
        this.mMajor = mMajor;
    }

    public Book(int mID, String mCode, String mName, String mMajor) {
        this.mID = mID;
        this.mCode = mCode;
        this.mName = mName;
        this.mMajor = mMajor;
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

    public String getmMajor() {
        return mMajor;
    }

    public void setmMajor(String mMajor){
        this.mMajor = mMajor;
    }
}
