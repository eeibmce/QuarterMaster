package com.example.quartermaster;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(SplashPage.this, HomePage.class));
        finish();
    }
}


//    private static int SPLASH_SCREEN_TIMEOUT = 2000;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        setContentView(R.layout.activity_splash_page);
//
//        Animation fadeOut = new AlphaAnimation(1, 0);
//        fadeOut.setInterpolator(new AccelerateInterpolator());
//        fadeOut.setStartOffset(500);
//        fadeOut.setDuration(1800);
//        ImageView image = findViewById(R.id.imageView8);
//        ImageView image1 = findViewById(R.id.imageView10);
//
//        image.setAnimation(fadeOut);
