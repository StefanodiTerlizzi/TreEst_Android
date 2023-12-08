package com.example.treest.Profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.treest.ComunicationController;
import com.example.treest.ErrorHandler;
import com.example.treest.ImageUtility;
import com.example.treest.MainActivity;
import com.example.treest.R;
import com.example.treest.model.Model;
import com.example.treest.model.User;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;


public class FragmentView extends Fragment {

    //TextView uid;
    TextView name;
    //TextView pversion;
    ImageView image;
    EditText editName;
    ImageView modName;
    TextView undo;
    TextView btn_save;

    public FragmentView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_viewprofile, container, false);

        //uid = view.findViewById(R.id.uid);
        //name = view.findViewById(R.id.name);
        //pversion = view.findViewById(R.id.pversion);
        image = view.findViewById(R.id.image);
        editName = view.findViewById(R.id.editName);
        undo = view.findViewById(R.id.undo);
        btn_save = view.findViewById(R.id.save);

        /*
        view.findViewById(R.id.btn_modify).setOnClickListener(
                v -> ((Profile) getActivity()).toModify()
        );
        */

        undo.setOnClickListener(v->{
            if (Model.getInstance().getMe().getpVersion() != 0) {
                image.setImageBitmap(
                        ImageUtility.Base64toBitmap(Model.getInstance().getMe().getPicture().getValue())
                );
            }
            editName.setText(Model.getInstance().getMe().getName().getValue());
        });

        Model.getInstance().getMe().getPicture().observe(
                getViewLifecycleOwner(),
                s -> {
                    if (Model.getInstance().getMe().getpVersion() != 0) {
                        image.setImageBitmap(
                            ImageUtility.Base64toBitmap(Model.getInstance().getMe().getPicture().getValue())
                        );
                    }
                }
        );

        btn_save.setOnClickListener(
                v -> {

                    JSONObject toSend = new JSONObject();
                    String toSend_name = null;
                    String toSend_img = null;

                    if ( !( editName.getText().toString().equals(Model.getInstance().getMe().getName().getValue()) ) && editName.getText().toString().length() < 20) {
                        //nome è cambiato
                        Log.d(MainActivity.TAG, "name: setting");
                        toSend_name = editName.getText().toString();
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
                    /* check immagini uguale ??
                    Log.d(MainActivity.TAG, "image");
                    Log.d(MainActivity.TAG, String.valueOf(ImageUtility.BitmapToBase64noCompression(ImageUtility.drawableToBitmap(image.getDrawable())).equals(Model.getInstance().getMe().getPicture().getValue())));
                    Log.d(MainActivity.TAG, String.valueOf(ImageUtility.BitmapToBase64(ImageUtility.drawableToBitmap(image.getDrawable())).equals(Model.getInstance().getMe().getPicture().getValue())));
                    */

                    if (
                            !(ImageUtility.BitmapToBase64(ImageUtility.drawableToBitmap(image.getDrawable())).equals(User.getDefaultImg())) /*&&
                                    !(ImageUtility.BitmapToBase64(ImageUtility.drawableToBitmap(image.getDrawable())).equals(Model.getInstance().getMe().getPicture().getValue())) &&
                                    !(ImageUtility.BitmapToBase64noCompression(ImageUtility.drawableToBitmap(image.getDrawable())).equals(Model.getInstance().getMe().getPicture().getValue()))*/
                    ) {

                        //immagine diversa da default
                        int dimensione = ImageUtility.BitmapToBase64(ImageUtility.drawableToBitmap(image.getDrawable())).length();
                        if (dimensione >= 137000) {
                            Snackbar.make(
                                    getActivity().findViewById(R.id.fragmentContainerBottom),
                                    "l'immagine non può pesare più di 100KB",
                                    Snackbar.LENGTH_LONG).show();
                            toView();
                        }
                        Boolean quadrato = ImageUtility.drawableToBitmap(image.getDrawable()).getHeight() == ImageUtility.drawableToBitmap(image.getDrawable()).getWidth();
                        if (!(quadrato)) {
                            Snackbar.make(
                                    getActivity().findViewById(R.id.fragmentContainerBottom),
                                    "l'immagine deve essere quadrata",
                                    Snackbar.LENGTH_LONG).show();
                            toView();
                        }

                        if (dimensione < 137000 && quadrato ) {
                            Log.d(MainActivity.TAG, "image: setting");
                            toSend_img = ImageUtility.BitmapToBase64(
                                    ImageUtility.drawableToBitmap(
                                            image.getDrawable()
                                    )
                            );
                        }
                    }



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
                                            "profilo aggiornato correttamente",
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



        Model.getInstance().getMe().getName().observe(
                getViewLifecycleOwner(),
                s -> {
                    //Log.d(MainActivity.TAG, "11: send uid to server");
                    //name.setText(s);
                    editName.setText(s);
                }
        );

        view.findViewById(R.id.editImage).setOnClickListener(v -> ((Profile) getActivity()).PickImageFromStorage());


        ComunicationController cc = new ComunicationController(view.getContext());
        cc.getProfile(
                response -> {
                    //Log.d(MainActivity.TAG, response.getClass().toString());
                    try {

                        Model.getInstance().getMe().setUid(
                                ((JSONObject)response).getString("uid")
                        );
                        Model.getInstance().getMe().setName(
                                ((JSONObject)response).getString("name")
                        );
                        Model.getInstance().getMe().setPversion(
                                Integer.valueOf(
                                        ((JSONObject)response).getString("pversion")
                                )
                        );
                        Model.getInstance().getMe().setPicture(
                                        ((JSONObject)response).getString("picture")
                        );
                        //set();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Intent i = new Intent(getContext(), ErrorHandler.class);
                    startActivity(i);
                    Log.d(MainActivity.TAG, error.toString());
                }
        );

        return view;
    }

    public void toView() {
        ((Profile) getActivity()).toView();
    }


    public void set() {
        //uid.setText(Model.getInstance().getUser().getUid());
        //name.setText(Model.getInstance().getMe().getName().getValue());
        //pversion.setText(String.valueOf(Model.getInstance().getMe().getpVersion()));
    }
}