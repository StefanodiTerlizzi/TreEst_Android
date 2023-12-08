package com.example.treest.ListPosts;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treest.R;
import com.example.treest.Wall.FragmentViewWall;
import com.example.treest.model.Model;
import com.example.treest.model.Post;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {
    private LayoutInflater inflater;
    private FragmentViewWall fragmentViewWall;

    public Adapter(FragmentViewWall fragmentViewWall) {
        this.inflater = LayoutInflater.from(fragmentViewWall.getContext());
        this.fragmentViewWall = fragmentViewWall;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.post_element, parent, false), fragmentViewWall);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post p = Model.getInstance().getPosts().get(position);
        holder.set( p );


    }

    @Override
    public int getItemCount() {
        return Model.getInstance().getPosts().size();
    }
}
