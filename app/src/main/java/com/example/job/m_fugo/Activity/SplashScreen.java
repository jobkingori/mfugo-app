package com.example.job.m_fugo.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.job.m_fugo.R;

import static com.example.job.m_fugo.Activity.Login.MyPREF;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences editor = getSharedPreferences(MyPREF, MODE_PRIVATE);

                int id = editor.getInt("ID", 0);

                if (id >= (1)) {
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else if (id == (2)) {
                    Intent i = new Intent(SplashScreen.this, VetsActivity.class);
                    startActivity(i);
                    finish();
                }
                else if (id == (0)) {
                    Intent i = new Intent(SplashScreen.this, Login.class);
                    startActivity(i);
                    finish();
                }

            }
        }, 2000);

    }
}