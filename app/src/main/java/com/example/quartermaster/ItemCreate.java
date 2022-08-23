package com.example.quartermaster;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ItemCreate extends AppCompatActivity {

    Button mCreateBtn;
    Spinner mItemType;
    EditText mQuantity, mExtraInfo;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_create);

        //Declarations
        fAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mCreateBtn = findViewById(R.id.CreateBtn);
        mItemType = findViewById(R.id.ItemType);
        mQuantity = findViewById(R.id.Quantity);
        mExtraInfo = findViewById(R.id.ExtraInfo);


        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(ItemCreate.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ListofItems));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mItemType.setAdapter(myAdapter);

        mCreateBtn.setOnClickListener(v -> {
            String itemType = mItemType.getSelectedItem().toString();
            // Checks that item is elected
            if (TextUtils.isEmpty(itemType)) {
                Toast.makeText(ItemCreate.this, "Must Select ItemType", Toast.LENGTH_SHORT).show();
                return;
            }
            // Extra info
            String extraInfo = mExtraInfo.getText().toString().trim();
            String repairStatus = "Working";
            // Get email
            assert user != null;
            String email = user.getEmail();
            //Adding categories to item on firestore
            Map<String, Object> item = new HashMap<>();
            item.put("ItemType", itemType);
            item.put("OwnerEmail", email);
            item.put("ExtraInfo", extraInfo);
            item.put("RepairStatus", repairStatus);
            String stquantity = mQuantity.getText().toString();
            int quantity = Integer.parseInt(stquantity);
            if (quantity > 100) {
                mQuantity.setError("Only 100 or less items may be added at a time");
            }
            for (int n = quantity; n > 0; n--) {
                // Add a new document with a generated ID
                db.collection("Items")
                        .add(item)
                        .addOnSuccessListener(documentReference -> {
                            Toast.makeText(ItemCreate.this, "Item successfully added", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), ItemListView.class);
                            startActivity(i);
                        })
                        .addOnFailureListener(e -> Toast.makeText(ItemCreate.this, "Item could not be added", Toast.LENGTH_SHORT).show());
            }
        });
    }
}