package com.example.treest;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.treest.Profile.Profile;
import com.example.treest.Wall.ChooseWall;
import com.example.treest.model.Model;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomMenu extends Fragment {

    BottomNavigationView bottomNavigationView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_menu, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        bottomNavigationView = getView().findViewById(R.id.navigation);

        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            MenuItem item = bottomNavigationView.getMenu().getItem(i);
            if (Model.getInstance().getNavBarActive() == i) {
                bottomNavigationView.setSelectedItemId(item.getItemId());
            }
        }

        //btn_bacheca
        getView().findViewById(R.id.btn_home).setOnClickListener(v -> {
            if (v.getContext().getClass() != MainActivity.class) {
                Model.getInstance().setNavBarActive(0);
                Intent i = new Intent(v.getContext(), MainActivity.class);
                startActivity(i);
            }

        });

        //btn_profilo
        getView().findViewById(R.id.btn_profile).setOnClickListener(v -> {
            if (v.getContext().getClass() != Profile.class) {
                Model.getInstance().setNavBarActive(1);
                Intent i = new Intent(v.getContext(), Profile.class);
                startActivity(i);
            }

        });

        //btn_chooseWall
        getView().findViewById(R.id.btn_chooseWall).setOnClickListener(v -> {
            if (v.getContext().getClass() != ChooseWall.class) {
                Model.getInstance().setNavBarActive(2);
                Intent i = new Intent(v.getContext(), ChooseWall.class);
                startActivity(i);
            }
        });




    }
}