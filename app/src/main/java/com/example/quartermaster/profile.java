package com.example.quartermaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import  com.google.firebase.auth.FirebaseUser;

public class profile extends AppCompatActivity {

    Button profileLogOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        View profileLogOutBtn = findViewById(R.id.ProfileLogoutBtn);

        profileLogOutBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }

        });

        }

}