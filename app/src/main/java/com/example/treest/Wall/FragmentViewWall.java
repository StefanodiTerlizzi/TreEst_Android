package com.example.treest.Wall;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.treest.ComunicationController;
import com.example.treest.ErrorHandler;
import com.example.treest.ListPosts.Adapter;
import com.example.treest.ListOfficialPosts.*;
import com.example.treest.MainActivity;
import com.example.treest.R;
import com.example.treest.TransitionType;
import com.example.treest.model.DB.AppDatabase;
import com.example.treest.model.DB.User;
import com.example.treest.model.DB.UserDAO;
import com.example.treest.model.Line;
import com.example.treest.model.Model;
import com.example.treest.model.OfficialPost;
import com.example.treest.model.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FragmentViewWall extends Fragment {

    TextView terminus_from;
    TextView terminus_to;
    ImageButton btn_switch;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_view_wall, container, false);
        terminus_from = v.findViewById(R.id.terminus_from);
        terminus_to = v.findViewById(R.id.terminus_to);
        btn_switch = v.findViewById(R.id.btn_switch);
        return v;
    }

    @Override
    public void onResume() {

        super.onResume();


        ImageButton dettagli_bacheca = getView().findViewById(R.id.btn_dettaglibacheca);
        FloatingActionButton btn_add_post = getView().findViewById(R.id.btn_add_post);

        //swipe down refresh
        /*
        SwipeRefreshLayout swipeRefreshLayout = getView().findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
                    swipeRefreshLayout.setRefreshing(true);
                    onResume();
                    swipeRefreshLayout.setRefreshing(false);
                }
        );
        */



        dettagli_bacheca.setOnClickListener( v -> ((MainActivity)getActivity()).HandleDettagliTratta(TransitionType.ENTER_FROM_RIGHT) );

        btn_add_post.setOnClickListener( v -> /*((MainActivity)getActivity()).HandleAddPost()*/
                new FragmentNewPost().show(getChildFragmentManager(), "new_post_Fragment")
        );

        Line selectedLine = Model.getInstance().getSelectedLine();


        if (selectedLine.getTerminus1().getDid() == Model.getInstance().getDid()) {

            terminus_from.setText(selectedLine.getTerminus2().getSname());

            terminus_to.setText(selectedLine.getTerminus1().getSname());

            btn_switch.setOnClickListener(v -> {
                Model.getInstance().setDid(selectedLine.getTerminus2().getDid());
                onResume();
            });

        } else {

            terminus_from.setText(selectedLine.getTerminus1().getSname());

            terminus_to.setText(selectedLine.getTerminus2().getSname());

            btn_switch.setOnClickListener(v -> {
                Model.getInstance().setDid(selectedLine.getTerminus1().getDid());
                onResume();
            });

        }


        ComunicationController cc = new ComunicationController(getContext());

        cc.getOfficialPosts(
                Model.getInstance().getDid(),
                response -> {
                    List<OfficialPost> officialPosts = new ArrayList<OfficialPost>();
                    try {
                        JSONArray array = ((JSONObject)response).getJSONArray("officialposts");
                        for (int i = 0; i < array.length(); i++) {
                            officialPosts.add(new OfficialPost(array.getJSONObject(i)));
                        }
                        Model.getInstance().setOfficialPosts(officialPosts);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    createOfficialPostList();
                    /*
                    esamegennaio did:[numero della tratta] numero di post ufficiali: [lunghezza dell’array di officialposts]
                    Poi, per ogni oggetto dell’array stampa a log
                    esamegennaio title:[title] timestamp:[timestamp
                    */
                    Log.d("esamegennaio did", Model.getInstance().getDid()+" numero di post ufficiali: "+ officialPosts.size());
                    for (OfficialPost p:officialPosts) {
                        Log.d("esamegennaio title",p.getTitle() +" timestamp: "+p.getDate()+" "+p.getTime() );
                    }
                },
                error -> Log.d(MainActivity.TAG, error.getLocalizedMessage())
        );

        cc.getPosts(
                Model.getInstance().getDid(),
                response -> {
                    try {
                        JSONArray post_json = ((JSONObject)response).getJSONArray("posts");
                        /*
                        List<Post> follow = new ArrayList<>();
                        List<Post> unfollow = new ArrayList<>();
                        for (int i = 0; i < post_json.length(); i++) {
                            Post p = new Post((JSONObject) post_json.get(i));
                            if (p.getFollowing()) {
                                follow.add(p);
                            } else {
                                unfollow.add(p);
                            }
                        }
                        follow.addAll(unfollow);
                        Model.getInstance().setPosts(follow);
                        */
                        List<Post> posts = Post.JSONlistToPostList(post_json);
                        Model.getInstance().setPosts(posts);

                        //check foto con DB TODO

                        AppDatabase db = Room.databaseBuilder(getContext(),AppDatabase.class, Model.DB_NAME).build();
                        UserDAO userDao = db.userDao();

                        new Thread(() -> {

                            List<User> users = userDao.getAll();
                            Map<String, User> usersDBMap = new HashMap<String, User>();
                            for (User u: users) {
                                usersDBMap.put(String.valueOf(u.getUid()), u);
                            }

                            Map<String, User> usersPostersMap = new HashMap<String, User>();
                            for (Post p: posts) {
                                usersPostersMap.put(p.getAuthor(), new User(Integer.valueOf(p.getAuthor()), p.getPversion(), ""));
                            }

                            //DB
                            for (String key : usersPostersMap.keySet()) {

                                User u = new User();
                                u.setUid(usersPostersMap.get(key).getUid());
                                u.setpVersion(usersPostersMap.get(key).getpVersion());
                                u.setPicure(usersPostersMap.get(key).getPicture());

                                if (usersDBMap.containsKey(key) == false) {
                                        userDao.InsertAll(u);
                                } else if (
                                        usersDBMap.containsKey(key) &&
                                        Integer.valueOf(usersDBMap.get(key).getpVersion()) < Integer.valueOf(usersPostersMap.get(key).getpVersion())
                                ) {
                                        userDao.updateAll(u);
                                }

                            }

                            //Log.d(MainActivity.TAG, "usersDBMap: "+usersDBMap);
                            //Log.d(MainActivity.TAG, "usersPostersMap: "+usersPostersMap);


                            new Handler(Looper.getMainLooper()).post( () -> createPostList() );


                        }).start();







                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Intent i = new Intent(getContext(), ErrorHandler.class);
                    startActivity(i);
                    Log.d(MainActivity.TAG, error.getLocalizedMessage());
                }
        );


    }

    public void createOfficialPostList() {
        com.example.treest.ListOfficialPosts.Adapter adapter = new com.example.treest.ListOfficialPosts.Adapter(this);
        RecyclerView recyclerViewOfficialPosts = getView().findViewById(R.id.recyclerviewOfficialPost);
        recyclerViewOfficialPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewOfficialPosts.setAdapter(adapter);
    }

    private void createPostList() {
        Adapter myAdapter = new Adapter(this);
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myAdapter);
    }

    public void unFollow(String author) {
        ComunicationController cc = new ComunicationController(getContext());
        cc.unfollow(
                author,
                response -> {
                    Log.d(MainActivity.TAG, "unFollow: "+author);
                    onResume();
                },
                error -> Log.d(MainActivity.TAG, error.getLocalizedMessage())
        );
    }

    public void follow(String author) {
        ComunicationController cc = new ComunicationController(getContext());
        cc.follow(
                author,
                response -> {
                    Log.d(MainActivity.TAG, "Follow: "+author);
                    onResume();
                },
                error -> Log.d(MainActivity.TAG, error.getLocalizedMessage())
        );
    }

    public void ViewOfficialPost(OfficialPost p) {
        //Log.d(MainActivity.TAG, "ViewOfficialPost: "+p.toString());
        ((MainActivity) getActivity()).OfficialPost(p);
    }
}