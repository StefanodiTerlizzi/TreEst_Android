package com.example.treest.model;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Post {
    private String delay = "";
    private String status = "";
    private String comment = "";
    private Boolean followingAuthor;
    private String datetime;
    private String authorName;
    private String pversion;
    private String author;

    public Post(JSONObject post_json) {
        try {
            if (post_json.has("delay")) {
                this.delay = post_json.getString("delay");
            }
            if (post_json.has("status")) {
                this.status = post_json.getString("status");
            }
            if (post_json.has("comment")) {
                this.comment = post_json.getString("comment");
            }
            if (post_json.has("followingAuthor")) {
                this.followingAuthor = post_json.getBoolean("followingAuthor");
            }
            if (post_json.has("datetime")) {
                this.datetime = post_json.getString("datetime");
            }
            if (post_json.has("authorName")) {
                this.authorName = post_json.getString("authorName");
            }
            if (post_json.has("pversion")) {
                this.pversion = post_json.getString("pversion");
            }
            if (post_json.has("author")) {
                this.author = post_json.getString("author");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    @Override
    public String toString() {
        return "Post{" +
                "delay=" + delay +
                ", status=" + status +
                ", comment='" + comment + '\'' +
                ", followingAuthor=" + followingAuthor +
                ", datetime='" + datetime + '\'' +
                ", authorName='" + authorName + '\'' +
                ", pversion='" + pversion + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    public String getDelay() {
        switch (delay) {
            case "0":
                return "in orario";
            case "1":
                return "ritardo di pochi minuti";
            case "2":
                return "ritardo oltre i 15 minuti";
            case "3":
                return "treni soppressi";
            default:
                return "";
        }
    }

    public String getStatus() {
        switch (status) {
            case "0":
                return "situazione ideale";
            case "1":
                return "accettabile";
            case "2":
                return "gravi problemi per i passeggeri";
            default:
                return "";
        }
    }

    public String getComment() {
        return comment;
    }

    public String getAuthor() {
        return author;
    }

    public String getDateTime() {
        return datetime;
    }

    public boolean getFollowing() {
        return this.followingAuthor;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getPversion() {
        return pversion;
    }

    public static List<String> getListDelay() {
        return Arrays.asList("","in orario", "ritardo di pochi minuti", "ritardo oltre i 15 minuti", "treni soppressi");
    }

    public static List<String> getListStatus() {
        return Arrays.asList("", "situazione ideale", "accettabile", "gravi problemi per i passeggeri");
    }

    public static int getDelayFromString(String s) {
        switch (s) {
            case "in orario":
                return 0;
            case "ritardo di pochi minuti":
                return 1;
            case "ritardo oltre i 15 minuti":
                return 2;
            case "treni soppressi":
                return 3;
            default:
                return -1;
        }
    }

    public static String getStringFromDelay(int delay) {
        switch (delay) {
            case 0:
                return "in orario";
            case 1:
                return "ritardo di pochi minuti";
            case 2:
                return "ritardo oltre i 15 minuti";
            case 3:
                return "treni soppressi";
            default:
                return "";
        }
    }

    public static int getStatusFromString(String s) {
        switch (s) {
            case "situazione ideale":
                return 0;
            case "accettabile":
                return 1;
            case "gravi problemi per i passeggeri":
                return 2;
            default:
                return -1;
        }
    }

    public static String getStringFromStatus(int status) {
        switch (status) {
            case 0:
                return "situazione ideale";
            case 1:
                return "accettabile";
            case 2:
                return "gravi problemi per i passeggeri";
            default:
                return "";
        }
    }


    public static List<Post> JSONlistToPostList(JSONArray post_json) {
        List<Post> result = new ArrayList<>();
        for (int i = 0; i < post_json.length(); i++) {
            try {
                Post p = new Post((JSONObject) post_json.get(i));
                result.add(p);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}