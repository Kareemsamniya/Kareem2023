package com.example.kareem2023;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private TextView etSplashWelcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        etSplashWelcome= findViewById(R.id.etSplashWelcome);
        //start next activity (screen) automatically  after period of time
        Handler h = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                //to open new activity from current to next
                Intent i = new Intent(SplashScreen.this, SignInActivity.class);
                startActivity(i);
                //to close current activity
                finish();
            }
        };
        h.postDelayed(r, 3000);
    }
}
