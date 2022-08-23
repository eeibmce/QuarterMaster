package com.example.quartermaster;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
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
import java.util.Random;

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
        //Generate code
        btGenerate.setOnClickListener(view -> {
            String sText = etInput.getText().toString().trim();
            if (TextUtils.isEmpty(sText)) {
                etInput.setError("Must provide text to be encoded," +
                        "Item Ids to encode can be found on the devices page");
                return;
            }
            try {
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.encodeBitmap(sText, BarcodeFormat.QR_CODE, 400, 400);
                ImageView imageViewQrCode = ivOutput;
                imageViewQrCode.setImageBitmap(bitmap);
            } catch (Exception e) {
                Toast.makeText(QrActivity.this, "Generation failed", Toast.LENGTH_LONG).show();
            }
            btSave.setVisibility(View.VISIBLE);
        });
        // Launch
        btScan.setOnClickListener(view -> {
            ScanOptions options = new ScanOptions();
            options.setBeepEnabled(true);
            barcodeLauncher.launch(options);
        });

        btSave.setOnClickListener(view -> {

            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/QrCodes/");
            myDir.mkdirs();
            Random generator = new Random();
            int n = 10000;
            n = generator.nextInt(n);
            String fname = "Image-" + n + ".jpg";
            File file = new File(myDir, fname);
            Bitmap bmap = Bitmap.createBitmap(ivOutput.getWidth(), ivOutput.getHeight(), Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bmap);
            ivOutput.draw(canvas);
//                ivOutput.setDrawingCacheEnabled(true);
//                ivOutput.buildDrawingCache();
//                Bitmap bmap = ivOutput.getDrawingCache();
            if (file.exists())
                file.delete();
            try {
                FileOutputStream out = new FileOutputStream(file);
                bmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
            }
//            try {
//                String path = Environment.getExternalStorageDirectory().toString();
//                OutputStream fOutputStream = null;
//                File QrCode = new File(path + "/QrCodes/", "QrCode.jpg");
//                if (!QrCode.exists()) {
//                    QrCode.mkdirs();
//                }
//                ivOutput.setDrawingCacheEnabled(true);
//                ivOutput.buildDrawingCache();
//                Bitmap bmap = ivOutput.getDrawingCache();
//                fOutputStream = new FileOutputStream(QrCode);
//
//                bmap.compress(Bitmap.CompressFormat.JPEG, 100, fOutputStream);
//
//                fOutputStream.flush();
//                fOutputStream.close();
//
//                MediaStore.Images.Media.insertImage(getContentResolver(), QrCode.getAbsolutePath(), QrCode.getName(), QrCode.getName());
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
//                return;
//            } catch (IOException e) {
//                e.printStackTrace();
//                Toast.makeText(this, "Save Failed", Toast.LENGTH_SHORT).show();
//                return;
//            }
        });
    }


}

