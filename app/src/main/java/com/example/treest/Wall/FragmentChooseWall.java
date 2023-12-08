package com.example.treest.Wall;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.collection.ArraySet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.treest.ComunicationController;
import com.example.treest.ErrorHandler;
import com.example.treest.ListLines.Adapter;
import com.example.treest.MainActivity;
import com.example.treest.R;
import com.example.treest.model.Line;
import com.example.treest.model.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;


public class FragmentChooseWall extends Fragment {

    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_wall, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        //sharedPreferences = getContext().getSharedPreferences(Model.NAMESHAREDPREF, 0);
        ComunicationController cc = new ComunicationController(this.getContext());
        cc.getLines(
                response -> {
                    try {
                        Model.getInstance().setLineFromNetwork((JSONObject) response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Intent i = new Intent(getContext(), ErrorHandler.class);
                    startActivity(i);
                    Log.d(MainActivity.TAG, "getlines: "+error.getLocalizedMessage());
                }
        );

        Model.getInstance().getLines().observe(this, o -> updateListUI());

    }

    public void updateListUI() {
        Adapter myAdapter = new Adapter(this);
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myAdapter);
    }

    public void HandleSelectedWall(int did, Line line) {
        Model.getInstance().setDid(did);
        Model.getInstance().setSelectedLine(line);
        Model.getInstance().setNavBarActive(0);
        Intent i = new Intent(getContext(), MainActivity.class);
        startActivity(i);
    }
}