package com.example.kareem2023;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {

    private TextInputEditText etSignUpEmail;
    private TextInputEditText etSignUpPassword;
    private TextInputEditText etSignUpRepassword;
    private TextInputEditText etSignUpName;
    private TextInputEditText etSignUpPhone;
    private Button btnSignUpSave;
    private Button btnSignUpCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etSignUpEmail=  findViewById(R.id.etSignUpEmail);
        etSignUpPassword=  findViewById(R.id.etSignUpPassword);
        etSignUpRepassword=  findViewById(R.id.etSignUpRepassword);
        etSignUpName= findViewById(R.id.etSignUpName);
        etSignUpPhone=  findViewById(R.id.etSignUpPhone);
        btnSignUpSave= findViewById(R.id.btnSignUpSave);
        btnSignUpCancel= findViewById(R.id.btnSignUpCancel);


    }
    public void onClickSignUpCancel(View V)
    {

      finish();
   }
}