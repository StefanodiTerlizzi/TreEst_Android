package com.example.treest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.OnTokenCanceledListener;

public class LocationUtility {
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 0;
    private static FusedLocationProviderClient fusedLocationProviderClient = null;
    private static Activity activity = null;
    private static LocationHandler locationHandler = null;

    public static void set(Activity act, LocationHandler handler) {
        activity = act;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
        locationHandler = handler;
    }

    public static boolean checkLocationPermission() throws Error {
        /*if (fusedLocationProviderClient == null || activity == null) {
            throw new Error("paramentri non settati");
        }*/

        if (fusedLocationProviderClient == null && activity == null) throw new Error("error in LocationUtility.checkLocationPermission(),LocationUtility.activity is not set, use LocationUtility.set() to set");

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_DENIED) {
            // permessi non (ancora) concessi
            return false;
        } else {
            // permessi concessi
            return true;
        }
    }




    public static void requestLocationPermission() {
        ActivityCompat.requestPermissions(
                activity,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSION_ACCESS_FINE_LOCATION
        );
    }

    @SuppressLint("MissingPermission") //ok perchè la chiamo solo dopo aver controllato i permessi
    public static void onGrantedLocationPermission() throws Error {
        if (fusedLocationProviderClient == null && activity == null) throw new Error("error in LocationUtility.onGrantedLocationPermission(),LocationUtility.activity is not set, use LocationUtility.set() to set");
        fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, new CancellationToken() {
            @NonNull
            @Override
            public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                Log.d(MainActivity.TAG, "onCanceledRequested: "+onTokenCanceledListener.toString());
                return null;
            }
            @Override
            public boolean isCancellationRequested() {
                return false;
            }
        }).addOnSuccessListener(activity, location -> {
            locationHandler.HandleLocationResponse(location);
            //Log.d( MainActivity.TAG, location.toString() );
        });
    }


    /*
    @SuppressLint("MissingPermission")
    public static void getCurrentLocation() {
        if (    ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_DENIED
        ) {
            // permessi non (ancora) concessi
            requestLocationPermission();
        } else {
            fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, new CancellationToken() {
                @NonNull
                @Override
                public CancellationToken onCanceledRequested(@NonNull OnTokenCanceledListener onTokenCanceledListener) {
                    Log.d(MainActivity.TAG, "onCanceledRequested: "+onTokenCanceledListener.toString());
                    return null;
                }

                @Override
                public boolean isCancellationRequested() {
                    return false;
                }
            }).addOnSuccessListener(activity, location -> {

                Log.d( MainActivity.TAG, location.toString() );
            });
        }

    }
    */

    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //Log.d(MainActivity.TAG, "onRequestPermissionsResult");
        //Log.d(MainActivity.TAG+" requestcode: ", String.valueOf(requestCode));
        //Log.d(MainActivity.TAG+" permission: ", String.valueOf(permissions[0]));
        //Log.d(MainActivity.TAG+" grantResults: ", String.valueOf(grantResults[0]));
        //Log.d(MainActivity.TAG+" PackageManager.PERMISSION_GRANTED: ", String.valueOf(PackageManager.PERMISSION_GRANTED));

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            LocationUtility.onGrantedLocationPermission();
        } else {
            locationHandler.HandleNonPermission();
        }
    }
}
