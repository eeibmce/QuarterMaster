package com.example.quartermaster;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class ItemListView extends AppCompatActivity {


    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Button mItemViewBtn, mFilterBtn;
    TextView mUID, mEnterId;
    Spinner itemViewSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_view);
        mItemViewBtn = findViewById(R.id.searchBtn);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        mUID = findViewById(R.id.UID);
        mEnterId = findViewById(R.id.ItemIdSearch);
        itemViewSpinner = findViewById(R.id.FilterItems);
        mFilterBtn = findViewById(R.id.filterBtn);


        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(ItemListView.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ListofItems));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemViewSpinner.setAdapter(myAdapter);

        mFilterBtn.setOnClickListener(v -> {
            String itemType = itemViewSpinner.getSelectedItem().toString();
            // Checks that item is elected
            if (TextUtils.isEmpty(itemType)) {
                Toast.makeText(ItemListView.this, "Must Select ItemType", Toast.LENGTH_SHORT).show();
                return;
            }
            mUID.setText(Html.fromHtml("<b>Item ID's:</b>"));

            fStore.collection("Items").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    ArrayList<QueryDocumentSnapshot> docList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        docList.add(document);

                        fStore.collection("Items").document(document.getId()).get().addOnCompleteListener(tasks -> {
                            if (tasks.isSuccessful()) {
                                DocumentSnapshot document2 = tasks.getResult();
                                if (document2.exists()) {

                                    Map<String, Object> map = document2.getData();
                                    assert map != null;
                                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                                        if (entry.getValue().equals(itemType)) {
                                            mUID.append("\n");
                                            mUID.append("\n -> ");
                                            mUID.append(document.getId());

                                            mUID.append("\n");


                                            mUID.append(map.get("ItemType").toString().toUpperCase(Locale.ROOT));
                                            mUID.append("\n");
                                            mUID.append(map.get("OwnerEmail").toString().toUpperCase(Locale.ROOT));


                                        }

                                    }
                                }
                            } else {
                                Toast.makeText(ItemListView.this, "Does not exist, please check ID and try again", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }
            });

        });
        mItemViewBtn.setOnClickListener(v -> {
            String Uid = mEnterId.getText().toString();
            Intent i = new Intent(getApplicationContext(), ItemView.class);
            i.putExtra("Uid", Uid);
            startActivity(i);
        });
    }


}