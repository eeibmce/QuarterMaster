package com.example.quartermaster;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ItemEdit extends AppCompatActivity {

    TextView mItemId;
    Spinner mEditItemType, mEditRepairStatus;
    EditText mEditOwnerEmail, mEditExtraInfo;
    Button mEditBtn, mDeleteBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_edit);

        //Declarations
        fAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        mItemId = findViewById(R.id.ItemID);
        mEditItemType = findViewById(R.id.EditItemType);
        mEditOwnerEmail = findViewById(R.id.EditOwnerEmail);
        mEditExtraInfo = findViewById(R.id.EditExtraInfo);
        mEditRepairStatus = findViewById(R.id.EditRepairStatus);
        mEditBtn = findViewById(R.id.EditItemBtn);
        mDeleteBtn = findViewById(R.id.DeleteItemBtn);

        String Uid = getIntent().getExtras().getString("Uid").trim();

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(ItemEdit.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ListofItems));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEditItemType.setAdapter(myAdapter);

        ArrayAdapter<String> repairAdapter = new ArrayAdapter<>(ItemEdit.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.RepairStatusList));
        repairAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEditRepairStatus.setAdapter(repairAdapter);

        //Pull current doc
        DocumentReference docRef = db.collection("Items").document(Uid);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Set edit fields as currently saved
                    String ItemType = document.getString("ItemType");
                    String OwnerEmail = document.getString("OwnerEmail");
                    String ExtraInfo = document.getString("ExtraInfo");

                    mItemId.setText(Uid);
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                            R.array.ListofItems, android.R.layout.simple_list_item_1);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mEditItemType.setAdapter(adapter);
                    if (ItemType != null) {
                        int spinnerPosition = adapter.getPosition(ItemType);
                        mEditItemType.setSelection(spinnerPosition);
                        mEditOwnerEmail.setText(OwnerEmail);
                        mEditExtraInfo.setText(ExtraInfo);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Document Not Found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "get failed with " + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });


        mEditBtn.setOnClickListener(v -> {
            //Get updated values
            String itemType = mEditItemType.getSelectedItem().toString();
            String email = mEditOwnerEmail.getText().toString().trim();
            String extraInfo = mEditExtraInfo.getText().toString().trim();
            String repairStatus = mEditRepairStatus.getSelectedItem().toString();
            // ensure email is there and in valid format
            if (TextUtils.isEmpty(email)) {
                mEditOwnerEmail.setError("Email is Required.");
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                mEditOwnerEmail.setError("Invalid Email Address");
                return;
            }


            //Adding categories to item on firestore
            Map<String, Object> item = new HashMap<>();
            item.put("ItemType", itemType);
            item.put("OwnerEmail", email);
            item.put("ExtraInfo", extraInfo);
            item.put("RepairStatus", repairStatus);
            // Edit Document
            db.collection("Items").document(Uid)
                    .set(item)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(ItemEdit.this, "Item successfully Updated", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), HomePage.class);
                        startActivity(i);
                    })
                    .addOnFailureListener(e -> Toast.makeText(ItemEdit.this, "Item could not be added", Toast.LENGTH_SHORT).show());
        });
        // item deletion
        mDeleteBtn.setOnClickListener(v -> db.collection("Items").document(Uid)
                .delete()
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(ItemEdit.this, "Item successfully Deleted", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), HomePage.class);
                    startActivity(i);
                })
                .addOnFailureListener(e -> Toast.makeText(ItemEdit.this, "Error Deleting Item", Toast.LENGTH_SHORT).show()));


    }
}

