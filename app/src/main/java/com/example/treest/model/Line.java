package com.example.treest.model;

import android.util.Log;

import com.example.treest.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

public class Line {
    private Terminus terminus1;
    private Terminus terminus2;

    public Line(String line_SHPR_json) {
        if (line_SHPR_json != null) {
            try {
                JSONObject j = new JSONObject(line_SHPR_json);
                this.terminus1 = new Terminus(j.getString("terminus1_name"), j.getString("terminus1_did"));
                this.terminus2 = new Terminus(j.getString("terminus2_name"), j.getString("terminus2_did"));
                //Log.d(MainActivity.TAG, "Terminal 1 from string: "+this.terminus1.toString());
                //Log.d(MainActivity.TAG, "Terminal 2 from string: "+this.terminus2.toString());

            } catch (JSONException e) {
                Log.d(MainActivity.TAG, "Line costructor: "+e.getLocalizedMessage());
                e.printStackTrace();
            }
        }

    }

    public Line(JSONObject line_json) {
        try {
            this.terminus1 = new Terminus((JSONObject) line_json.getJSONObject("terminus1"));
            this.terminus2 = new Terminus((JSONObject) line_json.getJSONObject("terminus2"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "Line{" +
                "terminus1=" + terminus1 +
                ", terminus2=" + terminus2 +
                '}';
    }

    public String getTitle() {
        return ( terminus1.getSname() + " - " + terminus2.getSname() );
    }

    public Terminus getTerminus1() {
        return this.terminus1;
    }

    public Terminus getTerminus2() {
        return this.terminus2;
    }

    public class Terminus {
        private String sname;
        private int did;

        public Terminus(JSONObject terminus_json) {
            try {
                this.sname = terminus_json.getString("sname");
                this.did = terminus_json.getInt("did");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public Terminus(String name, String did) {
            this.sname = name;
            this.did = Integer.valueOf(did);
        }

        @Override
        public String toString() {
            return "Termius{" +
                    "sname='" + sname + '\'' +
                    ", did=" + did +
                    '}';
        }

        public String getSname() {
            return sname;
        }

        public int getDid() {
            return did;
        }

    }
}
