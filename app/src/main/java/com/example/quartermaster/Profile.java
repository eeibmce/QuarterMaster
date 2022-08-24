package com.example.quartermaster;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Profile extends AppCompatActivity {

    Button mProfileLogOutBtn;
    TextView mUserEmail;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mProfileLogOutBtn = findViewById(R.id.ProfileLogoutBtn);
        mUserEmail = findViewById(R.id.Email);
        fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // setting email to email of current user
        assert user != null;
        String email = "Email: " + user.getEmail();

        mUserEmail.setText(email);
        // log out script
        mProfileLogOutBtn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        });


    }

}