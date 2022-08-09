package com.example.quartermaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchLogin(View v){
        //launch a new activity

        Intent i = new Intent(this, Login.class);
        startActivity(i);

    }

    public void launchRegister(View v){
        //launch a new activity

        Intent i = new Intent(this, Register.class);
        startActivity(i);

    }
}
