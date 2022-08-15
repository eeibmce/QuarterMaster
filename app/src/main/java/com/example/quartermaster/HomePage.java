package com.example.quartermaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomePage extends AppCompatActivity {

    LinearLayout qrOptions, profileBtn, itemsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //Check if user is logged in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Toast.makeText(HomePage.this, "You are logged in", Toast.LENGTH_SHORT).show();
            // Name, email address
            String name = user.getDisplayName();
            String email = user.getEmail();
            //Check if email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
            // if not logged in got ot login page
        } else {
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        }

        View mitemsBtn = findViewById(R.id.itemsBtn);
        View mprofileBtn = findViewById(R.id.profileBtn);

        mitemsBtn.setOnClickListener(v -> {

                startActivity(new Intent(getApplicationContext(),ItemCreate.class));

        });

        mprofileBtn.setOnClickListener(v -> {

            startActivity(new Intent(getApplicationContext(),MainActivity.class));

        });




    }
}
//test