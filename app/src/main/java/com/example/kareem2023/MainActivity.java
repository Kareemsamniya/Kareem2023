package com.example.kareem2023;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.kareem2023.data.Alergy.MyAlergy;
import com.example.kareem2023.data.productTable.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private Button btnMainCkScan;
    private Button btnMainCkCode;
    private ArrayList<Product> arrProducts;
    private ArrayList<MyAlergy> AlergyTypes1;
    private MyAlergy BambaAlergieses = new MyAlergy() ;
    private ArrayList<MyAlergy> AlergyTypes2;
    private MyAlergy BisleyAlergieses = new MyAlergy() ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        btnMainCkCode = findViewById(R.id.btnMainCkCode);
        btnMainCkScan = findViewById(R.id.btnMainChkScan);

        ArrayList <String> arrAlergiesesBamba = new ArrayList<>();
        arrAlergiesesBamba.add("milk");
        arrAlergiesesBamba.add("butter");
        BambaAlergieses.setAlergyName(arrAlergiesesBamba);
        AlergyTypes1=new ArrayList<>();
        AlergyTypes1.set(0,BambaAlergieses);
        arrProducts = new ArrayList<>();
        arrProducts.add(0,new Product("87820","Bamba","Osem",AlergyTypes1));

        ArrayList <String> arrAlergiesesBisley = new ArrayList<>();
        arrAlergiesesBisley.add("milk");
        arrAlergiesesBisley.add("butter");
        BisleyAlergieses.setAlergyName(arrAlergiesesBamba);
        AlergyTypes2=new ArrayList<>();
        AlergyTypes2.set(0,BisleyAlergieses);
        arrProducts.add(1,new Product("45960","Bisley","Osem",AlergyTypes2));
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

