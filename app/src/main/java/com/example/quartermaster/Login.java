package com.example.quartermaster;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login extends AppCompatActivity {
    EditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mToRegister, mToResetPassword;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mLoginBtn = findViewById(R.id.LoginBtn);
        mToRegister = findViewById(R.id.toRegister);
        mToResetPassword = findViewById((R.id.toPasswordReset));


        mLoginBtn.setOnClickListener(v -> {

            String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                mEmail.setError("Email is Required.");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                mPassword.setError("Password is Required.");
                return;
            }

            if (password.length() < 6) {
                mPassword.setError("Password Must be >= 6 Characters");
                return;
            }


            // authenticate the user

            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    mPassword.setText("");
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Toast.makeText(Login.this, "Error ! " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }

            });

        });

        mToRegister.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Register.class)));

        mToResetPassword.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), PasswordReset.class)));

    }
}