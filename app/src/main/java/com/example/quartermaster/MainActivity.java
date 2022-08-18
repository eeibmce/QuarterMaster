package com.example.quartermaster;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class MainActivity extends AppCompatActivity {

    Button btScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btScan = findViewById(R.id.scanner);
        btScan.setOnClickListener(view -> {
            IntentIntegrator intentIntegrator = new IntentIntegrator(
                    MainActivity.this
            );
            intentIntegrator.setPrompt("For Flash Use Volume Up Key");
            intentIntegrator.setBeepEnabled(true);
            intentIntegrator.setOrientationLocked(true);
            intentIntegrator.setCaptureActivity(Capture.class);
            intentIntegrator.initiateScan();

        });
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(
                requestCode, resultCode, data
        );
        if (intentResult.getContents() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("Result");
            builder.setMessage(intentResult.getContents());
            builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss());
            builder.show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "OOPS... You did not scan anything",
                    Toast.LENGTH_SHORT).show();
        }
    }
}

