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

        mPasswordResetBtn.setOnClickListener(reset -> {
            String email = mEmail.getText().toString();
            fAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        task.getResult();

                        if (task.isSuccessful()) {
                            Toast.makeText(PasswordReset.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), Login.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(PasswordReset.this, "Email failed to send", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}