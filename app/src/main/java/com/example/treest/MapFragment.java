package com.example.treest;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.treest.model.Model;
import com.example.treest.model.Station;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    GoogleMap googleMap = null;

    public MapFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mappa_fragm);
        mapFragment.getMapAsync(this);

        LocationUtility.set(getActivity(), new LocationHandler() {
                    @Override
                    public void HandleLocationResponse(Location location) {
                        if (googleMap != null) {
                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(location.getLatitude(), location.getLongitude()))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                    .title("sei quì"));

                            Log.d(MainActivity.TAG, "Handler Location: " + location.toString());
                        } else {
                            Snackbar.make(v.findViewById(R.id.mappa_fragm), "ci dispiace, abbiamo un problema con la localizzazione, riprova", Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void HandleNonPermission() {
                        Snackbar.make(v.findViewById(R.id.mappa_fragm), "non hai dato i permessi per la tua posizione, puoi sempre concederli dalle impostazioni", Snackbar.LENGTH_LONG).show();
                    }
        });

        try {
            if (LocationUtility.checkLocationPermission()) {
                LocationUtility.onGrantedLocationPermission();
            } else {
                LocationUtility.requestLocationPermission();
            }
        } catch (Error e) {
            Log.e(MainActivity.TAG, "error while using location: "+e.getLocalizedMessage());
        }


        v.findViewById(R.id.btn_back_map).setOnClickListener(v1 -> ((MainActivity)getActivity()).HandleDettagliTratta(TransitionType.ENTER_FROM_LEFT));

        return v;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        this.googleMap = googleMap;

        for ( Station s: Model.getInstance().getStations() ) {
            this.googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(s.getLat(), s.getLon()))
                    .title(s.getName()));
        }

        LatLng first = new LatLng(
                Model.getInstance().getStations().get(0).getLat(),
                Model.getInstance().getStations().get(0).getLon()
        );
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(first));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(first)      // Sets the center of the map to Mountain View
                .zoom(11)                   // Sets the zoom
                .bearing(0)                // Sets the orientation of the camera to east
                .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder


        this.googleMap.addPolyline( createPoly( Model.getInstance().getStations() ) );

        this.googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }


    public PolylineOptions createPoly(List<Station> stations) {
        PolylineOptions polylineOptions = new PolylineOptions();
        List<LatLng> points = new ArrayList<>();
        for (Station s: stations) {
            points.add( new LatLng(s.getLat(), s.getLon()) );
        }

        LatLng[] array = new LatLng[points.size()];
        points.toArray(array); // fill the array

        polylineOptions.add(array).color(Color.RED).clickable(false);
        return polylineOptions;
    }
}