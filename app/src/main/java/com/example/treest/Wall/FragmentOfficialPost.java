package com.example.treest.Wall;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.treest.ComunicationController;
import com.example.treest.MainActivity;
import com.example.treest.R;
import com.example.treest.TransitionType;
import com.example.treest.model.Model;
import com.example.treest.model.OfficialPost;
import com.example.treest.model.Post;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentOfficialPost extends DialogFragment {

    OfficialPost post;

    TextView title;
    TextView description;
    TextView date;
    TextView time;


    public FragmentOfficialPost() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            //this.post = bundle.getInt("position", -1);
            String x = bundle.getString("post");
            //Log.d(MainActivity.TAG, "bundle: "+bundle.getString("post"));
            //this.post = new OfficialPost(bundle)
            try {
                JSONObject j = new JSONObject(x);
                this.post = new OfficialPost(j);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Log.d(MainActivity.TAG, "off: "+this.post.toString());
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_officialpost, container, false);


        this.title = v.findViewById(R.id.title);
        this.description = v.findViewById(R.id.description);
        this.date = v.findViewById(R.id.date);
        this.time = v.findViewById(R.id.time);



        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.title.setText(post.getTitle());
        this.description.setText(post.getDescription());
        this.date.setText(post.getDate());
        this.time.setText(post.getTime());

        ((ImageView) getView().findViewById(R.id.btn_back)).setOnClickListener(
                v -> ((MainActivity) getActivity()).HandleSelectedWall(TransitionType.DEFAULT)
        );
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
