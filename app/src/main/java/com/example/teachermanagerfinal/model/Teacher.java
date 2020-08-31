package com.example.teachermanagerfinal.model;

public class Teacher {
    public int mID;
    public String mCode;
    public String mName;
    public String mGender;
    public String mMajor;
    public String mSalary;

    public Teacher() {
    }

    public Teacher(String mCode, String mName, String mGender, String mMajor, String mSalary) {
        this.mCode = mCode;
        this.mName = mName;
        this.mGender = mGender;
        this.mMajor = mMajor;
        this.mSalary = mSalary;
    }

    public Teacher(int mID, String mCode, String mName, String mGender, String mMajor, String mSalary) {
        this.mID = mID;
        this.mCode = mCode;
        this.mName = mName;
        this.mGender = mGender;
        this.mMajor = mMajor;
        this.mSalary = mSalary;
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

    public String getmGender() {
        return mGender;
    }

    public void setmGender(String mGender) {
        this.mGender = mGender;
    }

    public String getmMajor() {
        return mMajor;
    }

    public void setmMajor(String mMajor) {
        this.mMajor = mMajor;
    }

    public String getmSalary() {
        return mSalary;
    }

    public void setmSalary(String mSalary) {
        this.mSalary = mSalary;
    }
}
