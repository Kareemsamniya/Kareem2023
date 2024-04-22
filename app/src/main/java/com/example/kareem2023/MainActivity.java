package com.example.kareem2023;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

/**
 * الشاشة الرئيسية
 */
public class MainActivity extends AppCompatActivity {
    private Button btnMainCkScan;
    private Button btnMainCkCode;

    private FloatingActionButton fabMainAdd;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        btnMainCkCode = findViewById(R.id.btnMainCkCode);
        btnMainCkScan = findViewById(R.id.btnMainChkScan);
        fabMainAdd = findViewById(R.id.fabMainAdd);
        fabMainAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddProduct.class);
                startActivity(i);

            }
        });


    }
        public void onClickCheckWithCode(View V)
        {
            
            Intent i = new Intent(MainActivity.this, Code.class);
            startActivity(i);

        }
        public void onClickCheckWithScan(View V)
        {

            Intent i = new Intent(MainActivity.this, BarcodeScannerActivity.class);
            startActivity(i);

        }

    @Override//بناء القائمة
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override//معالجة حدث اختيار عنصر من القائمة
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if(item.getItemId()==R.id.itemSignOut)
        {
            Toast.makeText(this, "Log Out", Toast.LENGTH_SHORT).show();
            showYesNoDialog();
        }
        return true;
    }
    /**
     * تجهيو ديالوج
     */
    public void showYesNoDialog()
    {
        //تجهيز بناء شباك حوار "ديالوج" يتلقى بارامتر مؤشر للنشاط (اكتيفيتي) الحالي
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Log out");//تحديد العنوان
        builder.setMessage("Are you sure?");//تحديد فحوى شباك الحوار

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                //
                Toast.makeText(MainActivity.this, "Signing out", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                Toast.makeText(MainActivity.this, "Signing out", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();//بناء شباك الحوار(ديالوج)
        dialog.show();//عرض الشباك
    }


    }

