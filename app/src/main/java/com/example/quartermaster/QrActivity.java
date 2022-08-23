package com.example.quartermaster;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class QrActivity extends AppCompatActivity {
    // Register the launcher and result handler
    public ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() == null) {
            Toast.makeText(QrActivity.this, "Scan Failed", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(QrActivity.this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            String Uid = result.getContents();
            Intent i = new Intent(getApplicationContext(), ItemView.class);
            i.putExtra("Uid", Uid);
            startActivity(i);
        }
    });
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
                etInput.setError("Must provide text to be encoded," +
                        "Item Ids to encode can be found on the devices page");
                return;
            }
//            MultiFormatWriter writer = new MultiFormatWriter();

//            try {
//                BitMatrix matrix = writer.encode(sText, BarcodeFormat.QR_CODE, 350, 350);
//                BarcodeEncoder encoder = new BarcodeEncoder();
//                Bitmap bitmap = encoder.createBitmap(matrix);
//                ivOutput.setImageBitmap(bitmap);
//                InputMethodManager manager = (InputMethodManager) getSystemService(
//                        Context.INPUT_METHOD_SERVICE
//                );
//                manager.hideSoftInputFromWindow(etInput.getApplicationWindowToken(), 0);
//            } catch (WriterException e) {
//                e.printStackTrace();
//            }
            try {
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.encodeBitmap(sText, BarcodeFormat.QR_CODE, 400, 400);
                ImageView imageViewQrCode = ivOutput;
                imageViewQrCode.setImageBitmap(bitmap);
            } catch (Exception e) {
                Toast.makeText(QrActivity.this, "Generation failed", Toast.LENGTH_LONG).show();
            }
        });
        // Launch
        btScan.setOnClickListener(view -> {
            ScanOptions options = new ScanOptions();
            options.setBeepEnabled(true);
            barcodeLauncher.launch(options);
        });
        // Start scan
//        btScan.setOnClickListener(view -> {
//
//            IntentIntegrator intentIntegrator = new IntentIntegrator(
//
//                    QrActivity.this
//            );
//            intentIntegrator.setPrompt("For Flash Use Volume Up Key");
//            intentIntegrator.setBeepEnabled(true);
//            intentIntegrator.setOrientationLocked(true);
//            intentIntegrator.setCaptureActivity(Capture.class);
//            intentIntegrator.initiateScan();
//        });
//    }

//        // Get scan result
//        @Override
//        protected void onActivityResult ( int requestCode,
//        int resultCode,
//        @Nullable Intent data){
//            super.onActivityResult(requestCode, resultCode, data);
//            IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
//            if (intentResult.getContents() != null) {
//                String Uid = intentResult.getContents();
//                Intent i = new Intent(getApplicationContext(), ItemView.class);
//                i.putExtra("Uid", Uid);
//                startActivity(i);
//            } else {
//                Toast.makeText(getApplicationContext(),
//                        "OOPS... You did not scan anything",
//                        Toast.LENGTH_SHORT).show();
//            }
//        }
    }
}

