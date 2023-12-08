package com.example.treest.Wall;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.treest.ComunicationController;
import com.example.treest.MainActivity;
import com.example.treest.R;
import com.example.treest.TransitionType;
import com.example.treest.model.Model;
import com.example.treest.model.Post;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;


public class FragmentNewPost extends DialogFragment {

    SharedPreferences sharedPreferences;

    Boolean savePost = true;

    //element
    Spinner spinner_delay;
    Spinner spinner_status;
    EditText comment;

    public FragmentNewPost() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_post, container, false);

        sharedPreferences = getContext().getSharedPreferences(Model.NAMESHAREDPREF, 0);

        //drop-down delay
        spinner_delay = v.findViewById(R.id.spinner_delay);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter_delay = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, Post.getListDelay());
        // Specify the layout to use when the list of choices appears
        adapter_delay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_delay.setAdapter(adapter_delay);

        //drop-down delay
        spinner_status = v.findViewById(R.id.spinner_status);
        ArrayAdapter<String> adapter_status = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, Post.getListStatus());
        adapter_status.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_status.setAdapter(adapter_status);

        //comment
        comment = v.findViewById(R.id.inp_comment);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        //ripristino post
        String postSP = sharedPreferences.getString(Model.POSTCONTENT_SP, null);
        if (postSP != null) {
            try {
                JSONObject post = new JSONObject(postSP);
                Log.d(MainActivity.TAG, "reset post from SP: "+post);
                if (post.has("delay")) {
                    int spinnerPosition =
                    ((ArrayAdapter<String>) spinner_delay.getAdapter())
                    .getPosition(Post.getStringFromDelay(post.getInt("delay")));

                    spinner_delay.setSelection(spinnerPosition);
                }
                if (post.has("status")) {
                    int spinnerPosition =
                    ((ArrayAdapter<String>) spinner_status.getAdapter())
                    .getPosition(Post.getStringFromStatus(post.getInt("status")));

                    spinner_status.setSelection(spinnerPosition);
                }
                if (post.has("comment")) {
                    comment.setText(post.getString("comment"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        getView().findViewById(R.id.btn_result).setOnClickListener(
        v -> {
            JSONObject toSend = createPost();
            if (toSend.has("delay") || toSend.has("status") || toSend.has("comment")) {
                ComunicationController cc = new ComunicationController(getContext());
                cc.addPost(
                        toSend,
                        response -> {
                            Log.d(MainActivity.TAG, "send Post");
                            sharedPreferences.edit().remove(Model.POSTCONTENT_SP).commit();
                            savePost = false;
                            ((MainActivity)getActivity()).HandleSelectedWall(TransitionType.DEFAULT);
                        },
                        error ->  {
                            Snackbar.make(
                                    getActivity().findViewById(R.id.fragmentContainerBottom),"problema di connessione, riprova più tardi", Snackbar.LENGTH_LONG).show();
                            Log.d(MainActivity.TAG, "not send");
                        }
                );
            }
        });

        getView().findViewById(R.id.btn_trash).setOnClickListener(
                v -> {
                    Log.d(MainActivity.TAG, "delete Post");
                    sharedPreferences.edit().remove(Model.POSTCONTENT_SP).commit();
                    savePost = false;
                    ((MainActivity)getActivity()).HandleSelectedWall(TransitionType.DEFAULT);
                }
        );
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(MainActivity.TAG, "onPause save post: "+savePost);
        if (savePost) {
            sharedPreferences.edit().putString(Model.POSTCONTENT_SP, createPost().toString()).commit();
        }
    }

    public JSONObject createPost() {
        JSONObject toSend = new JSONObject();
        int delay = Post.getDelayFromString(spinner_delay.getSelectedItem().toString());
        int status = Post.getStatusFromString(spinner_status.getSelectedItem().toString());
        try {
            toSend.put("sid", sharedPreferences.getString(Model.SID_SP, null) );
            toSend.put("did", sharedPreferences.getInt(Model.WALL_SP, -1) );
            if (delay != -1) {
                toSend.put("delay", delay);
            }
            if (status != -1) {
                toSend.put("status", status);
            }
            if (comment.getText().length() != 0) {
                toSend.put("comment", comment.getText());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return toSend;
    }

}