package com.example.quartermaster;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class PasswordReset extends AppCompatActivity {

    EditText mEmail;
    Button mPasswordResetBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        mEmail = findViewById(R.id.Email);
        mPasswordResetBtn = findViewById(R.id.PasswordReset);
        fAuth = FirebaseAuth.getInstance();

        mPasswordResetBtn.setOnClickListener(task1 -> {
            String email = mEmail.getText().toString();
            fAuth.sendPasswordResetEmail(email).addOnCompleteListener(task2 -> {
                //task2.getResult();

                if (task2.isSuccessful()) {
                    Toast.makeText(PasswordReset.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), Login.class);
                    startActivity(i);
                } else {
                    Toast.makeText(PasswordReset.this, "Email failed to send, likely used", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}