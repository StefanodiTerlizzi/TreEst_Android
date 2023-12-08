package com.example.treest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.treest.model.Model;

public class ErrorHandler extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_handler);

        findViewById(R.id.btn_retry).setOnClickListener(v -> this.retry());
    }


    public void retry() {
        Model.getInstance().setNavBarActive(0);
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}