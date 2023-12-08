package com.example.treest;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.treest.model.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class ComunicationController {
    private static final String BASE_URL = "https://ewserver.di.unimi.it/mobicomp/treest/";
    private static final String REGISTER_URL = "register.php";
    private static final String GETPROFILE_URL = "getProfile.php";
    private static final String SETPROFILE_URL = "setProfile.php";
    private static final String GETLINES_URL = "getLines.php";
    private static final String GETPOSTS_URL = "getPosts.php";
    private static final String FOLLOW_URL = "follow.php";
    private static final String UNFOLLOW_URL = "unfollow.php";
    private static final String ADDPOST_URL = "addPost.php";
    private static final String GETUSERPICTURE_URL = "getUserPicture.php";
    private static final String GETSTATIONS_URL = "getStations.php";
    private static final String GETOFFICIALPOST_URL = "statolineatreest.php";


    private RequestQueue reqQueue;

    public ComunicationController(Context context) {
        reqQueue = Volley.newRequestQueue(context);
    }


    public void register(Response.Listener onResponse, Response.ErrorListener OnError) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                BASE_URL+REGISTER_URL,
                null,
                response -> {
                    onResponse.onResponse((JSONObject) response);
                },
                error -> {
                    OnError.onErrorResponse(error);
                }
        );
        reqQueue.add(request);
    }

    public void getProfile(Response.Listener onResponse, Response.ErrorListener OnError) {

        JSONObject bodyReq = new JSONObject();
        try {
            bodyReq.put("sid", Model.getInstance().getMe().getSid());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL+GETPROFILE_URL,
                bodyReq,
                response -> {
                    onResponse.onResponse((JSONObject)response);
                },
                error -> {
                    OnError.onErrorResponse(error);
                }
        );
        reqQueue.add(request);

    }

    public void setProfile(JSONObject bodyReq, Response.Listener onResponse, Response.ErrorListener OnError) {
        /*TODO passo JsonObject o lo prendo dal model?*/
        try {
            bodyReq.put("sid", Model.getInstance().getMe().getSid());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL+SETPROFILE_URL,
                bodyReq,
                response -> {
                    onResponse.onResponse((JSONObject)response);
                },
                error -> {
                    OnError.onErrorResponse(error);
                }
        );
        reqQueue.add(request);
    }

    public void getLines(Response.Listener onResponse, Response.ErrorListener OnError) {

        JSONObject bodyReq = new JSONObject();
        try {
            bodyReq.put("sid", Model.getInstance().getMe().getSid());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL+GETLINES_URL,
                bodyReq,
                response -> {
                    onResponse.onResponse((JSONObject)response);
                },
                error -> {
                    OnError.onErrorResponse(error);
                }
        );
        reqQueue.add(request);

    }

    public void getPosts(int did, Response.Listener onResponse, Response.ErrorListener OnError) {

        JSONObject bodyReq = new JSONObject();
        try {
            bodyReq.put("sid", Model.getInstance().getMe().getSid());
            bodyReq.put("did", did);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL+GETPOSTS_URL,
                bodyReq,
                response -> {
                    onResponse.onResponse((JSONObject)response);
                },
                error -> {
                    OnError.onErrorResponse(error);
                }
        );
        reqQueue.add(request);

    }

    public void follow(String uid, Response.Listener onResponse, Response.ErrorListener OnError) {

        JSONObject bodyReq = new JSONObject();
        try {
            bodyReq.put("sid", Model.getInstance().getMe().getSid());
            bodyReq.put("uid", uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL+FOLLOW_URL,
                bodyReq,
                response -> {
                    onResponse.onResponse((JSONObject)response);
                },
                error -> {
                    OnError.onErrorResponse(error);
                }
        );
        reqQueue.add(request);

    }

    public void unfollow(String uid, Response.Listener onResponse, Response.ErrorListener OnError) {

        JSONObject bodyReq = new JSONObject();
        try {
            bodyReq.put("sid", Model.getInstance().getMe().getSid());
            bodyReq.put("uid", uid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL+UNFOLLOW_URL,
                bodyReq,
                response -> {
                    onResponse.onResponse((JSONObject)response);
                },
                error -> {
                    OnError.onErrorResponse(error);
                }
        );
        reqQueue.add(request);

    }


    public void addPost(JSONObject bodyReq, Response.Listener onResponse, Response.ErrorListener OnError) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL+ADDPOST_URL,
                bodyReq,
                response -> {
                    onResponse.onResponse((JSONObject)response);
                },
                error -> {
                    OnError.onErrorResponse(error);
                }
        );
        reqQueue.add(request);
    }

    public void getUserPicture(int uid, Response.Listener onResponse, Response.ErrorListener OnError) {
        JSONObject bodyReq = new JSONObject();
        try {
            bodyReq.put("sid", Model.getInstance().getMe().getSid());
            bodyReq.put("uid", String.valueOf(uid));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL+GETUSERPICTURE_URL,
                bodyReq,
                response -> {
                    onResponse.onResponse((JSONObject)response);
                },
                error -> {
                    OnError.onErrorResponse(error);
                }
        );
        reqQueue.add(request);

    }

    public void getStations(int did, Response.Listener onResponse, Response.ErrorListener OnError) {
        JSONObject bodyReq = new JSONObject();
        try {
            bodyReq.put("sid", Model.getInstance().getMe().getSid());
            bodyReq.put("did", did);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL+GETSTATIONS_URL,
                bodyReq,
                response -> {
                    onResponse.onResponse((JSONObject)response);
                },
                error -> {
                    OnError.onErrorResponse(error);
                }
        );
        reqQueue.add(request);

    }

    public void getOfficialPosts(int did, Response.Listener onResponse, Response.ErrorListener OnError) {

        JSONObject bodyReq = new JSONObject();
        try {
            //bodyReq.put("sid", Model.getInstance().getMe().getSid());
            bodyReq.put("did", did);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                BASE_URL+GETOFFICIALPOST_URL,
                bodyReq,
                response -> {
                    onResponse.onResponse((JSONObject)response);
                },
                error -> {
                    OnError.onErrorResponse(error);
                }
        );
        reqQueue.add(request);

    }

}