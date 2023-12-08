package com.example.treest.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Station {
    private String name;
    private Double lat;
    private Double lon;

    public Station(String name, Double lat, Double lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public Station(JSONObject station_json) {
        try {
            this.name = station_json.getString("sname");
            this.lat = station_json.getDouble("lat");
            this.lon = station_json.getDouble("lon");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    @Override
    public String toString() {
        return "Station{" +
                "name='" + name + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }
}
