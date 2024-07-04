package com.example.kareem2023;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import android.content.Intent;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class Scannnner extends AppCompatActivity {


    private Button sacn_qr;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scannnner);

        scan_qr = findViewById(R.id.scanqr);
        textView = findViewById(R.id.text);

        sacn_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(Scannnner.this);
                intentIntegrator.setPrompt("scan any QR/Bar code");
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.initiateScan();

            }
        });


    }

        @Override
        protected void onActivityResult(int requestCode,int resultCode, @Nullable Intent data)
        {
            IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
            if(intentResult.getContents()!=null)
            {
                if(intentResult.getContents()!=null)
                {
                    textView.setText(intentResult.getContents());
                }
                else
                {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                super.onActivityResult(requestCode, resultCode, data);
            }


        }

}