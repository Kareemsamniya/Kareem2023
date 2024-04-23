package com.example.kareem2023;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HaventAlergy extends AppCompatActivity  {
    private Button btnHaventTry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_havent_alergy);
        btnHaventTry = findViewById(R.id.btnHaventTry);

    }
    public void onClickHaventTryAnother(View V)
    {
        Intent i = new Intent(HaventAlergy.this, MainActivityNormal.class);
        startActivity(i);

    }
}