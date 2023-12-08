package com.example.treest;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.treest.listStations.Adapter;
import com.example.treest.model.Model;
import com.example.treest.model.Station;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentDettagliTratta extends Fragment {
    private int did;
    TextView direzione;
    CardView btn_maps;


    public FragmentDettagliTratta() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        did = getContext().getSharedPreferences(Model.NAMESHAREDPREF, 0).getInt(Model.WALL_SP, -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dettagli_tratta, container, false);

        direzione = v.findViewById(R.id.direzione);
        btn_maps = v.findViewById(R.id.btn_maps);

        btn_maps.setOnClickListener(v1 -> {
            ((MainActivity)getActivity()).toMap();
        });



        ComunicationController cc = new ComunicationController(getContext());
        cc.getStations(
                did,
                (response) -> {
                    List<Station> stations = new ArrayList<>();

                    try {
                        JSONArray array = ((JSONObject)response).getJSONArray("stations");
                        for (int i = 0; i < array.length(); i++) {
                            Station s = new Station((JSONObject) array.get(i));
                            stations.add(s);
                        }
                        Model.getInstance().setStations(stations);
                        drawStations();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                (error) -> {
                    Intent i = new Intent(getContext(), ErrorHandler.class);
                    startActivity(i);
                    Log.d(MainActivity.TAG, "error: "+error.getLocalizedMessage());
                }
        );
        return v;
    }

    private void drawStations() {
        List<Station> s = Model.getInstance().getStations();
        Log.d(MainActivity.TAG, "drawStations: "+s);

        direzione.setText(s.get(s.size()-1).getName());

        Adapter myAdapter = new Adapter(this);
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((ImageView) getView().findViewById(R.id.btn_back)).setOnClickListener(
                v -> ((MainActivity) getActivity()).HandleSelectedWall(TransitionType.ENTER_FROM_LEFT)
        );

    }
}