package com.example.kareem2023;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Code extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
    }

    @Override//بناء القائمة
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.code_menu, menu);
        return true;
    }

    /**
     * معالجة حدث اختيار عنصر من القائمة(للرحوع الى الصفحة الرئيسية)
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if(item.getItemId()==R.id.itemReturn)
        {
            Toast.makeText(this, "Return", Toast.LENGTH_SHORT).show();
            finish();
        }
        return true;
    }
}