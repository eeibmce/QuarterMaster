package com.example.quartermaster;


import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ItemListView extends AppCompatActivity {


    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Button mItemViewBtn, mFilterBtn, mItemCreate;
    TextView mUID, mEnterId; //mSearchBar;
    Spinner mSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list_view);
        mItemViewBtn = findViewById(R.id.searchBtn);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mUID = findViewById(R.id.UID);
        mEnterId = findViewById(R.id.ItemIdSearch);
        mSearchBar = findViewById(R.id.SearchBar);
        mFilterBtn = findViewById(R.id.filterBtn);
        mItemCreate = findViewById(R.id.ItemCreate);
        mUID.setMovementMethod(new ScrollingMovementMethod());
        mUID. setTextIsSelectable(true);


        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(ItemListView.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ListofItems));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSearchBar.setAdapter(myAdapter);


        mFilterBtn.setOnClickListener(v -> {
            String search = mSearchBar.getSelectedItem().toString().trim();
            mUID.setText("");

            db.collection("Items")
                    .whereEqualTo("ItemType", search)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                mUID.setText("");
                                mUID.append("ItemId: " + document.getId());
                                mUID.append("\n");
                                mUID.append("ItemType: " + document.getString("ItemType"));
                                mUID.append("\n");
                                mUID.append("OwnerEmail: " + document.getString("OwnerEmail"));
                                mUID.append("\n");
                                mUID.append("ExtraInfo: " + document.getString("ExtraInfo"));
                                mUID.append("\n");
                            }
                        } else {
                            System.out.println("Error getting documents: " + task.getException());
                        }
                    });

        });


        mItemViewBtn.setOnClickListener(v -> {
            String Uid = mEnterId.getText().toString();
            if (TextUtils.isEmpty(Uid)) {
                mEnterId.setError("Id is Required");
                return;
            }
            Intent i = new Intent(getApplicationContext(), ItemView.class);
            i.putExtra("Uid", Uid);
            startActivity(i);
        });
        mItemCreate.setOnClickListener(view -> {
            finish();
            Intent i = new Intent(getApplicationContext(), ItemCreate.class);
            startActivity(i);
        });

        mUID.setOnClickListener(new View.OnClickListener() { // set onclick listener to my textview
            @Override
            public void onClick(View view) {
                ClipboardManager cm = (ClipboardManager)getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(mUID.getText().toString());
                Toast.makeText(getApplicationContext(), "Copied :)", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
