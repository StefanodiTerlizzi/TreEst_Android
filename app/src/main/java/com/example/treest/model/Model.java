package com.example.treest.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.treest.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Model {
    public static final String DB_NAME = "UsersDB";
    private static Model INSTANCE = null;



    private Model(){};

    public static Model getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new Model();
        }
        return INSTANCE;
    }

    /*Model*/
    private SharedPreferences sharedPreferences;

    private User me = new User();
    private MutableLiveData<List<Line>> lines = new MutableLiveData<>(new ArrayList<>());
    //selected line
    private Line selectedLine = null;
    private List<Post> postsSelectedLine = new ArrayList<>();
    private List<OfficialPost> officialPosts = new ArrayList<>();
    //stations
    private List<Station> stations = new ArrayList<>();
    //Shared Preferences
    public static final String NAMESHAREDPREF = "SharedPref";
    public static final String SID_SP = "sid";
    public static final String WALL_SP = "wall";
    public static final String SELECTELINE_SP = "selectedLine";
    public static final String POSTCONTENT_SP = "PostContent";
    //navBar
    private int activeNavBar = 0;

    private int did = -1;


    public void setSharedPreferences(Context applicationContext) {
        sharedPreferences = applicationContext.getSharedPreferences(Model.NAMESHAREDPREF, 0);
    }


    public User getMe() {
        return me;
    }

    public void setUserFromSid(String sid) {
        this.me = new User(sid);
    }

    public void setLineFromNetwork(JSONObject obj) throws JSONException {
        this.lines.getValue().clear();
        JSONArray lines_json = obj.getJSONArray("lines");
        for (int i = 0; i < lines_json.length(); i++) {
            lines.getValue().add( new Line((JSONObject) lines_json.get(i)) );
        }
        this.lines.setValue(this.lines.getValue());
    }

    public Line getLine(int position) {
        return this.lines.getValue().get(position);
    }

    public int getLineSize() {
        return this.lines.getValue().size();
    }

    public MutableLiveData getLines() {
        return this.lines;
    }

    public List<Post> getPosts() {
        return postsSelectedLine;
    }

    public void setPosts(List<Post> list) {
        postsSelectedLine = list;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    public int getNavBarActive() {
        return activeNavBar;
    }

    public void setNavBarActive(int i) {
        activeNavBar = i;
    }

    public String getSid() {
        if (me.getSid() == null) {
            String sid = sharedPreferences.getString(SID_SP, null);
            //non ci arrivo mai se ho gia settato nel model
            me.setSid(sid);
        }
        return me.getSid();
    }

    public void setSid(String sid) {
        me.setSid(sid);
        sharedPreferences.edit().putString(Model.SID_SP, sid).commit();
    }

    public int getDid() {
        if (did == -1) {
            this.did = sharedPreferences.getInt(WALL_SP, -1);
        }
        return did;
    }

    public void setDid(int did) {
        this.did = did;
        sharedPreferences.edit().putInt(WALL_SP, did).commit();
    }

    public Line getSelectedLine() {
        if (selectedLine == null) {
            selectedLine = new Line(sharedPreferences.getString(SELECTELINE_SP,null));
        }
        return selectedLine;
    }

    public void setSelectedLine(Line line) {
        try {
            JSONObject j = new JSONObject();
            j.put("terminus1_name", line.getTerminus1().getSname());
            j.put("terminus2_name", line.getTerminus2().getSname());
            j.put("terminus1_did", line.getTerminus1().getDid());
            j.put("terminus2_did", line.getTerminus2().getDid());
            this.selectedLine = line;
            sharedPreferences.edit().putString(Model.SELECTELINE_SP, j.toString()).commit();
        } catch (JSONException e) {
            Log.d(MainActivity.TAG, "setSelectedWall: "+e.getLocalizedMessage());
            e.printStackTrace();
        }
    }


    public List<OfficialPost> getOfficialPosts() {
        return officialPosts;
    }

    public void setOfficialPosts(List<OfficialPost> officialPosts) {
        this.officialPosts = officialPosts;
    }
}
