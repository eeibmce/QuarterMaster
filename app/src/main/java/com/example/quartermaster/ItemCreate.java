package com.example.quartermaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ItemCreate extends AppCompatActivity {

    Button mCreateBtn;
    EditText mItemType;
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

        mCreateBtn.setOnClickListener(v -> {
            String itemType = mItemType.getText().toString().trim();
            // Checks that item is elected
            if (TextUtils.isEmpty(itemType)) {
                mItemType.setError("ItemType is Required.");
                return;
            }
            // Get email
            assert user != null;
            String email = ((FirebaseUser) user).getEmail();
            //Adding categories to item on firestore
            Map<String, String> item = new HashMap<>();
            item.put("ItemType", itemType);
            item.put("OwnerEmail", email);

            // Add a new document with a generated ID
            db.collection("Items")
                    .add(item)
                    .addOnSuccessListener(documentReference ->
                            Toast.makeText(ItemCreate.this, "Item successfully added", Toast.LENGTH_SHORT).show()
                    )
                    .addOnFailureListener(e ->
                            Toast.makeText(ItemCreate.this, "Item could not be added", Toast.LENGTH_SHORT).show()
                    );
        });
    }
}