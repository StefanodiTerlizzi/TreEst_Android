package com.example.treest.listStations;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treest.FragmentDettagliTratta;
import com.example.treest.R;
import com.example.treest.model.Model;
import com.example.treest.model.Station;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {
    private LayoutInflater inflater;
    private FragmentDettagliTratta fragmentDettagliTratta;

    public Adapter(FragmentDettagliTratta fragmentDettagliTratta) {
        this.inflater = LayoutInflater.from(fragmentDettagliTratta.getContext());
        this.fragmentDettagliTratta = fragmentDettagliTratta;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.station_element, parent, false), fragmentDettagliTratta);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Station s = Model.getInstance().getStations().get(position);
        String pos = "";
        if (position == 0) {
            pos = "first";
        } else if (position == Model.getInstance().getStations().size()-1) {
            pos = "last";
        }
        holder.set(s, pos);
    }

    @Override
    public int getItemCount() {
        return Model.getInstance().getStations().size();
    }
}
