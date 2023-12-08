package com.example.treest.listStations;


import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treest.FragmentDettagliTratta;
import com.example.treest.ImageUtility;
import com.example.treest.R;
import com.example.treest.model.Station;

public class ViewHolder extends RecyclerView.ViewHolder {
    FragmentDettagliTratta fragmentDettagliTratta;
    TextView name;
    ImageView img;

    public ViewHolder(@NonNull View itemView, FragmentDettagliTratta fragmentDettagliTratta) {
        super(itemView);
        this.fragmentDettagliTratta = fragmentDettagliTratta;
        this.name = itemView.findViewById(R.id.name);
        this.img = itemView.findViewById(R.id.imageView);
    }

    public void set(Station s, String position) {
        name.setText(s.getName());
        Drawable d = fragmentDettagliTratta.getResources().getDrawable(R.drawable.line_2, fragmentDettagliTratta.getActivity().getTheme());

        if (position.equals("first")) {
            d = fragmentDettagliTratta.getResources().getDrawable(R.drawable.line_1, fragmentDettagliTratta.getActivity().getTheme());
        } else if (position.equals("last")) {
            d = fragmentDettagliTratta.getResources().getDrawable(R.drawable.line_3, fragmentDettagliTratta.getActivity().getTheme());
        }
        img.setImageDrawable(d);
    }


}
