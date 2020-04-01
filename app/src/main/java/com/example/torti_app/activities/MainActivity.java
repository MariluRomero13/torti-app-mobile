package com.example.torti_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.example.torti_app.Models.User;
import com.example.torti_app.R;

public class MainActivity extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.pg);
        progressBar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkSesion();
            }
        }, 2000);
    }

    private void checkSesion() {
        if(User.getToken(getApplicationContext()) !=  null) {
            startActivity(new Intent(getApplicationContext(), DeliveryHistoryActivity.class));
        } else {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        finish();
    }
}
