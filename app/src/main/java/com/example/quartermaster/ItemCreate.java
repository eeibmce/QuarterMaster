package com.example.quartermaster;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ItemCreate extends AppCompatActivity {

    Button mCreateBtn, mViewBtn;
    Spinner mItemType;
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
        mViewBtn = findViewById(R.id.viewBtn);


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


            // Get email
            assert user != null;
            String email = user.getEmail();
            //Adding categories to item on firestore
            Map<String, Object> item = new HashMap<>();
            item.put("ItemType", itemType);
            item.put("OwnerEmail", email);

            // Add a new document with a generated ID
            db.collection("Items")
                    .add(item)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(ItemCreate.this, "Item successfully added", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), HomePage.class);
                        startActivity(i);
                    })
                    .addOnFailureListener(e -> Toast.makeText(ItemCreate.this, "Item could not be added", Toast.LENGTH_SHORT).show());
        });
        mViewBtn.setOnClickListener(v -> {

            startActivity(new Intent(getApplicationContext(), ItemView.class));

        });
    }
}