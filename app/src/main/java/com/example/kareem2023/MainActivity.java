package com.example.kareem2023;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private Button btnMainCkScan;
    private Button btnMainCkCode;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        btnMainCkCode = findViewById(R.id.btnMainCkCode);
        btnMainCkScan = findViewById(R.id.btnMainChkScan);
    }
        public void onClickCheckWithCode(View V)
        {

            Intent i = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(i);

        }
        public void onClickCheckWithScan(View V)
        {

            Intent i = new Intent(MainActivity.this, BarcodeScannerActivity.class);
            startActivity(i);

        }



    }

