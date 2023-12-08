package com.example.treest.model;

import androidx.lifecycle.MutableLiveData;


public class User {

    private MutableLiveData<String> sid = new MutableLiveData<>(null);
    private MutableLiveData<String> uid = new MutableLiveData<>();
    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<String> picture = new MutableLiveData<>();
    private Integer pversion;
    private static String defaultImg;

    public User() {

    }

    public static void setDefaultImg(String defaultImg) {
        User.defaultImg = defaultImg;
    }

    public static String getDefaultImg() {
        return defaultImg;
    }

    public User(String sid) {
        this.sid.setValue(sid);
    }

    public String getSid() {
        return sid.getValue();
    }

    public MutableLiveData<String> getUid() {
        return uid;
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public MutableLiveData<String> getPicture() {
        return picture;
    }

    public Integer getpVersion() {
        return pversion;
    }

    public void setSid(String sid) {
        this.sid.setValue(sid);
    }

    public void setUid(String uid) {
        this.uid.setValue(uid);
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public void setPversion(Integer pversion) {
        this.pversion = pversion;
    }

    @Override
    public String toString() {
        return "User{" +
                "sid='" + sid + '\'' +
                ", uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", picture=" + picture +
                ", pversion=" + pversion +
                '}';
    }

    public void setPicture(String picture) {
        this.picture.setValue(picture);
    }



}
