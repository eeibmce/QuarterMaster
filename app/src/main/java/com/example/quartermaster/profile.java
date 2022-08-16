package com.example.quartermaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class profile extends AppCompatActivity {

    Button mProfileLogOutBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mProfileLogOutBtn = findViewById(R.id.ProfileLogoutBtn);

        mProfileLogOutBtn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        });

        View mBackBtn = findViewById(R.id.backBtn);

        mBackBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), HomePage.class)));

    }

}