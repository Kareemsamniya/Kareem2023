package com.example.kareem2023;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabMainAdd;
    private SearchView srshMainV;
    private ListView lstMainVTask;

    //spnr1
    private Spinner spnrMainSubject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        fabMainAdd = findViewById(R.id.fabMainAdd);
        srshMainV = findViewById(R.id.srshMainV);
        lstMainVTask = findViewById(R.id.lstMainVTask);
        spnrMainSubject=findViewById(R.id.spnrMainSubject);
        fabMainAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(i);

            }
        });
    }
}