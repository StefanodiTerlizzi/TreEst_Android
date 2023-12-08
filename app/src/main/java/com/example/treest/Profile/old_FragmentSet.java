package com.example.treest.Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.treest.ComunicationController;
import com.example.treest.ImageUtility;
import com.example.treest.MainActivity;
import com.example.treest.R;
import com.example.treest.model.Model;
import com.example.treest.model.User;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;


public class old_FragmentSet extends Fragment {

    private EditText name;
    private ImageView image;
    private Button btn_save;


    public old_FragmentSet() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.old_fragment_setprofile, container, false);

        name = view.findViewById(R.id.name);
        image = view.findViewById(R.id.image);

        btn_save = view.findViewById(R.id.btn_save);
        name.setText(Model.getInstance().getMe().getName().getValue());

        if (Model.getInstance().getMe().getpVersion() != 0) {
            image.setImageBitmap(ImageUtility.Base64toBitmap(Model.getInstance().getMe().getPicture().getValue()));
        }

        image.setOnClickListener(v -> ((Profile) getActivity()).PickImageFromStorage());


        btn_save.setOnClickListener(
                v -> {

                    JSONObject toSend = new JSONObject();
                    String toSend_name = null;
                    String toSend_img = null;

                    if ( !( name.getText().toString().equals(Model.getInstance().getMe().getName().getValue()) ) ) {
                        //nome è cambiato
                        Log.d(MainActivity.TAG, "name: setting");
                        toSend_name = name.getText().toString();
                    }


                    Log.d(MainActivity.TAG, "check: "+
                            !(
                                    ImageUtility.BitmapToBase64(
                                            ImageUtility.drawableToBitmap(image.getDrawable())
                                    ).equals(User.getDefaultImg())
                            ) + " " +
                            !(
                                    ImageUtility.BitmapToBase64(
                                            ImageUtility.drawableToBitmap(image.getDrawable())
                                    ).equals(Model.getInstance().getMe().getPicture().getValue())
                            )

                    );

                    //Log.d(MainActivity.TAG, "image1: \n"+ImageUtility.BitmapToBase64( ImageUtility.drawableToBitmap(image.getDrawable()) ));
                    //Log.d(MainActivity.TAG, "image2: \n"+Model.getInstance().getMe().getPicture().getValue() );
                    /* TODO CHECK immaggini quadrate prima di caricare*/

                    if (
                            !(ImageUtility.BitmapToBase64(ImageUtility.drawableToBitmap(image.getDrawable())).equals(User.getDefaultImg())) &&
                            !(ImageUtility.BitmapToBase64(ImageUtility.drawableToBitmap(image.getDrawable())).equals(Model.getInstance().getMe().getPicture().getValue()))
                    ) {

                        //immagine diversa da prima e divresa da default
                        int dimensione = ImageUtility.BitmapToBase64(ImageUtility.drawableToBitmap(image.getDrawable())).length();
                        if (dimensione >= 137000) {
                            Snackbar.make(
                                    getActivity().findViewById(R.id.fragmentContainerBottom),
                                    "The image cannot weigh more than 100KB",
                                    Snackbar.LENGTH_LONG).show();
                            toView();
                        }
                        Boolean quadrato = ImageUtility.drawableToBitmap(image.getDrawable()).getHeight() == ImageUtility.drawableToBitmap(image.getDrawable()).getWidth();
                        if (!(quadrato)) {
                            Snackbar.make(
                                    getActivity().findViewById(R.id.fragmentContainerBottom),
                                    "The image should have ratio 1:1",
                                    Snackbar.LENGTH_LONG).show();
                            toView();
                        }

                        if (dimensione < 137000 && quadrato) {
                            Log.d(MainActivity.TAG, "image: setting");
                            toSend_img = ImageUtility.BitmapToBase64(
                                    ImageUtility.drawableToBitmap(
                                            image.getDrawable()
                                    )
                            );
                        }
                    }

                    /* old funzionante
                    if
                    (
                        !(
                                ImageUtility.BitmapToBase64(
                                        ImageUtility.drawableToBitmap(image.getDrawable())
                                ).equals(User.getDefaultImg())
                        ) &&
                        !(
                                ImageUtility.BitmapToBase64(
                                        ImageUtility.drawableToBitmap(image.getDrawable())
                                ).equals(Model.getInstance().getMe().getPicture().getValue())
                        ) &&
                        ImageUtility.BitmapToBase64(
                                ImageUtility.drawableToBitmap(image.getDrawable())
                        ).length() < 137000

                    ) {
                        //immagine è cambiata
                        Log.d(MainActivity.TAG, "image: setting");
                        toSend_img = ImageUtility.BitmapToBase64(
                                ImageUtility.drawableToBitmap(
                                        image.getDrawable()
                                )
                        );
                    }


                    if (ImageUtility.BitmapToBase64(ImageUtility.drawableToBitmap(image.getDrawable())).length() < 137000) {
                        Snackbar.make(
                                getActivity().findViewById(R.id.fragmentContainerBottom),
                                "The image cannot weigh more than 100KB",
                                Snackbar.LENGTH_LONG).show();
                        toView();
                    }
                    */

                    Boolean itsTimeToSend = false;
                    try {
                        if (toSend_name != null) {
                            toSend.put("name", toSend_name);
                            Log.d(MainActivity.TAG, "changed: name");
                            itsTimeToSend = true;
                        }
                        if (toSend_img != null) {
                            toSend.put("picture", toSend_img);
                            Log.d(MainActivity.TAG, "changed: picture");
                            itsTimeToSend = true;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    //Log.d(MainActivity.TAG, "send data:"+toSend.toString());
                    if (itsTimeToSend) {
                        ComunicationController cc = new ComunicationController(getContext());
                        cc.setProfile(
                                toSend,
                                response -> {
                                    Snackbar.make(
                                            getActivity().findViewById(R.id.fragmentContainerBottom),
                                            "Profile update correctly",
                                            Snackbar.LENGTH_LONG).show();
                                    toView();
                                },
                                error -> {
                                    Log.d(MainActivity.TAG, "errore mentre cercavo di aggiornare il profilo: "+ error.getLocalizedMessage());
                                    toView();
                                }
                        );
                    } else {
                        toView();
                    }

                }
        );
        return view;
    }

    public void toView() {
        ((Profile) getActivity()).toView();
    }


}