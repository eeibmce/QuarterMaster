package com.example.quartermaster;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashPage extends AppCompatActivity {
    TextView wel,learning;
    private static int Splash_timeout=5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);
        wel=findViewById(R.id.appName);
        learning=findViewById(R.id.appName);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent splashintent=new Intent(SplashPage.this,HomePage.class);
                startActivity(splashintent);
                finish();
            }
        },Splash_timeout);
        Animation myanimation= AnimationUtils.loadAnimation(SplashPage.this,R.anim.animation2);
        wel.startAnimation(myanimation);

        Animation myanimation2= AnimationUtils.loadAnimation(SplashPage.this,R.anim.animation1);
        learning.startAnimation(myanimation2);
    }
}