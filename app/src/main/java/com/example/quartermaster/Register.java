package com.example.quartermaster;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    EditText mEmail, mPassword, mFullName;
    Button mRegisterBtn;
    TextView mToLogIn;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fAuth = FirebaseAuth.getInstance();
        mFullName = findViewById(R.id.FullName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mRegisterBtn = findViewById(R.id.RegisterBtn);
        mToLogIn = findViewById(R.id.toLogIn);


        mRegisterBtn.setOnClickListener(v -> {
            final String fullName = mFullName.getText().toString().trim();
            final String email = mEmail.getText().toString().trim();
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
                mPassword.setError("Password must be at least 6 Characters");
                return;
            }

            // register the user in firebase

            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {

                    // send verification link
                    FirebaseUser user = fAuth.getCurrentUser();
                    assert user != null;
                    user.sendEmailVerification()
                            .addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    Toast.makeText(Register.this, "Verification Email Sent", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Register.this, "Email Verification sending failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            });
            mPassword.setText("");
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });
        mToLogIn.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Login.class)));
    }
}





