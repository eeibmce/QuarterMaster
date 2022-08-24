package com.example.quartermaster;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.File;
import java.io.FileOutputStream;

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
    Button btGenerate, btScan, btSave;
    ImageView ivOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        etInput = findViewById(R.id.et_input);
        btGenerate = findViewById(R.id.bt_generate);
        ivOutput = findViewById(R.id.iv_output);
        btScan = findViewById(R.id.bt_scan);
        btSave = findViewById(R.id.bt_save);
        btSave.setVisibility(View.INVISIBLE);


        String Uid = getIntent().getExtras().getString("Uid").trim();
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        etInput.setText(Uid);
        //Generate qrcode
        btGenerate.setOnClickListener(view -> {
            // get text to be encoded
            String sText = etInput.getText().toString().trim();
            if (TextUtils.isEmpty(sText)) {
                etInput.setError("Must provide text to be encoded," +
                        "Item Ids to encode can be found on the devices page");
                return;
            }
            // try create qr code
            try {
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.encodeBitmap(sText, BarcodeFormat.QR_CODE, 400, 400);
                ImageView imageViewQrCode = ivOutput;
                imageViewQrCode.setImageBitmap(bitmap);
            } catch (Exception e) {
                Toast.makeText(QrActivity.this, "Generation failed", Toast.LENGTH_LONG).show();
            }
            // enable saving qr code
            btSave.setVisibility(View.VISIBLE);
        });
        // Launch qr scanner
        btScan.setOnClickListener(view -> {
            ScanOptions options = new ScanOptions();
            options.setBeepEnabled(true);
            barcodeLauncher.launch(options);
        });
        // saving qr to jpeg
        btSave.setOnClickListener(view -> {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            String sText = etInput.getText().toString().trim();
            String fname = "Qr-" + sText + ".jpg";
            File file = new File(path, fname);
            path.mkdirs();
            // turn ivOutput to bitmap
            Bitmap bmap = Bitmap.createBitmap(ivOutput.getWidth(), ivOutput.getHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bmap);
            ivOutput.draw(canvas);
            // delete qr file if it already exists
            if (file.exists())
                file.delete();
            try {
                // create and save jpeg
                FileOutputStream out = new FileOutputStream(file);
                bmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
                Toast.makeText(this, "QrCode Saved to Pictures", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


}

