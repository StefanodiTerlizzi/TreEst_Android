package com.example.treest.ListPosts;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.treest.ComunicationController;
import com.example.treest.ImageUtility;
import com.example.treest.MainActivity;
import com.example.treest.R;
import com.example.treest.Wall.FragmentViewWall;
import com.example.treest.model.DB.*;
import com.example.treest.model.Model;
import com.example.treest.model.Post;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView delay;
    TextView status;
    TextView comment;
    TextView authorName;
    TextView date;
    TextView time;
    MaterialButton btn_follow;
    ImageView profile;
    //TextView uid;

    FragmentViewWall fragmentViewWall;

    public ViewHolder(@NonNull View itemView, FragmentViewWall fragmentViewWall) {
        super(itemView);

        this.delay = itemView.findViewById(R.id.delay);
        this.status = itemView.findViewById(R.id.status);
        this.comment = itemView.findViewById(R.id.comment);
        this.authorName = itemView.findViewById(R.id.author);
        this.date = itemView.findViewById(R.id.date);
        this.time = itemView.findViewById(R.id.time);
        this.btn_follow = itemView.findViewById(R.id.follow);
        this.profile = itemView.findViewById(R.id.imageView);

        //this.uid = itemView.findViewById(R.id.uid);


        this.fragmentViewWall = fragmentViewWall;
    }

    public void set(Post p) {
        delay.setText(p.getDelay());
        status.setText(p.getStatus());
        comment.setText(p.getComment());
        authorName.setText(p.getAuthorName());
        String[] datetime = p.getDateTime().split(" ");
        date.setText(datetime[0]);
        time.setText(datetime[1]);
        //uid.setText(p.getAuthor());



        if (p.getFollowing()) {
            btn_follow.setText("segui già");
            btn_follow.setIconResource(R.drawable.person_remove);
            btn_follow.setPressed(true);
            btn_follow.setOnClickListener(v -> fragmentViewWall.unFollow(p.getAuthor()) );
        } else {
            btn_follow.setText("segui");
            btn_follow.setIconResource(R.drawable.person_add);
            btn_follow.setPressed(false);
            btn_follow.setOnClickListener(v -> fragmentViewWall.follow(p.getAuthor()) );
        }


        AppDatabase db = Room.databaseBuilder(fragmentViewWall.getContext(), AppDatabase.class, Model.DB_NAME).build();
        UserDAO userDao = db.userDao();
        new Thread(() -> {
            List<User> users = userDao.getAll();

            users.removeIf(u -> u.getUid() != Integer.valueOf(p.getAuthor()));

            if (users.get(0).getPicture().equals("")) {
                //Log.d(MainActivity.TAG, "scarica immagine "+users.get(0));
                setImageFromNetwork(users.get(0));
            } else {
                //Log.d(MainActivity.TAG, "img db "+users.get(0));
                setImageFromDB(users.get(0));
            }

        }).start();

    }

    private void setImageFromDB(User u) {
        Bitmap b = ImageUtility.Base64toBitmap(u.getPicture());

        if (b == null) {
            b = ImageUtility.drawableToBitmap(
                    fragmentViewWall.getActivity().getDrawable(R.drawable.default_profile_img)
            );
        }

        Bitmap finalB = b;
        new Handler(Looper.getMainLooper()).post(
                () -> this.profile.setImageBitmap(finalB)
        );

    }


    public void setImageFromNetwork(User u) {
        ComunicationController cc = new ComunicationController(fragmentViewWall.getContext());

        cc.getUserPicture(
                u.getUid(),
                response -> {
                    try {
                        String img = ((JSONObject)response).getString("picture");

                        Bitmap b = ImageUtility.Base64toBitmap(img);

                        Log.d(MainActivity.TAG, "setImageFromNetwork: "+u+", "+b);

                        if (b != null) {
                            //conversione immagine ok
                            this.profile.setImageBitmap(b);
                        } else {
                            this.profile.setImageDrawable(
                                    fragmentViewWall.getActivity().getDrawable(R.drawable.default_profile_img)
                            );
                        }

                        AppDatabase db = Room.databaseBuilder(fragmentViewWall.getContext(), AppDatabase.class, Model.DB_NAME).build();
                        UserDAO userDao = db.userDao();

                        u.setPicure(img);

                        new Thread(() -> userDao.updateAll(u)).start();

                    } catch (Exception e) {
                        Log.d(MainActivity.TAG, e.getLocalizedMessage());
                    }
                },
                error -> Log.d(MainActivity.TAG, "set: "+error.getLocalizedMessage())
        );

    }


}
