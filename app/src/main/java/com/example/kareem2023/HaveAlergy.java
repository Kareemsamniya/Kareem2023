package com.example.kareem2023;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HaveAlergy extends AppCompatActivity
{
    private Button btnHaveAlergy;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_have_alergy);
        btnHaveAlergy= findViewById(R.id.btnHaveTry);
    }

    /**
     * اذا لم يكن موجود المنتج يقوم بالضعط على "TRY ANOTHER PRODUCT" وتعود الى القائمة الرئيسية
     * @param V
     */
    public void onClickHaveTryAnother(View V)
    {
        Intent i = new Intent(HaveAlergy.this, MainActivity.class);
        startActivity(i);

    }
}