package com.example.treest.ListOfficialPosts;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.treest.ComunicationController;
import com.example.treest.ImageUtility;
import com.example.treest.MainActivity;
import com.example.treest.R;
import com.example.treest.Wall.FragmentViewWall;
import com.example.treest.model.DB.AppDatabase;
import com.example.treest.model.DB.User;
import com.example.treest.model.DB.UserDAO;
import com.example.treest.model.Model;
import com.example.treest.model.OfficialPost;
import com.example.treest.model.Post;
import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;

import java.util.List;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView date;
    TextView time;
    TextView description;
    FragmentViewWall fragmentViewWall;
    ConstraintLayout background;

    public ViewHolder(@NonNull View itemView, FragmentViewWall fragmentViewWall) {
        super(itemView);

        this.title = itemView.findViewById(R.id.title);
        this.description = itemView.findViewById(R.id.description);
        this.date = itemView.findViewById(R.id.date);
        this.time = itemView.findViewById(R.id.time);
        this.fragmentViewWall = fragmentViewWall;
        this.background = itemView.findViewById(R.id.background);
    }

    public void set(OfficialPost p) {

        title.setText(p.getTitle());
        description.setText(p.getDescription());
        date.setText(p.getDate());
        time.setText(p.getTime());
        background.setOnClickListener( v -> fragmentViewWall.ViewOfficialPost(p));

        /*
        String[] datetime = p.getDateTime().split(" ");
        date.setText(datetime[0]);
        time.setText(datetime[1]);
        */

    }


}
