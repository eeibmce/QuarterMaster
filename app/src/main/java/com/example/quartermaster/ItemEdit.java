package com.example.quartermaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ItemEdit extends AppCompatActivity {

    TextView mItemId;
    Spinner mEditItemType;
    EditText mEditOwnerEmail, mEditExtraInfo;
    Button mEditBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_edit);

        //Declarations
        fAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mItemId = findViewById(R.id.ItemID);
        mEditItemType = findViewById(R.id.EditItemType);
        mEditOwnerEmail = findViewById(R.id.EditOwnerEmail);
        mEditExtraInfo = findViewById(R.id.EditExtraInfo);
        mEditBtn = findViewById(R.id.EditItemBtn);

        String Uid = getIntent().getExtras().getString("Uid").trim();

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(ItemEdit.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ListofItems));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEditItemType.setAdapter(myAdapter);

        //Pull current doc
        DocumentReference docRef = db.collection("Items").document(Uid);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {

                } else {
                    Toast.makeText(getApplicationContext(), "Document Not Found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "get failed with " + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });

        // Set edit fields as currently saved
        mItemId.setText(Uid);
        String compareValue = docRef.get("ItemType", itemType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.ListofItems, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEditItemType.setAdapter(adapter);
        if (compareValue != null) {
            int spinnerPosition = adapter.getPosition(compareValue);
            mEditItemType.setSelection(spinnerPosition);
        }
        mEditBtn.setOnClickListener(v -> {
        });

        // Extra info
        String extraInfo = mEditExtraInfo.getText().toString().trim();

        // Get email
        String email = mEditOwnerEmail.getText().toString().trim();
        //Adding categories to item on firestore
        Map<String, Object> item = new HashMap<>();
        item.put("ItemType", mEditItemType);
        item.put("OwnerEmail", email);
        item.put("ExtraInfo", extraInfo);
        // Edit Document
        db.collection("Items").document(Uid)
                .set(item)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(ItemEdit.this, "Item successfully Updated", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), HomePage.class);
                    startActivity(i);
                })
                .addOnFailureListener(e -> Toast.makeText(ItemEdit.this, "Item could not be added", Toast.LENGTH_SHORT).show());
    }
}

