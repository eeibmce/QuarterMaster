package com.example.quartermaster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ItemCreate extends AppCompatActivity {

    Button mCreateBtn;
    EditText mItemType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_create);

        mCreateBtn = findViewById(R.id.CreateBtn);
        mItemType = findViewById(R.id.ItemType);
        mCreateBtn.setOnClickListener(v -> {

        });
    }
}