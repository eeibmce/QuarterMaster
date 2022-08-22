package com.example.quartermaster;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QrActivity extends AppCompatActivity {
    EditText etInput;
    Button btGenerate, btScan;
    ImageView ivOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        etInput = findViewById(R.id.et_input);
        btGenerate = findViewById(R.id.bt_generate);
        ivOutput = findViewById(R.id.iv_output);
        btScan = findViewById(R.id.bt_scan);
        String Uid = getIntent().getExtras().getString("Uid").trim();
        etInput.setText(Uid);
        //Generate code
        btGenerate.setOnClickListener(view -> {
            String sText = etInput.getText().toString().trim();
            if (TextUtils.isEmpty(sText)) {
                etInput.setError("Must provide text to be encoded");
                return;
            }
            MultiFormatWriter writer = new MultiFormatWriter();

            try {
                BitMatrix matrix = writer.encode(sText, BarcodeFormat.QR_CODE, 350, 350);
                BarcodeEncoder encoder = new BarcodeEncoder();
                Bitmap bitmap = encoder.createBitmap(matrix);
                ivOutput.setImageBitmap(bitmap);
                InputMethodManager manager = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE
                );
                manager.hideSoftInputFromWindow(etInput.getApplicationWindowToken(), 0);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        });
        // Start scan
        btScan.setOnClickListener(view -> {

            IntentIntegrator intentIntegrator = new IntentIntegrator(

                    QrActivity.this
            );
            intentIntegrator.setPrompt("For Flash Use Volume Up Key");
            intentIntegrator.setBeepEnabled(true);
            intentIntegrator.setOrientationLocked(true);
            intentIntegrator.setCaptureActivity(Capture.class);
            intentIntegrator.initiateScan();
        });
    }

    // Get scan result
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult.getContents() != null) {
            String Uid = intentResult.getContents();
            Intent i = new Intent(getApplicationContext(), ItemView.class);
            i.putExtra("Uid", Uid);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(),
                    "OOPS... You did not scan anything",
                    Toast.LENGTH_SHORT).show();
        }
    }
}

