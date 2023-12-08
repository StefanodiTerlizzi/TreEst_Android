package com.example.treest.ListLines;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treest.Wall.FragmentChooseWall;
import com.example.treest.R;
import com.example.treest.model.Model;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {
    private LayoutInflater inflater;
    private FragmentChooseWall fragmentChooseWall;

    public Adapter(FragmentChooseWall fragmentChooseWall) {
        this.inflater = LayoutInflater.from(fragmentChooseWall.getContext());
        this.fragmentChooseWall = fragmentChooseWall;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.line_element, parent, false), fragmentChooseWall);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Log.d(MainActivity.TAG, "debug: "+Model.getInstance().getLine(position));
        holder.set( Model.getInstance().getLine(position) );
    }

    @Override
    public int getItemCount() {
        return Model.getInstance().getLineSize();
    }
}
