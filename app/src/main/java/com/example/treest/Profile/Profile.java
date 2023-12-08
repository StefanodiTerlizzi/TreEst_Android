package com.example.treest.Profile;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.treest.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Profile extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                    try {
                        final InputStream imageStream = getContentResolver().openInputStream(uri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        ImageView image =  (ImageView)findViewById(R.id.image);
                        image.setImageBitmap(selectedImage);
                        /*
                        image.setImageBitmap(
                                Bitmap.createScaledBitmap(selectedImage, image.getWidth(), image.getHeight(), false)
                        );
                         */
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    //Log.d(MainActivity.TAG, "onActivityResult: "+ uri.toString());
                }
            });

    private int externalStorageRequestPermissionCode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

    }

    public void toModify() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerUp, old_FragmentSet.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null) // name can be null
                .commit();
    }

    public void toView() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerUp, FragmentView.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null) // name can be null
                .commit();
    }

    public void PickImageFromStorage() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_DENIED) {
            // permessi non (ancora) concessi
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    externalStorageRequestPermissionCode
            );

        } else {
            // permessi concessi
            mGetContent.launch("image/*");
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == externalStorageRequestPermissionCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                PickImageFromStorage();
            } else {
                Snackbar.make(
                        findViewById(R.id.image),
                        "if you want to set your profile image enable permissions in settings -> TreEst",
                        Snackbar.LENGTH_LONG).show();
            }
        }
    }

}