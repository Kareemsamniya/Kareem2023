
package com.example.kareem2023;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class SignInActivity extends AppCompatActivity {

    private TextInputEditText etSignInEmail;
    private TextInputEditText etSignInPassword;
    private Button btnSignInSignIn;
    private Button btnSignInSignUp;
    private Button btnSignInSave;
    private Button btnSignInCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        etSignInEmail=  findViewById(R.id.etSignInEmail);
        etSignInPassword=  findViewById(R.id.etSignInPassword);
        btnSignInSignIn=  findViewById(R.id.btnSignInSignIn);
        btnSignInSignUp= findViewById(R.id.btnSignInSignUp);
        btnSignInSave= findViewById(R.id.btnSignInSave);
        btnSignInCancel= findViewById(R.id.btnSignInCancel);
    }
}