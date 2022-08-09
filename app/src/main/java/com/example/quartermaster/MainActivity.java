package com.example.quartermaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button mLogOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLogOutBtn = findViewById(R.id.LogoutBtn);
        mLogOutBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();//logout
            finish();
        });
    }


}
