package com.example.quartermaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button mLogOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            //Check Email
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

        } else {
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        }


        mLogOutBtn = findViewById(R.id.LogoutBtn);
        mLogOutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();//logout
            finish();
        });

    }
}
