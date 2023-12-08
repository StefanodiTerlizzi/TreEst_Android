package com.example.treest.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OfficialPost {
    private String title;
    private String date;
    private String time;
    private String description;

    @Override
    public String toString() {
        return "OfficialPost{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public OfficialPost (JSONObject post) {
        try {
            if (post.has("title")) {
                this.title = post.getString("title");
            }
            if (post.has("timestamp")) {
                String datetime = post.getString("timestamp");
                String[] split = datetime.split(" ");
                this.date = split[0];
                this.time = split[1];
            }
            if (post.has("description")) {
                this.description = post.getString("description");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public JSONObject toJSONOBJECT() {
        JSONObject p = new JSONObject();
        try {
            p.put("title", this.getTitle());
            p.put("timestamp", this.getDate()+" "+this.getTime());
            p.put("description", this.getDescription());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return p;
    }
}
