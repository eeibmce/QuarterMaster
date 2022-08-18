package com.example.quartermaster;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class QrActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
    }

    public class ItemView extends AppCompatActivity {

        FirebaseAuth fAuth;
        FirebaseFirestore fStore;
        TextView mItemInfo;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_item_view);
            fAuth = FirebaseAuth.getInstance();
            fStore = FirebaseFirestore.getInstance();
            mItemInfo = findViewById(R.id.ItemInfo);
            String sText = getIntent().getStringExtra("sText");

            // Need to be fed string from scanned qr code
            fStore.collection("Items").document(sText).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {

                        Map<String, Object> map = document.getData();
                        mItemInfo.setText("");
                        assert map != null;
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            mItemInfo.append("\n");
                            mItemInfo.append(entry.getValue().toString());
                        }
                    }
                } else {
                    Toast.makeText(com.example.quartermaster.QrActivity.this, "Does not exist, please check ID and try again", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}



