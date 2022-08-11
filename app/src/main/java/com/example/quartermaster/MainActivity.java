package com.example.quartermaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Button mLogOutBtn,mToCreateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Check if user is logged in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
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

        // Log out button
        mLogOutBtn = findViewById(R.id.LogoutBtn);
        mLogOutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();//logout
            finish();
        });

        mToCreateBtn = findViewById(R.id.ToCreateBtn);
        mToCreateBtn.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), ItemCreate.class);
            startActivity(i);
        });
        Spinner mySpinner =(Spinner) findViewById(R.id.ItemType);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ListofItems));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
    }
}
