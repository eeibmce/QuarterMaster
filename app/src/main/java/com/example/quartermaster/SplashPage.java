package com.example.quartermaster;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashPage extends AppCompatActivity {
    private static final int Splash_timeout = 5000;
    TextView anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);
        anim = findViewById(R.id.appName);
        // start animations
        new Handler().postDelayed(() -> {
            Intent splashintent = new Intent(SplashPage.this, HomePage.class);
            startActivity(splashintent);
            finish();
        }, Splash_timeout);
        // text animation
        Animation myanimation = AnimationUtils.loadAnimation(SplashPage.this, R.anim.animation2);
        anim.startAnimation(myanimation);
        // Box animation
        Animation myanimation2 = AnimationUtils.loadAnimation(SplashPage.this, R.anim.animation1);
        anim.startAnimation(myanimation2);
    }
}