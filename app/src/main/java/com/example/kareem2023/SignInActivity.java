
package com.example.kareem2023;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class SignInActivity extends AppCompatActivity {

    private TextInputEditText etSignInEmail;
    private TextInputEditText etSignInPassword;
    private Button btnSignInSignIn;
    private Button btnSignInSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        etSignInEmail=  findViewById(R.id.etSignInEmail);
        etSignInPassword=  findViewById(R.id.etSignInPassword);
        btnSignInSignIn=  findViewById(R.id.btnSignInSignIn);
        btnSignInSignUp= findViewById(R.id.btnSignInSignUp);

    }
    public void onClickSignInSignUp(View V)
    {

        Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(i);
    }

}