package com.example.quartermaster;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Map;

public class ItemView extends AppCompatActivity {


    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Button mItemViewBtn;
    TextView mUID, mEnterId, mItemInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);
        mItemViewBtn = findViewById(R.id.searchBtn);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mUID = findViewById(R.id.UID);
        mEnterId = findViewById(R.id.ItemIdSearch);
        mItemInfo = findViewById(R.id.ItemInfo);


        mUID.setText("Item ID's");

        fStore.collection("Items").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<QueryDocumentSnapshot> docList = new ArrayList<>();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    docList.add(document);
                    mUID.append("\n");
                    mUID.append(document.getId());

                }
            }
        });

        mItemViewBtn.setOnClickListener(v -> {
            fStore.collection("Items").document(mEnterId.getText().toString()).get().addOnCompleteListener(task -> {
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
                    Toast.makeText(ItemView.this, "Does not exist, please check ID and try again", Toast.LENGTH_SHORT).show();

                }
            });
        });
    }


}