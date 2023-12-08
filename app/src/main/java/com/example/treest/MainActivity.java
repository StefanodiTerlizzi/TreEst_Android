package com.example.treest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.example.treest.Wall.FragmentChooseWall;
import com.example.treest.Wall.FragmentOfficialPost;
import com.example.treest.Wall.FragmentViewWall;
import com.example.treest.model.Model;
import com.example.treest.model.OfficialPost;
import com.example.treest.model.User;
import com.google.android.gms.common.util.MapUtils;
import com.google.android.gms.maps.SupportMapFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "treest_debug";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set model context
        Model.getInstance().setSharedPreferences(getApplicationContext());
        // TODO CODING
        if (Model.getInstance().getSid() == null) {
            //primo avvio
            ComunicationController cc = new ComunicationController(this);
            cc.register(
                    response -> {
                        try {
                            String sid = ((JSONObject)response).getString(Model.SID_SP);
                            Model.getInstance().setSid(sid);
                            HandleSelectedWall(TransitionType.DEFAULT);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Log.d(TAG, error.getLocalizedMessage())
            );
        } else {
            //non primo avvio
            HandleSelectedWall(TransitionType.DEFAULT);
        }




        /* TODO: sistemare fa schifo così, setting default profile img for user without image */
        User.setDefaultImg(
                ImageUtility.BitmapToBase64(
                        ImageUtility.drawableToBitmap(
                                getResources().getDrawable(R.drawable.default_profile_img, this.getTheme())
                        )
                )
        );



    }

    public void HandleSelectedWall(TransitionType transitionType) {

        if (Model.getInstance().getDid() == -1) {
            // no bacheca visitata
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerUp, FragmentChooseWall.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null) // name can be null
                    .commit();
        } else {
            //ultima bacheca visitata
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(transitionType.getStart(), transitionType.getEnd())
                    .replace(R.id.fragmentContainerUp, FragmentViewWall.class, null)
                    .setReorderingAllowed(true)
                    .addToBackStack(null) // name can be null
                    .commit();
        }



    }

    public void HandleDettagliTratta(TransitionType transitionType) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(transitionType.getStart(), transitionType.getEnd())
                .replace(R.id.fragmentContainerUp, FragmentDettagliTratta.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null) // name can be null
                .commit();
    }



    public void toMap() {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, FragmentTransaction.TRANSIT_NONE)
                .replace(R.id.fragmentContainerUp, MapFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null) // name can be null
                .commit();
    }

    public void OfficialPost(OfficialPost post) {
        //Log.d(TAG, "OfficialPost: "+post.toString());
        JSONObject p = post.toJSONOBJECT();
        //Log.d(TAG, "OfficialPost: "+p);
        Bundle b = new Bundle();
        b.putString("post", p.toString());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerUp, FragmentOfficialPost.class, b)
                .setReorderingAllowed(true)
                .addToBackStack(null) // name can be null
                .commit();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LocationUtility.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}