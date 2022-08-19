package com.example.quartermaster;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashPage extends AppCompatActivity {

    //    Variables
//    Animation topAnim, bottomAnim;
//    ImageView imageView3;
//    TextView textView, textView2;
    private static final int SPLASH_SCREEN_TIMEOUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_page);
//        //Animations
//        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
//        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
//
//        //Hooks
//        imageView3 = findViewById(R.id.imageView3);
//        textView = findViewById(R.id.textView);
//        textView2 = findViewById(R.id.textView2);
//
//        imageView3.setAnimation(topAnim);
//        textView.setAnimation(bottomAnim);
//        textView2.setAnimation(bottomAnim);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_page);
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(500);
        fadeOut.setDuration(1800);
        ImageView image = findViewById(R.id.imageView8);
        image.setAnimation(fadeOut);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(SplashPage.this, HomePage.class);
                startActivity(intent);
                finish();
            }

        }, SPLASH_SCREEN_TIMEOUT);
    }
}