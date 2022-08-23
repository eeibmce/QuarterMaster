package com.example.quartermaster;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ItemView extends AppCompatActivity {


    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TextView mItemInfo;
    Button mtoEditItemBtn, mtoQrActivityBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mItemInfo = findViewById(R.id.ItemInfo);
        mtoEditItemBtn = findViewById(R.id.toEditItemBtn);
        mtoQrActivityBtn = findViewById(R.id.toQrActivityBtn);

        String Uid = getIntent().getExtras().getString("Uid").trim();

        // Need to be fed string from scanned qr code or searched id
        fStore.collection("Items").document(Uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    mItemInfo.setText("");
                    mItemInfo.append("ItemId: " + document.getId());
                    mItemInfo.append("\n");
                    mItemInfo.append("ItemType: " + document.getString("ItemType"));
                    mItemInfo.append("\n");
                    mItemInfo.append("OwnerEmail: " + document.getString("OwnerEmail"));
                    mItemInfo.append("\n");
                    mItemInfo.append("ExtraInfo: " + document.getString("ExtraInfo"));
                    mItemInfo.append("\n");
                }
            } else {
                Toast.makeText(ItemView.this, "Does not exist, please check ID and try again", Toast.LENGTH_SHORT).show();
            }
        });
        mtoEditItemBtn.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), ItemEdit.class);
            i.putExtra("Uid", Uid);
            startActivity(i);
        });
        mtoQrActivityBtn.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), QrActivity.class);
            i.putExtra("Uid", Uid);
            startActivity(i);
            finish();
        });
    }
}



