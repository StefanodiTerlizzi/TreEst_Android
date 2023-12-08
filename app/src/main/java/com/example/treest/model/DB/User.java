package com.example.treest.model.DB;

import androidx.annotation.NonNull;
import androidx.room.*;

@Entity
public class User {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "pVersion")
    public String pVersion;

    @ColumnInfo(name = "picure")
    public String picture;
    @Ignore
    public User(int uid, String pVersion, String picture) {
        this.uid = uid;
        this.pVersion = pVersion;
        this.picture = picture;
    }
    @Ignore
    public User(int uid) {
        this.uid = uid;
    }

    public User() {}

    @NonNull
    @Override
    public String toString() {
        return "{uid: "+uid+", pVersion: "+pVersion+", picture: "+picture+"}";
    }

    public int getUid() {
        return uid;
    }

    public String getpVersion() {
        return pVersion;
    }

    public String getPicture() {
        return picture;
    }

    public void setpVersion(String version) {
        this.pVersion = version;
    }

    public void setPicure(String picture) {
        this.picture = picture;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
