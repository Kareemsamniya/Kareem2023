package com.example.kareem2023;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NotFound extends AppCompatActivity {
    private Button btnNotFoundTry;
    private Button btnAddProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_found);
        btnNotFoundTry = findViewById(R.id.btnNotFoundTry);
    }

    public void onClickNotFoundTryAnother(View V)
    {
        Intent i = new Intent(NotFound.this, MainActivity.class);
        startActivity(i);

    }

}